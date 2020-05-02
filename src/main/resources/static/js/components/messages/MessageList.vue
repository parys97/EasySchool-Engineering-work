<template>
    <v-list class="messageList" v-if="messages">
        <template v-for="(item, index) in messages">
            <message-row
                    :message="item"
                    :key="'item' + index"
            ></message-row>
        </template>
    </v-list>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'

    export default {
        name: "MessageList",
        components: { MessageRow },
        props: ['messages'],
    }
</script>

<style scoped>
    .messageList{
        height: 300px;
        width: 300px;
        overflow-x:hidden;
        overflow-y:auto;
        padding-left: 10px;
        padding-right: 10px;
    }
</style>