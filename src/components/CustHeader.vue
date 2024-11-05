<template>
    <div class="nav">
        <div class="left-links">
            <router-link to="/customer-home">Home</router-link>
            <router-link to="/food-items">Items</router-link>
            <router-link to="/privacy-policy">Privacy</router-link>
            <router-link to="/contactus">Contact us</router-link>
        </div>
        <div class="right-links">
            <router-link to="/customer-profile">👤</router-link>
            <router-link to="/addtocart">🛒</router-link>
            <router-link to="/feedback">💬</router-link>
            <a @click="logout">🚪</a>
        </div>
    </div>
</template>

<script>
export default {
  name: 'CustomerHeader',
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push({ name: 'LoginPage' });
    }
  }
}
</script>

<style>
.nav {
    background-color: #333;
    overflow: hidden;
    display: flex;
    justify-content: space-between;
    height: 50px;
    align-items: center;
    color: white;
}

.nav a {
    color: white;
    padding: 14px 16px;
    text-align: center;
    font-size: 18px;
    text-decoration: none;
}

.nav a:hover {
    background: #ddd;
    color: grey;
}

.right-links {
    display: flex;
    align-items: center;
}

.right-links a {
    margin-left: 10px;
}
</style>
