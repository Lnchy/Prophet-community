<template>
    <div id="app">
        <canvas id="canvas"></canvas>
        <div class="input">
            <Row>
                <i-col span="15">
                    <Row style="margin-bottom: 2em;">
                        <i-col span="10">
                            <span class="title">登录先知</span>
                        </i-col>
                        <i-col offset="4" span="10" style="text-align:right;line-height:35px">
                            <router-link to="/Reg">
                            <a style="text-align:right;">没有帐号？立即注册</a>
                            </router-link>
                        </i-col>
                    </Row>
                    <i-form :model="LoginItem" label-position="right">
                        <Form-item prop="userEmail">
                            <i-input v-model="LoginItem.userEmail" size="large" type="text" placeholder="输入您的邮箱"></i-input>
                        </Form-item>
                        <Form-item prop="userPwd">
                            <i-input v-model="LoginItem.userPwd" size="large" type="password" placeholder="输入您的密码"></i-input>
                        </Form-item>
                        <Form-item>
                            <i-button size="large" type="info" :loading="loading" @click="login" long>
                                <span v-if="!loading">登录</span>
                                <span v-else>登录中...</span>
                            </i-button>
                        </Form-item>
                    </i-form>
                </i-col>
                <i-col offset="2" span="7">
                    <Row style="margin-bottom: 2em">
                        <h2>遇到问题？</h2>
                    </Row>
                    <Row style="margin-bottom: 2em">
                        <router-link to="/reg">
                            <i-button type="primary" long>注册新用户</i-button>
                        </router-link>
                    </Row>
                    <Row style="margin-bottom: 2em">
                        <i-button type="error" long>忘记密码</i-button>
                    </Row>                 
                </i-col>
            </Row>
        </div>
        
        <Affix :offset-bottom="0">
            <div class="foot">
                <Row>
                    <i-col>
                        Copyright © 2018 先知社区 版权所有 Powered by Prophet community
                    </i-col>
                </Row>
            </div>
        </Affix>
        
    </div>
</template>



<script>

export default {
    data() {
        return {
            LoginItem: {
                userEmail: '',
                userPwd: ''
            },
            loading: false
        }
    },
    methods: {
        login() {
            this.loading = true;

            if (this.LoginItem.userEmail.length == 0 || this.LoginItem.userPwd.length == 0) {
                this.$Message.error("请输入完整");
                this.loading = false;
                return;
            }
            axios({
                headers: {'Content-Type': 'application/json'},
                // withCredentials: true,
                method: 'post',
                url: '/api/user/login',
                data: JSON.stringify(this.LoginItem)
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.$Message.success(resp.data.statusMsg);
                    let vue = this;
                    setTimeout(function() {
                        vue.$router.push({
                            path: '/people/0/activities'
                        });
                    },2000);
                } else {
                    this.$Message.error(resp.data.statusMsg);
                }
                this.loading = false;
            })
            .catch((error) => {
                this.$Message.error("发生了错误，请重试");
                this.loading = false;
            })
            
        }
    },
    created() {
        //查看自己的信息，如果有登录状态就直接跳转
        axios({
            headers: {'Content-Type': 'application/json'},
            // withCredentials: true,
            method: 'get',
            url: '/api/user/aboutMe'
        })
        .then((resp) => {
            if (resp.data.resultStatus) {
                this.$router.push({
                    path: '/people/' + resp.data.data.userId + '/activities'
                });
            }
        });
    }
}
</script>

<style scoped>
body{
    overflow:hidden;
}
.input {
    padding: 6em 3em;
    background-color: rgba(255, 255, 255, 0.85);
    width: 50em;
    border-radius: 2px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
    box-shadow: 0px 0px 10px #444;
}
.input .title {
    color: #049BD2;
    font-size: 25px;
}
.foot {
    position: absolute;
    bottom: 0px;
    width:100%;
    padding: 1em 10%;
    background-color: #2D3237;
    color: #767768;
    font-size: 17px;
}
canvas{
    display: block;
    vertical-align: top;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    width: 100%;
    height: 100%;
    background: linear-gradient(to right top,#82C4FF,#4D99F1);
}
</style>