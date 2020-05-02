<template>
    <v-data-table
            :headers="headers"
            :items="users"
            class="elevation-1"
    >
        <template v-slot:items="props">
            <td>{{ props.item.name }}</td>
            <td>{{ props.item.id }}</td>
            <td>{{ props.item.email}}</td>
            <td>{{ props.item.gender }}</td>
            <td>{{ props.item.role }}</td>

        </template>
    </v-data-table>

</template>

<script>
    import axios from "axios";
    import {mapState} from 'vuex'

    export default {
        computed: mapState(['profile']),
        data () {
            return {
                users: [],
                clickedItem: null,
                headers: [
                    {text: 'Name', value: 'name'},
                    {text: 'Id', sortable: false, value: 'id'},
                    {text: 'Email', sortable: false, value: 'email'},
                    {text: 'Gender', sortable: false, value: 'gender'},
                    {text: 'Role', sortable: false, value: 'role'},
                ]
            }
        },
        created(){
            axios.get('/user').then((response) => {
                this.users = response.data
            });
        },

        beforeMount() {
            if (this.profile.role !== 'admin') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>

</style>