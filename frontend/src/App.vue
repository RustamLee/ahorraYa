<template>
  <div id="app">
    <HeaderApp />
    <CardsContainer>
      <DiscountCard/>
    </CardsContainer>
  </div>
</template>

<script>
import HeaderApp from "@/components/HeaderApp.vue";
import CardsContainer from "@/components/CardsContainer.vue";
import DiscountCard from "@/components/DiscountCard.vue";

export default {
  name: "App",
  components: {
    HeaderApp: HeaderApp,
    CardsContainer: CardsContainer,
    DiscountCard: DiscountCard,
  },
};
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  height: 100%;
}

body, html {
  font-family: 'Poppins', sans-serif;
  background-color: #eae8eb;
  height: 100%;
  margin: 0;
  padding: 0;
  color: #4e4e4e;
}
</style>
