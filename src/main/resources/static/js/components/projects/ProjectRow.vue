<template>
    <v-card class="ma-4">
        <v-card-text primary-title>
            <div>Thematic: <h3 class="headline mb-0">{{ project.thematics }}</h3></div>
            <div>Requirement: {{project.requirement}}</div>
            <div>Teacher: {{ project.author.name }} , email: {{ project.author.email }}</div>
            <div>Due by: {{project.term}}</div>
            <v-btn value="Show themes" @click="showThemes = !showThemes" small flat round>Show themes</v-btn>
            <div v-if="showThemes" >
                <theme-list
                        :project="project"
                        :themes="project.themes"
                ></theme-list>
            </div>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="project.author.id == profile.id" value="Edit" @click="showEditFormProject" small flat round>Edit</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import ThemeList from 'components/themes/ThemeList.vue'
    import { mapState } from 'vuex'

    export default {
        props: ['project'],
        components: { ThemeList },
        computed: mapState(['profile']),
        data() {
            return {
                showThemes: false
            }
        },
        methods: {
            showEditFormProject() {
                this.$router.push('/projectEditForm/'+ this.project.id)
            },

        },
    }
</script>

<style scoped>
</style>