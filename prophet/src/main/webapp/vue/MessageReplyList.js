import submitform from './MessageBoard.js';

export default {
    data() {
        return {
            replysList: []
        }
    },
    methods: {
        updateReplys() { //更新内容
            var storage = window.localStorage;
            this.replysList = storage.hasOwnProperty('reply') ? JSON.parse(storage['reply']) : [];
        },
        removeReply(id) {
            if (confirm("是否要删除此评论？")){
                for (var i = 0; i < this.replysList.length; i++) {
                    if (this.replysList[i].id == id){
                        this.replysList.splice(i, 1);
                    }
                }

                var storage = window.localStorage;
                storage['reply'] = JSON.stringify(this.replysList);
            }
        }
    },
    components:{
        'submit-form': submitform
    },
    created: function () {
        this.updateReplys();
    },
    template: `
    <div>
        <submit-form @up="updateReplys"></submit-form>
    
        <div v-for="reply in replysList" class="aReply" :key="reply.id">
            <p>
                {{ reply.username }} 
            </p>
            <p> 
                 {{ reply.replycontent }}
            </p>
            <button @click="removeReply(reply.id)" style="background-color: #ee2455;">删除</button>
        </div>
        <p v-if="replysList.length == 0">暂时没有任何留言</p>
    </div>
    `
};