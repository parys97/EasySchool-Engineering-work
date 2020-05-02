<template>
    <div v-bind:class="[message.author.id === profile.id ? 'ownersMessage info' : 'message']">
        <span v-if="message.author.id !== profile.id">
            <v-avatar size="24px" >
                <img
                        :src="message.author.userpic"
                        :alt="message.author.name"
                >
            </v-avatar>
        </span>
        <span v-if="message.author.id !== profile.id" class="authorName" >{{ message.author.name }}</span>
        <div>{{ message.text }}</div>
    </div>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        props: ['message'],
        computed: mapState(['profile']),
    }
</script>

<style>
    .message{
        background-color: #ededed;
        border-radius:10px;
        padding: 5px;
        margin-bottom: 5px;
        max-width: 160px;
        height: auto;
        word-break: break-word;
        clear: both;
    }
    .ownersMessage{
        color: white;
        /*background-color: #C49F0A;*/
        border-radius:10px;
        padding: 5px;
        margin-bottom: 5px;
        max-width: 160px;
        height: auto;
        word-break: break-word;
        float: right;
        clear: both;
    }
    .authorName{
        font-size: 11px;
        color: #909091;
    }
</style>