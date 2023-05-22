<template>
  <div class="review-list">
    <div class="button-container">
      <div class="tab-buttons">
        <button @click="showAllReviews" :class="{ active: activeTab === 'all' }">전체</button>
        <button @click="showFollowerReviews" :class="{ active: activeTab === 'follower' }">팔로워</button>
        <button @click="showMyReviews" :class="{ active: activeTab === 'my' }">내 글</button>
      </div>
      <router-link to="/review/write">
        <button class="register-button">후기 글 작성</button>
      </router-link>
    </div>
    <div v-for="(chunk, index) in chunkedReviews" :key="index" class="review-row">
      <review-list-item v-for="review in chunk" :key="review.review_id" :review="review"></review-list-item>
    </div>
  </div>
</template>

<script>
import ReviewListItem from "./ReviewListItem.vue";
import axios from "axios";

export default {
  name: "ReviewList",
  components: {
    ReviewListItem,
  },
  data() {
    return {
      user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
      reviews: [],
      my_reviews: [],
      follower_reviews: [],
      currentPage: 1,
      pageSize: 8,
      activeTab: "",
    };
  },
  created() {
    axios.get("http://localhost:80/review/api/list").then((res) => {
      this.reviews = res.data.list;
    })

    axios.get("http://localhost:80/review/api/follower_review/" + this.user_id).then((response) => {
      this.follower_reviews = response.data.review;
    })

    axios.get("http://localhost:80/review/api/my_review/" + this.user_id).then((response) => {
      this.my_reviews = response.data.review;
    })
  },
  computed: {
    chunkedReviews() {
      const chunkSize = 4; // 한 줄에 보여줄 리뷰 개수
      const chunks = [];
      let index = 0;

      const tmp_reviews = [];

      if (this.activeTab == "follower") {
        this.tmp_reviews = this.follower_reviews;
        console.log("팔로워 글 보기");
      } else if (this.activeTab == "my") {
        this.tmp_reviews = this.my_reviews;
        console.log("내 글 보기");
      } else {
        this.tmp_reviews = this.reviews;
        console.log("전체 보기");
      }

      while (index < this.tmp_reviews.length) {
        chunks.push(this.tmp_reviews.slice(index, index + chunkSize));
        index += chunkSize;
      }

      return chunks;
    },

    paginatedReviews() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tmp_reviews.slice(startIndex, endIndex);
    },
    totalPages() {
      return Math.ceil(this.tmp_reviews.length / this.pageSize);
    },

    currentReviews() {
      // 현재 선택된 탭에 따라 보여줄 리뷰 목록을 결정
      if (this.activeTab === "follower") {
        return this.follower_reviews;
      } else if (this.activeTab === "my") {
        return this.my_reviews;
      } else {
        return this.reviews;
      }
    },
  },
  methods: {
    goToPage(page) {
      this.currentPage = page;
    },

    showFollowerReviews() {
      this.activeTab = "follower";
    },

    showMyReviews() {
      this.activeTab = "my";
    },

    showAllReviews() {
      this.activeTab = "all";
    },
  },
};
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}

.register-button {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  margin-top: 10px;
}

.review-list {
  overflow-x: auto;
  white-space: nowrap;
  margin: 100px;
  /* 상하좌우 여백 설정 */
}

.review-row {
  display: flex;
  margin-bottom: 16px;
  justify-content: center;
}

.review-card {
  flex: 0 0 auto;
  width: 25%;
  /* 가로로 4개씩 */
  padding: 16px;
  /* background-color: #ffffff; */
  /* Change background color to white */
  /* border-radius: 4px; */
  /* border: 2px solid #185ea4; */
  /* Increase border thickness and add blue color */
  margin-right: 16px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}

.pagination button {
  padding: 6px 10px;
  background-color: #f5f5f5;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 8px;
}

.pagination button.active {
  background-color: #42a1ff;
  color: #ffffff;
}

.tab-buttons {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  margin-top: 10px;
}
</style>