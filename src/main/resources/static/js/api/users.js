import Vue from 'vue'

const users = Vue.resource('/user{/id}')

export default {
    add: user => users.save({}, user),
}