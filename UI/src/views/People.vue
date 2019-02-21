<template>
    <div id='app'>
        <!-- 图钉 -->
        <Affix class="NavigationBar">
            <Row type="flex" justify="center">
                <i-col span="17">
                    <Row type="flex" justify="start">
                        <i-col>
                            <span style="font-size: 1.4em;color:#0084FF">先知社区</span>
                        </i-col>
                        <i-col offset="1">
                            <router-link to="/">首页</router-link>
                        </i-col>
                        <i-col offset="1">
                            <router-link to="/">话题</router-link>
                        </i-col>
                        <i-col offset="1">
                            <Input search placeholder="搜索回答" />
                        </i-col>
                        <i-col offset="1">
                            <i-button type="info">提问</i-button>
                        </i-col>
                        <i-col offset="5">
                            <router-link to="/">个人中心</router-link>
                        </i-col>
                    </Row>
                </i-col>
            </Row>
        </Affix>

        <Row type="flex" justify="content">
            <i-col span="17" class="people-content">
                内容12311234
            </i-col>
        </Row>

        <!-- <i-button @click='outLogin'>退出登录</i-button>
        <br>
        {{ userId }}<br>
        {{ userBirthday }}<br>
        {{ userEmail }}<br>
        {{ userName }}<br>
        {{ userPortraitUrl }}<br>
        {{ userBackgroundImg }}<br> -->
    </div>
</template>

<script>
export default {
    data() {
        return{
            userId: '',
            userBackgroundImg: '/image/default/portrait.png',
            userBirthday: '',
            userEmail: '',
            userName: '',
            userPortraitUrl: '/image/default/portrait.png'

        }
    },
    methods: {
        outLogin() {
            axios({
                method: 'get',
                url: '/api/user/outlogin'
            })
        }
    },
    created() {
        this.userId = this.$route.params.id;
        //如果没有url里没有id
        if (this.userId == 0) {
            axios({
                method: 'get',
                url: '/api/user/aboutMe'
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.$router.push({
                        path: '/people/' + resp.data.data.userId + '/activities'
                    });
                    this.userId = resp.data.data.userId;
                    this.userName = resp.data.data.userName;
                    this.userBackgroundImg = resp.data.data.userBackgroundImg;
                    this.userBirthday = resp.data.data.userBirthday;
                    this.userEmail = resp.data.data.userEmail;
                    this.userBackgroundImg = resp.data.data.userBackgroundImg;
                    this.userPortraitUrl = resp.data.data.userPortraitUrl;
                } else {
                    this.$router.push({
                        path: '/login'
                    });
                }
            })
        } else {
            axios({
                method: 'get',
                url: '/api/user/aboutMe'
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.userName = resp.data.data.userName;
                    this.userBackgroundImg = resp.data.data.userBackgroundImg;
                    this.userBirthday = resp.data.data.userBirthday;
                    this.userEmail = resp.data.data.userEmail;
                    this.userBackgroundImg = resp.data.data.userBackgroundImg;
                    this.userPortraitUrl = resp.data.data.userPortraitUrl;
                } else {
                    this.$router.push({
                        path: '/login'
                    });
                }
            })
        }
    }
}
</script>

<style scoped>
    #app{
        font-size: 18px;
    }
    .NavigationBar{
        line-height: 3em;
        box-shadow: 2px 2px 5px #E8E8E8;
        margin-bottom: 1em;
        background-color: white;
    }
    .NavigationBar a{
        color: #8590A6;
        
    }
    .people-content{
        background-color: white;
    }
</style>
