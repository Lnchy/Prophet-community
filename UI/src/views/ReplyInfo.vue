<template>
    <div id="app">
        <Top v-on:CallUserInfo="getUserInfo"></Top>

        <Row type="flex" justify="center" style="" class="Issue-content">
            <i-col span="17">

                <Row type="flex" justify="start">
                    <i-col span="16">
                        <Row>
                            <i-col>
                                <Breadcrumb>
                                    <BreadcrumbItem to="/">首页</BreadcrumbItem>
                                    <BreadcrumbItem to="/topic">话题</BreadcrumbItem>
                                    <BreadcrumbItem>问题</BreadcrumbItem>
                                </Breadcrumb>
                            </i-col>

                            <i-col style="margin-top:1em">
                                <h2>{{ issueTitle }}</h2>
                            </i-col>
                            <i-col style="margin-top:0.5em">
                                <MarkDown :body="issueContent"></MarkDown>
                                <!-- <vue-markdown :source="issueContent"></vue-markdown> -->
                            </i-col>

                            <i-col style="margin-top:1em">
                                <Row type="flex" justify="start">
                                    <i-col>
                                        <Button type="primary" size="large" @click="openReply = true">写回答</Button>
                                    </i-col>
                                    <i-col offset="1">
                                        <Button type="text" size="large">
                                            <Icon type="md-chatboxes" /> {{ issueReply }}个回答
                                        </Button>
                                    </i-col>
                                </Row>
                            </i-col>
                            
                        </Row>
                        
                    </i-col>

                    <i-col offset="3" span="5">
                        <Row style="margin-top:5em">
                            <i-col span="12" style="text-align: center;">
                                被浏览
                                <br>
                                <h3>{{ issueViews }}</h3>
                            </i-col>
                        </Row>
                    </i-col>
                </Row>

            </i-col>
        </Row>

        <Row type="flex" justify="center" style="margin-top:1em;" v-if="openReply">
            <i-col span="17">
                
                <Row type="flex" justify="start" class="Issue-content">
                    <i-col span="24" style="height:400px;">
                        <Meditor ref="Meditor" v-on:CallBlackText="getTextContent"/>
                    </i-col>

                    <i-col span="24" style="margin-top:1em;">
                        <Row type="flex" justify="end">
                            <i-col>
                                <Button size="large" @click="openReply = false">关闭</Button>
                            </i-col>
                            <i-col offset="1">
                                <Button @click="callChilMethod" type="primary" size="large">提交回答</Button>
                            </i-col>
                        </Row>
                    </i-col>
                </Row>

            </i-col>
        </Row>

        <!-- 回答 -->
        <Row type="flex" justify="center">
            <i-col span="17">
                <Row type="flex" justify="start">
                    <i-col style="width:69%;margin-top:1.5em;">
                        <Row>
                            <i-col class="Issue-content" style="margin-bottom:1em">
                                <Row type="flex" justify="center">
                                    <i-col>
                                        <router-link :to="{name: 'issue', params:{'id':this.issueId}}">
                                            查看全部 {{ issueReply }} 个回答
                                        </router-link>
                                    </i-col>
                                </Row>
                            </i-col>

                            <i-col v-if="replyInfo != '' && replyInfo != null">
                                <reply
                                class="Issue-content"
                                :replyInfo="replyInfo"
                                :userInfo="userInfo"/>
                            </i-col>
                        </Row>
                    </i-col>

                    <i-col style="width:30%;margin-left:1%;margin-top:1.5em;">
                        <Row>
                            <i-col class="Issue-content">
                                关于作者
                                <Divider />
                                <Row type="flex" justify="start" align="middle">
                                    <i-col offset="2">
                                        <Avatar
                                        :src="userPortraitUrl"
                                        icon="ios-person" 
                                        size="large" />
                                    </i-col>
                                    <i-col offset="1">
                                        <h4>{{ userInfo.userName }}</h4>
                                    </i-col>
                                    <div class="replyInfo-divider"></div>
                                    <i-col span="24">
                                        <Row type="flex" justify="center" style="text-align: center">
                                            <i-col>
                                                回答<br>
                                                <h3>309</h3>
                                            </i-col>

                                            <i-col offset="6">
                                                提问<br>
                                                <h3>30</h3>
                                            </i-col>

                                            <i-col offset="6">
                                                点赞<br>
                                                <h3>300</h3>
                                            </i-col>
                                        </Row>
                                    </i-col>
                                </Row>
                            </i-col>
                        </Row>
                    </i-col>
                </Row>
            </i-col>
        </Row>

        
    </div>
</template>

<script>

export default {
    data() {
        return {
            //回答的内容
            textValue: "",
            //展开回答
            openReply: false,
            //用户信息
            userInfo: "",
            //回答
            replyInfo: "",
            //回答id
            replyId: 0,
            //用户头像
            userPortraitUrl: "",

            issueId: 0,
            issueInterestId: 0,
            issueTitle: "",
            issueUserId: 0,
            issueAnonymous: 0,
            issueEssence: 0,
            issueBan: 0,
            issueCreateTime: "",
            issueReply: 0,
            issueViews: 0,
            issueContent: ""
        }
    },
    methods: {
        getTextContent(value) {     //获取富文本框的文本
            this.textValue = value;

            let fd = new FormData();

            fd.append("issueId", Number(this.issueId));
            fd.append("content", value);
            //保存之后跳转
            axios({
                url: "/api/reply/add",
                method: "post",
                data: fd
            })
            .then((resp) => {
                this.$Notice.success({
                    title: '您的回答已发表'
                });

                setTimeout(() => {
                    this.$router.push({
                        name: 'replyInfo',
                        params: {
                            'id': resp.data.data
                        }
                    })
                }, 500);
            })
        },
        callChilMethod() {      //发表回答
            //先保存
            this.$refs.Meditor.callSave();
        },
        getUserInfo(userInfo) {     //从组件里获得用户信息
            this.userInfo = userInfo;
            this.userPortraitUrl = "/api" + userInfo.userPortraitUrl;
        },
        getReply(replyId) {  //获取回答
            axios({
                method: "get",
                url: "/api/reply/info/" + replyId
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.replyInfo = resp.data.data;
                    //获取问题详情
                    this.getIssueInfo(resp.data.data.replyIssueId);

                } else {
                    //如果出现异常情况就直接提示并跳转
                    this.$Message.error(resp.data.statusMsg);

                    this.$router.push({
                        name: 'topic'
                    })
                }
            })
        },
        getIssueInfo(issueId) {     //获取问题详情
            //获取问题详情
            axios({
                url: "/api/issue/info/" + issueId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.issueId = resp.data.data.issueId;
                    this.issueInterestId = resp.data.data.issueInterestId;
                    this.issueTitle = resp.data.data.issueTitle;
                    this.issueUserId = resp.data.data.issueUserId;
                    this.issueAnonymous = resp.data.data.issueAnonymous;
                    this.issueEssence = resp.data.data.issueEssence;
                    this.issueBan = resp.data.data.issueBan;
                    this.issueCreateTime = resp.data.data.issueCreateTime;
                    this.issueReply = resp.data.data.issueReply;
                    this.issueViews = resp.data.data.issueViews;
                    this.issueContent = resp.data.data.issueContent;
                } else {
                    this.$router.push({
                        path: "/"
                    })
                }
            })

        }
    },
    created() {
        //从url里获取数据并赋值
        if (!isNaN(Number(this.$route.params.id))) {
            this.replyId = this.$route.params.id;
        } else {
            this.$router.push({
                path: "/"
            })
        }

        //获取回答详情和问题详情
        this.getReply(this.replyId);
       
    }
}
</script>

<style scoped>
    #app {
        font-size: 16px;
        background-color: #F6F6F6;
    }
    .Issue-content {
        box-shadow: 2px 2px 3px #E8E8E8;
        padding: 1em;
        background-color: white;
        margin-top: -0.5em;
        overflow: hidden;
    }
</style>
