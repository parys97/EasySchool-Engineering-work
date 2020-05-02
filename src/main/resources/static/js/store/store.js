import Vue from 'vue'
import Vuex from 'vuex'
import usersApi from 'api/users'
import messagesApi from 'api/messages'
import messageWithTeachersApi from 'api/messagesWithTeacher'
import projectsApi from 'api/projects'
import themesApi from 'api/themes'
import groupsApi from 'api/groups'
import tasksApi from 'api/tasks'
import taskCommitsApi from 'api/taskCommits'
import evaluationApi from 'api/evaluations'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        profile: frontendData.profile,
    },

    actions: {
        async addUserAction({commit, state}, user) {
            await usersApi.add(user)
        },
        async addMessageAction({commit, state}, message) {
            await messagesApi.add(message)
        },
        async addMessageWithTeacherAction({commit, state}, messageWithTeacher) {
            await messageWithTeachersApi.add(messageWithTeacher)
        },
        async updateMessageWithTeacherAction({commit}, messageWithTeacher) {
            await messageWithTeachersApi.update(messageWithTeacher)
        },
        async removeMessageWithTeacherAction({commit}, messageWithTeacher) {
            await messageWithTeachersApi.remove(messageWithTeacher.id)
        },
        async addProjectAction({commit, state}, project) {
            await projectsApi.add(project)
        },
        async updateProjectAction({commit}, project) {
            await projectsApi.update(project)
        },
        async removeProjectAction({commit}, project) {
            await projectsApi.remove(project.id)
        },
        async addThemeAction({commit, state}, theme) {
             await themesApi.add(theme)
        },
        async updateThemeAction({commit}, theme) {
            await themesApi.update(theme)
        },
        async removeThemeAction({commit}, theme) {
            await themesApi.remove(theme.id)
        },
        async addGroupAction({commit, state}, group) {
            await groupsApi.add(group)
        },
        async updateGroupAction({commit}, group) {
            await groupsApi.update(group)
        },
        async removeGroupAction({commit}, group) {
            await groupsApi.remove(group.id)
        },
        async addTaskAction({commit, state}, task) {
            await tasksApi.add(task)
        },
        async updateTaskAction({commit}, task) {
            await tasksApi.update(task)
        },
        async removeTaskAction({commit}, task) {
            await tasksApi.remove(task.id)
        },
        async addTaskCommitAction({commit, state}, taskCommit) {
            await taskCommitsApi.add(taskCommit)
        },
        async updateTaskCommitAction({commit}, taskCommit) {
            await taskCommitsApi.update(taskCommit)
        },
        async removeTaskCommitAction({commit}, taskCommit) {
            await taskCommitsApi.remove(taskCommit.id)
        },
        async addEvaluationAction({commit, state}, evaluation) {
            await evaluationApi.add(evaluation)
        },
        async updateEvaluationAction({commit}, evaluation) {
            await evaluationApi.update(evaluation)
        },
        async removeEvaluationAction({commit}, evaluation) {
            await evaluationApi.remove(evaluation.id)
        },

    }
})