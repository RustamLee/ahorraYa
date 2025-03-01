<script setup>
import {defineProps, defineEmits, ref} from 'vue';
import axios from "axios";

import DiscountCardModal from "@/components/DiscountCardModal.vue";
const showModal = ref(false);

const props = defineProps({
  discount: Object
});
const emit = defineEmits(['discount-deleted','discount-updated']);
const deleteDiscount = async () => {
  try {
    await axios.delete(`http://localhost:8081/discounts/${props.discount.id}`);
    console.log("Discount deleted");
    emit('discount-deleted', props.discount.id);
  } catch (error) {
    console.error("Error deleting discount", error);
  }
};
const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const handleDiscountUpdated = (updatedDiscount) => {
  emit('discount-updated', updatedDiscount);
};
</script>

<template>
  <div v-if="discount" class="discount-card">
    <div class="header-row">
      <div><i class="ri-store-2-line"></i>STORE</div>
      <div><i class="ri-bank-line"></i>BANK</div>
      <div><i class="ri-bank-card-line"></i>CARD</div>
      <div><i class="ri-percent-line"></i>DISCOUNT</div>
      <div><i class="ri-money-dollar-box-line"></i>LIMIT</div>
      <div><i class="ri-time-line"></i>PERIOD</div>
        <div class="edit-icon"  @click="openModal"><i class="ri-edit-line"></i></div>
    </div>

    <div class="data-row">
      <div>{{discount.shop.name}}</div>
      <div>{{ discount.bank.name }}</div>
      <div>{{ discount.cardType }}</div>
      <div>{{ discount.discount }}%</div>
      <div>{{ discount.discountLimit }}$</div>
      <div>{{discount.limitType}}</div>
      <div class="edit-icon" @click="deleteDiscount"><i class="ri-delete-bin-line"></i></div>
    </div>

    <div class="days-row">
      <div
          v-for="day in ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY']"
          :key="day"
          :class="{ active: discount && (discount.dayOfWeek === 'ALL' || discount.dayOfWeek === day) }"
      >
        {{ day.charAt(0) + day.slice(1).toLowerCase() }}
      </div>
    </div>

    <div class="details-row">
      <div><i class="ri-information-line"></i>DETAILS:</div>
      <div class="details-text">{{ discount.details }}</div>
    </div>

    <DiscountCardModal
        v-show="showModal"
        :discount="discount"
        @close="closeModal"
        @discount-updated="handleDiscountUpdated"
    />
  </div>
</template>

<style scoped>

i{
  padding: 2px;
  color: #3483fa;
}

.discount-card {
  display: grid;
  grid-template-columns: repeat(6, 1fr) auto;
  grid-template-rows: auto auto auto auto;
  gap: 10px;
  padding: 16px;
  background-color: #ffffff;
  border-radius: 8px;
  max-width: 1200px;
  height: fit-content;
  font-family: Arial, sans-serif;
}

.header-row {
  display: contents;
  font-weight: bold;
}

.header-row > div {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  font-weight: bold;
}

.data-row {
  display: contents;
}

.days-row {
  display: contents;
  padding-left: 10px;
}

.details-row {
  grid-column: span 7;
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 10px;
  align-items: center;
  font-weight: bold;
padding-left: 10px;
}

.details-row > div {
  display: flex;
  align-items: center;

}

.edit-icon {
  grid-column: 7;
  justify-self: center;
  cursor: pointer;
  color: #007BFF;
  font-size: 20px;
}
.header-row-edit{
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.days-row > div {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  border-radius: 8px;
  padding: 8px;
  border: 2px solid #f2f2f2;
  color: #3483fa;

}

.days-row > .active {
  background-color: #3483fa;
  color: white;
  font-weight: bold;
  border: none;
}

.details-text {
  padding: 8px;
  border: 2px solid #f2f2f2;
  border-radius: 8px;
  font-weight: lighter;
}
</style>
