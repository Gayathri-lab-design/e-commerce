<template>
  <v-btn @click="addItem">
ass
    </v-btn>
  <div>
    <Bar
      id="it-chart"
      :options="chartOptions"
      :data="chrtData"
    />
    
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import ChartDataLabels from 'chartjs-plugin-datalabels'
import axios from 'axios';
import data from '../assets/data.json';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ChartDataLabels)

export default {
  name: 'BarChart',
  components: { Bar },
  data() {
    return {
      url : data.baseUrl,
      chartData : data.chartData,
      chrtData: {
        labels: [],
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
  mounted(){
    this.getChartValues();
  },
  methods:{
    async getChartValues(){
      const res = await axios.get(this.url + "/v1/chartValues");
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
    },
    async fetchItems() {
      const response = await axios.get('http://localhost:3000/api/items');
      this.items = response.data.items;
    },
    async addItem() {
      // if (!this.newItem) return;
      alert("jg");
      await axios.post('http://localhost:3000/api/items', { name: "Krishna" });
      // this.newItem = '';
      // this.fetchItems(); // Refresh the list
    },
  }
}
</script>

<style scoped>
#it-chart {
  height: 400px; /* Adjust height as needed */
  width: 600px;  /* Adjust width as needed */
}
</style>
