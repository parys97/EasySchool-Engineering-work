import Vue from 'vue'
import VueRouter from 'vue-router'
import Auth from 'pages/Auth.vue'
import ProjectForm from 'pages/ProjectForm.vue'
import ProjectEditForm from 'pages/ProjectEditForm.vue'
import AllProjectsList from 'pages/AllProjectsList.vue'
import MyProjects from 'pages/MyProjects.vue'
import UserList from 'pages/UserList.vue'
import UserRegistration from 'pages/Registration.vue'
import Profile from 'pages/Profile.vue'
import NotRegistered from 'pages/NotRegistered.vue'
import MyGroups from "pages/MyGroups.vue";
import GroupDetails from "pages/GroupDetails.vue";
import TaskDetails from "pages/TaskDetails.vue"
import MyStudentsGroups from "pages/MyStudentsGroups.vue";
import GroupDetailsForTeacher from "pages/GroupDetailsForTeacher.vue";
import MessengerWithTeacher from "pages/MessengerWithTeacher.vue"
import MyResults from "pages/MyResults.vue"
import StudentsGroupsResults from "pages/StudentsGroupsResults.vue"
import CalendarForStudent from "pages/CalendarForStudent.vue"

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Profile },
    { path: '/auth', component: Auth },
    { path: '/projectForm', component: ProjectForm },
    { path: '/projectEditForm/:id', component: ProjectEditForm },
    { path: '/allProjectsList', component: AllProjectsList },
    { path: '/myGroups', component: MyGroups },
    { path: '/myProjects', component: MyProjects },
    { path: '/groupDetails/:id', component: GroupDetails },
    { path: '/messengerWithTeacher/:id', component: MessengerWithTeacher },
    { path: '/taskDetails/:id', component: TaskDetails},
    { path: '/userList', component: UserList },
    { path: '/registration', component: UserRegistration },
    { path: '/notRegistered', component: NotRegistered },
    { path: '/myStudentsGroups', component: MyStudentsGroups},
    { path: '/groupDetailsForTeacher/:id', component: GroupDetailsForTeacher},
    { path: '/myResults', component: MyResults},
    { path: '/studentsGroupsResults', component: StudentsGroupsResults},
    { path: '/calendarForStudent', component: CalendarForStudent},
    { path: '*', component: Profile },
]

export default new VueRouter({
    mode: 'history',
    routes
})