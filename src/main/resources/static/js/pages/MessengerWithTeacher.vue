<template>
    <v-container>
        <v-layout row>
            <v-text-field
                    placeholder="Write something"
                    v-model="text"
                    :counter="250"
                    @keyup.enter="send"

            />
            <v-btn color="info" @click="send">Send</v-btn>
        </v-layout>
        <message-with-teacher-list
                :messagesWithTeacher="messagesWithTeacher"
        ></message-with-teacher-list>
    </v-container>
</template>

<script>
    import axios from "axios"
    import { mapState } from 'vuex'
    import {mapActions} from 'vuex'
    import MessageWithTeacherList from 'components/messegesWithTeacher/MessageWithTeacherList.vue'

    export default {
        name: "MessengerWithTeacher",
        components: { MessageWithTeacherList },
        computed: mapState(['profile']),
        data() {
            return {
                group: {},
                messagesWithTeacher: [],
                text: '',
                lastMessageWithTeacher: {}
            }
        },

        methods: {
            ...mapActions(['updateMessageWithTeacherAction']),
            send() {
                if(this.text !== '' && this.text.length <= 250){
                    const messageWithTeacher = {
                        text: this.text,
                        grp: {
                            id: this.group.id
                        },
                        usersWhoSaw: [this.profile]
                    }
                    axios.post('/messageWithTeacher', messageWithTeacher).then(response => {
                        this.group.messagesWithTeacher.push(response.data)
                        this.text = ''
                    })
                }
            }
        },

        created() {
            axios.get('/group/' + this.$route.params.id).then((response) => {
                this.group = response.data
                this.messagesWithTeacher = response.data.messagesWithTeacher
                if(this.messagesWithTeacher.length !== 0){
                    axios.get('/messageWithTeacher/getLast', {
                        params: {
                            id_group: this.group.id
                        }
                    }).then((response) => {
                        this.lastMessageWithTeacher = response.data
                        if(!response.data.usersWhoSaw.map(el => el.id).includes(this.profile.id)){
                            this.lastMessageWithTeacher.usersWhoSaw.push(this.profile)
                            this.updateMessageWithTeacherAction(this.lastMessageWithTeacher)
                        }
                    });
                }
            });

        },

        beforeMount() {
            if (this.profile.role !== 'student' && this.profile.role !== 'teacher') {
                this.$router.replace('/profile')
            }
        },
    }
</script>

<style scoped>

</style>