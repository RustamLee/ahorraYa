<template>
  <div class="modal" @click.self="closeModal">
    <div class="modal-content">
      <i class="ri-close-line close" @click="closeModal"></i>
      <h2> Discount Card</h2>
      <form @submit.prevent="submitDiscount">
        <div class="form-group">
          <label for="bankName">Bank</label>
          <input required type="text" id="bankName" v-model="discountData.bankName"/>
        </div>
        <div class="form-group">
          <label for="shopName">Store</label>
          <input required type="text" id="shopName" v-model="discountData.shopName"/>
        </div>
        <div class="form-group">
          <label for="discount">Discount <span>(%)</span></label>
          <input required
                 type="number"
                 id="discount"
                 min="1"
                 max="100"
                 v-model="discountData.discount"/>
        </div>
        <div class="form-group">
          <label for="discountLimit">Discount Limit <span>(in $)</span></label>
          <input required type="number" id="discountLimit" inputmode="numeric" v-model="discountData.discountLimit"/>
        </div>
        <label for="dayOfWeek">Day of the Week</label>
        <select
            v-model="discountData.dayOfWeek"
            id="dayOfWeek"
            :class="{ 'error-border': !discountData.dayOfWeek && isSubmitted }"
        >
          <option value="" disabled>Select...</option>
          <option v-for="(day, key) in daysOfWeek" :key="key" :value="key">
            {{ day }}
          </option>
        </select>
        <label for="limitType">Limit Type<span>(Day, Week or Month)</span></label>
        <select
            v-model="discountData.limitType"
            id="limitType"
            :class="{ 'error-border': !discountData.limitType && isSubmitted }"
        >
          <option value="" disabled>Select...</option>
          <option v-for="(limit, key) in limitType" :key="key" :value="key">
            {{ limit }}
          </option>
        </select>

        <label for="cardType">Card Type<span>(Debit o Credit)</span></label>
        <select
            v-model="discountData.cardType"
            id="cardType"
            :class="{ 'error-border': !discountData.cardType && isSubmitted }"
        >
          <option value="" disabled>Select...</option>
          <option v-for="(card, key) in cardType" :key="key" :value="key">
            {{ card }}
          </option>
        </select>


        <div class="form-group">
          <label for="details">Details <span>(text)</span></label>
          <textarea class="area" id="details" v-model="discountData.details" placeholder="Optional..."></textarea>
        </div>
        <button type="submit">Save</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import api from "@/axios";
import { useToast } from 'vue-toastification';
import {defineProps, defineEmits, reactive, ref} from 'vue';
import {useAuthStore} from "@/store";

const toast = useToast();
const authStore = useAuthStore();
const props = defineProps({
  initialData: Object, // Если передается существующая скидка
});
const emit = defineEmits(['close', 'save']);
const daysOfWeek = {
  MONDAY: "Monday",
  TUESDAY: "Tuesday",
  WEDNESDAY: "Wednesday",
  THURSDAY: "Thursday",
  FRIDAY: "Friday",
  SATURDAY: "Saturday",
  SUNDAY: "Sunday",
  ALL: "All",
};
const isSubmitted = ref(false);
const limitType = {
  DAILY: "Daily",
  WEEKLY: "Weekly",
  MONTHLY: "Monthly",
};

const cardType = {
  DEBIT: "Debit",
  CREDIT: "Credit",
};


const discountData = reactive({
  bankName: props.initialData?.bankName || "",
  shopName: props.initialData?.shopName || "",
  dayOfWeek: props.initialData?.dayOfWeek || "",
  discount: props.initialData?.discount || '',
  discountLimit: props.initialData?.discountLimit || '',
  limitType: props.initialData?.limitType || '',
  cardType: props.initialData?.cardType || '',
  details: props.initialData?.details || '',
});

const closeModal = () => {
  emit('close');
};

const submitDiscount = async () => {
  try {
    isSubmitted.value = true;
    if (!discountData.dayOfWeek || !discountData.limitType || !discountData.cardType) {
      return;
    }
    const userEmail = authStore.userEmail;
    if (!userEmail) throw new Error("User not authenticated");
    const userId = authStore.userId;
    const bankResponse = await api.post("/banks/create", {name: discountData.bankName});
    const bankId = bankResponse.data.id;
    const shopResponse = await api.post("/shops/create", {name: discountData.shopName});
    const shopId = shopResponse.data.id;
    const discountResponse = await api.post("/discounts/create", {
      userId,
      bankId,
      shopId,
      ...discountData,
    });
    toast.success("Discount created successfully!");
    emit("save", discountResponse.data);
  } catch (error) {
    console.error("Error creating discount:", error);
    toast.error("Error creating discount!");

  }
  closeModal();
};

</script>

<style scoped>

.error-border {
  border: 1px solid red;
}

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

span {
  font-size: 12px;
}

textarea{
  font-size: 14px;
  font-family: 'Poppins', sans-serif;
  color: #4e4e4e;
}

h2 {
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
  border-radius: 8px;
  width: 400px;
  position: relative;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
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
  margin-top: 10px;
  font-weight: bold;
  text-align: left;
}

input[type="text"],
input[type="number"],
textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #4e4e4e;
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

button:hover {
  background-color: #2869d6;
}
</style>
