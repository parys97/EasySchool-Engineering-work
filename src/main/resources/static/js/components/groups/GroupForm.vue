<template>
    <v-card class="teal lighten-3">
        <v-layout column>
            <v-select
                    v-model="members"
                    :items="allStudents"
                    item-text="email"
                    label="Add members"
                    multiple
                    chips
                    return-object
            >
            </v-select>
            <v-select
                    v-model="responsibleUser"
                    :items="members"
                    item-text="email"
                    label="Сhoose responsible person"
                    chips
                    return-object
            >
            </v-select>
            <label class="labelErrorValidationGroup" v-if="showLabelErrorValidationGroup">All fields must be filled in</label>
            <v-btn @click="save">
                Add
            </v-btn>
        </v-layout>
    </v-card>



</template>


<script>
    import axios from "axios";

    export default {
        name: "GroupForm",
        props: ['project'],
        data() {
            return{
                allStudents: [],
                members: [],
                responsibleUser: null,
                showLabelErrorValidationGroup: false
            }
        },
        methods: {
            save() {
                if(this.responsibleUser){
                    axios.post('/group',{
                        project: this.project,
                        participants: this.members,
                        responsibleUser: this.responsibleUser
                    }).then(response => {
                        this.project.groups.push(response.data)
                        this.members = []
                        this.responsibleUser = null
                        this.showLabelErrorValidationGroup = false

                    })
                }
                else{
                    this.showLabelErrorValidationGroup = true
                }
            }
        },

        watch: {
            members() {
                if (!this.members.find(el => el === this.responsibleUser)) {
                    this.responsibleUser = null
                }
            },
            responsibleUser() {
                if (this.responsibleUser) {
                    this.showLabelErrorValidationGroup = false
                }
            }
        },
        created(){
            axios.get('/user/allStudents').then((response) => {
                this.allStudents = response.data
            });
        }
    }
</script>

<style scoped>
    .labelErrorValidationGroup{
        color: red;
        margin-left: 10px;
    }

</style>