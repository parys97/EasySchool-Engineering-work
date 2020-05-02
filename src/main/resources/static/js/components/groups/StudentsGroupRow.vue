<template>
    <v-card class="ma-4">
        <v-card-text primary-title>
            <div>Theme: <span v-if="group.theme">{{ group.theme.nameTheme }}</span></div>
            <div>Responsible person: <span>{{ group.responsibleUser.name }} ({{ group.responsibleUser.email }})</span></div>
            <div>Deadline: <span>{{ deadline }}</span></div>


            <v-progress-linear v-if="group.tasks.length !== 0"
                               :color="colorProgressLinear"
                               height="15"
                               :value="Math.round((group.tasks.map(el => el.progress).reduce((a, b) => a + b, 0))/
                                (group.tasks.length))"
            ></v-progress-linear>
            <v-progress-linear v-if="group.tasks.length === 0"
                               :color="colorProgressLinear"
                               height="15"
                               value="0"
            ></v-progress-linear>

        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn value="Goto" @click="showDetailsGroupProject" small flat round>See more</v-btn>
        </v-card-actions>

    </v-card>
</template>

<script>
    import axios from "axios"

    export default {
        name: "studentsGroupRow",
        props: ['group'],

        data() {
            return {
                colorProgressLinear: '',
                deadline: null
            }
        },

        methods: {
            showDetailsGroupProject() {
                this.$router.push('/groupDetailsForTeacher/' + this.group.id)
            }
        },

        created() {
            if(this.group.finalFile) this.colorProgressLinear = 'success'
            else this.colorProgressLinear = 'error'
            axios.get('/project/'+this.group.project).then((response) => {
                this.deadline = response.data.term
            });
        }


    }
</script>

<style scoped>

</style>