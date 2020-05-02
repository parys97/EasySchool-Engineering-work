<template>
    <v-app>
        <v-navigation-drawer class="teal lighten-3" dark app v-model="drawer">
            <navigation-drawer/>
        </v-navigation-drawer>
        <v-toolbar v-if="!$router.currentRoute.path.includes('notRegistered')" class="teal darken-3" dark app>
            <v-toolbar-side-icon v-if="profile" @click="drawer = !drawer"></v-toolbar-side-icon>
            <v-toolbar-title>EasySchool</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showProfile">
                {{profile.name}}
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content class="teal lighten-5">
            <v-container fluid @click="drawer = false">
                <router-view></router-view>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import NavigationDrawer from 'components/menu/NavigationDrawer.vue'
    import { mapState } from 'vuex'

    export default {
        components: {
            NavigationDrawer
        },
        data() {
            return {
                drawer: false
            }
        },
        computed: mapState(['profile']),
        methods: {
            showProfile() {
                this.$router.push('/')
            }
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }else{
                if(!this.profile.role && !this.$router.currentRoute.path.includes('notRegistered')){
                    this.$router.push('/notRegistered')
                }
            }
        },

    }
</script>

<style>
</style>