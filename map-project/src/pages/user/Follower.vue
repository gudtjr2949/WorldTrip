<template>
    <div>
        <h3>Follower List</h3>
        <ul>
            <li v-for="follower in followerList" :key="follower.user_id">
                <strong>아이디:</strong> {{ follower.user_id }} - <strong>이름:</strong> {{ follower.user_name }}
            </li>
        </ul>
    </div>
</template>
  
<script>
import axios from "axios";
import router from "../../router";

export default {
    data() {
        return {
            followerList: [],
        };
    },
    created() {
        const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;

        axios.get(`http://localhost:80/user/api/follower_list/${userId}`).then((res) => {
            this.followerList = res.data.list;
            console.log(this.followerList);
        });
    },
};
</script>
  
<style scoped>
  /* 스타일 내용 작성 */
  </style>
  