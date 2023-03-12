<template>
<div x-data="{show:false}">
  <div class="flex items-center justify-center mt-40"></div>
  <div class="overlay" @click="$emit('close-modal', false)"> </div>
  <div x-show="show"
      x-transition:enter="transition ease-out duration-300"
      x-transition:enter-start="opacity-0 scale-90"
      x-transition:enter-end="opacity-100 scale-100"
      x-transition:leave="transition ease-in duration-300"
      x-transition:leave-start="opacity-100 scale-100"
      x-transition:leave-end="opacity-0 scale-90"
      class="absolute inset-0 flex items-center justify-center bg-gray-700 bg-opacity-50">

    <div ref="login"  id="loginRoot"  @click="show = false" class="flex flex-col w-full p-20 m-8 bg-white rounded-md lg:m-0 lg:w-1/4 sm:p-10"  v-click-out-side="customMethod"> 
      <div class="mb-8 text-center">
        <h1 class="my-3 text-4xl font-bold">Sign in</h1>
        <p class="text-sm text-coolGray-600">Sign in to access your account</p>
      </div>
      <form action="" class="">
        <div class="space-y-4">
          <div>
            <label for="email" class="flex justify-between mb-1">Email address</label>
            <input type="email" name="email" placeholder="email"
              class="w-full px-3 py-2 text-blue-800 border border-blue-300 rounded-md bg-blue-50 focus:outline-none focus:ring-1 focus:ring-blue-300">
          </div>
          <div>
            <div class="flex justify-between mb-1">
              <label for="password" class="text-sm">Password</label>
              <a href="#" class="text-xs text-blue-600 hover:underline">Forgot password?</a>
            </div>
            <input type="password" name="password" placeholder="password"
              class="w-full px-3 py-2 text-blue-800 border border-blue-300 rounded-md bg-blue-50 focus:outline-none focus:ring-1 focus:ring-blue-300">
          </div>
        </div>
        <div class="mt-6 space-y-2">
          <div class="flex gap-x-2">
            <button type="button" class="w-full px-8 py-3 text-blue-100 bg-blue-600 rounded-md">Sign
              in</button>
            <button @click="show = false" type="button"
              class="w-full px-8 py-3 text-blue-600 border border-blue-600 rounded-md">Cancel</button>
          </div>
          <p class="px-6 text-sm text-center text-coolGray-600">Don't have an account yet?
            <a href="#" class="text-blue-600 hover:underline">Sign up</a>.
          </p>
        </div>
      </form>
      <div class="flex justify-center  md:w-full pt-3">
          <button class="inline-flex items-center justify-center w-12 h-12 mr-2 text-pink-100 transition-colors duration-150 rounded-full focus:shadow-outline">
            <img   src="https://clove.kr/wJk/img/sns/naver_icon.png"/>
          </button>

          <button class="inline-flex items-center justify-center w-12 h-12 mr-2 text-pink-100 transition-colors duration-150  rounded-full focus:shadow-outline ">
            <img   src="../assets/free-icon-kakao-talk-4494622.png" />
          </button>

          <button class="inline-flex items-center justify-center w-12 h-8 mr-2 text-pink-100 transition-colors duration-150  rounded-full focus:shadow-outline ">
               <img src="https://lh3.googleusercontent.com/jUoaTIlBn5ibfQcND2n5OMD6Z7xoqNj-ShHlFR6QuLffLXD5pS8V2eNg1rGlrsRrnDkoQ28O8UHzqzBQKAGY4l1CS2NQSq2SkRScK6FOjl82jppyohK-" />
          </button>


        </div>
      </div>
  </div>
</div>
</template>

<script>
 import clickOutSide from "@mahdikhashan/vue3-click-outside";
export default {
  

  data () {
    return {
      user: undefined
    }
  },
  methods: {
    customMethod() {
      this.$emit("setlogin", false, 1);
    },
  
  },
   directives: {
    clickOutSide,
  },
}
</script>

<style>
.bg-image {
    background-image: url(https://i.postimg.cc/13pssvxG/bg-image.png);
}
.backdrop {
    backdrop-filter: blur(2px);
}
.overlay{
width: 100%;
height: 100;
position: fixed;
left: 0;
top: 0;
}   
</style>