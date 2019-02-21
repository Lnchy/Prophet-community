<template>
<div id="app">
    <div id="container"><img class="png" src="/image/404.png" /> <img class="png msg" src="/image/404_msg.png" />
        <p><router-link to="/"><a><img class="png" src="/image/404_to_index.png" /></a></router-link></p>
    </div>
    <div id="cloud" class="png"></div>
</div>
</template>
<script>
export default {
}
</script>


<style scoped>
#app {
    position: fixed;
    height: 100%;
    width: 100%;
    background: url("/image/error_bg.jpg") repeat-x scroll 0 0 #67ACE4;
}
body, div {
    margin: 0;
    padding: 0;
}
#container {
    margin: 0 auto;
    padding-top: 50px;
    text-align: center;
    width: 560px;
}
#container img {
    border: medium none;
    margin-bottom: 50px;
}
#container .error {
    height: 200px;
    position: relative;
}
#container .error img {
    bottom: -50px;
    position: absolute;
    right: -50px;
}
#container .msg {
    margin-bottom: 65px;
}
#cloud {
    background: url("/image/error_cloud.png") repeat-x scroll 0 0 transparent;
    bottom: 0;
    height: 170px;
    position: absolute;
    width: 100%;
}
</style>
