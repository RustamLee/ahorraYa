<template>
  <div class="cards_container">
    <DiscountCard
        v-for="discount in discounts"
        :key="discount.id"
        :discount="discount"
        @discount-deleted="removeDiscountFromList"
    />
  </div>
</template>

<script setup>
import { defineProps, watch, ref, onMounted } from "vue";
import axios from "axios";
import DiscountCard from "@/components/DiscountCard.vue";

const props = defineProps({
  selectedDay: String,
  selectedStore: String,
  selectedBank: String,
  selectedCard: String,
});
console.log("CardsContainer - selectedStore prop:", props.selectedStore);

const discounts = ref([]);

// Метод для получения всех скидок
const getAllDiscounts = async () => {
  try {
    const response = await axios.get("http://localhost:8081/discounts");
    discounts.value = response.data; // Сохраняем все скидки
  } catch (error) {
    console.error("Error fetching all discounts", error);
  }
};

// Метод для получения скидок по фильтру
const getDiscountsByFilter = async () => {
  console.log("CardsContainer - getDiscountsByFilter called");
  try {
    const params ={};
    if(props.selectedDay && props.selectedDay !== "all") {
      params.dayOfWeek = props.selectedDay.toUpperCase();
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
    const response = await axios.get("http://localhost:8081/discounts/filter", {
      params});
    discounts.value = response.data; // Сохраняем отфильтрованные скидки
  } catch (error) {
    console.error("Error fetching discounts", error);
  }
};

// Слушаем изменения selectedDay и вызываем соответствующий метод
watch ([() => props.selectedDay, () => props.selectedStore, ()=>props.selectedBank, ()=>props.selectedCard], ([newDay, newStore,newBank,newCard]) => {
  if (
      (newDay === "all" || !newDay) &&
      (newStore === "all" || !newStore) &&
      (newBank === "all" || !newBank) &&
      (newCard === "all" || !newCard)
  ) {
    getAllDiscounts();
  } else {
    getDiscountsByFilter();
  }
}, { immediate: true });

onMounted(() => {
  if (
      (!props.selectedDay || props.selectedDay === "all") &&
      (!props.selectedStore || props.selectedStore === "all") &&
      (!props.selectedBank || props.selectedBank === "all") &&
      (!props.selectedCard || props.selectedCard === "all")
  ) {
    getAllDiscounts();
  } else {
    getDiscountsByFilter(); // Иначе применяем фильтры
  }
});

// Функция для удаления скидки из списка
const removeDiscountFromList = (id) => {
  discounts.value = discounts.value.filter(discount => discount.id !== id);
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
