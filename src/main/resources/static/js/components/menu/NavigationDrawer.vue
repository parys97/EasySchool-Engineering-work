<template>
        <v-layout justify-start column>
            <div v-if="profile">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/'"
                       @click="showProfile">
                    My profile
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && (profile.role === 'teacher' || profile.role === 'student')">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/allProjectsList'"
                       @click="showAllProjectsList">
                    All projects
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'teacher'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/myProjects'"
                       @click="showMyProjects">
                    My projects
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'teacher'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/myStudentsGroups'"
                       @click="showMyStudentsGroups">
                    My students groups
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'student'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/myGroups'"
                       @click="showMyGroups">
                    My groups
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'student'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/myResults'"
                       @click="showMyResults">
                    My results
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'student'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/calendarForStudent'"
                       @click="showCalendarForStudent">
                    Calendar
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'teacher'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/studentsGroupsResults'"
                       @click="showStudentsGroupsResults">
                    Results my students groups
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'teacher'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/projectForm'"
                       @click="showProjectForm">
                    Add project
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'admin'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/registration'"
                       @click="showFormRegistration">
                    User registration
                </v-btn>
                <v-divider></v-divider>
            </div>
            <div v-if="profile && profile.role === 'admin'">
                <v-btn flat large class="ma-0 button"
                       :disabled="$route.path === '/userList'"
                       @click="showUserList">
                    User list
                </v-btn>
                <v-divider></v-divider>
            </div>
        </v-layout>

</template>

<script>
    import { mapState } from 'vuex'

    export default {
        computed: mapState(['profile']),
        methods: {
            showProfile() {
                this.$router.push('/')
            },
            showProjectForm() {
                this.$router.push('/projectForm')
            },
            showAllProjectsList() {
                this.$router.push('/allProjectsList')
            },
            showMyProjects() {
                this.$router.push('/myProjects')
            },
            showMyGroups() {
                this.$router.push('/myGroups')
            },
            showMyResults(){
                this.$router.push('/myResults')
            },
            showCalendarForStudent(){
                this.$router.push('/calendarForStudent')
            },
            showStudentsGroupsResults(){
                this.$router.push('/studentsGroupsResults')
            },
            showFormRegistration() {
                this.$router.push('/registration')
            },
            showUserList() {
                this.$router.push('/userList')
            },
            showMyStudentsGroups(){
                this.$router.push('/myStudentsGroups')
            }
        },
    }
</script>

<style scoped>
    .button{
        width: 100%;
    }
</style>