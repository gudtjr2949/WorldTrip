<template>
  <div id="app">
    <the-header></the-header>
    <img :src="require(`@/assets/map3.png`)" class="background-image">
    <router-view />
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheHeader from './pages/common/TheHeader.vue';
import TheFooter from './pages/common/TheFooter.vue'

export default {
  name: 'App',
  components: {
    TheHeader,
    TheFooter,
  },
  data() {
    return {
      // userInfo: {}
    }
  },
}
</script>

<style>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  /* -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; */
  text-align: center;
  color: #2c3e50;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: -1; /* 이미지를 뒤로 보냄 */
}

</style>

