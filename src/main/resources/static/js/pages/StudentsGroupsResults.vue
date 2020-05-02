<template>
    <v-data-table
            :headers="headers"
            :items="myStudentsGroups"
            class="elevation-1"
    >
        <template v-slot:items="props">
            <students-group-result-row :group="props.item"></students-group-result-row>
        </template>
    </v-data-table>
</template>

<script>
    import {mapState} from 'vuex'
    import axios from "axios"
    import StudentsGroupResultRow from 'components/projectResults/StudentsGroupResultForTeacherRow.vue'

    export default {
        computed: mapState(['profile']),
        components: { StudentsGroupResultRow },
        name: "StudentsGroupsResults",
        data() {
            return {
                myStudentsGroups: [],
                headers: [
                    {text: 'Project', sortable: false, value: 'project'},
                    {text: 'Theme', sortable: false, value: 'nameTheme'},
                    {text: 'Responsible student', sortable: false, value: 'email'},
                    {text: 'Members', sortable: false, value: 'members'},
                    {text: 'Deadline', sortable: false, value: 'deadline'},
                    {text: 'Evaluation', sortable: false, value: 'evaluation'},
                ]
            }
        },
        created(){
            axios.get('/project/myProjects').then((response) => {
                this.myStudentsGroups = response.data.map(el => el.groups).flat(1)
            });
        },
        beforeMount() {
            if (this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>

</style>