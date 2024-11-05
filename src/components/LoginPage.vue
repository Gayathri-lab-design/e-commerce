<template>
    <div class="login">
      <h1>Customer Login</h1>
      <div class="form-box">
        <form @submit.prevent="login" class="login-form">
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>
          </div>
          <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
import axios from 'axios';
import data from '../assets/data.json';

  export default {
    data() {
      return {
        username: '',
        password: '',
        url : data.baseUrl,
        admins: []
      };
    },
    methods: {
     async login() {
      const requestBody = {
        name : this.username,
        password : this.password
      };
      await axios.post(this.url +"/v1/login", requestBody)
      .then(response =>{
         if(response.data == true){
           this.$router.push('/customer-home');
         }
         else{
          alert("Enter Valid USERNAME AND PASSWORD.!")
         }
        })
      .catch(reject => console.log(reject.data));
      },
    },
  };
  </script>
  
  <style scoped>
  .login {
    max-width: 600px;
    margin: 0 auto;
    margin-top: 100px;
  }
  
  .login h1 {
    text-align: center;
    font-size: 28px;
  }
  
  .form-box {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: white;
  }
  
  .login-form {
    width: 80%;
    margin: 0 auto;
  }
  
  .form-group {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }
  
  .form-group label {
    flex: 1;
    font-weight: bold;
  }
  
  .form-group input[type="text"],
  .form-group input[type="password"] {
    flex: 2;
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  button {
    width: 100%;
    padding: 10px 20px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  </style>
  