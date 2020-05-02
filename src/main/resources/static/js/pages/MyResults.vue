<template>
    <v-data-table
            :headers="headers"
            :items="myGroups"
            class="elevation-1"
    >
        <template v-slot:items="props">
            <result-row :group="props.item"></result-row>
        </template>
    </v-data-table>
</template>

<script>
    import {mapState} from 'vuex'
    import axios from "axios"
    import ResultRow from 'components/projectResults/ProjectResultForStudentRow.vue'

    export default {
        computed: mapState(['profile']),
        components: { ResultRow },
        data() {
            return {
                myGroups: [],
                headers: [
                    {text: 'Theme', sortable: false,value: 'nameTheme'},
                    {text: 'Project', sortable: false,value: 'project'},
                    {text: 'Teacher', sortable: false,value: 'teacher'},
                    {text: 'Responsible student', sortable: false, value: 'email'},
                    {text: 'Members', sortable: false, value: 'members'},
                    {text: 'Deadline', sortable: false, value: 'deadline'},
                    {text: 'Evaluation', sortable: false, value: 'evaluation'},
                ]
            }
        },

        created() {
            axios.get('/group/myGroupsId').then((response) => {
                for (let groupId of response.data) {
                    axios.get('/group/' + groupId).then((response) => {
                        this.myGroups.push(response.data)
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

</style>