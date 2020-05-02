<template>
    <v-card class="my-1">
        <v-card-text primary-title >
            <v-layout row>
                <div>Progress:</div>
                {{ taskCommit.progress }}%
                <v-spacer/>
                <div>Date: {{ dateCommit }}<v-spacer/>Time: {{ timeCommit }}</div>

                <v-spacer/>
                <div class="text-xs-center">
                    <v-btn @click="downloadFile">Download</v-btn>
                    <div>{{ taskCommit.file.name }}</div>
                </div>
            </v-layout>
            <br>
            <div>{{ taskCommit.comment }}</div>
        </v-card-text>
        <v-card-actions>

        </v-card-actions>
    </v-card>
</template>

<script>
    import axios from "axios";

    export default {
        name: "TaskCommitRow",
        props: ['taskCommit'],
        data () {
            return {
                dateCommit: '',
                timeCommit: ''
            }
        },
        methods: {
            downloadFile(){
                axios
                    .get('/file/download/'+this.taskCommit.file.id, {
                        responseType: "blob",
                    })
                    .then(response => {
                        const url = window.URL.createObjectURL(new Blob([response.data]));
                        const link = document.createElement("a");
                        link.href = url;
                        link.setAttribute("download", this.taskCommit.file.name);
                        document.body.appendChild(link);
                        link.click();
                        link.parentNode.removeChild(link);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        },
        created() {
            this.dateCommit = this.taskCommit.taskCommitTime.split('T')[0]
            this.timeCommit = this.taskCommit.taskCommitTime.split('T')[1].split('.')[0]
        }
    }
</script>

<style scoped>

</style>