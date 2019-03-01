<template>
    <div id="app">
        <div class="title">
            <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
                {{ issueInfo.issueTitle }}
            </router-link>
        </div>
        <div class="content" v-bind:class="{'open':open, 'close': !open}">
            <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
            
            <MarkDown :body="issueInfo.issueContent"></MarkDown>
            </router-link>
            <div class="openButtom" @click="open = !open">
                <span v-if="!open">
                    阅读全文<Icon type="ios-arrow-down" />
                </span>
                <span v-else>
                    收起<Icon type="ios-arrow-up" />
                </span>
            </div>
        </div>

        <Row type="flex" justify="start" align="middle" class="functionList">
            <i-col>
                <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
                    <Button type="primary" ghost size="large">写回答</Button>
                </router-link>
            </i-col>
            <i-col offset="1" v-if="issueInfo.issueReply > 0">
                <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
                    <Button type="text" size="large">
                        <Icon type="md-chatboxes" /> {{ issueInfo.issueReply }}个回答
                    </Button>
                </router-link>
            </i-col>

            <i-col offset="1" v-else>
                <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
                    <Button type="text" size="large">
                        <Icon type="md-chatboxes" /> 我来回答
                    </Button>
                </router-link>
            </i-col>

            <i-col offset="1">
                <Button type="text" size="large">
                    <Icon type="md-eye" /> {{ issueInfo.issueViews }} 次浏览
                </Button>
            </i-col>

            <i-col offset="1">
                <Button type="text" size="large">
                    <Icon type="ios-share-alt" /> 分享
                </Button>
            </i-col>
        </Row>
        <Divider/>
    </div>
</template>

<script>
export default {
    props: ['issueInfo'],
    data() {
        return {
            open: false
        }
    }
}
</script>

<style scoped>
    #app {
        font-size: 16px;
        background-color: white;
        margin: 1em 0px;
    }
    .title {
        font-size: 17px;
        font-weight: bold;
        margin-bottom: 0.5em;
        cursor: pointer;
    }
    .title a {
        color: black;
    }
    .content {
        overflow: hidden;
        position: relative;
        padding-bottom: 2em;
    }
    .content a {
        color: black;
    }
    .open {
        height: auto;
    }
    .close {
        height: 3em;
    }
    .openButtom {
        position: absolute;
        right:0px;
        bottom: 0px;
        padding-right: 1em;
        padding-left: 2em;
        background-color: white;
        color: #1D82FE;
        cursor: pointer;
    }
    .functionList{
        margin: 0.5em 0px;
    }
</style>
