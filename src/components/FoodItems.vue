<template>
    <div>
      <CustHeader></CustHeader>
      <div class="food-items-container">
        <h1>Food Items</h1>
        <div class="filters">
          <input type="text" v-model="searchQuery" placeholder="Search food items">
        </div>
      
        <div class="food-grid">
          <div v-for="(item, index) in filteredFoodItems" :key="index" class="food-item">
            <img :src="require(`@/assets/home_fooditems/${item.image.trim()}`)" :alt="item.name" class="food-image">
            <div class="food-details">

              <!-- Dialog Box -->
              <v-dialog v-model="dialog" class="dialog-box">
                <v-card>
                  <v-card-title>
                    <span class="headline">Order Details</span>
                  </v-card-title>
                  <v-card-text>
                    <img :src="require(`@/assets/home_fooditems/${image.trim()}`)" :alt="item.name" class="food-image">
                    <div>
                      <h3>{{ orderedFood }}</h3>
                      <p>Price:₹{{ price }}</p>
                      <v-row>
                        <v-col cols="12">
                          <v-text-field
                            v-model="quantity"
                            type="number"
                            label="Quantity"
                            @input="calculateTotalPrice"
                            min="1"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <h4>Total Price:$ {{ totalPrice }}</h4>
                    </div>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" @click="placeOrder">Order</v-btn>
                    <v-btn color="grey" @click="dialog = false">Cancel</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>

              <!-- Snackbar -->
              <v-snackbar
                :timeout="2000"
                v-model="snack"
                color="success"
                class="snackbar"
                variant="outlined"
                contained
                location="bottom right"
              >
                <h4 style="color: black;font-family: 'Courier New', Courier, monospace;">
                  {{ snackmsg }}
                </h4>
              </v-snackbar>

              <h3 class="food-name">{{ item.name }}</h3>
              <p class="food-price">{{ item.price }}</p>
              <button class="buy-now-btn" @click="orderNow(item.name, item.price, item.image.trim())">Order Now</button>
            </div>
          </div>
        </div>
      </div>
      <div class="gap"></div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import CustHeader from './CustHeader.vue';
  import data from '../assets/data.json';
  
  export default {
    name: "FoodItems",
    components: { CustHeader },
    data() {
      return {
        foodItems: [],
        orderedFood :'',
        quantity :'',
        price:'',
        image:'',
        dialog : false,
        url : data.baseUrl,
        filteredFoodItems: [],
        searchQuery: '',
        cart: [],
        snackmsg  : "",
        snack : false,
        totalPrice : ''
      };
    },
    mounted() {
      this.fetchFoodItems();
    },
    methods: {
      async fetchFoodItems() {
        try {
          const response = await axios.get(this.url +"/v1/foodItems");
          this.foodItems = response.data;
          this.filteredFoodItems = this.foodItems;
        } catch (error) {
          console.error('Error fetching food items:', error);
        }
      },
      filterItems() {
        this.filteredFoodItems = this.foodItems.filter(item => {
          return item.name.toLowerCase().includes(this.searchQuery.toLowerCase());
        });
      },
      addToCart(item) {
        this.snack=true;
        this.snackmsg = "Added to cart: "+item.name+"."
        this.cart.push(item);
        console.log("Added to cart:", item);
      },
      orderNow(name, price, image){
        this.dialog = true;
        this.orderedFood = name;
        this.price = price;
        this.totalPrice = price;
        this.image= image;
      },
      async placeOrder() {
        const requestBody = {
          foodname : this.orderedFood,
          price : this.totalPrice,
          quantity : this.quantity
        };
        const response = await axios.post(this.url + "/v1/order", requestBody)
        console.log(response.data);
        console.log('Order placed for:', this.orderedFood, 'Quantity:', this.quantity);
        this.dialog = false; // Close the dialog
        this.snack= true;
        this.snackmsg= "Ordered Successfully ..";
      },
      calculateTotalPrice() {
        this.totalPrice = this.price * this.quantity;
      }
    },
    watch: {
      searchQuery() {
        this.filterItems();
      },
      quantity() {
        this.calculateTotalPrice();
      }
    }
  };
  </script>
  
  <style scoped>
  h1 {
    text-align: center;
  }
  
  .filters {
    margin-bottom: 20px;
  }
  
  .filters input[type="text"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }
  
  .food-items-container {
    margin: 10px 10px 80px 10px;
    overflow: visible;
  }

  .dialog-box {
    height: calc(100vh);
    width: calc(50vw);
    padding: 10px;
    max-width: 100%;
  }
  
  .food-grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: center; 
  
  }
  
  .food-item {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: calc(33.33% - 20px); 
    margin-bottom: 20px; 
    margin:15px 40px;
  }
  
  .food-image {
    width: 100%;
    height: 200px;
    border-radius: 5px;
    object-fit: cover;
  }
  
  .food-details {
    margin-top: 10px;
  }
  
  .food-name {
    margin: 0;
    font-size: 18px;
  }
  
  .food-price {
    margin: 5px 0;
    font-size: 16px;
  }
  
  .add-to-cart-btn,
  .buy-now-btn {
    padding: 8px 16px;
    margin-right: 10px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
  }
  
  .add-to-cart-btn:hover,
  .buy-now-btn:hover {
    background-color: #0056b3;
  }
  
  .gap {
    margin-bottom: 30px;
  }
  </style>
  