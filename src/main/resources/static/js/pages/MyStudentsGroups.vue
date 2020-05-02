<template>
    <v-container>
        <div class="title mb-3">My students group</div>
        <v-layout align-space-around justify-start column>
            <students-group-row v-for="group in myStudentsGroup"
                         :key="group.id"
                         :group="group"
            />
        </v-layout>
    </v-container>
</template>

<script>

    import StudentsGroupRow from "components/groups/StudentsGroupRow.vue";
    import { mapState } from 'vuex'
    import axios from "axios";

    export default {
        computed: mapState([ 'profile']),
        components: {
            StudentsGroupRow
        },
        data() {
            return {
                myProjetcs: [],
                myStudentsGroup: []
            }
        },
        created(){
            axios.get('/project/myProjects').then((response) => {
                this.myProjects = response.data
                this.myStudentsGroup = this.myProjects.map(el => el.groups).flat(1)
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