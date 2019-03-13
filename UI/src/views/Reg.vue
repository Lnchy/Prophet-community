<template>
    <div id="app">
        
        <div id="reg">
            <Row style="margin-bottom: 2em;">
                <i-col><span style="color: #049BD2;font-size: 25px;">注册先知</span></i-col>
            </Row>
            <Row>
                <i-col>
                    <i-form :model="regItems" :label-width="90">
                        <Row>
                            <i-col :span="14">
                                <Form-item label="邮箱">
                                    <i-input v-model="regItems.userEmail" @on-blur="testEmail" size="large" placeholder="输入您使用的邮箱"></i-input>
                                </Form-item>
                            </i-col>
                            <i-col :span="8" offset="2">
                                该邮箱用于登录，一旦注册成功不可更改
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item label="昵称">
                                    <i-input v-model="regItems.userName" @on-blur="testUserName" size="large" placeholder="自定义您的昵称"></i-input>
                                </Form-item>
                            </i-col>
                            <i-col :span="8" offset="2">
                                2~10个字符，可以使用中文，字母，数字
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item label="密码">
                                    <i-input type="password" v-model="regItems.userPwd" size="large" placeholder="请输入您的密码"></i-input>
                                </Form-item>
                            </i-col>
                            <i-col :span="8" offset="2">
                                6~20个字符，区分大小写
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item label="确认密码">
                                    <i-input type="password" v-model="userPwd2" size="large" placeholder="请重复输入密码确认"></i-input>
                                </Form-item>
                            </i-col>
                            <i-col :span="8" offset="2">
                                请再次填写密码
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item label="生日">
                                    <Date-picker type="date" size="large" placeholder="选择或输入日期" @on-change="regItems.userBirthday=$event"></Date-picker>
                                </Form-item>
                            </i-col>
                            <i-col :span="8" offset="2">
                                请填写您的生日
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item>
                                    <i-button type="info" :loading="loading" @click="reg" size="large" long>
                                        <span v-if="!loading">注册</span>
                                        <span v-else>注册中...</span>
                                    </i-button>
                                </Form-item>
                            </i-col>
                        </Row>
                        <Row>
                            <i-col :span="14">
                                <Form-item>
                                    <!-- <router-link to="/login"> -->
                                        <i-button type="dashed" size="large" @click="login" long>已有帐号？返回登录</i-button>
                                    <!-- </router-link> -->
                                </Form-item>
                            </i-col>
                        </Row>
                    </i-form>
                </i-col>
            </Row>
        </div>
        <!-- <Affix :offset-bottom="0">
            <div id="foot">
                Copyright © 2018 先知社区 版权所有 Powered by Prophet community
            </div>
        </Affix> -->
    </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
      regItems: {
        userName: "",
        userEmail: "",
        userPwd: "",
        userBirthday: "1991-1-1"
      },
      userPwd2: "",
      loading: false,
      emailOk: false
    };
  },
  methods: {
        testEmail() {
            let emailReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if (!emailReg.test(this.regItems.userEmail)) {
                this.$Message.error('邮箱格式有误');
                return;
            }
            if (this.regItems.userEmail.length > 0) {
                axios({
                    headers: {'Content-Type': 'application/json'},
                    method: 'get',
                    url: '/api/user/reg/email?email=' + this.regItems.userEmail
                })
                .then((resp) => {
                    if(resp.data.statusCode != '200') {
                        this.$Message.error(resp.data.statusMsg);
                        this.emailOk = false;
                    } else {
                        this.emailOk = true;
                    }
                })
            }
        },
        testUserName() {
            let nameReg = /^[\u4e00-\u9fa5a-zA-Z0-9]{3,10}$/;
            if (!nameReg.test(this.regItems.userName)){
                this.$Message.error('昵称只允许3-10位，并且是大小写字母或中文或数字');
            } else {
                //去验证
                axios({
                    headers: {'Content-Type': 'application/json'},
                    method: 'get',
                    url: '/api/user/reg/name?name=' + this.regItems.userName
                })
                .then((resp) => {
                    if (resp.data.statusCode != 200) {
                        this.$Message.error(resp.data.statusMsg);
                    }
                })
            }
        },
        reg() {
            let nameReg = /^[\u4e00-\u9fa5a-zA-Z0-9]{3,10}$/;
            if (!this.emailOk) {
                this.$Message.error('请输入未注册过的邮箱！');
            } else if (!nameReg.test(this.regItems.userName)) {
                this.$Message.error('昵称只允许3-10位，并且是大小写字母或中文或数字');
            } else if (this.regItems.userPwd.length < 6 || this.regItems.userPwd != this.userPwd2) {
                this.$Message.error('两次密码输入必须一致，并至少6位');
            } else if (this.regItems.userBirthday.length < 1) {
                this.$Message.error('请填写生日');
            } else {
                axios({
                    headers: {'Content-Type': 'application/json'},
                    method: 'post',
                    url: '/api/user/reg/',
                    data: JSON.stringify(this.regItems)
                })
                .then((resp) => {
                    if (resp.data.statusCode == 200) {
                        this.$Message.success('注册成功！');
                        let vue = this;
                        setTimeout(function(){
                            vue.$router.push({
                                path:'/login'       //跳转到登录页面
                            })
                        },2000);
                    } else {
                        this.$Message.error(resp.data.statusMsg);
                        this.emailOk = false;
                    }
                })
            }
        },
        login() {
            this.$router.push({
                path:'/login'       //跳转到登录页面
            })
        }
  },
  created() {
  }
};
</script>

<style scoped>
    #app {
        position: absolute;
        top: 0px;
        left: 0px;
        right: 0px;
        bottom: 0px;
        background: linear-gradient(to right top, #9c4dff, #42a7ff);
    }
    #reg {
        width: 70em;
        padding: 3em 3em 1em 3em;
        background-color: rgba(255, 255, 255, 0.95);
        border-radius: 2px;
        margin: 2em auto;
        box-shadow: 0px 0px 10px #444;
        z-index: 99;
    }
    #foot {
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        margin-top: 2em;
        background-color: #2d3237;
        color: #767768;
        padding: 1em 10%;
        font-size: 17px;
        z-index: 70;
    }
</style>