<template>
    <div class="markdownredue">
        <vue-markdown :source="body"></vue-markdown>
    </div>
</template>

<script>
import VueMarkdown from 'vue-markdown'

export default {
    props:['body'],
    data() {
        return{
            
        }
    },
    components: {
        VueMarkdown
    }
}
</script>

<style>
 .markdownredue img {
    width: 60%;
    text-align: center;
    margin: 1em auto;
 }
</style>
