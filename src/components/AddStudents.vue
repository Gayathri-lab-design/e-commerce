<template>
  <v-app fluid class="app-container" v-if="show">
    <!-- Overlay and Loader Content -->
    <v-overlay 
      contained
      v-model="overlay"
      class="align-center justify-center"
    >
      <v-progress-circular
        v-if="loading"
        indeterminate
        :size="50"
        :width="8"
        color="#02c4ff"
        class="loader"
      ></v-progress-circular>
    </v-overlay>

    <!-- Snackbar -->
    <v-snackbar
      :timeout="2000"
      v-model="snack"
      :color="Color"
      class="snackbar"
      variant="outlined"
      contained
      location="top right"
    >
      <h4 style="color: white;font-family: 'Courier New', Courier, monospace;">
        <v-icon>{{ iconName }}</v-icon>&nbsp; {{ snackmsg }}
      </h4>
    </v-snackbar>

    <v-container>
      <h2 class="text-center"><v-icon left>mdi-laptop</v-icon>&nbsp; INFORMATION TECHNOLOGY</h2>
      <br>
      <v-form @submit.prevent="validateAndSubmit">
        <v-select
          v-model="section"
          name="section"
          :items="sections"
          label="Select a Section"
          required
          outlined
          class="mb-3"
        />
        <v-text-field
          v-model="boys"
          name="boys"
          label="Count of Boys"
          type="number"
          :max="100"
          :min="0"
          required
          outlined
          class="mb-3"
          :class="{'error-field': boysError}"
          @input="validateBoys"
        />
        <v-text-field
          v-model="girls"
          name="girls"
          label="Count of Girls"
          type="number"
          :max="100"
          :min="0"
          required
          outlined
          class="mb-3"
          :class="{'error-field': girlsError}"
          @input="validateGirls"
        />
        <v-btn type="submit" color="success" :disabled="!isFormValid"><v-icon left>mdi-check</v-icon>&nbsp; Submit</v-btn>
        <v-btn color="warning" @click="navigateTo"><v-icon left>mdi-chart-bar</v-icon>&nbsp; View Graph</v-btn>
      </v-form>
    </v-container>
  </v-app>

<!-- Bar chart-->
<v-app fluid v-if="!show">
<div style="padding-left: 4%; margin-top: 3%;">
  <v-btn color="warning" variant="outlined" @click="showChart"><v-icon left>mdi-arrow-left</v-icon>Back</v-btn>
</div>
<div>
    <Bar
      id="it-chart"
      :options="chartOptions"
      :data="chrtData"
    />
  </div>
</v-app>

</template>

<script>
import axios from 'axios';
import data from '../assets/data.json';

// Bar
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import ChartDataLabels from 'chartjs-plugin-datalabels'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ChartDataLabels)

export default {
  name: 'DataInput',
  components:{Bar},
  data() {
    return {
      url : data.baseUrl,
      show :true,
      boysError: false,
      girlsError: false,
      overlay: false,
      loading: false,
      snackmsg: "",
      iconName: "",
      snack: false,
      Color: '',
      section: '',
      department: 'Information Technology',
      boys: 0, // Initialize as null to check for empty
      girls: 0, // Initialize as null to check for empty
      sections: ['SECTION-A', 'SECTION-B', 'SECTION-C'],

      // Bar Data's
      chartData : data.chartData,
      chrtData: {
        labels: ['SECTION-A', 'SECTION-B', 'SECTION-C'],
        datasets: [
          {
            label: 'Boys',
            data: [12, 21, 32],
            backgroundColor: 'blue'
          },
          {
            label: 'Girls',
            data: [1, 32, 43],
            backgroundColor: 'pink'
          }
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: 'Information Technology'
          },
          datalabels: {
            anchor: 'end',
            align: 'end',
            formatter: (value, context) => {
              const total = context.chart.data.datasets.reduce((acc, dataset) => acc + dataset.data[context.dataIndex], 0);
              const percentage = ((value / total) * 100).toFixed(1); // Calculate percentage
              return `${percentage}%`; // Return percentage string
            },
            color: 'black' // Color of the text
          }
        },
        scales: {
          x: {
            stacked: false
          },
          y: {
            title: {
              display: true,
              text: 'Count',
              font: {
                size: 14
              }
            },
            max: 100 // Adjust based on your data
          }
        }
      }
    }
  },
  computed: {
    isFormValid() {
      return (
        this.section &&
        this.boys !== null && this.boys !== '' && this.boys >= 0 && this.boys <= 100 &&
        this.girls !== null && this.girls !== '' && this.girls >= 0 && this.girls <= 100 &&
        !this.boysError && !this.girlsError
      );
    }
  },
  // mounted(){
  //   this.getChartValues();
  // },
  methods: {
    showChart(){
      this.show = !this.show
    },
    validateBoys() {
      this.boysError = this.boys === null || this.boys === '' || this.boys < 0 || this.boys > 100;
      if (this.boys < 0) this.boys = 0;
      else if (this.boys > 100) this.boys = 100;
    },
    validateGirls() {
      this.girlsError = this.girls === null || this.girls === '' || this.girls < 0 || this.girls > 100;
      if (this.girls < 0) this.girls = 0;
      else if (this.girls > 100) this.girls = 100;
    },
    validateAndSubmit() {
      this.validateBoys();
      this.validateGirls();
      
      if (this.isFormValid) {
        this.submitData({ section: this.section, boys: this.boys, girls: this.girls });
      }
    },
    submitData(submitEvent) {
      console.log(submitEvent);
      this.snackAlert("Details Added .", data.snack.successIcon, data.snack.successColor);
      if(this.section == "SECTION-A"){
        this.chrtData.datasets[0].data[0] += Number(this.boys);
        this.chrtData.datasets[1].data[0] += Number(this.girls);
      }
      else if(this.section == "SECTION-B"){
        this.chrtData.datasets[0].data[1] = Number(this.boys);
        this.chrtData.datasets[1].data[1] = Number(this.girls);
      }
      else if(this.section == "SECTION-C"){
        this.chrtData.datasets[0].data[2] = Number(this.boys);
        this.chrtData.datasets[1].data[2] = Number(this.girls);
      }
    },
      // const requestBody = {
      //   department: this.department,
      //   section: this.section,
      //   boys: this.boys,
      //   girls: this.girls,
      // };
      // const response = await axios.post(this.url + "/v1/addStudDetails", requestBody);
      // console.log(response.status)
      //   if(response.status === 200){
      //       if(response.data !== "" || response.data !== null){
      //         this.snackAlert(response.data, data.snack.successIcon, data.snack.successColor);
      //       }
      //       // this.resetForm();
      //   }
      //   else if(response.status !== 200){
      //     if(response.data !== "" || response.data !== null){
      //       this.snackAlert(response.data, data.snack.failureIcon, data.snack.failureColor);
      //     }
      //     else{
      //       this.snackAlert("UNEXPEXTED ERROR !");
      //     }
      //     // this.resetForm();
      //   }
    
    snackAlert(msg, icon, color){
        setTimeout(() => {
          this.loading = true;
          this.overlay = true;
          setTimeout(() => {
            this.overlay = false;
            this.loading = false; 
            this.snack = true;
            this.snackmsg = msg;
            this.Color = color;
            this.iconName = icon;
          }, 1200);
        });
        this.resetForm();
      },
    resetForm() {
      this.section = '';
      this.boys = null; // Reset to null to allow empty checking
      this.girls = null; // Reset to null to allow empty checking
    },
    navigateTo() {
      console.log("called..");
      console.log(this.$router)
      this.$router.push('/chart');
    },

    // BAR
    async getChartValues(){
      const res = await axios.get("https://dataware-1.onrender.com"+ "/v1/chartValues");
      this.assignChart(res);
      console.log(this.chrtData)
    },
    assignChart(res){
      for(let i=0; i< res.data.labels.length ;i++){
        this.chrtData.labels[i]=res.data.labels[i];
      }
      for(let i=0; i< res.data.boys.length ;i++){
        this.chrtData.datasets[0].data[i]=res.data.boys[i];
      }
      for(let i=0; i< res.data.girls.length ;i++){
        this.chrtData.datasets[1].data[i]=res.data.girls[i];
      }
    }
  }
}
</script>

<style scoped>

#it-chart {
  height: 400px; /* Adjust height as needed */
  width: 600px;  /* Adjust width as needed */
}

.app-container {
  height: 100%;
  background-image: url('../assets/class_room.jpg');
  background-position: center;
  background-size: cover;
  opacity: 0.8;
}
.v-container {
  max-width: 500px;
  margin: auto; /* Center the container */
  padding: 20px;
  border-radius: 8px;
  background-color: #ffffff; /* White background for the form */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2); /* Slightly darker shadow */
  background: linear-gradient(135deg, #ffffff, #e0f7fa);
}
.v-container:hover {
  transform: translateY(-5px); /* Lift effect on hover */
}
h2 {
  color: #333;
  font-family: 'Courier New', Courier, monospace;
  font-stretch: ultra-expanded;
}
.v-icon {
  font-size: 20px;
}
.v-btn {
  width: 210px;
  margin-left: 12px;
}
.error-message {
  color: red;
  margin-top: 2px;
  font-size: 10px;
}
.error-field {
  border: 1px solid red; /* Change border color for error */
}
</style>
