<template>
    <CustHeader></CustHeader>
    <div class="add-to-cart-container">
      <h1>Add to Cart</h1>
    
    
      <div class="cart-items">
        <div v-for="(item, index) in selectedItems" :key="index" class="cart-item">
          <img :src="require(`@/assets/home_fooditems/${item.image}`)" :alt="item.name" class="cart-item-image">
          <div class="cart-item-details">
            <h3 class="cart-item-name">{{ item.name }}</h3>
            <p class="cart-item-price">{{ item.price }}</p>
    
            <input type="number" v-model="item.quantity" @input="updateTotalPrice" min="1">
            <p class="cart-item-total-price">Total: {{ calculateTotalPrice(item) }}</p>
    
          </div>
        </div>
      </div>
    
    
      <div class="address-form">
        <h2>Delivery Address</h2>
        <input type="text" v-model="address" placeholder="Enter your address">
      </div>
    
    
      <div class="payment-selection">
        <h2>Select Payment Method</h2>
        <label>
          <input type="radio" v-model="paymentMethod" value="credit_card">
          Credit Card
        </label>
        <label>
          <input type="radio" v-model="paymentMethod" value="UPI">
          UPI
        </label>
    
      </div>
    
    
      <button class="checkout-btn" @click="proceedToCheckout">Proceed to Checkout</button>
    </div>
    <div class="gap"></div>
  </template>
  
   <script>
  import CustHeader from './CustHeader.vue'
  export default {
    components: { CustHeader },
    data() {
      return {
        selectedItems: [],
        address: '',
        paymentMethod: '',
        totalPrice: 0
      };
    },
    methods: {
  
      calculateTotalPrice(item) {
        return parseFloat(item.price) * item.quantity;
      },
  
      updateTotalPrice() {
        this.totalPrice = this.selectedItems.reduce((total, item) => total + parseFloat(this.calculateTotalPrice(item)), 0);
      },
  
      addItemToCart(item) {
  
        const index = this.selectedItems.findIndex(selectedItem => selectedItem.id === item.id);
        if (index !== -1) {
  
          this.selectedItems[index].quantity++;
        } else {
  
          this.selectedItems.push({ ...item, quantity: 1 });
        }
  
        this.updateTotalPrice();
      },
  
      proceedToCheckout() {
  
      }
    }
  };
  </script>
  
    
    <style scoped>
  .add-to-cart-container {
      margin: 20px;
    
    }
    
    
    .cart-items {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
    }
    
    .cart-item {
      background-color: #f9f9f9;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    
    .cart-item-image {
      width: 100%;
      height: 200px;
      border-radius: 5px;
    }
    
    .cart-item-details {
      margin-top: 10px;
    }
    
    .cart-item-name {
      margin: 0;
      font-size: 18px;
    }
    
    .cart-item-price {
      margin: 5px 0;
      font-size: 16px;
    }
    
    
    input[type="number"] {
      width: 50px;
      padding: 5px;
      border: 1px solid #ccc;
      border-radius: 5px;
      box-sizing: border-box;
    }
    
    .cart-item-total-price {
      font-size: 14px;
    }
    
    
    .address-form {
      margin-top: 30px;
    }
    
    
    .payment-selection {
      margin-top: 30px;
    }
    
    
    .checkout-btn {
      padding: 10px 20px;
      margin-top: 20px;
      border: none;
      border-radius: 5px;
      background-color: #007bff;
      color: #fff;
      cursor: pointer;
    }
    
    .checkout-btn:hover {
      background-color: #0056b3;
    }
    .gap{
      margin-bottom:100px;
    }
  </style>
    