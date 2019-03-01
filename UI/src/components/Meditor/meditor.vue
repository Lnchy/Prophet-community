<template>
    <div class="meditor">
        <mark-down
        ref="mark"
        :toolbars="config" 
        :mode="mode"
        theme="GitHub"
        :height="height"
        :autoSave="autoSave"/>
        <div class="upimage" @click="upimage"><Icon type="ios-image" size="23"/></div>
        <input type="file" style="display:none" @change="fileChange" id="imgFile">
    </div>
</template>

<script>
import MarkDown from 'vue-meditor'

export default {
    data() {
        return {
            autoSave: false,
            height: 300,
            mode: 3,
            config :{
                
                print:false, // 隐藏掉打印功能
                code:false,  //隐藏代码块功能
                link:false,
                checked:false,
                notChecked:false,
                // shift:false,
                exportmd:false,
                importmd:false,
                theme:false,
                fullscreen:false,
                image: false
            }
        }
    },
    methods: {
        save(text) {
            this.$emit('CallBlackText', text);
        },
        callSave() {
            //获取文本的内容
            this.save(this.$refs.mark.value);
        },
        upimage() {
            document.getElementById("imgFile").click();
            
        },
        fileChange() {

            let needUpImage = document.getElementById("imgFile").files[0];
            let canvas = document.createElement("canvas");
            let ctx = canvas.getContext("2d");
            let image = new Image();
            let imgUrl = URL.createObjectURL(needUpImage);
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
                        url: "/api/reply/upImage",
                        method: "post",
                        data: fd
                    })
                    .then((resp) => {
                        if (resp.data.resultStatus) {
                            let imgPath = "![image](" + this.localhost + resp.data.data + ")";
                            this.$refs.mark.value = this.$refs.mark.value + imgPath;
                        } else {
                            this.$Message.error(resp.data.statusMsg);
                        }
                    })
                })
                
            }
        }
    },
    components:{
        MarkDown
    }
}
</script>

<style>
    .meditor {
        height: 100%;
    }
    .meditor .markdown .markdown-content .markdown-editor textarea {
        width: 100%;
        min-height: 100%;
        outline: none;
        border: 0;
        background: white;
        line-height: 22px;
        caret-color: #ccc;
        color: black;
        font-size: 16px;
        font-family: Consolas;
        resize: none;
        padding: 12px 8px;
        overflow: hidden;
    }
    .meditor .markdown .markdown-content .markdown-editor .index {
        background: white;
        min-height: 100%;
        width: 36px;
        line-height: 22px;
        padding: 12px 0;
    }
    .meditor .markdown .markdown-content .markdown-editor .index li {
        background: white;
        color: #ccc;
        font-size: 14px;
        text-align: center;
        font-family: Consolas;
    }
    .upimage {
        width: 30px;
        height: 28px;
        position: absolute;
        left: 370px;
        top:6px;
        cursor: pointer;
        text-align: center;
    }
    .upimage:hover {
        background-color: #F7F7F7;
        color: #0084FF;
    }
</style>
