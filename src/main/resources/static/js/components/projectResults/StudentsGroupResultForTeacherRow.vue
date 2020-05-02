<template>
    <tr :bgcolor="color">
        <td>{{ project.thematics }}</td>
        <td v-if="group.theme">{{ group.theme.nameTheme }}</td>
        <td v-else>None</td>
        <td>{{ group.responsibleUser.email }}</td>
        <td>
            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn
                            small
                            flat
                            color="info"
                            class="ma-2"
                            v-on="on"
                    >list of members
                    </v-btn>
                </template>
                <v-list>
                    <v-list-tile
                            v-for="(member, i) in group.participants"
                            :key="i"
                            @click=""
                    >
                        <v-list-tile-title>{{ member.email }}</v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
        </td>
        <td>{{ project.term }}</td>
        <td v-if="group.evaluation">{{ group.evaluation.evaluation }}</td>
        <td v-else>None</td>
    </tr>
</template>

<script>
    import axios from "axios"
    export default {
        name: "StudentsGroupResultForTeacherRow",
        props: ['group'],
        data() {
            return {
                color: 'white',
                project: {}
            }
        },

        created() {
            if (this.group.evaluation) {
                let evaluation = this.group.evaluation.evaluation
                if (evaluation >= 0 && evaluation <= 0.5) this.color = '#FF96A0'
                else if (evaluation > 0.5 && evaluation <= 1.5) this.color = '#FFC69B'
                else if (evaluation > 1.5 && evaluation <= 3) this.color = '#FFF199'
                else if (evaluation > 3 && evaluation <= 4) this.color = '#BEFF95'
                else if (evaluation > 4 && evaluation <= 5) this.color = '#8CFFA8'
            }

            if(typeof (this.group.project) == 'number'){
                axios.get('/project/' + this.group.project).then((response) => {
                    this.project = response.data
                });
            } else{
                axios.get('/project/' + this.group.project.id).then((response) => {
                    this.project = response.data
                });
            }
        }
    }
</script>

<style scoped>

</style>