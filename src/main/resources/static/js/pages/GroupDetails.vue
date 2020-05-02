<template>
    <v-container>
        <v-layout row>
            <v-btn @click="showMessengerWithTeacher">
                Contact with teacher
                <v-badge v-if="showNotificationOfMessageWithTeacher" color="red">
                    <template v-slot:badge>
                        <span>!</span>
                    </template>
                    <v-icon/>
                </v-badge>
            </v-btn>
            <v-spacer/>
            <div v-if="group.evaluation">
                <v-layout row>
                    <v-btn color="info" flat v-if="commentEvaluation && commentEvaluation !== ''"
                           @click="dialogSeeEvaluationComment = true">
                        See comment
                    </v-btn>
                    <v-rating
                            v-model="group.evaluation.evaluation"
                            color="yellow darken-3"
                            background-color="grey darken-1"
                            empty-icon="$vuetify.icons.ratingFull"
                            half-increments
                            hover
                            large
                            readonly
                    ></v-rating>
                </v-layout>
            </div>
        </v-layout>
        <v-dialog v-model="dialogSeeEvaluationComment" max-width="500px">
            <v-card>
                <v-card-title>
                    <span class="headline">Comment on the evaluation for the project</span>
                </v-card-title>
                <v-card-text>
                    <v-textarea readonly
                                v-model="commentEvaluation"
                    />
                </v-card-text>
                <v-card-actions>
                    <v-spacer/>
                    <v-btn color="blue darken-1" flat @click="dialogSeeEvaluationComment = false">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <div v-if="!group.theme">
            <div>
                You have not selected the theme of the project.
                If you are the Responsible Person of this group, then you have the option to choose a theme.
                Otherwise, please contact your Responsible Person in messenger to agree on a theme.
            </div>
            <v-select v-if="responsibleUser.id === profile.id"
                      v-model="selectedTheme"
                      :items="availableThemes"
                      item-text="nameTheme"
                      label="Choose a theme"
                      outline
                      return-object
            ></v-select>
            <div v-if="responsibleUser.id !== profile.id">
                Availble themes:
                <theme-list
                        :project="project"
                        :themes="availableThemes"
                ></theme-list>
                <span v-if="availableThemes === []">
                Sorry, there are no available themes. Contact your teacher to add themes
            </span>
            </div>

            <v-btn v-if="selectedTheme" @click="acceptTheme">
                Accept
            </v-btn>
        </div>
        <div v-if="group.theme">
            <h1 class="nameTheme font-weight-black"> {{ project.thematics }}: "{{ group.theme.nameTheme }}"</h1>
            <v-divider/>
            <br>
            <div id="chart">
                <apexchart type=area height=350 :options="chartOptions" :series="series"/>
            </div>
            <v-card :color="colorFinalTask">
                <v-card-text primary-title class="my-1 finalTask">
                    <div>
                        This is a final task. There Responsible Person must send the final version of yours project!
                        <h4>Responsible person:</h4>
                        <span>
                    {{ responsibleUser.name }} ({{ responsibleUser.email }})
                </span>
                        <h4>Deadline:</h4>
                        {{ deadline }}
                    </div>
                </v-card-text>
                <v-card-actions class="white">

                    <v-layout row>
                        <v-spacer></v-spacer>
                        <v-btn v-if="group.finalFile" @click="downloadFinalFile" small flat round>Download file</v-btn>
                        <div v-if="group.finalFile">({{ group.finalFile.name }})</div>
                        <v-btn :disabled="delayOfProject" v-if="responsibleUser.id === profile.id" value="LoadFile"
                               @click="dialogLoadFileToFinalTask = true" small flat round>Load file
                        </v-btn>
                    </v-layout>
                </v-card-actions>
            </v-card>
            <v-dialog v-model="dialogLoadFileToFinalTask" max-width="600px">
                <v-card>
                    <v-card-title>
                        <span class="headline">Load file</span>
                    </v-card-title>
                    <v-card-text>
                        <v-form ref="form">
                            <input type="file" id="fileInput" ref="file" v-on:change="handleFileUpload()"
                                   required="true"/>
                            <v-btn>
                                <label for="fileInput">Choose file</label>
                            </v-btn>
                            <v-label v-if=" file !== '' ">{{ file.name }}</v-label>
                            <v-label v-if=" file === '' ">Not selected</v-label>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" flat @click="dialogLoadFileToFinalTask = false">Cancel</v-btn>
                        <v-btn color="blue darken-1" flat @click="loadFileToFinalTask">Load</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-progress-linear v-if="tasks.length !== 0"
                               :color="colorFinalTask"
                               height="15"
                               :value="Math.round((tasks.map(el => el.progress).reduce((a, b) => a + b, 0))/(tasks.length))"
            ></v-progress-linear>
            <task-list
                    :tasks="tasks"
                    :group="group"
                    :disable="delayOfProject"
            ></task-list>
            <v-btn :disabled="delayOfProject" v-if="responsibleUser.id === profile.id" value="Add" @click="dialogAddTask = true">Add task</v-btn>
        </div>
        <div class="messenger">
            <v-toolbar height="50px" class="teal darken-4" dark>
                Messenger
                <v-icon dark v-if="showMessenger" @click="showMessenger = false">arrow_drop_down</v-icon>
                <v-icon dark v-if="!showMessenger" @click="showMessenger = true">arrow_drop_up</v-icon>
            </v-toolbar>
            <message-list v-if="showMessenger"
                          :messages="group.messages"
            ></message-list>
            <v-divider/>
            <message-form v-if="showMessenger" :group="group"/>
        </div>
        <v-dialog v-model="dialogAddTask" max-width="600px">
            <v-card>
                <v-card-title>
                    <span class="headline">Add task</span>
                </v-card-title>
                <v-card-text>
                    <v-form ref="form">
                        <v-text-field label="Task name" required v-model="nameTask"
                                      :rules="nameTaskValidation"></v-text-field>
                        <v-select
                                v-model="responsibleUsersTask"
                                :items="group.participants"
                                item-text="email"
                                label="Сhoose responsible persons"
                                chips
                                multiple
                                return-object
                                :rules="responsibleUserTaskValidation"
                        ></v-select>
                        <v-menu>
                            <v-text-field :value="termTask" slot="activator" :rules="termTaskValidation"
                                          label="Due date" prepend-icon="date_range" required>
                            </v-text-field>
                            <v-date-picker dark v-model="termTask" :max="deadline"></v-date-picker>
                        </v-menu>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogAddTask = false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="addTask">Add</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    import {mapActions} from 'vuex'
    import ThemeList from 'components/themes/ThemeList.vue'
    import MessageList from 'components/messages/MessageList.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import TaskList from 'components/tasks/TaskList.vue'
    import axios from "axios"
    import {addHandler} from 'util/ws'

    export default {
        name: "GroupDetails",
        computed: mapState(['profile']),
        components: {
            ThemeList,
            MessageList,
            MessageForm,
            TaskList
        },
        data() {
            return {
                message: null,
                group: {},
                lastMessageWithTeacher: {},
                showNotificationOfMessageWithTeacher: false,
                project: {},
                deadline: null,
                delayOfProject: false,
                responsibleUser: {},
                availableThemes: [],
                tasks: [],
                commentEvaluation: '',
                selectedTheme: null,
                showMessenger: true,
                dialogAddTask: false,
                dialogLoadFileToFinalTask: false,
                dialogSeeEvaluationComment: false,
                colorFinalTask: '',
                file: '',
                newTask: null,
                nameTask: '',
                responsibleUsersTask: [],
                termTask: null,
                series: [],
                chartOptions: {},
                termTaskValidation: [
                    v => !!v || 'This field is required'
                ],
                nameTaskValidation: [
                    v => v.length <= 40 || 'Maximum lenght is 40 characters',
                    v => v.length >= 3 || 'Minimum lenght is 3 characters'
                ],
                responsibleUserTaskValidation: [
                    v => v.length > 0 || 'This field is required'
                ],
            }
        },
        methods: {
            ...mapActions(['updateGroupAction', 'updateThemeAction']),
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            },
            loadFileToFinalTask() {
                if (this.$refs.form.validate() && this.file !== '') {
                    let formData = new FormData();
                    formData.append("file", this.file);
                    axios
                        .post("/file/upload", formData, {
                            headers: {
                                "Content-Type": "multipart/form-data"
                            }
                        })
                        .then(response => {
                            this.group.finalFile = response.data
                            this.updateGroupAction(this.group)
                            this.colorFinalTask = 'success'
                            this.dialogLoadFileToFinalTask = false
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            },
            downloadFinalFile() {
                axios
                    .get('/file/download/' + this.group.finalFile.id, {
                        responseType: "blob",
                    })
                    .then(response => {
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement("a");
                        link.href = url;
                        link.setAttribute("download", this.group.finalFile.name);
                        document.body.appendChild(link);
                        link.click();
                        link.parentNode.removeChild(link);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
            acceptTheme() {
                this.group.theme = this.selectedTheme
                this.group.theme.available = false
                this.updateThemeAction(this.group.theme)
                this.updateGroupAction(this.group)
            },
            addTask() {
                if (this.$refs.form.validate()) {
                    axios.post('/task', {
                        group: {
                            id: this.group.id
                        },
                        nameTask: this.nameTask,
                        responsibleUsers: this.responsibleUsersTask,
                        deadline: this.termTask,
                    }).then(response => {
                        this.tasks.push(response.data)
                        this.dialogAddTask = false
                    })
                }
            },
            setChart() {
                this.series = []
                for (let task of this.group.tasks) {
                    this.series.push({
                        name: task.nameTask,
                        data: [0].concat(task.taskCommits.map(el => el.progress))
                    })
                }
                let maxNumberCommitsInTask = Math.max(...this.group.tasks.map(el => el.taskCommits).map(el => el.length))
                let categories = [0];
                for (let i = 1; i <= maxNumberCommitsInTask; i++) {
                    categories.push(i);
                }
                this.chartOptions = {
                    xaxis: {
                        categories: categories,
                        title: {
                            text: 'Number of commit'
                        }
                    },
                    yaxis: {
                        title: {
                            text: 'Progress [%]'
                        },
                        min: 0,
                        max: 100
                    },
                }
            },
            checkDeadline() {
                let today = new Date()
                let projectDeadline = this.deadline
                let yearDeadline = parseInt(projectDeadline.toString().split("-")[0])
                let monthDeadline = parseInt(projectDeadline.toString().split("-")[1])
                let dayDeadline = parseInt(projectDeadline.toString().split("-")[2])
                let projectDeadlineInDateFormat = new Date(yearDeadline, monthDeadline - 1, dayDeadline)
                if (today.getTime() > projectDeadlineInDateFormat.getTime())
                    this.delayOfProject = true
            },
            showMessengerWithTeacher() {
                this.$router.push('/messengerWithTeacher/' + this.group.id)
            }
        },
        created() {
            addHandler(data => {
                this.group.messages.push(data)
            })
            axios.get('/group/' + this.$route.params.id).then((response) => {
                this.group = response.data
                this.responsibleUser = this.group.responsibleUser
                this.tasks = this.group.tasks
                if (this.group.evaluation) this.commentEvaluation = this.group.evaluation.comment
                if (this.group.finalFile) this.colorFinalTask = "success"
                else this.colorFinalTask = "error"
                if(this.group.messagesWithTeacher.length !== 0) {
                    axios.get('/messageWithTeacher/getLast', {
                        params: {
                            id_group: this.group.id
                        }
                    }).then((response) => {
                        this.lastMessageWithTeacher = response.data
                        if (!response.data.usersWhoSaw.map(el => el.id).includes(this.profile.id))
                            this.showNotificationOfMessageWithTeacher = true
                    });
                }
                if (this.group.theme == null) {
                    axios.get('/project/' + this.group.project.id).then((response) => {
                        this.project = response.data
                        this.availableThemes = this.project.themes.filter(el => el.available === true)
                        this.deadline = this.project.term
                        this.checkDeadline()
                    });
                } else {
                    axios.get('/project/' + this.group.project).then((response) => {
                        this.project = response.data
                        this.deadline = this.project.term
                        this.setChart()
                        this.checkDeadline()
                    });
                }

            });
        },
        beforeMount() {
            if (this.profile.role !== 'student') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>
    .nameTheme {
        text-align: center;
        color: #00796B;
    }

    .finalTask {
        color: white;
    }

    .messenger {
        position: fixed;
        bottom: 10px;
        right: 10px;
        background-color: white;
        width: 300px;
        z-index: 1;
    }

    .progressFinalTask {
        margin: 5px 50px 0px 30px;
    }

    #fileInput {
        width: 0;
        height: 0;
    }
</style>