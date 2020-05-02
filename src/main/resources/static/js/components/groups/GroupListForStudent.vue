<template>
    <v-list class="projectGroups teal lighten-5" v-if="groups">
        <template
                v-for="(item, index) in groups"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            ></v-divider>

            <group-row-for-student
                    :group="item"
                    :key="'item' + index"
            ></group-row-for-student>
        </template>
    </v-list>
</template>

<script>
    import GroupRowForStudent from 'components/groups/GroupRowForStudent.vue'

    export default {
        name: "GroupList",
        components: {GroupRowForStudent},
        props: ['groups'],
    }
</script>

<style scoped>
    .projectGroups {
        max-height: 600px;
        overflow: auto;
    }
</style>