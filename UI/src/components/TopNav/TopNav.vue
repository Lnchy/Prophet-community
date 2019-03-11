<template>
    <div id="app">
        <!-- 图钉 -->
        <Affix class="NavTopAffix">
            <Row type="flex" justify="center" class="NavigationBar">
                <i-col span="17">
                    <Row type="flex" justify="start" align="middle">
                        <i-col span="18">
                            <Row type="flex" justify="start" align="middle">
                                <i-col>
                                    <span style="font-size: 1.4em;color:#0084FF">先知社区</span>
                                </i-col>
                                <i-col offset="2">
                                    <router-link to="/">首页</router-link>
                                </i-col>
                                <i-col offset="2">
                                    <router-link to="/topic">话题</router-link>
                                </i-col>
                                <i-col offset="2">
                                    <Input search placeholder="搜索回答" />
                                </i-col>
                                <i-col offset="1">
                                    <i-button type="info">提问</i-button>
                                </i-col>
                            </Row>
                        </i-col>

                        <i-col span="6">
                            <Row type="flex" justify="end" align="middle" v-if="login">
                                <i-col offset="5">
                                    <Icon size="25" type="ios-mail" />
                                </i-col>
                                <i-col offset="5">
                                    <Icon size="20" type="ios-chatbubbles" />   
                                </i-col>
                                <i-col offset="5">
                                    <Dropdown trigger="click">
                                        <a href="javascript:void(0)">
                                            <Avatar 
                                            :src="portrait"
                                            shape="square" size="small" />
                                        </a>
                                        
                                        <DropdownMenu slot="list">
                                            <DropdownItem>
                                                <router-link 
                                                :to="{ name:'people', params: { id: this.userId} }">
                                                我的主页
                                                </router-link>
                                            </DropdownItem>
                                            <DropdownItem @click.native="outLogin">
                                                退出
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </Dropdown>
                                </i-col>
                            </Row>

                            <Row type="flex" justify="end" align="middle" v-if="!login">
                                <i-col>
                                    <router-link to="/login">
                                        <Button>
                                            登录
                                        </Button>
                                    </router-link>
                                </i-col>
                                <i-col offset="3">
                                    <router-link to="/reg">
                                        <Button type="primary">
                                            注册
                                        </Button>
                                    </router-link>
                                </i-col>
                            </Row>
                        </i-col>
                        
                    </Row>
                </i-col>
            </Row>
        </Affix>
    </div>
</template>

<script>

export default {
    data() {
        return {
            //Login?
            login: true,
            //用户Id
            userId: "",
            //用户信息
            userInfo: "",
            //头像
            portrait: "",
            //消息
            message: []
        }
    },
    methods: {
        getMyInfo() {
            axios({
                url: "/api/user/aboutMe",
                method: "get"
            })
            .then((resp) => {
                // 已登录
                if (resp.data.resultStatus) {
                    this.login = true;

                    this.userId = resp.data.data.userId;
                    this.portrait = this.localhost + resp.data.data.userPortraitUrl;
                    this.userInfo = resp.data.data;

                    //传递给父组件
                    this.$emit('CallUserInfo', this.userInfo);
                } else {
                    this.login = false;

                    console.log("去登录")
                    this.$router.push({
                        path:"/login"
                    });
                }

                //未登录
            })
            .catch((error) => {
                this.login = false;
                console.log("去登录")
                this.$router.push({
                    path:"/login"
                })
            })
        },
        outLogin() {
            this.$Modal.confirm({
                title: '退出登录',
                content: '<p>确定要注销登录吗？</p>',
                okText: '确定',
                cancelText: '取消',
                onOk: () => {
                     axios({
                        method: 'get',
                        url: '/api/user/outlogin'
                    })
                    .then((resp) => {
                        this.$router.push({
                            path: '/login'
                        });
                    })
                }
            });

        }
    },
    created() {
        this.getMyInfo();
    }
}
</script>

<style scoped>
    #app{
        font-size: 16px;
        background-color: #F6F6F6;
        width:100%;
    }
    .NavTopAffix {
        width: 100%;
        margin-bottom:10px;
    }
    .NavigationBar{
        line-height: 3em;
        box-shadow: 2px 2px 3px #E8E8E8;
        background-color: white;
        position: relative;
        left: 0px;right: 0px;
        top: 0px;
    }
    .NavigationBar a{
        color: #8590A6;
        
    }
</style>

