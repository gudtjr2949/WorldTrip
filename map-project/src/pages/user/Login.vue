<template>
  <div class="login-container">
    <div class="login-form">
      <div class="form-group">
        <label for="user_id">아이디</label>
        <input type="text" id="user_id" ref="user_id" />
      </div>
      <div class="form-group">
        <label for="user_pw">비밀번호</label>
        <input type="password" id="user_pw" ref="user_pw" />
      </div>
      <div class="button-group">
        <button class="login-button" @click="checkValue">로그인</button>
        <button class="login-button" @click="Join">회원가입</button>
      </div>
      <router-link to="/kakao">
        <img src="@/assets/pngegg.png" alt="카카오 이미지" class="kakao-image"/>
      </router-link>
    </div>
    
  </div>
</template>
<script>
import axios from "axios";
import router from "../../router";

export default {
  data() {
    return {
      user_id: '',
      user_pw: '',
    }
  },
  methods: {
    checkValue() {
      let err = true;
      let msg = "";

      !this.$refs.user_id.value &&
        ((msg = "아이디 입력해주세요"),
          (err = false),
          this.$refs.user_id.focus());
      err &&
        !this.$refs.user_pw.value &&
        ((msg = "비밀번호 입력해주세요"),
          (err = false),
          this.$refs.user_pw.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registReview 호출
      else this.Login();
    },
    Login() {
      axios
        .post("http://localhost:80/user/api/login", {
          user_id: this.$refs.user_id.value,
          user_pw: this.$refs.user_pw.value,
        })
        .then((response) => {
          // 로그인 성공 시 세션 스토리지에 저장
          const userInfo = {
            user_id: response.data.user_id,
            user_name: response.data.user_name
          };

          sessionStorage.setItem("userInfo", JSON.stringify(userInfo));

          router.push("/");
          this.$router.go(this.$router.currentRoute);
        }).catch((error) => {
          alert("로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
        });
    },
    Join() {
      router.push("/user/join");
    },
  }
}
</script>
<style scoped> .login-container {
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   height: 100vh;
   /* background-image: url('/Users/leehyungseok/VS-workspace/map-project/src/assets/wave.png'); */

 }

 .login-form {
   background-color: #ffffff;
   padding: 40px 500px;
   border-radius: 8px;
   box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);

 }

 .form-group {
   margin-bottom: 20px;
 }

 label {
   display: block;
   font-weight: bold;
 }

 input {
   width: 100%;
   padding: 8px;
   border: 1px solid #cccccc;
   border-radius: 4px;
 }

 .button-group {
   display: flex;
   /* 버튼들을 가로로 나란히 배치하기 위한 설정 */
 }

 .login-button {
   flex: 1;
   /* 버튼들이 동일한 너비를 가지도록 설정 */
   padding: 12px;
   background-color: #42a1ff;
   color: #ffffff;
   font-weight: bold;
   border: none;
   border-radius: 4px;
   cursor: pointer;
   margin-right: 8px;
   /* 버튼들 사이의 간격을 조정 */
 }

 .login-button:last-child {
   margin-right: 0;
   /* 마지막 버튼의 오른쪽 간격 제거 */
 }

 .login-button:hover {
   background-color: #1e88e5;
 }

 .kakao-image {
   margin-top: 20px;
   width: 90px;
 }
</style>