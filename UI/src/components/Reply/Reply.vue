<template>
    <div class="replyInfo">
        <Row :id="replyInfo.replyId">
            <i-col>
                <Row type="flex" justify="start" align="middle">
                    <i-col>
                        <Avatar 
                        shape="square" 
                        icon="ios-person" 
                        :src="imgUrl"
                        size="large" />
                    </i-col>
                    <i-col offset="1">
                        <h3>{{ replyInfo.userInfo.userName }}</h3>
                    </i-col>
                </Row>

                <Row type="flex" justify="start" style="margin:0.5em 0px;">
                    <i-col style="font-size:13px">
                        {{ replyInfo.replyPraise }} 人赞同了该回答
                    </i-col>
                </Row>

                <!-- 内容 -->
                <Row type="flex" justify="start" style="margin:0.5em 0px;">
                    <i-col span="24">
                        <div 
                        style="position: relative; overflow: hidden;"
                        v-bind:class="{'height': show,'autoHeight': !show}">
                            <MarkDown :body="replyInfo.replyContent"></MarkDown>
                            <div class="clickShow" v-if="show" @click="openReply">
                                <div class="clickShowButtom" span="24">
                                    <Icon type="ios-arrow-down" />&nbsp;展开阅读全文
                                </div>
                            </div>
                        </div>
                    </i-col>
                </Row>

                <Row type="flex" justify="start" style="margin:0.5em 0px;">
                    <i-col span="17">
                        <Row type="flex" justify="start">
                            <i-col>
                                <Button size="large" v-if="!good" @click="agreeWith(replyInfo)">
                                    <Icon type="md-arrow-dropup" />&nbsp;赞同 &nbsp;{{ replyInfo.replyPraise }}
                                </Button>
                                <Button size="large" type="primary" v-else @click="cancelApproval(replyInfo)">
                                    <Icon type="md-arrow-dropup"/>&nbsp;已赞同 &nbsp;{{ replyInfo.replyPraise }}
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

                    </i-col>

                    <i-col span="7">
                        <Row type="flex" justify="end" v-if="long && !show">
                            <Button type="text" size="large" @click="openReply">收起</Button>
                        </Row>
                    </i-col>
                </Row>

            </i-col>
        </Row>

    </div>
</template>

<script>
export default {
    props: ['replyInfo','userInfo'],
    data() {
        return {
            //点赞
            good: false,
            long: false,
            show: false,
            imgUrl: this.localhost + this.replyInfo.userInfo.userPortraitUrl,
            href: "#" + this.replyInfo.replyId,

            replyList: [

            ],
            replyPage:"",
            replyPageCode: 1,
            modal:false,
            replyInput: ""
        }
    },
    mounted() {
        this.show = this.replyInfo.replyContent.length > 500;
        this.long = this.show;

        this.whetherAgreeWith(this.replyInfo);

        this.getReplyReply(1);
    },
    methods: {
        openReply() {   //显示或收起全文
            this.show = !this.show;

            //跳转
            const returnEle = document.getElementById(this.replyInfo.replyId);
            if (!!returnEle) {
                returnEle.scrollIntoView(true);
            }
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
        getReplyReply(pageCode) {   //获取回复的回复
            axios({
                url: "/api/reply/reply/" + this.replyInfo.replyId + "/" + pageCode,
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
                fd.append('issueId', this.replyInfo.replyIssueId);
                fd.append('content', this.replyInput);
                fd.append('replyId', this.replyInfo.replyId);

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
    }
}
</script>

<style>
    .replyInfo {
        font-size: 16px;
        background-color: white;
        margin: 1em 0px;
    }
    .my-modal-content {
        border: 0;
    }
    .my-modal-content .ivu-modal-content {
        position: relative;
        background-color: #fff;
        border: 0;
        border-radius: 6px;
        background-clip: padding-box;
        box-shadow: 0 4px 12px rgba(0,0,0,.15);

        height: 480px;
        overflow: hidden;
    }
    .ivu-modal-content-reply {
        height: 350px;
        overflow-y: scroll;
    }
    .replyInfo .autoHeight {
        height: auto;
    }
    .replyInfo .height {
        height: 10em;
    }
    .replyInfo .clickShow {
        position: absolute;
        top: 0px;
        left: 0px;
        bottom: 0px;
        right: 0px;
        background: linear-gradient(to top ,white, transparent);
        z-index: 5;
    }
    .replyInfo .clickShowButtom {
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        text-align: center; 
        color:#007ACC;
        cursor: pointer;
    }
    .replyInfo .food {
        position: fixed;
        bottom: 0px;
    }
    
</style>
