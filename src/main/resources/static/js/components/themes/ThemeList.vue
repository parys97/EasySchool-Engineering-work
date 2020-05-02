<template>
    <v-list class="projectThemes teal lighten-5" v-if="project.themes">
        <template
                v-for="(item, index) in themes"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            ></v-divider>

            <theme-row
                    :theme="item"
                    :project="project"
                    :key="'item' + index"
            ></theme-row>
        </template>


    </v-list>
</template>

<script>
    import ThemeRow from 'components/themes/ThemeRow.vue'

    export default {
        name: 'ThemeList',
        components: {ThemeRow },
        props: ['project','themes']
    }
</script>

<style scoped>
    .projectThemes{
        max-height: 300px;
        overflow: auto;
    }
</style>