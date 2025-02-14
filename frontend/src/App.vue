<template>
  <div id="app">
    <HeaderApp @day-changed="handleDayChange"
               @store-changed="handleStoreChange"
                @bank-changed="handleBankChange"
    />
    <CardsContainer :selected-day="selectedDay"
                    :selected-store="selectedStore"
                    :selected-bank="selectedBank"
    >
      <DiscountCard/>
    </CardsContainer>
  </div>
</template>

<script>
import {ref} from "vue";
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
  setup() {
    const selectedDay = ref("all");
    const selectedStore = ref("all");
    const selectedBank = ref("all");
    const handleDayChange = (newDay) => {
      selectedDay.value = newDay;
    };
    const handleStoreChange = (newStore) => {
      console.log("App - handleStoreChange called with:", newStore);
      selectedStore.value = newStore;
    };
    const handleBankChange = (newBank) => {
      console.log("App - handleBankChange called with:", newBank);
      selectedBank.value = newBank;
    };


    return {
      selectedDay,
      selectedStore,
      selectedBank,
      handleDayChange,
      handleStoreChange,
      handleBankChange,
    };
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
