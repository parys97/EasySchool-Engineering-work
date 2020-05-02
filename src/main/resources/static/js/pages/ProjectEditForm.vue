<template>
    <v-container>
        <div class="title mb-3">Basic information</div>
        <v-card>
            <v-card-text primary-title >
                <div>
                    <div class="title mt-2 mb-2">Thematic:</div>
                    {{ project.thematics }}
                    <div class="title mt-2 mb-2">Requirement:</div>
                    {{ project.requirement }}
                    <div class="title mt-2 mb-2">Deadline</div>
                    {{ project.term }}

                </div>
            </v-card-text>
            <v-card-actions class="teal lighten-2">
                <v-spacer></v-spacer>
                <v-menu>
                    <template v-slot:activator="{ on }">
                        <v-btn dark icon v-on="on" >
                            <v-icon  size="30px">more_vert</v-icon>
                        </v-btn>
                    </template>
                    <v-list>
                        <v-list-tile @click="dialogEditProject = true">
                            <v-list-tile-title>Edit</v-list-tile-title>
                        </v-list-tile>
                    </v-list>
                </v-menu>
            </v-card-actions>
        </v-card>
        <br><br><br>
        <div>
            <div class="title mb-3">Themes</div>
            <theme-list
                    :project="project"
                    :themes="themes"
            ></theme-list>
            <br>
            <theme-form :project="project"></theme-form>
        </div>
        <br><br><br>
        <div>
            <div class="title mb-3">Groups</div>
            <group-list
                    :project="project"
            ></group-list>
            <br>
            <group-form :project="project"></group-form>
        </div>
        <div class="deleteProjectButton">
            <v-btn color="error" @click="dialogConfirm = true">Delete project</v-btn>
        </div>


        <v-dialog v-model="dialogEditProject" max-width="600px">
            <v-card>
                <v-card-title>
                    <span class="headline">Edit project</span>
                </v-card-title>
                <v-card-text>
                    <v-form ref="form">
                        <v-text-field
                                v-model="thematicsUpdate"
                                :counter="20"
                                :rules="thematicsValidation"
                                label="Thematics"
                                prepend-icon="title"
                        ></v-text-field>
                        <v-textarea
                                solo
                                v-model="requirementUpdate"
                                :counter="100"
                                :rules="requirementValidation"
                                name="input-7-4"
                                label="Requirement"
                                prepend-icon="edit"
                        ></v-textarea>
                        <v-menu>
                            <v-text-field :value="termUpdate" slot="activator" :rules="termValidation" label="Due date" prepend-icon="date_range" required>
                            </v-text-field>
                            <v-date-picker dark v-model="termUpdate" :min="project.term"></v-date-picker>
                        </v-menu>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogEditProject = false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="updateProject">Apply</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>


        <v-dialog
                v-model="dialogConfirm"
                max-width="290"
        >
            <v-card>
                <v-card-text>
                    Are you sure you want to delete this project?
                    All groups and themes in this project will also be deleted
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="removeProject"
                    >
                        Yes
                    </v-btn>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="dialogConfirm = false"
                    >
                        Cancel
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex'
    import { mapState } from 'vuex'
    import axios from "axios";
    import ThemeList from 'components/themes/ThemeList.vue'
    import ThemeForm from 'components/themes/ThemeForm.vue'
    import GroupList from 'components/groups/GroupList.vue'
    import GroupRow from 'components/groups/GroupRow.vue'
    import GroupForm from 'components/groups/GroupForm.vue'

    export default {
        name: "ProjectEditForm",
        computed: mapState(['profile']),
        components: { ThemeList, ThemeForm, GroupList, GroupForm, GroupRow },

        data () {
            return {
                project: {},
                groups: [],
                themes: [],
                dialogConfirm: false,
                dialogEditProject: false,
                thematicsUpdate: '',
                requirementUpdate: '',
                termUpdate: null,
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
            ...mapActions(['removeProjectAction', 'updateProjectAction']),
            initPage() {
                axios.get('/project/' + this.$route.params.id).then((response) => {
                    this.project = response.data
                    this.groups = this.project.groups
                    this.themes = this.project.themes
                    this.thematicsUpdate = this.project.thematics
                    this.requirementUpdate = this.project.requirement
                    this.termUpdate = this.project.term
                });
            },
            removeProject() {
                this.dialogConfirm = false
                this.$router.push('/allProjectsList')
                this.removeProjectAction(this.project)
            },
            updateProject() {
                if(this.$refs.form.validate()){
                    this.project.thematics = this.thematicsUpdate
                    this.project.requirement = this.requirementUpdate
                    this.project.term = this.termUpdate
                    this.updateProjectAction(this.project).then(res => {
                        this.dialogEditProject = false
                        this.initPage()
                    })

                }
            }
        },
        created(){
            this.initPage()
        },
        beforeMount() {
            if (this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>
    .deleteProjectButton{
        width: 100%;
        display: flex;
        justify-content: center;
        margin-top: 30px;
    }
</style>