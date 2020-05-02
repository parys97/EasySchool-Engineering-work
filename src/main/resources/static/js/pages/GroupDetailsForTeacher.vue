<template>
    <v-container>
        <v-layout row>
            <v-btn @click="showMessengerWithGroup">
                Contact with group
                <v-badge v-if="showNotificationOfMessageWithTeacher" color="red">
                    <template v-slot:badge>
                        <span>!</span>
                    </template>
                    <v-icon/>
                </v-badge>
            </v-btn>
            <v-btn v-if="deadlineIsOver || group.finalFile" @click="dialogEvaluationOfTheProject = true">Evaluation</v-btn>
            <v-spacer/>
            <v-rating v-if="group.evaluation"
                      v-model="evaluation"
                      color="yellow darken-3"
                      background-color="grey darken-1"
                      empty-icon="$vuetify.icons.ratingFull"
                      half-increments
                      hover
                      large
                      readonly
            ></v-rating>
        </v-layout>
        <div v-if="!group.theme">
            This group didn't choose the theme of project yet.
            Contact your group to let them select the theme.
        </div>
        <div v-if="group.theme">
            <h1 class="nameTheme"> {{ project.thematics }}: "{{ group.theme.nameTheme }}"</h1>
            <v-divider/>
            <br>
            <div id="chart">
                <apexchart type=area height=350 :options="chartOptions" :series="series"/>
            </div>
            <v-card :color="colorFinalTask">
                <v-card-text primary-title class="my-1 finalTask">
                    <div>
                        This is a final task. There Responsible Person must send the final version of groups project!
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
                    </v-layout>
                </v-card-actions>
            </v-card>
            <v-progress-linear v-if="tasks.length !== 0"
                               :color="colorFinalTask"
                               height="15"
                               :value="Math.round((tasks.map(el => el.progress).reduce((a, b) => a + b, 0))/(tasks.length))"
            ></v-progress-linear>
            <task-list-for-teacher
                    :tasks="tasks"
                    :group="group"
            ></task-list-for-teacher>
        </div>


        <v-dialog v-model="dialogEvaluationOfTheProject" max-width="500px">
            <v-card>
                <v-card-title>
                    <span class="headline">Evaluation of the project</span>
                </v-card-title>
                <v-card-text>
                    <v-form ref="form">
                        <div class="text-xs-center">
                            <v-rating
                                    v-model="evaluationUpdate"
                                    color="yellow darken-3"
                                    background-color="grey darken-1"
                                    empty-icon="$vuetify.icons.ratingFull"
                                    half-increments
                                    hover
                                    large
                            ></v-rating>
                        </div>
                        <v-textarea
                                label="Add comment"
                                :counter="250"
                                v-model="evaluationComment"
                        />
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogEvaluationOfTheProject = false">Cancel</v-btn>
                    <v-btn color="blue darken-1" flat @click="evaluateGroup">Evaluate</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    import {mapActions} from 'vuex'
    import axios from "axios"
    import TaskListForTeacher from "components/tasks/TaskListForTeacher.vue";

    export default {
        name: "GroupDetailsForTeacher",
        computed: mapState(['profile']),
        components: {
            TaskListForTeacher,
            TaskListForTeacher
        },
        data() {
            return {
                message: null,
                group: {},
                deadlineIsOver: false,
                lastMessageWithTeacher: {},
                showNotificationOfMessageWithTeacher: false,
                project: {},
                deadline: null,
                responsibleUser: {},
                availableThemes: [],
                tasks: [],
                selectedTheme: null,
                series: [],
                chartOptions: {},
                colorFinalTask: '',
                dialogEvaluationOfTheProject: false,
                evaluation: 0,
                evaluationUpdate: this.evaluation,
                evaluationComment: ''
            }
        },
        methods: {
            ...mapActions(['updateGroupAction', 'updateThemeAction', 'updateEvaluationAction']),
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
            checkDeadline() {
                let today = new Date()
                let projectDeadline = this.deadline
                let yearDeadline = parseInt(projectDeadline.toString().split("-")[0])
                let monthDeadline = parseInt(projectDeadline.toString().split("-")[1])
                let dayDeadline = parseInt(projectDeadline.toString().split("-")[2])
                let projectDeadlineInDateFormat = new Date(yearDeadline, monthDeadline - 1, dayDeadline)
                if (today.getTime() > projectDeadlineInDateFormat.getTime())
                    this.deadlineIsOver = true
            },
            evaluateGroup(){
                if(this.evaluationComment.length <= 250){
                    if(!this.group.evaluation){
                        axios.post('/evaluation',{
                            evaluation: this.evaluationUpdate,
                            comment: this.evaluationComment
                        }).then(response => {
                            this.group.evaluation = {
                                id: response.data.id
                            }
                            this.updateGroupAction(this.group)
                            this.evaluation = this.evaluationUpdate
                            this.dialogEvaluationOfTheProject = false
                        })
                    }
                    else {
                        axios.put('/evaluation/'+this.group.evaluation.id,{
                            evaluation: this.evaluationUpdate,
                            comment: this.evaluationComment
                        }).then(response => {
                            this.updateGroupAction(this.group)
                            this.evaluation = this.evaluationUpdate
                            this.dialogEvaluationOfTheProject = false
                        })
                    }
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
            showMessengerWithGroup(){
                this.$router.push('/messengerWithTeacher/'+this.group.id)
            }
        },
        created() {
            axios.get('/group/' + this.$route.params.id).then((response) => {
                this.group = response.data
                if(this.group.evaluation){
                    this.evaluationComment = this.group.evaluation.comment
                    this.evaluation = this.group.evaluation.evaluation
                    this.evaluationUpdate = this.evaluation
                }
                this.responsibleUser = this.group.responsibleUser
                this.tasks = this.group.tasks
                if (this.group.finalFile) this.colorFinalTask = "success"
                else this.colorFinalTask = "error"
                if(this.group.messagesWithTeacher.length !== 0){
                    axios.get('/messageWithTeacher/getLast', {
                        params: {
                            id_group: this.group.id
                        }
                    }).then((response) => {
                        this.lastMessageWithTeacher = response.data
                        if(!response.data.usersWhoSaw.map(el => el.id).includes(this.profile.id))
                            this.showNotificationOfMessageWithTeacher = true
                    });
                }


                if (this.group.theme == null) {
                    axios.get('/project/' + this.group.project.id).then((response) => {
                        this.project = response.data
                        this.deadline = this.project.term
                        this.availableThemes = this.project.themes.filter(el => el.available === true)
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
            if (this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>
    .nameTheme {
        text-align: center;
        color: #6B7372;
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
</style>