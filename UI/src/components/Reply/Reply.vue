<template>
    <div id="app">
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
                        {{ replyInfo.praiseNum }} 人赞同了该回答
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
                                <Button size="large" v-if="!good" @click="agreeWith">
                                    <Icon type="md-arrow-dropup" />&nbsp;赞同 &nbsp;{{ replyInfo.praiseNum }}
                                </Button>
                                <Button size="large" type="primary" v-else @click="cancelApproval">
                                    <Icon type="md-arrow-dropup"/>&nbsp;已赞同 &nbsp;{{ replyInfo.praiseNum }}
                                </Button>
                            </i-col>

                            <i-col offset="1">
                                <Button size="large" type="text">
                                    <Icon type="ios-chatboxes" /> 条评论
                                </Button>
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

            ]
        }
    },
    mounted() {
        this.show = this.replyInfo.replyContent.length > 500;
        this.long = this.show;
        this.whetherAgreeWith();
    },
    methods: {
        openReply() {
            this.show = !this.show;

            //跳转
            const returnEle = document.getElementById(this.replyInfo.replyId);
            if (!!returnEle) {
                returnEle.scrollIntoView(true);
            }
        },
        whetherAgreeWith() {      // 有没有赞这个回答
            axios({
                url:"/api/praise/whetherAgreeWith/" + this.replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                this.good = resp.data.resultStatus;
            })
        },
        agreeWith() {       //点赞
            axios({
                url: "/api/praise/agreeWith/" + this.replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    //获取点赞结果
                    this.whetherAgreeWith();
                    this.replyInfo.praiseNum = this.replyInfo.praiseNum + 1;
                }
            })
        },
        cancelApproval() {  //取消赞
            axios({
                url: "/api/praise/cancelApproval/" + this.replyInfo.replyId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    //获取点赞结果
                    this.whetherAgreeWith();
                    this.replyInfo.praiseNum = this.replyInfo.praiseNum - 1;
                }
            })
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
    .autoHeight {
        height: auto;
    }
    .height {
        height: 10em;
    }
    .clickShow {
        position: absolute;
        top: 0px;
        left: 0px;
        bottom: 0px;
        right: 0px;
        background: linear-gradient(to top ,white, transparent);
        z-index: 5;
    }
    .clickShowButtom {
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        text-align: center; 
        color:#007ACC;
        cursor: pointer;
    }
    .food {
        position: fixed;
        bottom: 0px;
    }
</style>
