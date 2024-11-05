// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import HelloWorld from './components/HelloWorld.vue';
import AddStudents from './components/AddStudents.vue';
import CustHeader from './components/CustHeader.vue';
import AddToCart from './components/AddToCart.vue';
import FoodItems from './components/FoodItems.vue';
import HomePage from './components/HomePage.vue';
import LoginPage from './components/LoginPage.vue';

const routes = [
  {
    path: '/chart',
    name: 'HelloWorld',
    component: HelloWorld
  },
  {
    path: '/cjkj',
    name: 'AddStudents',
    component: AddStudents
  },
  {
    path: '/',
    name: 'LoginPage',
    component: LoginPage
  },
  {
    path: '/addtocart',
    name: 'AddToCart',
    component: AddToCart
  },
  {
    path: '/food-items',
    name: 'FoodItems',
    component: FoodItems
  },
  {
    path: '/custheader',
    name: 'CustHeader',
    component: CustHeader
  },
  {
    path: '/customer-home',
    name: 'HomePage',
    component: HomePage
  }

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
