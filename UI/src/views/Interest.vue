<template>
    <div id="app">
        <Top v-on:CallUserInfo="getUserInfo"></Top>

        <Row type="flex" justify="center">
            <i-col span="17">
                <Row type="flex" justify="start">
                    <!-- 左边 -->
                    <i-col style="width:69%;margin-right:1%">

                        <Row type="flex" justify="start">
                            <!-- 白色块 -->
                            <i-col span="24" class="interest-content">
                                <Row type="flex" justify="start">
                                    <i-col span="3" style="text-align:center">
                                        <Avatar shape="square" icon="ios-chatbubbles-outline" size="large" />
                                    </i-col>
                                    <i-col span="20" style="margin-bottom:1em">
                                        <h3>{{ interestInfo.intersetName }}</h3>
                                        <p style="font-size:14px;margin:1em 0px;" v-bind:class="{'setHeight': !showDescribe,'autoHeight': showDescribe}">
                                            {{ interestInfo.intersetDescribe }}
                                        </p>
                                        <Button type="text" @click="showDescribe = !showDescribe">
                                            <span v-if="!showDescribe">展开详细介绍</span>
                                            <span v-else>收起详细介绍</span>
                                        </Button>
                                    </i-col>
                                </Row>

                                <!-- 关注或未关注 -->
                                <Row type="flex" justify="start">
                                    <i-col>
                                        <Button size="large" v-if="follow" @click="noFollow(interestInfo.intersetName,interestInfo.interestId)">取消关注</Button>
                                        <Button type="primary" size="large" v-else @click="followInterest(interestInfo.interestId)">关注</Button>
                                    </i-col>
                                    <i-col offset="1">
                                        <Button size="large" type="primary" @click="openNewIssue = true">提问</Button>
                                    </i-col>
                                </Row>
                            </i-col>
                        </Row>

                        <!-- 提问 -->
                        <Row type="flex" justify="start" class="interest-content" style="margin-top:1em" v-if="openNewIssue">
                            <i-col span="24" style="padding-bottom:1em">
                                标题：<Input v-model="issueTitle" placeholder="输入您要提问的问题" style="width:80%"/>
                            </i-col>
                            
                            <i-col span="24" style="height:400px;">
                                <Meditor ref="Meditor" v-on:CallBlackText="getTextContent"/>
                            </i-col>

                            <i-col span="24" style="margin-top:1em;">
                                <Row type="flex" justify="end">
                                    <i-col>
                                        <Button size="large" @click="openNewIssue = false">关闭</Button>
                                    </i-col>
                                    <i-col offset="1">
                                        <Button @click="callChilMethod" type="primary" size="large">提交问题</Button>
                                    </i-col>
                                </Row>
                            </i-col>
                        </Row>
                        
                        <!-- 问题列表 -->
                        <Row type="flex" justify="start" class="interest-content" style="margin-top:1em">
                            <i-col
                            span="24"
                            v-if="replyList.length == 0">
                            还没有人提问哦
                            </i-col>

                            <i-col
                            span="24"
                            v-for="(item, index) in replyList"
                            :key="index">

                                <Issue
                                :issueInfo="item"></Issue>

                            </i-col>
                        </Row>

                        <Row type="flex" justify="center" class="interest-content" style="margin-top:1em">
                            <i-col>
                                <!-- 翻页功能 -->
                                <Page
                                :current="pageHelp.pageCode"
                                :total="pageHelp.pageCount * 10" 
                                @on-change="changePage" 
                                show-elevator/>
                            </i-col>
                        </Row>
                    </i-col>

                    <!-- 右边 -->
                    <i-col style="width:30%">
                        <Row type="flex" justify="start" class="interest-content">
                            <i-col span="12" style="text-align:center">
                                问题数<br><br>
                                <h3>20</h3>
                            </i-col>

                            <i-col span="12" style="text-align:center">
                                回答数<br><br>
                                <h3>30</h3>
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
            //用户信息
            userInfo: "",
            //从url里获取的ID
            interestId: "",
            //话题信息
            interestInfo: "",
            //是否展开详细介绍
            showDescribe: false,
            //是否有关注这个分类
            follow: false,
            //回复列表,
            replyList: [],
            //第几页
            pageCode: 1,
            //页面相关
            pageHelp: "",
            //富文本框的显示
            openNewIssue: false,
            //问题标题
            issueTitle: "",
            //问题回答
            issueAbout: ""
        }
    },
    methods: {
        getInterestInfo() {     //获取分类信息
            axios({
                url: "/api/interest/info/" + this.interestId,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.interestInfo = resp.data.data;
                }
            })
        },
        isFollow() {    //判断是否有关注过
            axios({
                url: "/api/interest/isFollow/" + this.interestId,
                method: "get"
            })
            .then((resp) => {
                //是否有关注该
                this.follow = resp.data.resultStatus;
            })
        },
        noFollow(name, id) {    //取消关注
           this.$Modal.confirm({
                title: '取消关注',
                content: '<p>确定要取消关注《' + name + '》吗？</p>',
                okText: '取消',
                cancelText: '确定',
                onOk: () => {
                    //取消什么事情都不做
                },
                onCancel: () => {
                    //点击确定了之后就执行取消
                    let fd = new FormData();
                    fd.append("id",id);
                    axios({
                        method: "post",
                        url: "/api/interest/NoFollow",
                        data: fd
                    })
                    .then((resp) => {
                        if (resp.data.resultStatus) {
                            //成功了刷新判断
                            this.isFollow()
                        } else {
                            //提示网络错误
                            this.$Message.error(resp.data);
                        }
                    })
                }
            }); 
        },
        followInterest(id) {    //关注一个分类
            let fd = new FormData();
            fd.append("id",id);
            axios({
                url: "/api/interest/follow",
                method: "post",
                data: fd
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    //成功了刷新判断
                    this.isFollow()
                }
            }) 
        },
        getAllReplyOrIssue() {      //获取分类下的回复或者问题
            axios({
                url: "/api/issue/reply/" + this.interestId + "/" + this.pageCode,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    if (resp.data != null) {
                        this.replyList = resp.data.data;
                        this.pageHelp = resp.data.pageHelp;
                    }
                }
            })
        },
        getUserInfo(userInfo) {     //从组件里获得用户信息
            this.userInfo = userInfo;
        },
        changePage(pageCode) {      //更换页面
            this.pageCode = pageCode;
            this.getAllReplyOrIssue();
        },
        callChilMethod() {      //回调富文本框内容
            //先保存
            this.$refs.Meditor.callSave();
        },
        getTextContent(value) {     //获得富文本框内容
            this.issueAbout = value;
            
            if (this.issueAbout.length > 5 && this.issueTitle.length > 5) {
                let fd = new FormData();
                fd.append("issueInterestId", this.interestId);
                fd.append("issueTitle", this.issueTitle);
                fd.append("issueContent", this.issueAbout);

                // let objData = {};
                // fd.forEach((value, key) => objData[key] = value);
                axios({
                    // headers: {'Content-Type': 'application/json'},
                    url: "/api/issue/add",
                    method: "post",
                    data: fd
                })
                .then((resp) => {
                    if (resp.data.resultStatus) {
                        this.$Notice.success({
                            title: '您的提问已发表'
                        });

                        setTimeout(() => {
                            this.$router.push({
                                name: 'issue',
                                params: {
                                    'id': resp.data.data
                                }
                            })
                        },1000);
                        
                    } else {
                        this.$Notice.error({
                            title: "出现错误，请重试"
                        });
                    }
                })
            } else {
                this.$Message.error("请填写完整您的问题或者问题描述，长度不少于6个字符");
            }
        }
    },
    created() {
        // 获取url里的id
        if (!isNaN(Number(this.$route.params.id))) {
            this.interestId = this.$route.params.id;
        }

        //获取当前分类标签信息
        this.getInterestInfo();
        //获取当前用户是否关注了该分类
        this.isFollow();
        //获取回复列表
        this.getAllReplyOrIssue();
    }
}
</script>

<style scoped>
    #app {
        font-size: 16px;
        background-color: #F6F6F6;
    }
    .interest-content {
        box-shadow: 2px 2px 3px #E8E8E8;
        padding: 1em;
        background-color: white;
        overflow: hidden;
    }
    .setHeight {
        height: 5em;
        overflow: hidden;
    }
    .autoHeight {
        height: auto;
    }
</style>
