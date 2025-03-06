<template>
  <div class="cards_container">
    <DiscountCard
        v-for="discount in props.discounts"
        :key="discount.id"
        :discount="discount"
        @discount-deleted="removeDiscountFromList"
        @discount-updated="handleDiscountUpdated"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import DiscountCard from "@/components/DiscountCard.vue";
const emit = defineEmits(['discount-deleted', 'discount-updated']);
const props = defineProps({
  selectedDay: String,
  selectedStore: String,
  selectedBank: String,
  selectedCard: String,
  discounts:Array
});

console.log("CardsContainer - selectedStore prop:", props.selectedStore);
const removeDiscountFromList = (id) => {
  emit('discount-deleted', id);
};

const handleDiscountUpdated = (updatedDiscount) => {
  emit('discount-updated', updatedDiscount);
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

@media (max-width: 400px) {
  .cards_container {
    padding: 1px;
  }
}
</style>
