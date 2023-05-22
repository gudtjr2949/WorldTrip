<template>
  <div class="footer">
    <div class="footer-content">
      주식회사 최범선
    </div>
  </div>
</template>
  
<script>
export default {
  name: "TheFooter",
};
</script>
  
<style scoped>
.footer {
  /* position: fixed; */
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 40px;
  text-align: center;
  background: linear-gradient(135deg, #3be036, #ffffff);
}

.footer-content {
  font-size: 14px;
  color: black;
}
</style>
  