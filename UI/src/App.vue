<template>
    <div id="app">
        <router-view :key="key"></router-view>
    </div>
</template>

<script>
export default {
    computed: {
        key() {
                return this.$route.name !== undefined? this.$route.name +new Date(): this.$route +new Date()
        }
    }
}
</script>

<style>
    #app {
        font-size: 16px;
        background-color: #F6F6F6;
    }
    /* 分割线 */
    .replyInfo-divider {
        display: block;
        height: 1px;
        width: 100%;
        min-width: 100%;
        margin: 10px 0;
        clear: both;
        background: #e8eaec;
    }
    table{ 
        table-layout:fixed; 
        empty-cells:show; 
        border-collapse: collapse; 
        margin:0 auto; 
        border:1px solid #cad9ea; 
        color:#666; 
    } 
    td{ 
        height:30px; 
    }
    table th { 
        background-repeat:repeat-x; 
        height:30px; 
    }
    table td,table th{ 
        border:1px solid #cad9ea; 
        padding:0 1em 0; 
    } 
    table tr.alter{ 
        background-color:#f5fafe; 
    } 
</style>
