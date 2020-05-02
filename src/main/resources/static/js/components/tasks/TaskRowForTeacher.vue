<template>
    <v-card class="my-5">
        <v-card-text primary-title>
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
                    <!--                <span v-bind:class="[task.responsibleUser.id === profile.id ? 'teal darken-4 forResponsibleUserTask' : '']">-->
                    <!--                        {{ task.responsibleUser.name }} ({{ task.responsibleUser.email }})-->
                    <!--                    </span>-->
                    <h4>Deadline:</h4>
                    {{ task.deadline }}

                </div>
            </v-layout>
        </v-card-text>
        <v-card-actions>
            <v-layout row>
                <v-spacer/>
                <div v-if="task.taskCommits.length > 0">({{ actualFile.name }})</div>
                <v-btn v-if="task.taskCommits.length > 0" value="Download" @click="downloadFile" small flat round>Download
                    actual file
                </v-btn>
                <div v-if="task.taskCommits.length === 0">File missing</div>
            </v-layout>
        </v-card-actions>

    </v-card>
</template>

<script>
    import {mapState} from 'vuex'
    import axios from "axios"

    export default {
        name: "TaskRowForTeacher",
        props: ['task', 'group'],
        computed: mapState(['profile']),

        data() {
            return {
                progressValue: null,
                progressColor: '',
                actualFile: ''
            }
        },
        methods: {
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
            if(this.task.taskCommits.length > 0){
                this.actualFile = this.task.taskCommits[0].file
                if(this.task.taskCommits.length > 1)
                for(let i= 1; i<this.task.taskCommits.length; i++){
                    if(this.task.taskCommits[i].taskCommitTime > this.task.taskCommits[i-1].taskCommitTime)
                        this.actualFile = this.task.taskCommits[i].file
                }
            }
            this.progressValue = this.task.progress
            if (this.progressValue >= 0 && this.progressValue <= 25) this.progressColor = '#BD3B1B'
            else if (this.progressValue > 25 && this.progressValue <= 50) this.progressColor = '#8C710E'
            else if (this.progressValue > 50 && this.progressValue <= 75) this.progressColor = '#748C3B'
            else if (this.progressValue > 75 && this.progressValue <= 99) this.progressColor = '#9EAD17'
            else if (this.progressValue == 100) this.progressColor = 'success'
        },
    }


</script>

<style scoped>

    .progress {
        margin: 30px 50px 0px 30px;
    }


</style>