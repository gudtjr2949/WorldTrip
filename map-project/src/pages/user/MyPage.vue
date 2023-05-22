<template>
    <div class="mypage">
        <div class="user-info">
            <h3 class="user-info-title">사용자 정보</h3>
            <div class="user-info-item">
                <strong>아이디:</strong> {{ user.user_id }}
            </div>
            <div class="user-info-item">
                <strong>이름:</strong> {{ user.user_name }}
            </div>
            <div class="user-info-item">
                <strong>이메일:</strong> {{ user.user_email }} @ {{ user.user_domain }}
            </div>
            <div class="user-info-item">
                <strong>역할:</strong> {{ user.role }}
            </div>
        </div>

        <div class="follower-following">
            <router-link to="/user/follower" class="follower-container">
                <h3>팔로워</h3>
                {{ follower.length }}
            </router-link>

            <div class="following-container">
                <h3>팔로잉</h3>
                {{ following.length }}
            </div>
        </div>

        <div>
            <h3 class="my-review-title">나의 리뷰</h3>
            <ul class="my-review-list">
                <li v-for="item in mylist" :key="item.id" class="my-review-item">
                    <h4>{{ item.title }}</h4>
                    <p>{{ item.content }}</p>
                </li>
            </ul>
        </div>
    </div>
</template>
  
<script>
import axios from "axios";
import router from "../../router";

export default {
    data() {
        return {
            user: {},
            follower: [],
            following: [],
            mylist: [],
        };
    },
    created() {
        const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;
        axios.get(`http://localhost:80/user/api/mypage/${userId}`).then((res) => {
            this.user = res.data.user;
        });

        axios.get(`http://localhost:80/user/api/follower_list/${userId}`).then((res) => {
            this.follower = res.data.list;
            console.log(this.follower);
        });

        axios.get(`http://localhost:80/user/api/following_list/${userId}`).then((res) => {
            this.following = res.data.list;
            console.log(this.following);
        });

        axios.get(`http://localhost:80/review/api/my_review/${userId}`).then((res) => {
            this.mylist = res.data.review;
            console.log(this.mylist);
        })
    },
};
</script>
  
<style scoped>
.mypage {
    margin: 10px 300px;
}

.user-info {
    margin: 10px;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 4px;
}

.user-info-title {
    margin-bottom: 8px;
}

.user-info-item {
    margin-bottom: 4px;
}

.follower-following {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.follower-container,
.following-container {
    width: 45%;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 4px;
    margin: 10px;
}

.follower-container h3,
.following-container h3 {
    margin-bottom: 8px;
}

</style>
  