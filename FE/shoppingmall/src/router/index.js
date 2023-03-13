import { createRouter, createWebHistory } from 'vue-router'

import Test_Loing from '../views/Test-Loing.vue'
import Home from '../views/Home.vue'
import InputTag from '../components/first-view.vue';
import NotFound from "../views/NotFound.vue";
import SingUp from "../views/SingUp.vue";
import User_Login from "../views/User-Login.vue";
                                
const routes = [
    {
      path: "/",
      name: "Home",
      component: Home,
    },

    {
      path: "/login/oauth2/code/naver",
      name: "TestTest_Loingoing",
      component: Test_Loing,
    },


    {
      path: "/login",
      name: "User_Login",
      component: User_Login,
    },

    {
      path: "/singup",
      name: "singup",
      component: SingUp,
    },
    
    {
        path: "/1",
        name: "InputTag",
        component: InputTag,
     },
    
    {
        path: "/404",
        name: "notFound",
        component: NotFound,
    },

    {
        path: "/:pathMatch(.*)*",
        redirect: "/404",
    },
  ];
  
const router = createRouter({
    history: createWebHistory(),
    routes
});

export {router}