<template>
    <div id='app'>
        <Top></Top>
        <Row type="flex" justify="center" style="position: relative;">
            <i-col span="17" class="people-content">
                <Row style="height: 400px">
                    <i-col class="people-upImage">
                        <Row type="flex" justify="end">
                            <i-col>
                                <input type="file" style="display:none" @change="fileChange" id="imgFile">
                                <button class="people-upImage-buttom" @click="upImage">更换背景图片</button>
                            </i-col>
                        </Row>
                    </i-col>
                    <i-col id="myBackgroundImg">
                        
                    </i-col>
                    <i-col class="people-aboutme">
                        <div class="people-aboutme-portrait"></div>
                        <div class="people-aboutme-info">
                            <p>
                                <span style="font-size:1.5em;font-weight: bold">{{ userName }}</span>
                            </p>
                            <br>
                            <p>
                                Email: {{ userEmail }}
                            </p>
                        </div>
                    </i-col>
                </Row>
            </i-col>

            <i-col span="17">
                <Row type="flex" justify="start" style="margin-top:0.5em;margin-bottom:10em;">
                    <i-col class="people-content people-content-white" style="width:69%;padding:1em;">
                        <Tabs>
                            <TabPane label="动态">标签一的内容</TabPane>
                            <TabPane label="回答">标签二的内容</TabPane>
                            <TabPane label="提问">标签三的内容</TabPane>
                        </Tabs>
                    </i-col>

                    <i-col class="people-content" style="width:30%;margin-left:1%;box-shadow:0px 0px 0px">
                        <Row class="people-content people-content-white" style="padding:1em">
                            <i-col>
                                <Row>
                                    <i-col>
                                    我的关注
                                        <Divider />
                                    </i-col>
                                    <p v-if="interests == null || interests.length == 0">
                                        你的关注空空如也
                                    </p>
                                    <Row v-for="item in interests" :key="item.interestId" style="margin-top:1em;">
                                        <i-col span="12">
                                            {{ item.intersetName }}
                                        </i-col>
                                        <i-col span="12">
                                            <Button @click="unFollow(item.interestId,item.intersetName)">取消关注</Button>
                                        </i-col>
                                    </Row>
                                </Row>
                            </i-col>
                        </Row>
                        
                        <Row type="flex" justify="center" align="middle" class="people-content people-content-white" style="padding:1em;margin-top:1em;">
                            <i-col span="12" style="text-align: center;">
                                关注了<br>
                                <span style="font-size:1.5em;font-weight: bold">
                                    23
                                </span>
                            </i-col>

                            <i-col span="12" style="text-align: center;">
                                点赞了<br>
                                <span style="font-size:1.5em;font-weight: bold">
                                    23
                                </span>
                            </i-col>
                        </Row>

                        <Row style="margin:1em">
                            联系我们 © 2019 先知
                        </Row>
                    </i-col>
                </Row>
            </i-col>
        </Row>

        <!-- <i-button @click='outLogin'>退出登录</i-button> -->
    </div>
</template>

<script>
export default {
    data() {
        return{
            userId: '',
            userBackgroundImg: '',
            userBirthday: '',
            userEmail: '',
            userName: '',
            userPortraitUrl: '',
            //关注分类
            interests : [

            ]
        }
    },
    methods: {
        showImage() {   //显示背景图片和头像
            let bg = document.getElementById("myBackgroundImg");
            bg.style.background = "url(/api" + this.userBackgroundImg + ")";
            bg.style.backgroundSize = "100% 100%";

            let portrait = document.getElementsByClassName("people-aboutme-portrait")[0];
            portrait.style.background = "url(/api" + this.userPortraitUrl + ")";
            portrait.style.backgroundSize = "100% 100%";
        },
        upImage() {     //上传图片，触发图片压缩并上传
            document.getElementById("imgFile").click();
        },
        fileChange() {     //压缩并上传
            let file = document.getElementById("imgFile").files[0];
            let canvas = document.createElement("canvas");
            let ctx = canvas.getContext("2d");
            let image = new Image();
            let imgUrl = URL.createObjectURL(file);
            image.src = imgUrl;

            image.onload = () => {
                URL.revokeObjectURL(imgUrl);

                let imageWidth = image.width;
                let imageHeight = image.height;
                //判断图片长度
                if (parseInt(image.height) > 400) {
                    let scale = (400 / image.height.toFixed(2));
                    imageWidth = Math.ceil(scale * imageWidth);
                    imageHeight = Math.ceil(scale * imageHeight);
                }

                //缩放画布
                canvas.width = imageWidth;
                canvas.height = imageHeight;

                ctx.drawImage(image, 0, 0, imageWidth, imageHeight);
                
                canvas.toBlob((data) => {
                    let fd = new FormData();
                    fd.append("img", data);

                    //开始上传
                    axios({
                        headers:{'Content-Type':'multipart/form-data'},
                        url: "/api/user/changeBgImg",
                        method: "post",
                        data: fd
                    })
                    .then((resp) => {
                        if (resp.data.resultStatus) {
                            this.showInfo();
                        } else {
                            this.$Message.error(resp.data.statusMsg);
                        }
                    })
                })
                
            }
        },
        getAllUserInterest() {  //获取当前个人的所有关注分类
            axios({
                method: "get",
                url: "/api/interest/follow/user/" + this.userId
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.interests = resp.data.data;
                } else {
                    //没有关注分类
                }
            })
        },
        showInfo() {    //获取个人信息并矫正url
            this.userId = this.$route.params.id;
            //如果没有url里没有id
            axios({
                method: 'get',
                url: '/api/user/aboutMe'
            })
            .then((resp) => {
                if (resp.data.resultStatus) {
                    this.userId = resp.data.data.userId;
                    this.userName = resp.data.data.userName;
                    this.userBirthday = resp.data.data.userBirthday;
                    this.userEmail = resp.data.data.userEmail;
                    this.userBackgroundImg = resp.data.data.userBackgroundImg;
                    this.userPortraitUrl = resp.data.data.userPortraitUrl;
                    if (this.$route.params.id == 0) {
                        
                        this.$router.push({
                            path: '/people/' + resp.data.data.userId + '/activities'
                        });
                    }
                } else {
                    this.$router.push({
                        path: '/login'
                    });
                }
                this.showImage();
                //获取关注
                this.getAllUserInterest();
            });
        },
        unFollow(interestId, interestName) {
            this.$Modal.confirm({
                title: '取消关注',
                content: '<p>确定要取消关注《' + interestName + '》吗？</p>',
                okText: '取消',
                cancelText: '确定',
                onOk: () => {
                    //取消什么事情都不做
                },
                onCancel: () => {
                    //点击确定了之后就执行取消
                    let fd = new FormData();
                    fd.append("id",interestId);
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
        }
    },
    created() {
        this.showInfo();
    }
    
}
</script>

<style scoped>
    #app{
        font-size: 16px;
        background-color: #F6F6F6;
    }
    .NavigationBar{
        line-height: 3em;
        box-shadow: 2px 2px 3px #E8E8E8;
        background-color: white;
    }
    .NavigationBar a{
        color: #8590A6;
        
    }
    .people-content{
        border-radius: 0.3em;
        box-shadow: 2px 2px 3px #E8E8E8;
    }
    .people-content-white{
        background-color: white;
    }
    #myBackgroundImg {
        background-repeat: no-repeat;
        height: 100%
    }
    .people-upImage {
        position: absolute;
        right: 10px;
        top: 10px;
        left: 0px;
        z-index: 5;
    }
    .people-upImage-buttom {
        font-size: 14px;
        padding: 0.3em 1em;
        background-color: transparent;
        border: 1px solid rgba(0, 0, 0, 0.5);
        color:rgba(0, 0, 0, 0.5);
        border-radius: 3px;
        cursor: pointer;
    }
    .people-aboutme {
        background-color: white;
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        height: 40%;
    }
    .people-aboutme-portrait {
        height: 150px;
        width: 150px;
        background-color: white;
        position: absolute;
        left: 50px;
        top: -30px;
        border-radius: 5px;
        border: 5px solid white;
    }
    .people-aboutme-info {
        background-color: white;
        position: absolute;
        left: 250px;
        top: 20px;
    }
</style>
