<template>
    <v-list class="teal lighten-5" v-if="tasks">
        <template
                v-for="(item, index) in tasks"
        >
            <task-row
                    :task="item"
                    :group="group"
                    :disable="disable"
                    :key="'item' + index"
            ></task-row>
        </template>


    </v-list>
    
</template>


<script>
    import TaskRow from 'components/tasks/TaskRow.vue'

    export default {
        name: "TaskList",
        components: { TaskRow },
        props: ['tasks' , 'group', 'disable']
    }
</script>

<style scoped>

</style>