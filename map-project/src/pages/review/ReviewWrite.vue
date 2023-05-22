<template>
    <div class="review-write">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" ref="title" class="form-control" />
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" ref="content" class="form-control"></textarea>
        </div>
        <div class="form-group">
            <label for="image">사진 업로드</label>
            <input type="file" multiple ref="image" @change="onInputImage" class="form-control-file" />
        </div>

        <div class="uploaded-images">
            <div v-for="(image, index) in uploadedImages" :key="index" class="uploaded-image">
                <img :src="image.url" alt="Uploaded Image" />
                <span class="remove-image" @click="removeImage(index)">x</span>
            </div>
        </div>

        <button @click="checkValue" class="btn btn-primary">작성</button>
    </div>
</template>
<script>
import axios from "axios";
import router from "../../router";

export default {
    data() {
        return {
            user_id: '',
            title: '',
            content: '',
            image: [],
            uploadedImages: [],
        }
    },
    methods: {
        checkValue() {
            // 사용자 입력값 체크하기
            // 작성자아이디, 제목, 내용이 없을 경우 각 항목에 맞는 메세지를 출력
            let err = true;
            let msg = "";

            !this.$refs.title.value &&
                ((msg = "제목 입력해주세요"),
                    (err = false),
                    this.$refs.title.focus());
            err &&
                !this.$refs.content.value &&
                ((msg = "내용 입력해주세요"),
                    (err = false),
                    this.$refs.content.focus());

            if (!err) alert(msg);
            // 만약, 내용이 다 입력되어 있다면 registReview 호출
            else this.registReview();
        },
        registReview() {
            const formData = new FormData();
            const imageFiles = this.image;

            for (let i = 0; i < imageFiles.length; i++) {
                formData.append("image[]", imageFiles[i]);
            }

            console.log(formData.get("image[]"));

            formData.append("user_id", JSON.parse(sessionStorage.getItem("userInfo")).user_id);
            formData.append("title", this.$refs.title.value);
            formData.append("content", this.$refs.content.value);

            axios.post("http://localhost:80/review/api/write", formData)
                .then(() => {
                    router.push("/review/list");
                });
        },
        onInputImage() {
            const selectedFiles = Array.from(this.$refs.image.files);
            this.image.push(...selectedFiles);
            this.uploadedImages = this.image.map((file) => ({
                url: URL.createObjectURL(file),
                file: file,
            }));
        },
        removeImage(index) {
            this.uploadedImages.splice(index, 1);
            this.image.splice(index, 1);
        },
    }
}
</script>
<style scoped>
.review-write {
    max-width: 400px;
    margin: 0 auto;
    height: 600px;
}

.form-group {
    margin-bottom: 20px;
}

label {
    font-weight: bold;
}

.form-control {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 10px;
}

textarea.form-control {
    height: 150px;
}

.custom-file {
    position: relative;
    display: inline-block;
    width: 100%;
    height: calc(2.25rem + 2px);
}

.custom-file-input {
    position: relative;
    z-index: 2;
    width: 100%;
    height: calc(2.25rem + 2px);
    margin: 0;
    opacity: 0;
}

.custom-file-input:focus~.custom-file-label {
    border-color: #80bdff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.custom-file-label {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    z-index: 1;
    padding: 0.375rem 0.75rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    border: 1px solid #ced4da;
    border-radius: 10px;
}

.btn {
    display: inline-block;
    padding: 10px 20px;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    text-decoration: none;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 10px;
}

.btn-primary {
    background-color: #007bff;
}

.btn-primary:hover {
    background-color: #0056b3;
}

.btn-primary:active {
    background-color: #004080;
}

.uploaded-images {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 10px;
}

.uploaded-image {
    position: relative;
    margin-right: 10px;
    margin-bottom: 10px;
}

.uploaded-image img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 4px;
}

.remove-image {
    position: absolute;
    top: -5px;
    right: -5px;
    width: 20px;
    height: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ffffff;
    border-radius: 50%;
    color: #ff0000;
    font-weight: bold;
    cursor: pointer;
}
</style>