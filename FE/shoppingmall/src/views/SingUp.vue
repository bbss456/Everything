<template>
<div class="bg-grey-lighter min-h-screen flex flex-col bg-gray-100">
            <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2">
                <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
                    <h1 class="mb-8 text-3xl text-center text-rose-600">{{SingUp}}</h1>
                    
                    <input 
                      type="text"
                      class="block border border-grey-light w-full p-3 rounded mb-4"
                      name="fullname"
                      placeholder="ID" />

                    <input 
                        type="text"
                        class="block border border-grey-light w-full p-3 rounded mb-4"
                        name="fullname"
                        placeholder="Full Name" />

                    <input 
                        type="text"
                        class="block border border-grey-light w-full p-3 rounded mb-4"
                        name="email"
                        placeholder="Email" />

                    <input 
                        type="password"
                        class="block border border-grey-light w-full p-3 rounded mb-4"
                        name="password"
                        placeholder="Password" />
                    
                    <input 
                        type="password"
                        class="block border border-grey-light w-full p-3 rounded mb-4"
                        name="confirm_password"
                        v-model="passwordcheck"
                        placeholder="Confirm Password" />
                        
                     <p class="text-left mb-6 text-rose-600 ">So I started to walk into the water...</p>

                    <button
                        type="submit"
                        class="text-white bg-gradient-to-br from-pink-500 to-orange-400 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-pink-200 dark:focus:ring-pink-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2rounded w-full"
                    >Create Account</button>
                </div>

                <div class="text-grey-dark mt-6">
                    Already have an account? 
                    <a class="text-fuchsia-400" href="../login/">
                    {{SingIn}}
                    </a>
                </div>

                <div class="text-grey-dark mt-1">
                    do you want to go back? 
                    <a class="text-fuchsia-400" href="/">
                    {{Home}}
                    </a>
                </div>
            </div>
        </div>
</template>
<script>

export default {
  name: 'App',
  
  data() {
    return {
      passwordcheck : null,
      SingIn : "Sing In",
      SingUp : "Sing Up",
      Home : "Home"

    }
  },
  methods : {
    checkPassword() {
      // 비밀번호 형식 검사(영문, 숫자, 특수문자)
      const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/

       if (validatePassword.test(this.passwordcheck) == false) {
          console.log("실패") 
      } else{
        console.log("성공")
      }
    }
  },

 watch: {
    'passwordcheck': function() {
      this.checkPassword()
    }
 },

  components: {
   
  }
}
</script>
