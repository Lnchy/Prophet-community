<template>
    <div id="app">
        <div class="title">
            <span v-if="issueInfo.reply == null">
                <router-link :to="{name: 'issue', params: {'id':issueInfo.issueId }}">
                    {{ issueInfo.issueTitle }}
                </router-link>
            </span>
            <span v-else>
                <router-link :to="{name: 'replyInfo', params: {'id':issueInfo.reply.replyId }}">
                    {{ issueInfo.issueTitle }}
                </router-link>
            </span>
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

        <!-- 单纯的问题 -->
        <Row type="flex" justify="start" align="middle" class="functionList" v-if="issueInfo.reply == null">
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

        <!-- 问题带回复 -->
        <Row type="flex" justify="start" align="middle" class="functionList" v-else>
            <i-col>
                <Button size="large" v-if="!good" @click="agreeWith(issueInfo.reply)">
                    <Icon type="md-arrow-dropup" />&nbsp;赞同 &nbsp;{{ issueInfo.reply.replyPraise }}
                </Button>
                <Button size="large" type="primary" v-else @click="cancelApproval(issueInfo.reply)">
                    <Icon type="md-arrow-dropup"/>&nbsp;已赞同 &nbsp;{{ issueInfo.reply.replyPraise }}
                </Button>
            </i-col>

            <i-col offset="1">
                <Button size="large" type="text" @click="modal = true">
                    <Icon type="ios-chatboxes" /> {{ replyPage.count }}条评论
                </Button>

                <Modal v-model="modal" width="600" footer-hide class-name="my-modal-content">
                    <p slot="header">
                        评论列表
                    </p>
                    <div class="ivu-modal-content-reply" v-if="replyList.length > 0">
                        <Row 
                        type="flex" 
                        justify="start" 
                        v-for="(item, index) in replyList" 
                        :key="index">
                            <i-col span="24">
                                <Row type="flex" justify="start" align="middle">
                                    <i-col>
                                        <Avatar 
                                        shape="square" 
                                        icon="ios-person" 
                                        :src="item.url" 
                                        size="large" />
                                    </i-col>
                                    <i-col style="margin-left:1em">
                                        <h3>{{ item.userInfo.userName }}</h3>
                                    </i-col>
                                </Row>
                                <Row type="flex" justify="start" style="margin-top:0.5em">
                                    <i-col style="font-size: 16px;" offset="2" span="22">
                                        {{ item.replyContent }}
                                    </i-col>
                                </Row>

                                <Row type="flex" justify="start" >
                                    <i-col style="font-size: 16px;" offset="2" span="22">
                                        <Button type="text"
                                        @click="agreeWith(item)"
                                        v-if="!item.good">
                                            <Icon type="ios-thumbs-up" />
                                            {{ item.replyPraise }}
                                        </Button>

                                        <Button type="text"
                                        style="color: #007ACC;"
                                        @click="cancelApproval(item)"
                                        v-else>
                                            <Icon type="ios-thumbs-up" />
                                            {{ item.replyPraise }}
                                        </Button>
                                    </i-col>

                                </Row>

                                <div class="replyInfo-divider">
                                </div>
                            </i-col>
                        </Row>

                        <Row type="flex" justify="center">
                            <i-col>
                                <!-- 分页 -->
                                <Page 
                                :current="replyPage.pageCode"
                                :total="replyPage.pageCount * 10" 
                                @on-change="changePage" 
                                size="small" />
                            </i-col>
                        </Row>
                    </div>

                    <div class="ivu-modal-content-reply" v-else>
                        没有评论
                    </div>
                    <Row type="flex" justify="start" style="margin-top:1em;background-color:white;">
                        <i-col span="20">
                            <Input size="large" v-model="replyInput" placeholder="写下你的评论"/>
                        </i-col>
                        <i-col offset="1">
                            <Button size="large" type="primary" @click="addReplyReply">发布</Button>
                        </i-col>
                    </Row>
                    <div slot="footer" style="align:left;">
                        
                    </div>
                </Modal>

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
            //展开全文
            open: false,
            //评论显示
            modal: false,
            //分页
            replyPage: "",
            //回答的回复列表
            replyList: [],
            //是否点赞
            good: false,
            //回答回复输入框
            replyInput: ""
        }
    },
    methods: {
        agreeWith(replyInfo) {       //点赞
            replyInfo.good = true;
            axios({
                url: "/api/praise/agreeWith/" + replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    
                    //获取点赞结果
                    this.whetherAgreeWith(replyInfo);
                    //赞加一
                    replyInfo.replyPraise = replyInfo.replyPraise + 1;
                }
            })
        },
        cancelApproval(replyInfo) {  //取消赞
            replyInfo.good = false;
            axios({
                url: "/api/praise/cancelApproval/" + replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    //获取点赞结果
                    if (resp.data.resultStatus) {
                        //获取点赞结果
                        this.whetherAgreeWith(replyInfo);
                        //赞减一
                        replyInfo.replyPraise = replyInfo.replyPraise - 1;
                    }
                }
            })
        },
        whetherAgreeWith(replyInfo) {      // 有没有赞这个回答
            axios({
                url:"/api/praise/whetherAgreeWith/" + replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                if (replyInfo.replyReplyId == null) {
                    this.good = resp.data.resultStatus;
                } else {
                    replyInfo.good = resp.data.resultStatus;
                }
            })
        },
        getReplyReply(pageCode) {   //获取回复的回复
            axios({
                url: "/api/reply/reply/" + this.issueInfo.reply.replyId + "/" + pageCode,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    if (resp.data.data != null) {
                        this.replyList = resp.data.data;

                        this.replyList = this.replyList.map((item, index) => {
                            return Object.assign(
                                item,
                                {url: this.localhost + item.userInfo.userPortraitUrl},
                                {good: false});
                        })

                        this.replyList.forEach((item) => {
                            this.whetherAgreeWith(item);
                        })

                    }

                    this.replyPage = resp.data.pageHelp;
                }
            });

        },
        changePage(pageCode) {      //更改页面
            this.getReplyReply(pageCode);
        },
        addReplyReply() {       //添加一条评论
            if (this.replyInput.length < 1) {
                this.$Message.error("请写下您的评论");
            } else {
                let fd = new FormData();
                fd.append('issueId', this.issueInfo.reply.replyIssueId);
                fd.append('content', this.replyInput);
                fd.append('replyId', this.issueInfo.reply.replyId);

                axios({
                    url: "/api/reply/reply/add",
                    method: "post",
                    data: fd
                })
                .then((resp) => {
                    if (resp.data.resultStatus) {
                        this.$Message.success('评论成功');
                        //刷新评论列表
                        this.getReplyReply(1);
                        //清空输入框
                        this.replyInput = "";
                    } else {
                        this.$Message.error('网络错误，请稍后再试');
                    }
                })
            }
        }
    },
    mounted() {
        if (this.issueInfo.reply != null) {
            this.whetherAgreeWith(this.issueInfo.reply)

            this.getReplyReply(1);
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
