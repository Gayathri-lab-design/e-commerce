<template>
  <v-app>
    <v-main>
      <!-- <HelloWorld/> -->
      <!-- <AddStudents /> -->
       <!-- <router-link :to="{ name: 'AddStudents' }">add</router-link>
       <router-link :to="{ name: 'HelloWorld' }">add</router-link> -->
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
// import AddStudents from './components/AddStudents.vue';
// import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',

  components: {
    // HelloWorld,
    // AddStudents
  },

  data: () => ({
    //
  }),
}
</script>
