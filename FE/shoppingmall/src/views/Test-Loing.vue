<template>
  <div> </div>
</template>

<script>

import axios from "axios";

export default {
    setup() {
     var url = 'http://localhost:8080/auth/naver';
     var param = [];
     var count = 0;
     console.log("안녕");
     const searchParams = new URLSearchParams(location.search);
     console.log(param)
     for (const urlparam of searchParams) {
        param[count++] = urlparam[1] 
    }
    axios.get(url, {
        params: {
          code : param[0],
          state : param[1]
        },
      }).then((res) => {
        console.log(res.data);
      }).catch((error) => {
        console.log("error##");
        console.log(error);
      }).then(() => {
        console.log('Naver auth!!');
      });
    },

    data () {
    return {

    }
  },

  methods: {
     naver(searchParams) {
      console.log(searchParams)
    },
  },
  
  watch: {
    searchParams : function (val) {
      this.fullName = val + ' ' + this.lastName
    }
  },
}
</script>

<style>

</style>