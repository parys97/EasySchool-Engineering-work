import Vue from 'vue'

const groups = Vue.resource('/group{/id}')

export default {
    add: group => groups.save({}, group),
    update: group => groups.update({id: group.id}, group),
    remove: id => groups.remove({id})
}