import Vue from 'vue'

const taskCommits = Vue.resource('/taskCommit{/id}')

export default {
    add: taskCommit => taskCommits.save({}, taskCommit),
    update: taskCommit => taskCommits.update({id: taskCommit.id}, taskCommit),
    remove: id => taskCommits.remove({id})
}