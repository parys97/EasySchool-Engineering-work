<template>
    <v-card class="my-1">
        <v-card-text primary-title>
            <div>
                <h4>Project:</h4>
                {{ projectThematic }}
                <h4>Requirements:</h4>
                {{ requirement }}
                <h4>Project theme:</h4>
                {{ nameTheme }}
                <h4>Responsible person:</h4>
                <span>
                    {{ group.responsibleUser.name }} ({{ group.responsibleUser.email }})
                    <v-icon v-if="group.responsibleUser.id === profile.id"
                            color="teal darken-3">supervisor_account</v-icon>
                </span>

                <h4>Members:</h4>
                <v-list>
                    <template
                            v-for="(item, index) in group.participants"
                    >
                        <div>{{ item.name }} ({{ item.email }})</div>
                    </template>
                </v-list>
            </div>
        </v-card-text>
        <v-card-actions class="teal lighten-2">
            <v-spacer></v-spacer>
            <v-btn dark value="Edit" @click="showDetailsGroup"
                   small flat round>
                Go to
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mapState} from 'vuex'
    import axios from "axios"

    export default {
        name: "GroupRow",
        props: ['group'],
        computed: mapState(['profile']),
        data() {
            return {
                nameTheme: '',
                projectThematic: '',
                requirement: '',
            }
        },
        methods: {
            showDetailsGroup() {
                this.$router.push('/groupDetails/' + this.group.id)
            },
        },

        created() {
            this.nameTheme = this.group.theme ? this.group.theme.nameTheme : 'Not selected'
            if(typeof (this.group.project) == 'number'){
                axios.get('/project/'+this.group.project).then((response) => {
                    this.projectThematic = response.data.thematics
                    this.requirement = response.data.requirement
                });
            }
            else{
                axios.get('/project/'+this.group.project.id).then((response) => {
                    this.projectThematic = response.data.thematics
                    this.requirement = response.data.requirement
                });
            }

        },
    }
</script>

<style scoped>

</style>