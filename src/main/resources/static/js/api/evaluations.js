import Vue from 'vue'

const evaluations = Vue.resource('/evaluation{/id}')

export default {
    add: evaluation => evaluations.save({}, evaluation),
    update: evaluation => evaluations.update({id: evaluation.id}, evaluation),
    remove: id => evaluations.remove({id})
}