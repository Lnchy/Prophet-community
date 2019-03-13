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
                                    <BreadcrumbItem>
                                        <router-link :to="{name: 'interest', params:{'id':this.issueInterestId}}">话题</router-link>
                                    </BreadcrumbItem>
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
                            <i-col v-if="replyList.length > 0">
                                <reply
                                class="Issue-content"
                                v-for="(item, index) in replyList" 
                                :key="index" 
                                :replyInfo="item"
                                :userInfo="userInfo"/>
                            </i-col>

                            <i-col class="Issue-content" v-else>
                                没有回复，快抢沙发吧
                            </i-col>

                            <i-col class="Issue-content" style="margin:1em 0px;" v-if="replyList.length > 0">
                                <!-- 切换页面 -->
                                <Page 
                                :current="pageHelp.pageCode" 
                                :total="pageHelp.pageCount * 10" 
                                @on-change="changePage"
                                show-elevator />
                            </i-col>
                        </Row>
                    </i-col>

                    <i-col style="width:30%;margin-left:1%;margin-top:1.5em;">
                        <Row>
                            <i-col class="Issue-content">
                                猜你喜欢
                                <Divider />

                                <i-col v-for="item in userLike" :key="item.issueId" style="margin-bottom:1em;">
                                    <router-link
                                    :to="{name: 'issue', params: {'id': item.issueId}}"> 
                                        {{ item.issueTitle }} 
                                    </router-link>
                                </i-col>
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
            //用户喜欢
            userLike: [],
            //当前页
            pageCode: 1,
            //回答列表
            replyList: [],
            //翻页
            pageHelp: "",

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
            
        },
        getUserLove() {     //获取用户喜欢
            axios({
                url: "/api/issue/like",
                method: "get"
            })
            .then((resp) => {
                if (resp.data.data != null) {
                    this.userLike = resp.data.data;
                } else {
                    this.userLike = [];
                }
            })
        },
        getReplysList(issueId, pageCode) {  //获取回答
            axios({
                method: "get",
                url: "/api/reply/info/" + issueId + "/" + pageCode
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    if (resp.data != null) {
                        this.replyList = resp.data.data.sort((a, b) => {
                            return b.praiseNum - a.praiseNum;
                        });
                    }

                    this.issueReply = resp.data.pageHelp.count;
                    this.pageHelp = resp.data.pageHelp;
                }
            })
        },
        changePage(pageCode) {
            this.$router.push({
                name: 'issuePage',
                params: {
                    'id': this.issueId,
                    'pageCode': pageCode
                }
            })
        }
    },
    created() {
        //从url里获取数据并赋值
        if (!isNaN(Number(this.$route.params.id))) {
            this.issueId = this.$route.params.id;
        } else {
            this.$router.push({
                path: "/"
            })
        }

        //获取页面
        if (!isNaN(Number(this.$route.params.pageCode))) {
            this.pageCode = this.$route.params.pageCode;
        }

        this.getReplysList(this.issueId, this.pageCode);


        //获取问题详情
        axios({
            url: "/api/issue/info/" + this.issueId,
            method: "get"
        })
        .then((resp) => {
            if (resp.data.resultStatus) {
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

        //获取用户喜欢
        this.getUserLove();
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
