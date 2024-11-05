<template>
    <div>
        <div>
      <CustHeader></CustHeader></div>
      <div class="wallpaper-carousel">
        <div class="slider" ref="slider">
          <div class="slides" :style="{ transform: 'translateX(' + (-currentIndex * slideWidth) + 'px)' }">
            <div v-for="(image, index) in images" :key="index" class="slide">
              <img :src="image.src" :alt="image.alt" class="carousel-image" />
            </div>
          </div>
        </div>
      </div>
      <div class="boxes-container">
      <div class="box">
        <h2>1</h2><br>
        <h2>Select the food items</h2>
      </div>
      <div class="box">
        <h2>2</h2><br>
        <h2>Fill the details</h2>
      </div>
      <div class="box">
        <h2>3</h2><br>
        <h2>Pay and enjoy your meal</h2>
      </div>
    </div>
    <div class="food-container">
    <h1 style="text-align: center;">Top Order's</h1>
    <div class="food-grid">
        

      <div v-for="(item, index) in foodItems" :key="index" class="food-item">
      
        <img :src="require(`@/assets/home_fooditems/${item.image}`)" :alt="item.name" class="food-image">
        <div class="food-details">
          <h3 class="food-name">{{ item.name }}</h3>
          <p class="food-price">{{ item.price }}</p>
          <button class="add-to-cart-btn">Add to Cart</button>
          <button class="buy-now-btn">Buy Now</button>
        </div>
      </div>
    </div>
</div>
    </div>
  </template>
  
  <script>
  import CustHeader from './CustHeader.vue';
  import axios from 'axios'; 
  
  export default {
    components: {
      CustHeader
    },
    name: "CustHome",
    data() {
      return {
        images: [
          { src: require("@/assets/Carousal/pic1.jpg"), alt: 'Wallpaper 1' },
          { src: require("@/assets/Carousal/pic2.jpg"), alt: 'Wallpaper 2' },
          { src: require("@/assets/Carousal/pic3.webp"), alt: 'Wallpaper 3' },
          { src: require("@/assets/Carousal/pic4.webp"), alt: 'Wallpaper 4' },
          { src: require("@/assets/Carousal/pic5.jpg"), alt: 'Wallpaper 5' }
        ],
        currentIndex: 0,
        slideWidth: 0,
        intervalId: null,
        intervalDuration: 3000 ,
        foodItems: []
      };
    },
    mounted() {
      this.slideWidth = this.$refs.slider.offsetWidth;
      this.startSlideShow();
      this.fetchFoodItems();
    },
    beforeUnmount() {
      clearInterval(this.intervalId);
    },
    methods: {
      startSlideShow() {
        this.intervalId = setInterval(() => {
          this.currentIndex = (this.currentIndex + 1) % this.images.length;
        }, this.intervalDuration);
      },
      async fetchFoodItems() {
      try {
        const response = await axios.get('db1.json');
        this.foodItems = response.data.foodItems; 
      } catch (error) {
        console.error('Error fetching food items:', error);
      }
    }
  
    }
  };
  </script>
  
  <style scoped>
 
.wallpaper-carousel {
  max-width: 100%;
  overflow: hidden;
}

.slider {
  overflow: hidden;
}

.slides {
  display: flex;
  transition: transform 0.5s ease;
}

.slide {
  flex: 0 0 auto;
  width: 100%;
}


.carousel-image {
  width: 100%; 
  height: 400px; 
  object-fit: contain; 
  margin-top:10px;
}
.boxes-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  
}

.box {
    flex: 1; 
  padding: 20px; 
  background-color: #f0f0f0; 
  border: 1px solid #ccc; 
  border-radius: 5px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  margin: 10px; 
  text-align: center;
}

.box h2 {
  margin: 0;
  font-size: 25px;
  color:orchid;
}
.food-container{
    margin:10px 10px 40px 10px;
}
.food-grid {
    display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); 
  gap: 20px;
 
  
}


.food-item {
  background-color: #f9f9f9; 
  padding: 20px; 
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
}


.food-image {
  width: 100%; 
  height: 200px; 
  border-radius: 5px; 
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
  </style>
  