<template>
  <div class="modal">
    <div class="modal-content">
      <i class="ri-close-line close" @click="closeModal"></i>
      <h2>Change Discount</h2>
      <div class="form-group">
        <label for="dayOfWeek">Day of week</label>
        <input type="text" id="dayOfWeek" v-model="editedDiscount.dayOfWeek" />
      </div>
      <div class="form-group">
        <label for="discount">Discount<span> (in %)</span></label>
        <input type="number" id="discount" v-model.number="editedDiscount.discount" />
      </div>
      <div class="form-group">
        <label for="discountLimit">Discount Limit <span> (in $)</span></label>
        <input type="number" id="discountLimit" v-model.number="editedDiscount.discountLimit" />
      </div>
      <div class="form-group">
        <label for="limitType">Limit Type <span> (DAILY, WEEKLY or MONTHLY)</span></label>
        <input type="text" id="limitType" v-model="editedDiscount.limitType" />
      </div>
      <div class="form-group">
        <label for="cardType">Card Type <span> (DEBIT or CREDIT)</span></label>
        <input type="text" id="cardType" v-model="editedDiscount.cardType" />
      </div>
      <div class="form-group">
        <label for="details">Details <span> (text)</span></label>
        <textarea id="details" v-model="editedDiscount.details"></textarea>
      </div>
      <button @click="saveChanges">Save</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref } from 'vue';
import axios from 'axios';

const props = defineProps({
  discount: Object
});

const emit = defineEmits(['close', 'discount-updated']);

const editedDiscount = ref({ ...props.discount });

const saveChanges = async () => {
  try {
    const response = await axios.put(`http://localhost:8081/discounts/${props.discount.id}`, editedDiscount.value);
    console.log("Discount updated");
    emit('discount-updated', response.data);
    closeModal();
  } catch (error) {
    console.error("Error updating discount", error);
  }
};

const closeModal = () => {
  emit('close');
};
</script>

<style scoped>
/* Стили для модального окна */

span{
  font-size: 12px;
}

h2{
  margin-top: 0;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  width-max: 500px;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}
.close:hover {
  color: #3483fa;
}

.form-group {
  margin-bottom: 10px;

}

label {
  display: block;
  margin-bottom: 5px;
  text-align: left;
  font-weight: bold;
}

input[type="text"],
input[type="number"],
textarea {
  width: 350px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color:#4e4e4e;
}

button {
  background-color: #3483fa;
  color: white;
  font-size: 14px;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
