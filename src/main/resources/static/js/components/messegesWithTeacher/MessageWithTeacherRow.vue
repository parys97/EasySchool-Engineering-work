<template>
    <v-card v-bind:class="[messageWithTeacher.author.role == 'student' ? 'studentsMessage' : 'teachersMessage']">
        <v-card-text v-bind:class="[messageWithTeacher.author.id === profile.id && messageWithTeacher.author.role == 'student' ? 'messageForAuthor' : '']">
            <v-layout row>
                <span>
                    <v-avatar size="24px">
                        <img
                                :src="messageWithTeacher.author.userpic"
                                :alt="messageWithTeacher.author.name"
                        >
                    </v-avatar>
                </span>
                <div class="ml-1"> {{ messageWithTeacher.author.name }}</div>
            </v-layout>
            <div class="ma-2">{{ messageWithTeacher.text }}</div>

        </v-card-text>
        <v-card-actions>
            <v-spacer/>
            <div>{{ messageWithTeacher.creationDate }}</div>
        </v-card-actions>
    </v-card>
</template>

<script>
    import { mapState } from 'vuex'

    export default {
        props: ['messageWithTeacher'],
        computed: mapState(['profile']),
    }
</script>

<style scoped>
    .teachersMessage{
        width: 80%;
        float: left;
        clear: both;
        margin: 0px 0px 20px 10px;
    }
    .studentsMessage{
        width: 80%;
        float: right;
        clear: both;
        margin: 0px 10px 20px 0px;
    }
    .messageForAuthor{
        background-color: #DBF9E7;
    }

</style>