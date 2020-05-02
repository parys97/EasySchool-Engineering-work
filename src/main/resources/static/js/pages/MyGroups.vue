<template>
    <v-container>
        <div class="title mb-3">My groups</div>
        <group-list-for-student
                    :groups="myGroups"
        ></group-list-for-student>
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    import GroupListForStudent from 'components/groups/GroupListForStudent.vue'
    import axios from "axios"

    export default {
        computed: mapState(['profile']),
        components: {GroupListForStudent},
        data() {
            return {
                myGroups: [],
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