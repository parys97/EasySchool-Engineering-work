<template>
    <v-list class="teal lighten-5" v-if="tasks">
        <template
                v-for="(item, index) in tasks"
        >
            <task-row-for-teacher
                    :task="item"
                    :group="group"
                    :key="'item' + index"
            ></task-row-for-teacher>
        </template>


    </v-list>

</template>


<script>
    import TaskRowForTeacher from "components/tasks/TaskRowForTeacher.vue";

    export default {
        name: "TaskListForTeacher",
        components: {TaskRowForTeacher},
        props: ['tasks' , 'group']
    }
</script>

<style scoped>

</style>