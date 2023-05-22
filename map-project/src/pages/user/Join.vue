<template>
    <div>
        <div class="form-group">
            <label for="user_id">아이디</label>
            <div class="input-container">
                <input type="text" id="user_id" ref="user_id" v-model="user_id" @input="checkDuplicateId" />
                <span class="status-message" v-if="idCheckResult === true" style="color: blue;">사용 가능한 아이디입니다.</span>
                <span class="status-message" v-if="idCheckResult === false" style="color: red;">이미 사용 중인 아이디입니다.</span>
            </div>
        </div>
        <div class="form-group">
            <label for="user_pw">비밀번호</label>
            <input type="password" id="user_pw" ref="user_pw" />
        </div>
        <div class="form-group">
            <label for="user_name">이름</label>
            <input type="text" id="user_name" ref="user_name" />
        </div>
        <div class="form-group">
            <label for="user_email_domain">이메일</label>
            <div class="email-domain-container">
                <input type="text" id="user_email" ref="user_email" />
                <span class="at-symbol">@</span>
                <input type="text" id="user_domain" ref="user_domain" />
            </div>
        </div>
        <button class="register-button" @click="checkValue">회원가입</button>
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
            user_name: '',
            user_email: '',
            user_domain: '',
            idCheckInProgress: false,
            idCheckResult: null,
        }
    },
    methods: {
        checkValue() {
            let err = true;
            let msg = "";

            !this.idCheckResult &&
                ((msg = "아이디 중복 확인해주세요"),
                    (err = false),
                    this.$refs.user_id.focus());
            err &&

                !this.$refs.user_id.value &&
                ((msg = "아이디 입력해주세요"),
                    (err = false),
                    this.$refs.user_id.focus());
            err &&
                !this.$refs.user_pw.value &&
                ((msg = "비밀번호 입력해주세요"),
                    (err = false),
                    this.$refs.user_pw.focus());
            err &&
                !this.$refs.user_name.value &&
                ((msg = "이름 입력해주세요"),
                    (err = false),
                    this.$refs.user_name.focus());
            err &&
                !this.$refs.user_email.value &&
                ((msg = "이메일 입력해주세요"),
                    (err = false),
                    this.$refs.user_email.focus());
            err &&
                !this.$refs.user_domain.value &&
                ((msg = "도메인 입력해주세요"),
                    (err = false),
                    this.$refs.user_domain.focus());

            if (!err) alert(msg);
            // 만약, 내용이 다 입력되어 있다면 registReview 호출
            else this.Join();
        },
        Join() {
            axios
                .post("http://localhost:80/user/api/join", {
                    user_id: this.$refs.user_id.value,
                    user_pw: this.$refs.user_pw.value,
                    user_name: this.$refs.user_name.value,
                    user_email: this.$refs.user_email.value,
                    user_domain: this.$refs.user_domain.value,
                })
                .then((response) => {
                    router.push("/user/login");
                });
        },
        checkDuplicateId() {
            this.idCheckInProgress = true;
            this.idCheckResult = null;

            axios
                .get(`http://localhost:80/user/api/idCheck/${this.user_id}`)
                .then((response) => {
                    const checkResult = response.data.check;
                    this.idCheckResult = checkResult;
                    this.idCheckInProgress = false;
                })
                .catch((error) => {
                    console.error(error);
                    this.idCheckResult = null;
                    this.idCheckInProgress = false;
                });
        },
    }
}
</script>
<style scoped>
.input-container {
    position: relative;
    /* margin-right: 10px; */
}

.input-container .status-message {
    margin-left: 10px;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    font-size: 12px;
}



.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-weight: bold;
}

.email-domain-container {
    align-items: center;
}

input {
    padding: 8px;
    border: 1px solid #cccccc;
    border-radius: 4px;
}

.at-symbol {
    margin: 0 4px;
    font-weight: bold;
}

.register-button {
    padding: 12px;
    background-color: #42a1ff;
    color: #ffffff;
    font-weight: bold;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 10px;
}

.register-button:hover {
    background-color: #1e88e5;
}
</style>