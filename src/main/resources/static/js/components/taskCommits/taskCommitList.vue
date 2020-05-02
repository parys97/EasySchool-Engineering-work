<template>
    <v-list class="taskCommits teal lighten-5" v-if="taskCommits.length !== 0">
        <template
                v-for="(item, index) in taskCommits"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            ></v-divider>

            <task-commit-row
                    :taskCommit="item"
                    :key="'item' + index"
            ></task-commit-row>
        </template>


    </v-list>
</template>

<script>
    import TaskCommitRow from 'components/taskCommits/TaskCommitRow.vue'

    export default {
        name: "taskCommitList",
        components: { TaskCommitRow },
        props: ['taskCommits']
    }
</script>

<style scoped>

</style>