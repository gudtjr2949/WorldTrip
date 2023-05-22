<template>
  <div class="review-card">
    <router-link :to="`/review/detail/${review.review_id}`" class="review-card-link">
      <div class="review-card-image">
        <div class="image-container">
          <img :src="getImageUrl(review)" alt="Review Image" class="review-image" style="width: 200px; height: 150px;"/>
        </div>
      </div>
      <div class="review-card-title">
        <h3>{{ review.title }}</h3>
      </div>
      <div class="review-card-content">
        <p><strong>작성자:</strong> {{ review.user_id }}</p>
        <p><strong>조회수:</strong> {{ review.hit }}</p>
        <p><strong>작성일:</strong> {{ review.register_time }}</p>
      </div>
    </router-link>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  name: "ReviewListItem",
  props: {
    review: Object,
  },
  methods: {
    getImageUrl(review) {
      return require("@/assets/" + review.review_id + "/" + review.first_image);
    },
  }
};
</script>
  
<style scoped>
.review-card {
  flex: 0 0 auto;
  width: 20%; /* Adjust the width as desired */
  padding: 8px; /* Adjust the padding as desired */
  background-color: #ffffff;
  /* border: 2px solid gray; */
  box-sizing: border-box;
  transition: transform 0.3s ease;
}
.review-card:hover {
  transform: scale(1.05);
}

.review-card-link {
  display: block;
  height: 100%;
  color: inherit;
  text-decoration: none;
}

.review-card-title {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 8px;
}

.review-card-content {
  font-size: 14px;
  color: #666666;
}

/* .review-card-image {
  display: flex;
  align-items: center;
} */

.image-container {
  max-width: 100%;
  max-height: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center; 
  align-items: center;
}

.image-container img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.review-image {
  width: 100%;
  height: 100%;
}
</style>
