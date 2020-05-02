<template>
    <v-container>
        <div class="title mb-3">My projects</div>
        <div class="addProjectButton">
            <v-btn color="success" value="Add" @click="dialogAddProject = true">Add project</v-btn>
        </div>
        <v-layout align-space-around justify-start column>
            <project-row v-for="project in myProjects"
                         :key="project.id"
                         :project="project"
            />
        </v-layout>
        <v-dialog v-model="dialogAddProject" max-width="600px">

            <v-card>
                <v-card-title>
                    <span class="headline">Add project</span>
                </v-card-title>

                <v-card-text>
                    <v-form ref="form">
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
                            <v-text-field :value="term" slot="activator" :rules="termValidation" label="Due date"
                                          prepend-icon="date_range" required>
                            </v-text-field>
                            <v-date-picker dark v-model="term"></v-date-picker>
                        </v-menu>
                        <div>
                            <v-btn large color="success" @click="save">Add</v-btn>
                            <v-btn large color="info" @click="dialogAddProject = false">Cancel</v-btn>

                        </div>
                    </v-form>
                </v-card-text>
            </v-card>

        </v-dialog>
    </v-container>
</template>

<script>

    import ProjectRow from 'components/projects/ProjectRow.vue'
    import { mapState } from 'vuex'
    import axios from "axios";
    import {mapActions} from 'vuex'

    export default {
        computed: mapState(['profile']),
        components: {
            ProjectRow
        },
        data() {
            return {
                myProjects: [],
                thematics: '',
                requirement: '',
                term: null,
                dialogAddProject: false,
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
                if (this.$refs.form.validate()) {
                    axios.post("/project", {
                        thematics: this.thematics,
                        requirement: this.requirement,
                        term: this.term,
                    }).then(response => {
                        this.myProjects.push(response.data)
                        this.thematics = ''
                        this.requirement = ''
                        this.term = null
                        this.dialogAddProject = false
                    })
                }
            },


        },
        created(){
            axios.get('/project/myProjects').then((response) => {
                this.myProjects = response.data
            });
        },
        beforeMount() {
            if (this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },

    }
</script>

<style scoped>
    .addProjectButton {
        width: 100%;
        display: flex;
        justify-content: center;
        margin-top: 30px;
    }
</style>