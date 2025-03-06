<template>
  <div id="app">
    <HeaderApp @day-changed="handleDayChange"
               @store-changed="handleStoreChange"
               @bank-changed="handleBankChange"
               @card-changed="handleCardChange"
               @new-discount="addDiscount"
    />
    <CardsContainer :selected-day="selectedDay"
                    :selected-store="selectedStore"
                    :selected-bank="selectedBank"
                    :selected-card="selectedCard"
                    :discounts="discounts"
                    @discount-deleted="removeDiscount"
                    @discount-updated="updateDiscount"

    >
      <DiscountCard/>
    </CardsContainer>
    <footer class="footer">
      <div class="footer-content">
        © 2025 AhorraYa. All rights reserved.
      </div>
    </footer>
  </div>
</template>
<script>
import {ref,watch, onMounted, computed } from "vue";
import HeaderApp from "@/components/HeaderApp.vue";
import CardsContainer from "@/components/CardsContainer.vue";
import DiscountCard from "@/components/DiscountCard.vue";
import api from "@/axios";
import { useAuthStore } from "@/store";


export default {
  name: "App",
  components: {
       HeaderApp: HeaderApp,
    CardsContainer: CardsContainer,
    DiscountCard: DiscountCard,
  },
  setup() {
    const discounts = ref([]);
    const selectedDay = ref("all");
    const selectedStore = ref("all");
    const selectedBank = ref("all");
    const selectedCard = ref("all");
    const authStore = useAuthStore();
    const userId = computed(() => authStore.userId);
    const addDiscount = (newDiscount) => {
      console.log('New discount received in App:', newDiscount);
      discounts.value.push(newDiscount);
      console.log('Updated discounts array:', discounts.value);
    };
    const getDiscountsByFilter = async () => {
      console.log("App - getDiscountsByFilter called");
      try {
        console.log("AuthStore userId:", authStore.userId);
        const params = { userId: userId.value };

        if (selectedDay.value && selectedDay.value !== "all") {
          params.dayOfWeek = selectedDay.value.toUpperCase();
          params.includeAllDays = true;
        }

        if (selectedStore.value && selectedStore.value !== "all") {
          params.shopId = selectedStore.value;
        }
        if (selectedBank.value && selectedBank.value !== "all") {
          params.bankId = selectedBank.value;
        }
        if (selectedCard.value && selectedCard.value !== "all") {
          params.cardType = selectedCard.value.toUpperCase();
        }

        const response = await api.get("/discounts/filter", { params });
        discounts.value = response.data;
      } catch (error) {
        console.error("Error fetching discounts", error);
      }
    };

    onMounted(() => {
      if (userId.value) {
        getDiscountsByFilter();
      }
    });

    watch(userId, (newUserId) => {
      console.log("User ID changed:", newUserId);
      if (newUserId) {
        setTimeout(() => {
          getDiscountsByFilter();
        }, 1500);
      } else {
        discounts.value = [];
      }
    });

    watch(
        [selectedDay, selectedStore, selectedBank, selectedCard],
        () => {
          getDiscountsByFilter();
        }
    );

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
    const handleCardChange = (newCard) => {
      console.log("App - handleCardChange called with:", newCard);
      selectedCard.value = newCard;
    };

    const removeDiscount = async (id) => {
      try {
        await api.delete(`/discounts/delete/${id}`);
        console.log("Discount deleted");
        discounts.value = discounts.value.filter(discount => discount.id !== id);
      } catch (error) {
        console.error("Error deleting discount", error);
      }
    };

    const updateDiscount = (updatedDiscount) => {
      const index = discounts.value.findIndex(discount => discount.id === updatedDiscount.id);
      if (index !== -1) {
        discounts.value[index] = updatedDiscount;
      }
    };

    return {
      discounts,
      selectedDay,
      selectedStore,
      selectedBank,
      selectedCard,
      handleDayChange,
      handleStoreChange,
      handleBankChange,
      handleCardChange,
      removeDiscount,
      updateDiscount,
      addDiscount,
    };
  }
};
</script>

<style>
footer {
  text-align: left;
  padding: 10px;
  background-color: #ffe602;
  color: #333;
  font-size: 14px;
  margin-top: auto;
  position: relative;
}
.footer-content {
  max-width: 1200px;
  margin: 0 auto; /* Центрирует содержимое футера */
  width: 100%;
}
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  height: 100%;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
}

body, html {
  font-family: 'Poppins', sans-serif;
  background-color: #eae8eb;
  height: 100%;
  margin: 0;
  padding: 0;
  color: #4e4e4e;
  width: 100%;
}

</style>
