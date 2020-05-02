<template>
    <v-card  class="teal lighten-3">
        <v-layout row>
            <v-text-field ref="form"
                    label="Add themes"
                    :counter="100"
                    :rules="themeValidation"
                    placeholder="Write something"
                    v-model="nameTheme"
                    @keyup.enter="save"
            />
            <v-btn @click="save">
                Add
            </v-btn>
        </v-layout>
    </v-card>
</template>

<script>
    import axios from "axios";

    export default {
        name: "ThemeForm",
        props: ['project'],
        data() {
            return {
                nameTheme: '',
                themeValidation: [
                    v => v.length <= 100 || 'Maximum lenght is 100 characters',
                ],
            }
        },
        methods: {
            save() {
                if(this.$refs.form.validate() && this.nameTheme != ''){
                    axios.post('/theme',{
                        nameTheme: this.nameTheme,
                        project: {
                            id: this.project.id
                        },
                        available: true
                    }).then(response => {
                        this.project.themes.push(response.data)
                        this.nameTheme = ''
                    })

                }
            }
        }
    }
</script>

<style scoped>

</style>