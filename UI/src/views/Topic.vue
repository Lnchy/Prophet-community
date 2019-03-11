<template>
    <div id="app">
        <Top v-on:CallUserInfo="getUserInfo"></Top>

        <Row type="flex" justify="center">
            <i-col span="17">
                <Row type="flex" justify="start">
                    <i-col style="width:68%;">
                        <Row class="content">
                            <i-col>
                                <Icon type="ios-apps-outline" size="25"/>话题广场
                                <Divider/>

                                <Row type="flex" justify="start">

                                    <i-col
                                    class="interestsItem"
                                    span="12"
                                    v-for="(item, index) in interestsAll"
                                    :key="index">
                                        <Row type="flex" justify="start">
                                            <i-col span="4">
                                                <Avatar shape="square" icon="ios-chatbubbles-outline" size="large" />
                                            </i-col>
                                            <i-col span="18" style="height:100px;overflow: hidden;">
                                                <Row>
                                                    <i-col span="12">
                                                        <router-link
                                                        :to="{
                                                            name: 'interest',
                                                            params: {
                                                                'id': item.interestId
                                                            }
                                                        }">
                                                            <h4>{{item.intersetName}}</h4>
                                                        </router-link>
                                                    </i-col>
                                                    <i-col span="12" style="text-align:right">
                                                        <Button 
                                                        v-if="interests.find(inter => { if(inter.interestId==item.interestId) return true }) == null"
                                                        type="text" @click="follow(item.interestId)">
                                                            <Icon type="ios-add" /> 关注
                                                        </Button>

                                                        <Button type="text" @click="noFollow(item.intersetName, item.interestId)" v-else>
                                                            取消关注
                                                        </Button>
                                                    </i-col>
                                                </Row>
                                                <span style="font-size:10px">
                                                    {{item.intersetDescribe}}
                                                </span>
                                            </i-col>
                                        </Row>
                                        <div class="replyInfo-divider"></div>
                                    </i-col>

                                </Row>
                            </i-col>
                        </Row>

                        <Row class="content">
                            <i-col>
                                <Icon type="ios-list" size="25"/>已关注列表
                                <Divider/>
                                <p v-if="interests == null || interests.length == 0">
                                    你的关注空空如也
                                </p>

                                <p v-else>
                                    <Tag 
                                    v-for="item in interests" 
                                    :key="item.interestId" 
                                    type="dot"
                                    closable
                                    :name="item.interestId"
                                    color="primary"
                                    @on-close="noFollow(item.intersetName, item.interestId)"
                                    >{{ item.intersetName }}</Tag>
                                </p>
                            </i-col>
                        </Row>

                        <Row align="top" class="content">
                            <i-col>
                                <Icon type="md-happy" size="25"/>猜你喜欢
                                <Divider />
                            </i-col>

                            <i-col>
                                <Issue 
                                v-for="item in userLike" :key="item.issueId"
                                :issueInfo="item">
                                
                                </Issue>
                            </i-col>
                        </Row>
                    </i-col>

                    <i-col style="width:31%; margin-left: 1%;height:auto">
                        <Row align="top" class="content">
                            <i-col>
                                <Icon type="ios-heart-outline" size="25"/>&nbsp;推荐关注
                                <Divider/>
                            </i-col>
                            <i-col>
                                <Row v-for="item in recommend" :key="item.interestId" style="margin-top:1em;">
                                    <i-col span="12">
                                        {{ item.intersetName }}
                                    </i-col>
                                    
                                    <i-col span="12">
                                        <Button 
                                        type="primary"
                                        v-if="interests.find(inter => { if (inter.interestId == item.interestId) return true }) == null"
                                        @click="follow(item.interestId)">关注</Button>
                                        <Button v-else @click="noFollow(item.intersetName, item.interestId)">
                                            取消关注
                                        </Button>
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
            userId: "",
            //关注分类
            interests : [
                
            ],
            //话题广场所有分类
            interestsAll:[

            ],
            //推荐分类
            recommend : [

            ],
            //猜你喜欢
            userLike : [

            ]
        }
    },
    methods: {
        getAllUserInterest() {  //获取当前个人的所有关注分类
            axios({
                method: "get",
                url: "/api/interest/follow/user/" + this.userId
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    if (resp.data.data == null) {
                        this.interests = [];
                    } else {
                        this.interests = resp.data.data;
                    }
                } else {
                    //没有关注分类
                }
            })
        },
        getUserInfo(userInfo) { //获取个人信息
            this.userId = userInfo.userId;
            this.getAllUserInterest();

            //获取推荐关注
            this.getRecommend();
            //获取用户喜欢的问题
            this.getLikeIssue();
            //获取话题广场的所有分类
            this.getAllInterests();
        },
        getRecommend() { //获取推荐关注
            axios({
                url: "/api/interest/follow/Recommend",
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    if (resp.data.data == null) {
                        this.recommend = [];
                    } else {
                        this.recommend = resp.data.data;
                    }
                }
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
                            //成功就直接刷新分类
                            this.getAllUserInterest();
                        } else {
                            //提示网络错误
                            this.$Message.error(resp.data);
                        }
                    })
                }
            }); 
        },
        follow(id) {    //关注一个分类
            let fd = new FormData();
            fd.append("id",id);
            axios({
                url: "/api/interest/follow",
                method: "post",
                data: fd
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    //成功了刷新
                    this.getAllUserInterest();
                } else {
                    //失败了刷新页面
                    this.getUserInfo();
                }
            }) 
        },
        getLikeIssue() { //获取喜欢的分类
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
        getAllInterests() {     //获取所有分类
            axios({
                url: "/api/interest",
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.interestsAll = resp.data.data;
                }
            })
        }
    }
}
</script>

<style scoped>
    #app{
        font-size: 16px;
        background-color: #F6F6F6;
    }
    .content {
        border-radius: 0.3em;
        box-shadow: 2px 2px 3px #E8E8E8;
        background-color: white;
        padding: 1em;
        margin-bottom: 1em;
        height: auto;
    }
    .interestsItem {
        color: black;
    }
    .interestsItem a {
        color: black;
    }
</style>
