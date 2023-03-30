<template>
<section class="h-screen" >

 <LoginModal v-show="isModalOpen" @setlogin="setlogin"></LoginModal>

 <headerComponent v-bind:message="message" @setlogin="setlogin"></headerComponent>
  <!-- <span>{{ $store.state.name}} </span> -->
 
  <div class="grid grid-cols-4 gap-4">
  <div class="black-bg" v-if="modarBoolean==true">
    <div class="white-bg">
        <h4>상세페이지</h4>
        <p>상세페이지 내용</p>
    </div>
  </div>

   
  <!-- <div v-for="(oneroomlist) in oneroomlist" :key="oneroomlist">
    <div onclick="location.href='이동할 링크주소(URL)';"  style=" cursor: pointer;">
      <img :src="oneroomlist.image" class="room-img">
      <h4>이름:{{oneroomlist.title}}</h4>
      <p>가격:{{oneroomlist.content}}</p>
      <p>가격:{{oneroomlist.price}}</p>
    </div>  
  </div> -->
</div>

</section>
</template>

<script>

import LoginModal from '../components/login-modal';
import headerComponent from '../components/header-components';

export default {
  
  name: 'App',
  
  data() {
    return {
      loginCount : 0,
      message:'hi',
      modarBoolean : false,
      isModalOpen :  false,
      declarationCount : [0,0,0],
      topNavigator : ['Home', 'Shop', 'About'],
      products: ['역삼동원룸', '천호동원룸', '마포구원룸'],
      events: ["dblclick", "click"]
      
    }
  },
  
  emits : ["setlogin"],

  methods : {

    increase(){
      this.declarationCount ++
    },

    controlModal(togle, count)  {
      if(this.loginCount <= 0 && count != 1) {
        this.loginCount += count;
      } else if (this.loginCount > 0) {
        this.loginCount -= count;
      }

      if(this.loginCount == 1) {
        this.isModalOpen = true;
      } else{
        this.isModalOpen = false
      }

    },
 
    setlogin(togle, count) {
      this.controlModal(togle, count);
    }
  
  },

  components: {
   LoginModal,
   headerComponent
  },
   
}
</script>

<style>

div {
  box-sizing: border-box;
}
.black-bg {
  width: 100%; height: 100%;
  background: rgba(0, 0, 0,0.5);
  position: fixed; padding: 20px;
}
.white-bg {
  width: 100%; background: white;
  border-radius: 8px;
  padding: 20px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.room-img {
  width: 100%;
  margin-top: 40px;
}

 .menu {
  background: darkslateblue;
  padding: 15px;
  border-radius: 5px;
 }
 .menu a {
  color: white;
  padding: 10px;
 }
</style>
