import Vue from 'vue'

const messagesWithTeacher = Vue.resource('/messageWithTeacher{/id}')

export default {
    add: messageWithTeacher => messagesWithTeacher.save({}, messageWithTeacher),
    update: messageWithTeacher => messagesWithTeacher.update({id: messageWithTeacher.id}, messageWithTeacher),
    remove: id => messagesWithTeacher.remove({id})
}