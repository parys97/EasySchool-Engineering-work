<template>
    <v-layout column>
        <v-flex>
            <v-sheet height="500">
                <v-calendar
                        ref="calendar"
                        v-model="focus"
                        :now="today"
                        :value="today"
                        type="month"
                        color="primary"
                >
                    <template v-slot:day="{ date }">
                        <template v-for="event in eventsMap[date]">
                            <v-menu
                                    :key="event.id"
                                    v-model="event.open"
                                    full-width
                                    offset-x
                            >
                                <template v-slot:activator="{ on }">
                                    <v-card
                                            v-if="!event.time"
                                            v-ripple
                                            :color="getColorTask(event)"
                                            class="my-event"
                                            v-on="on"
                                    >
                                        {{ event.nameTask }}

                                    </v-card>

                                </template>

                                <day-details-card :task="event"></day-details-card>
                            </v-menu>
                        </template>
                    </template>
                </v-calendar>
            </v-sheet>
        </v-flex>
        <v-layout justify-center>
            <v-btn @click="prev">
                <v-icon
                        dark
                        left
                >
                    keyboard_arrow_left
                </v-icon>
                Prev
            </v-btn>
            <v-btn @click="next">
                Next
                <v-icon
                        right
                        dark
                >
                    keyboard_arrow_right
                </v-icon>
            </v-btn>
        </v-layout>
    </v-layout>
</template>

<script>
    import axios from "axios"
    import DayDetailsCard from 'components/calendar/DayDetailsCard.vue'
    import {mapState} from 'vuex'

    export default {

        name: "CalendarForStudent",
        components: { DayDetailsCard },

        data() {
            return {
                today: new Date().toISOString().substr(0, 10),
                focus: new Date().toISOString().substr(0, 10),
                myTasks: [],
            }
        },
        computed: {
            ...mapState(['profile']),
            // convert the list of events into a map of lists keyed by date
            eventsMap() {
                const map = {}
                this.myTasks.forEach(e => (map[e.deadline] = map[e.deadline] || []).push(e))
                return map
            }
        },
        methods: {
            open(event) {
                alert(event.nameTask)
            },
            prev() {
                this.$refs.calendar.prev()
            },
            next() {
                this.$refs.calendar.next()
            },
            getColorTask(task) {
                if (task.progress >= 0 && task.progress <= 25) return'#FF96A0'
                else if (task.progress > 25 && task.progress <= 50) return '#FFC69B'
                else if (task.progress > 50 && task.progress <= 75) return '#FFF199'
                else if (task.progress > 75 && task.progress <= 99) return '#BEFF95'
                else if (task.progress === 100) return '#8CFFA8'
            }
        },
        created() {
            axios.get('/task/myTasksId').then((response) => {
                for (let taskId of response.data) {
                    axios.get('/task/' + taskId).then((response) => {
                        this.myTasks.push(response.data)
                    });
                }
            });
        },
        beforeMount() {
            if (this.profile.role !== 'student') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>
    .my-event {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        border-radius: 2px;
        border: 1px solid #1867c0;
        width: 100%;
        font-size: 12px;
        padding: 3px;
        cursor: pointer;
        margin-bottom: 1px;
    }
</style>
