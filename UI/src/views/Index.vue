<template>
    <div>
        <Top v-on:CallUserInfo="getUserInfo"></Top>
        
        <Row type="flex" justify="center" style="margin-top:1em">
            <i-col span="17">
                <Row type="flex" justify="start">
                    <!-- 左边 -->
                    <i-col style="width: 70%">
                        <Row type="flex" justify="start">
                            <i-col span="24" class="white-content">
                                <Issue
                                v-for="(item, index) in replyList"
                                :key="index"
                                :issueInfo="item">
                                </Issue>
                            </i-col>
                        </Row>
                    </i-col>

                    <!-- 右边 -->
                    <i-col style="width: 29%;margin-left:1%">
                        右边
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
            //翻页信息
            pageHelp: "",
            //当前页
            pageCode: 1,
            //回答数据
            replyList: []
        }
    },
    methods: {
        getUserInfo(userInfo) {     //从组件里获得用户信息
            this.userInfo = userInfo;
        },
        getIndexReplyList() {   //获取推荐信息
            axios({
                url: "/api/index/" + this.pageCode,
                method: "get"
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.replyList = resp.data.data;
                }
            })
        }
    },
    created() {
        this.getIndexReplyList();
    }
}
</script>

<style scoped>
    .white-content {
        box-shadow: 2px 2px 3px #E8E8E8;
        padding: 1em;
        background-color: white;
        margin-top: -0.5em;
        overflow: hidden;
    }
</style>
