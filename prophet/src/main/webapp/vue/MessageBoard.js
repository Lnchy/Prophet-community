export default {
    data(){
      return {
          username: '',
          replycontent: ''
      }
    },
    methods: {
        checkContentIsNull() {
            return !(this.username.length < 1 || this.replycontent.length < 1);
        },
        addReply() {
            if (!this.checkContentIsNull()) {
                alert("用户名或内容不能为空");
            } else {
                var storage = window.localStorage;
                var parse = [];
                if (storage.hasOwnProperty('reply')){
                    parse = JSON.parse(storage['reply']);
                }

                parse.push({
                    id: +new Date(),
                    username: this.username,
                    replycontent: this.replycontent
                });

                storage['reply'] = JSON.stringify(parse);

                this.username = '';
                this.replycontent = '';
                //向上抛出事件
                this.$emit('up');
            }
        }
    },
    template: `
    <div id="submitform">
        <table>
            <tr>
                <td align="right">用户名：</td>
                <td><input type="text" v-model="username"></td>
            </tr>
            <tr>
                <td valign="top" align="right">评论内容：</td>
                <td><textarea v-model="replycontent"></textarea></td>
            </tr>
            <tr align="right">
                <td colspan="2"><button @click="addReply">发布</button></td>
            </tr>
        </table>
    </div>
    `
}

