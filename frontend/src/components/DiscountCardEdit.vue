<template>
  <div class="modal" @click.self="closeModal">
    <div class="modal-content">
      <i class="ri-close-line close" @click="closeModal"></i>
      <h2>Change Discount</h2>
      <div class="form-group">
        <label for="dayOfWeek">Day of week</label>
        <select id="dayOfWeek" v-model="editedDiscount.dayOfWeek">
          <option value="MONDAY">Monday</option>
          <option value="TUESDAY">Tuesday</option>
          <option value="WEDNESDAY">Wednesday</option>
          <option value="THURSDAY">Thursday</option>
          <option value="FRIDAY">Friday</option>
          <option value="SATURDAY">Saturday</option>
          <option value="SUNDAY">Sunday</option>
          <option value="ALL">All</option>
        </select>
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
        <label for="limitType">Limit Type <span></span></label>
        <select v-model="editedDiscount.limitType" id="limitType">
          <option value="DAILY">Daily</option>
          <option value="WEEKLY">Weekly</option>
          <option value="MONTHLY">Monthly</option>
        </select>
      </div>

      <div class="form-group">
        <label for="cardType">Card Type <span></span></label>
        <select v-model="editedDiscount.cardType" id="cardType">
          <option value="DEBIT">Debit</option>
          <option value="CREDIT">Credit</option>
        </select>

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
import api from "@/axios";

const props = defineProps({
  discount: Object
});

const emit = defineEmits(['close', 'discount-updated']);

const editedDiscount = ref({ ...props.discount });

const saveChanges = async () => {
  try {
    const response = await api.put(`/discounts/update/${props.discount.id}`, editedDiscount.value);
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

select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #4e4e4e;
  font-size: 14px;
  font-family: 'Poppins', sans-serif;
}

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
  position: relative;
  max-width: 400px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  text-align: left;
  width: 100%;
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
  width: 100%;
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
  margin: 20px auto 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
