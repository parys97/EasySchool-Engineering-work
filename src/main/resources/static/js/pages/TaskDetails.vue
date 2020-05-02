<template>
    <v-container>
        <div class="text-xs-center mb-2">
            <h2>{{ nameTask }}</h2>
        </div>
        <div class="text-xs-center">
            <v-progress-circular
                    :rotate="-90"
                    :size="100"
                    :width="15"
                    :value="progressTaskValue"
                    :color="progressColor"
            >
                {{ progressTaskValue }}
            </v-progress-circular>
        </div>
        <div class="resposibleUsers text-xs-center">
            <v-list class=" teal lighten-5 mt-3">
                <v-layout>
                    <div class="mr-2 ml-2"
                         v-for="(item, i) in responsibleUsers"
                         :key="i"
                    >
                        <v-avatar v-if="item.userpic" size="40px">
                            <img
                                    :src="item.userpic"
                                    :alt="item.name"
                            >
                        </v-avatar>

                        <v-avatar
                                v-else
                                size="40px"
                                color="teal lighten-1"
                        >
                            <v-icon dark>account_circle</v-icon>
                        </v-avatar>
                        <div>{{ item.name}}</div>

                    </div>
                </v-layout>

            </v-list>
        </div>

        <div v-if="taskCommits.length !== 0">
            <div class="title mb-3">Commits</div>
            <task-commit-list
                    :task-commits="taskCommits"
            ></task-commit-list>
        </div>
        <div class="text-xs-center">
            <v-btn :disabled="delayOfTask" @click="dialogAddTaskCommit = true">Add commit</v-btn>
        </div>

        <v-dialog v-model="dialogAddTaskCommit" max-width="600px">
            <v-card>
                <v-card-title>
                    <span class="headline">Add task commit</span>
                </v-card-title>
                <v-card-text>
                    <v-form ref="form">
                        <v-textarea
                                label="Add comment"
                                :counter="200"
                                :rules="taskCommitCommentValidation"
                                v-model="comment"
                        />
                        <input type="file" id="fileInput" ref="file" v-on:change="handleFileUpload()" required="true"/>
                        <v-btn>
                            <label for="fileInput" class="labelInputFile">Choose file</label>
                        </v-btn>
                        <v-label v-if=" file !== '' ">{{ file.name }}</v-label>
                        <v-label v-if=" file === '' ">Not selected</v-label>

                        <v-slider
                                label="Set a progress"
                                v-model="progressSlider"
                                :thumb-size="24"
                                thumb-label="always"
                        ></v-slider>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogAddTaskCommit = false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="addTaskCommit">Add</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    import {mapActions} from 'vuex'
    import axios from "axios";
    import TaskCommitList from "components/taskCommits/taskCommitList.vue";

    export default {
        name: "TaskDetails",
        components: {TaskCommitList},
        computed: mapState(['profile']),
        data() {
            return {
                task: null,
                delayOfTask: false,
                nameTask: '',
                responsibleUsers: [],
                taskCommits: [],
                progressSlider: null,
                progressTaskValue: null,
                progressColor: '',
                comment: '',
                taskCommitCommentValidation: [
                    v => v.length <= 200 || 'Maximum lenght is 200 characters',
                ],
                dialogAddTaskCommit: false,
                file: '',
            }
        },
        methods: {
            ...mapActions(['addTaskCommitAction', 'updateTaskAction']),
            initPage() {
                axios.get('/task/' + this.$route.params.id).then((response) => {
                    this.task = response.data
                    this.nameTask = this.task.nameTask
                    this.responsibleUsers = this.task.responsibleUsers
                    this.taskCommits = this.task.taskCommits
                    this.progressSlider = this.task.progress
                    this.progressTaskValue = this.task.progress
                    if (this.progressTaskValue >= 0 && this.progressTaskValue <= 25) this.progressColor = '#BD3B1B'
                    else if (this.progressTaskValue > 25 && this.progressTaskValue <= 50) this.progressColor = '#8C710E'
                    else if (this.progressTaskValue > 50 && this.progressTaskValue <= 75) this.progressColor = '#748C3B'
                    else if (this.progressTaskValue > 75 && this.progressTaskValue <= 99) this.progressColor = '#9EAD17'
                    else if (this.progressTaskValue == 100) this.progressColor = 'success'
                    this.checkDeadline()
                });
            },
            checkDeadline() {
                let today = new Date()
                let taskDeadline = this.task.deadline
                let yearDeadline = parseInt(taskDeadline.toString().split("-")[0])
                let monthDeadline = parseInt(taskDeadline.toString().split("-")[1])
                let dayDeadline = parseInt(taskDeadline.toString().split("-")[2])
                let taskDeadlineInDateFormat = new Date(yearDeadline, monthDeadline - 1, dayDeadline)
                if (today.getTime() > taskDeadlineInDateFormat.getTime())
                    this.delayOfTask = true
            },
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            },
            addTaskCommit() {
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
                            if (this.comment == '') this.comment = "No comment"
                            this.addTaskCommitAction({
                                comment: this.comment,
                                progress: this.progressSlider,
                                task: {
                                    id: this.task.id
                                },
                                file: {
                                    id: response.data.id
                                }
                            }).then(res => {
                                this.task.progress = this.progressSlider
                                this.updateTaskAction(this.task).then(res => {
                                    this.comment = ''
                                    this.file = ''
                                    this.dialogAddTaskCommit = false
                                    this.initPage()
                                })
                            })

                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            },

        },

        created() {
            this.initPage()
        },

        beforeMount() {
            if (this.profile.role !== 'student') {
                this.$router.replace('/profile')
            }
        },
    }


</script>

<style scoped>
    .slider {
        max-width: 500px;

    }

    .resposibleUsers {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    #fileInput {
        width: 0;
        height: 0;
    }

    .labelInputFile {

    }
</style>