<template>
    <v-container>
        <v-form ref="form" v-if="showUserRegistrationForm">
            <v-text-field
                    v-model="email"
                    :rules="emailRules"
                    label="E-mail"
                    required
            ></v-text-field>
            <v-select
                    v-model="selectRole"
                    :items="roles"
                    :rules="[v => !!v || 'Role is required']"
                    label="Role"
                    required
            ></v-select>
            <v-btn
                    color="success"
                    @click="register"
            >
                Sign Up
            </v-btn>
        </v-form>
        <v-alert v-if="showSuccessAlert"
                 :value="true"
                 type="success"
        >
            The user was successfully registrated.
        </v-alert>
        <v-alert v-if="showErrorAlert"
                 :value="true"
                 type="error"
        >
            {{ errorTextAlert }}
        </v-alert>
    </v-container>
</template>

<script>
    import axios from "axios";
    import { mapActions } from 'vuex'
    import { mapState } from 'vuex'

    export default {
        name: "Registration",
        computed: mapState(['profile']),
        data() {
            return {
                email: '',
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+/.test(v) || 'E-mail must be valid'
                ],
                selectRole: null,
                roles: [
                    'student',
                    'teacher'
                ],
                errorTextAlert: 'Error',
                showSuccessAlert: false,
                showErrorAlert: false,
                showUserRegistrationForm: true,

            }
        },
        methods: {
            ...mapActions(['addUserAction']),
            register() {
                if(this.$refs.form.validate()){
                    axios.get('/user').then((response) => {
                        if(!response.data.map(el => el.email).includes(this.email)){
                            this.addUserAction({
                                email: this.email,
                                role: this.selectRole,
                                name: "No Name",
                            }).then(res => {
                                this.showSuccessAlert = true
                                this.showErrorAlert = false
                                this.showUserRegistrationForm = false
                                this.email = ''
                                this.selectRole = null
                            })


                        }
                        this.errorTextAlert = 'The user with such mail already exists.'
                        this.showErrorAlert = true
                    });
                }
                else{
                    this.errorTextAlert = 'Incorrectly entered data.'
                    this.showErrorAlert = true
                }
            }
        },
        beforeMount() {
            if (this.profile.role !== 'admin') {
                this.$router.replace('/profile')
            }
        },
    }

</script>

<style scoped>

</style>