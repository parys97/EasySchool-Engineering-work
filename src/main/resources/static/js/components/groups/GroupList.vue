<template>
    <v-list class="projectGroups teal lighten-5" v-if="project.groups">
        <template
                v-for="(item, index) in project.groups"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            ></v-divider>

            <group-row
                    :group="item"
                    :project="project"
                    :key="'item' + index"
            ></group-row>
        </template>


    </v-list>
</template>

<script>
    import GroupRow from 'components/groups/GroupRow.vue'

    export default {
        name: "GroupList",
        components: { GroupRow },
        props: ['project']
    }
</script>

<style scoped>
    .projectGroups{
        max-height: 600px;
        overflow: auto;
    }
</style>