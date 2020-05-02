<template>
    <v-card class="my-1">
        <v-card-text primary-title>
            <div>
                <h4>Project:</h4>
                {{ project.thematics }}
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
            <div>
                <v-menu>
                    <template v-slot:activator="{ on }">
                        <v-btn dark icon v-on="on">
                            <v-icon size="30px">more_vert</v-icon>
                        </v-btn>
                    </template>

                    <v-list>
                        <v-list-tile @click="dialogEditGroup = true">
                            <v-list-tile-title>Edit</v-list-tile-title>
                        </v-list-tile>
                        <v-list-tile @click="dialogConfirmRemove = true">
                            <v-list-tile-title>Delete</v-list-tile-title>
                        </v-list-tile>
                    </v-list>
                </v-menu>
            </div>

        </v-card-actions>
        <v-dialog
                v-model="dialogConfirmRemove"
                max-width="290"
        >
            <v-card>
                <v-card-text>
                    Are you sure you want to delete this group?
                    All work of this group will also be deleted
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="removeGroup"
                    >
                        Yes
                    </v-btn>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="dialogConfirmRemove = false"
                    >
                        Cancel
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-model="dialogEditGroup" max-width="600px">
            <v-card>
                <v-card-title>
                    <span class="headline">Edit group</span>
                </v-card-title>
                <v-card-text>
                    <v-select
                            v-model="membersEdit"
                            :items="allStudents"
                            item-text="email"
                            label="Add members"
                            multiple
                            chips
                            return-object
                    >
                    </v-select>
                    <v-select
                            v-model="responsibleUserEdit"
                            :items="membersEdit"
                            item-text="email"
                            label="Сhoose responsible person"
                            chips
                            return-object
                    >
                    </v-select>
                    <label class="labelErrorValidationGroup" v-if="showLabelErrorValidationGroup">All fields must be
                        filled in</label>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogEditGroup = false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="updateGroup">Apply</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-card>
</template>

<script>
    import {mapState} from 'vuex'
    import {mapActions} from 'vuex'
    import axios from "axios"

    export default {
        name: "GroupRow",
        props: ['group', 'project'],
        computed: mapState(['profile']),
        data() {
            return {
                dialogConfirmRemove: false,
                dialogEditGroup: false,
                nameTheme: '',
                allStudents: [],
                membersEdit: this.group.participants,
                responsibleUserEdit: this.group.responsibleUser,
                showLabelErrorValidationGroup: false
            }
        },
        methods: {
            ...mapActions(['removeGroupAction', 'updateGroupAction','updateThemeAction']),
            removeGroup() {
                this.dialogConfirmRemove = false
                this.removeGroupAction(this.group).then(res => {
                    const index = this.project.groups.indexOf(this.group)
                    this.project.groups.splice(index, 1)
                    if(this.group.theme) {
                        this.group.theme.available = true
                        this.updateThemeAction(this.group.theme)
                    }
                })
            },
            updateGroup() {
                if (this.responsibleUserEdit) {
                    this.group.participants = this.membersEdit
                    this.group.responsibleUser = this.responsibleUserEdit
                    this.updateGroupAction(this.group)
                    this.dialogEditGroup = false
                    this.showLabelErrorValidationGroup = false
                } else {
                    this.showLabelErrorValidationGroup = true
                }

            }
        },
        watch: {
            membersEdit() {
                if (!this.membersEdit.find(el => el === this.responsibleUserEdit)) {
                    this.responsibleUserEdit = null
                }
            },
            responsibleUserEdit() {
                if (this.responsibleUserEdit) {
                    this.showLabelErrorValidationGroup = false
                }
            }
        },
        created() {
            this.nameTheme = this.group.theme ? this.group.theme.nameTheme : 'Not selected'
            axios.get('/user/allStudents').then((response) => {
                this.allStudents = response.data
            });
        },
    }
</script>

<style scoped>
    .labelErrorValidationGroup {
        color: red;
        margin-left: 10px;
    }
</style>