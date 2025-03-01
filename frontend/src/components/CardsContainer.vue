<template>
  <div class="cards_container">
    <DiscountCard
        v-for="discount in discounts"
        :key="discount.id"
        :discount="discount"
        @discount-deleted="removeDiscountFromList"
        @discount-updated="handleDiscountUpdated"
    />
  </div>
</template>

<script setup>
import { defineProps, ref, watch, onMounted, computed } from "vue";
import api from '@/axios';
import DiscountCard from "@/components/DiscountCard.vue";
import { useAuthStore } from "@/store";


const authStore = useAuthStore();
const userId = computed(() => authStore.userId);
const props = defineProps({
  selectedDay: String,
  selectedStore: String,
  selectedBank: String,
  selectedCard: String,
});
console.log("CardsContainer - selectedStore prop:", props.selectedStore);

const discounts = ref([]);

const getDiscountsByFilter = async () => {
  console.log("CardsContainer - getDiscountsByFilter called");
  try {
    console.log("AuthStore userId:", authStore.userId);
    const params = {userId: userId.value};

    if(props.selectedDay && props.selectedDay !== "all") {
      params.dayOfWeek = props.selectedDay.toUpperCase();
      params.includeAllDays = true;
    }

    if(props.selectedStore && props.selectedStore !== "all") {
      params.shopId = props.selectedStore;
    }
    if(props.selectedBank && props.selectedBank !== "all") {
      params.bankId = props.selectedBank;
    }
    if(props.selectedCard && props.selectedCard !== "all") {
      params.cardType = props.selectedCard.toUpperCase();
    }
    const response = await api.get("/discounts/filter",
        {params});
    discounts.value = response.data;
  } catch (error) {
    console.error("Error fetching discounts", error);
  }
};

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
watch ([() => props.selectedDay, () => props.selectedStore, ()=>props.selectedBank, ()=>props.selectedCard], ([newDay, newStore, newBank, newCard]) => {
  if (
      (newDay === "all" || !newDay) &&
      (newStore === "all" || !newStore) &&
      (newBank === "all" || !newBank) &&
      (newCard === "all" || !newCard)
  ) {
    getDiscountsByFilter();
  } else {
    getDiscountsByFilter();
  }
}, { immediate: true });

onMounted(() => {
  if (userId.value) {
    getDiscountsByFilter();
  }
});
const removeDiscountFromList = (id) => {
  discounts.value = discounts.value.filter(discount => discount.id !== id);
};

const handleDiscountUpdated = (updatedDiscount) => {
  const index = discounts.value.findIndex(discount => discount.id === updatedDiscount.id);
  discounts.value[index] = updatedDiscount;
};

</script>


<style scoped>
.cards_container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  padding: 20px;
  gap: 16px;
}
</style>
