<template>
    <v-container>
        <v-form ref="form" v-if="showProjectForm">
            <div class="title mb-3">Create new project</div>
            <v-text-field
                    v-model="thematics"
                    :counter="20"
                    :rules="thematicsValidation"
                    label="Thematics"
                    prepend-icon="title"
            ></v-text-field>
            <v-textarea
                    solo
                    v-model="requirement"
                    :counter="100"
                    :rules="requirementValidation"
                    name="input-7-4"
                    label="Requirement"
                    prepend-icon="edit"
            ></v-textarea>
            <v-menu>
                <v-text-field :value="term" slot="activator" :rules="termValidation" label="Due date" prepend-icon="date_range" required>
                </v-text-field>
                <v-date-picker dark v-model="term"></v-date-picker>
            </v-menu>
            <div>
                <v-btn large color="success" @click="save">Add</v-btn>
            </div>
        </v-form>
        <v-alert v-if="showSuccessAlert"
                :value="true"
                type="success"
        >
            The project was successfully added. Now you can add themes to it.
        </v-alert>
        <v-alert v-if="showErrorAlert"
                :value="true"
                type="error"
        >
            The project was not added.Incorrectly entered data.
        </v-alert>


    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapState } from 'vuex'
    import ThemeList from 'components/themes/ThemeList.vue'
    import ThemeForm from 'components/themes/ThemeForm.vue'

    export default {
        name: 'ProjectForm',
        computed: mapState(['profile']),
        components: { ThemeList, ThemeForm },
        data() {
            return {
                thematics: '',
                requirement: '',
                term: null,
                showSuccessAlert: false,
                showErrorAlert: false,
                showProjectForm: true,
                thematicsValidation: [
                    v => v.length <= 20 || 'Maximum lenght is 20 characters',
                    v => v.length >= 3 || 'Minimum lenght is 3 characters'
                ],
                requirementValidation: [
                    v => v.length <= 100 || 'Maximum lenght is 100 characters',
                    v => v.length >= 10 || 'Minimum lenght is 10 characters'
                ],
                termValidation: [
                    v => !!v || 'This field is required'
                ]
            }
        },
        methods: {
            ...mapActions(['addProjectAction']),

            save() {
                if(this.$refs.form.validate()){
                    this.addProjectAction({
                        thematics: this.thematics,
                        requirement: this.requirement,
                        term: this.term,
                    })
                    this.thematics = ''
                    this.requirement = ''
                    this.term = null
                    this.showProjectForm = false
                    this.showSuccessAlert = true
                    this.showErrorAlert = false
                }
                else{
                    this.showErrorAlert = true
                }
            },
        },

        beforeMount() {
            if (this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>
</style>