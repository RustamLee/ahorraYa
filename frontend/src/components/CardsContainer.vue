<template>
  <div class="cards_container">
    <DiscountCard v-for="discount in discounts"
                  :key="discount.id"
                  :discount="discount"
                  @discount-deleted="removeDiscountFromList"
    />
  </div>
</template>

<script setup>
import DiscountCard from "@/components/DiscountCard.vue";
import {ref, onMounted} from "vue";
import axios from "axios";

const discounts = ref([]);
const getDiscounts = async () => {
  try {
    const response = await axios.get("http://localhost:8081/discounts");
    discounts.value = response.data;
  } catch (error) {
    console.error("Error fetching discounts", error);
  }
};

const removeDiscountFromList = (id) => {
  discounts.value = discounts.value.filter(discount => discount.id !== id);
};
onMounted(getDiscounts);

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
