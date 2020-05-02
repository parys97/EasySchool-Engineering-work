<template>
    <v-list-tile class="white">
        <v-list-tile-content>
            <v-list-tile-title>
                {{theme.nameTheme}}
            </v-list-tile-title>
        </v-list-tile-content>
        <v-list-tile-action v-if="$router.currentRoute.path.includes('projectEditForm')">
            <v-menu>
                <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on" >
                        <v-icon  size="30px" color="teal lighten-2">more_horiz</v-icon>
                    </v-btn>
                </template>
                <v-list>
                    <v-list-tile @click="dialogEditTheme = true">
                        <v-list-tile-title>Edit</v-list-tile-title>
                    </v-list-tile>
                    <v-list-tile @click="dialogConfirmRemove = true">
                        <v-list-tile-title>Delete</v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
        </v-list-tile-action>
        <v-snackbar
                v-model="snackbarError"
                color="error"
                :timeout="timeout"
                vertical
                top
        >
            Sorry, you can't delete this theme because it was already selected by the group
            <v-btn
                    dark
                    flat
                    @click="snackbarError = false"
            >
                Close
            </v-btn>
        </v-snackbar>
        <v-dialog v-model="dialogEditTheme" max-width="600px">
            <v-card>
                <v-card-title>
                    <span class="headline">Edit theme</span>
                </v-card-title>
                <v-card-text>
                    <v-text-field ref="form"
                                  :counter="100"
                                  :rules = 'themeValidation'
                                  v-model="editNameTheme"
                                  @keyup.enter="updateTheme"

                    />
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click="dialogEditTheme = false">Close</v-btn>
                    <v-btn color="blue darken-1" flat @click="updateTheme">Apply</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog
                v-model="dialogConfirmRemove"
                max-width="290"
        >
            <v-card>
                <v-card-text>
                    Are you sure you want to delete this theme?
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                            color="teal darken-4"
                            flat="flat"
                            @click="removeTheme"
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
    </v-list-tile>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: "ThemeRow",
        props: ['theme', 'project'],
        data () {
            return {
                snackbarError: false,
                dialogConfirmRemove: false,
                dialogEditTheme: false,
                timeout: 8000,
                editNameTheme: this.theme.nameTheme,
                themeValidation: [
                    v => v.length <= 100 || 'Maximum lenght is 100 characters',
                    v => v.length > 0 || 'Minimum lenght is 1 characters',
                ],
            }
        },
        methods: {
            ...mapActions(['removeThemeAction' , 'updateThemeAction']),
            removeTheme() {
                if(this.theme.available === true){
                    this.dialogConfirmRemove = false
                    this.removeThemeAction(this.theme).then(res =>{
                        const index = this.project.themes.indexOf(this.theme)
                        this.project.themes.splice(index, 1)
                    })
                }
                else {
                    this.dialogConfirmRemove = false
                    this.snackbarError = true
                }
            },
            updateTheme(){
                if(this.$refs.form.validate()) {
                    this.theme.nameTheme = this.editNameTheme
                    this.updateThemeAction(this.theme)
                    this.dialogEditTheme = false
                }
            }
        },
    }
</script>

<style scoped>
    .actions{
        float: right;
    }

</style>