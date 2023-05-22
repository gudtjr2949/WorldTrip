<template>
  <div style="width: 100%; height: 800px;">
  </div>
</template>
  
<script>
import axios from "axios";

export default {
  data() {
    return {
      user_id: '',
      user_name: '',
    };
  },
  created() {
    // this.user_id = this.$route.query.user_id;
    // this.user_name = this.$route.query.user_name;

    if (this.$route.query.user_id) {
      const userInfo = {
        user_id: this.$route.query.user_id,
        user_name: this.$route.query.user_name
      };

      console.log(this.$route.query.user_id);
      console.log(this.$route.query.user_name);

      sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
    }
  },
};
</script>
  
<style scoped></style>
  