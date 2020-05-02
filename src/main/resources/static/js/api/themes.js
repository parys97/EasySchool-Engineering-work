import Vue from 'vue'

const themes = Vue.resource('/theme{/id}')

export default {
    add: theme => themes.save({}, theme),
    update: theme => themes.update({id: theme.id}, theme),
    remove: id => themes.remove({id})
}