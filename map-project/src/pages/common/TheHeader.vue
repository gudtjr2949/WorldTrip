<template>
  <div class="header" ref="header">
    <div class="header-content">
      <div class="logo">
        <router-link to="/">
          <img :src="require(`@/assets/pngwing.png`)" alt="" />
        </router-link>
      </div>
      <div class="links">
        <router-link v-if="!userInfo" :to="`/user/login`" class="header-link">
          로그인
        </router-link>
        <router-link v-if="userInfo" :to="`/user/mypage`" class="header-link">
          마이페이지
        </router-link>
        <router-link to="/map">지도</router-link>
        <a class="header-link" @click="checkLogin('/review/list')">
          후기 게시판
        </a>
      
      </div>
      <div class="user-info" v-if="userInfo">
        <h3 class="header-user">
          {{ userInfo.user_name }} 님<a @click="Logout"> (로그아웃)</a>
        </h3>
      </div>
    </div>
  </div>
</template>
  
<script>
import router from "../../router/index";

export default {
  name: "TheHeader",
  router,
  data() {
    return {
      userInfo: '',
    };
  },
  mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
  },
  watch: {
    userInfo: {
      handler(userInfo) {
        console.log("변화감지");
        if (this.userInfo) {
          sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
        } else {
          sessionStorage.removeItem("userInfo");
        }
      },
      deep: true,
    },
  },
  methods: {
    checkLogin(path) {
      if (!this.userInfo) {
        alert("로그인 하세요");
        router.push("/user/login");
      } else {
        router.push(path);
      }
    },
    Logout() {
      this.userInfo = null;
      router.push("/");
    },
  }
};
</script>

<style>
img {
  width: 70px;
}

.header {
  padding: 10px 0px;
  text-align: center;
  background-color: #ffffff;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0px 1px 10px rgba(159, 157, 157, 0.3);
  /* 그림자 추가 */
}

a {
  font-weight: bold;
  color: #2c3e50;
}

a:hover {
  color: blue;
}

.header-link {
  font-weight: bold;
  color: #2c3e50;
  margin-right: 10px;
  text-decoration: none;
}

.header-link:hover {
  color: blue;
}

.logo {
  display: flex;
  align-items: center;
}

.links {
  display: flex;
}

.user-info {
  margin-left: auto;
}
</style>
