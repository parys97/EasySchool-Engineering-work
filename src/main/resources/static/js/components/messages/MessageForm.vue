<template>
    <v-text-field
            placeholder="Write something"
            v-model="text"
            @keyup.enter="save"
    />
</template>

<script>
    import {mapState} from 'vuex'
    import { sendMessage } from 'util/ws'

    export default {
        props: ['group'],
        computed: mapState(['profile']),
        data() {
            return {
                text: '',
                id: null,
            }
        },
        methods: {
            async save() {
                if(this.text !== ''){
                    const message = {
                        id: this.id,
                        text: this.text,
                        grp: {
                            id: this.group.id
                        },
                        author: this.profile
                    }
                    sendMessage(message)
                    this.text = ''
                    this.id = ''
                }
            }
        }
    }
</script>

<style>
</style>