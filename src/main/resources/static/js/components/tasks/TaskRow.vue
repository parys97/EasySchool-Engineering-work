<template>
    <v-card class="my-5">
        <v-card-text primary-title v-bind:class="[task.responsibleUsers.find(el => el.id == profile.id) ? 'taskForResponsibleUsers' : 'taskForNotResponsibleUsers']">
            <v-layout row>
                <div class="progress">
                    <v-progress-circular
                            :rotate="-90"
                            :size="100"
                            :width="15"
                            :value="progressValue"
                            :color="progressColor"
                    >
                        {{ progressValue }}
                    </v-progress-circular>
                </div>
                <div>
                    <!--{{ task.id }}-->
                    <h4>Task name:</h4>
                    {{ task.nameTask }}
                    <h4>Responsible persons:</h4>
                    <v-list v-bind:class="[task.responsibleUsers.find(el => el.id == profile.id) ? 'taskForResponsibleUsers' : 'taskForNotResponsibleUsers']">
                        <template
                                v-for="(item, index) in task.responsibleUsers"
                        >
                            <div>{{ item.name }} ({{ item.email }})</div>
                        </template>
                    </v-list>
                    <h4>Deadline:</h4>
                    {{ task.deadline }}

                </div>
            </v-layout>

        </v-card-text>
        <v-card-actions>
            <v-spacer/>
            <v-layout v-if="group.responsibleUser.id === profile.id">
                <v-btn v-if="task.taskCommits && task.taskCommits.length > 0" value="Download" @click="downloadFile" small flat round>Download
                    actual file
                </v-btn>
                <div v-if="task.taskCommits && task.taskCommits.length > 0">({{ actualFile.name }})</div>
                <div v-else>File missing</div>
            </v-layout>
            <v-btn :disabled="disable" v-if="task.responsibleUsers.find(el => el.id == profile.id)" value="Goto" @click="showDetailsTask" small flat round>Go to task</v-btn>
            <v-btn v-if="!task.responsibleUsers.find(el => el.id == profile.id)" value="Goto" @click="dialogConfirmJoin = true" small flat round>Join</v-btn>
        </v-card-actions>
        <v-dialog
                v-model="dialogConfirmJoin"
                max-width="290"
        >
            <v-card>
                <v-card-text>
                    Are you sure you want to join this task?
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="joinToTask"
                    >
                        Yes
                    </v-btn>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="dialogConfirmJoin = false"
                    >
                        Cancel
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>


    </v-card>
</template>

<script>
    import axios from "axios"
    import { mapState } from 'vuex'
    import { mapActions } from 'vuex'

    export default {
        name: "TaskRow",
        props: ['task', 'group', 'disable'],
        computed: mapState(['profile']),
        data () {
            return {
                progressValue: null,
                progressColor: '',
                dialogConfirmJoin: false,
                actualFile: ''
            }
        },
        methods: {
            ...mapActions(['updateTaskAction']),
            joinToTask() {
                this.task.responsibleUsers.push(this.profile)
                this.updateTaskAction(this.task)
                this.dialogConfirmJoin = false
            },
            showDetailsTask(){
                this.$router.push('/taskDetails/'+this.task.id)
            },
            downloadFile() {
                axios
                    .get('/file/download/' + this.actualFile.id, {
                        responseType: "blob",
                    })
                    .then(response => {
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement("a");
                        link.href = url;
                        link.setAttribute("download", this.actualFile.name);
                        document.body.appendChild(link);
                        link.click();
                        link.parentNode.removeChild(link);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },

        },
        created() {
            if(this.profile.id === this.group.responsibleUser.id){
                if(this.task.taskCommits && this.task.taskCommits.length > 0){
                    this.actualFile = this.task.taskCommits[0].file
                    if(this.task.taskCommits.length > 1)
                        for(let i= 1; i<this.task.taskCommits.length; i++){
                            if(this.task.taskCommits[i].taskCommitTime > this.task.taskCommits[i-1].taskCommitTime)
                                this.actualFile = this.task.taskCommits[i].file
                        }
                }
            }

            this.progressValue = this.task.progress
            if(this.progressValue >= 0 && this.progressValue <= 25) this.progressColor = '#BD3B1B'
            else if(this.progressValue > 25 && this.progressValue <= 50) this.progressColor = '#8C710E'
            else if(this.progressValue > 50 && this.progressValue <= 75) this.progressColor = '#748C3B'
            else if(this.progressValue > 75 && this.progressValue <= 99) this.progressColor = '#9EAD17'
            else if(this.progressValue == 100) this.progressColor = 'success'
        },
    }


</script>

<style scoped>
    .taskForResponsibleUsers{
        background-color: #DBF9E7!important;
    }
    .taskForNotResponsibleUsers{
        background-color: #E3CDE1!important;
    }
    .progress{
        margin: 30px 50px 0px 30px ;
    }


</style>