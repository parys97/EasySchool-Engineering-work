<template>
    <v-list class="list" v-if="messagesWithTeacher">
        <template v-for="(item, index) in sortedMessages">
            <message-with-teacher-row
                    :messageWithTeacher="item"
                    :key="'item' + index"
            ></message-with-teacher-row>
        </template>
    </v-list>
</template>

<script>
    import MessageWithTeacherRow from 'components/messegesWithTeacher/MessageWithTeacherRow.vue'

    export default {
        name: "MessageWithTeacherList",
        components: {MessageWithTeacherRow},
        props: ['messagesWithTeacher'],
        computed: {
            sortedMessages: function () {
                return this.messagesWithTeacher.sort((a, b) => -(a.id - b.id))
            }
        },

    }
</script>

<style scoped>
    .list{
        display: inline-block;
        width: 100%;
    }
</style>