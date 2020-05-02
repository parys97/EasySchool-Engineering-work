<template>
    <v-card
            color="grey lighten-4"
            min-width="350px"
            flat
    >
        <v-toolbar :color="color">
            <v-toolbar-title>{{ task.nameTask }}</v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-card-text>
            <div>Project: {{ project.thematics }}</div>
            <div>Techer: {{ teacher.name }}</div>
            <div>Theme: {{ task.group.theme.nameTheme }}</div>
            <v-progress-linear
                               :color="color"
                               height="10"
                               :value="task.progress"
            ></v-progress-linear>
            <div>{{ task.progress }}%</div>
        </v-card-text>
        <v-card-actions>
            <v-spacer/>
            <v-btn
                    flat
                    @click="goToTask(task.id)"
            >
                Go to task
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import axios from "axios"

    export default {
        name: "DayDetailsCard",
        props: ['task'],
        data() {
            return {
                project: {},
                teacher: {},
                color: 'white'
            }
        },
        methods: {
            goToTask(taskId) {
                this.$router.push('/taskDetails/' + taskId)
            },
        },
        created() {
            if (this.task.progress >= 0 && this.task.progress <= 25) this.color = '#FF96A0'
            else if (this.task.progress > 25 && this.task.progress <= 50) this.color =  '#FFC69B'
            else if (this.task.progress > 50 && this.task.progress <= 75) this.color =  '#FFF199'
            else if (this.task.progress > 75 && this.task.progress <= 99) this.color =  '#BEFF95'
            else if (this.task.progress === 100) this.color =  '#8CFFA8'

            axios.get('/project/' + this.task.group.project).then((response) => {
                this.project = response.data
                this.teacher = response.data.author
            });
        }
    }
</script>

<style scoped>

</style>