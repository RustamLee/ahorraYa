<template>
  <header>
    <div class="header-content">
      <div class="logo-container">
        <h1 class="app-name">AhorraYa</h1>
        <p class="slogan">Save money with us</p>
      </div>

      <!-- filters -->
      <div class="filters-container">
        <label for="day-filter">Day
          <select id="day-filter" @change="handleDayChange">
            <option value="all">All</option>
            <option value="monday">Monday</option>
            <option value="tuesday">Tuesday</option>
            <option value="wednesday">Wednesday</option>
            <option value="thursday">Thursday</option>
            <option value="friday">Friday</option>
            <option value="saturday">Saturday</option>
            <option value="sunday">Sunday</option>
          </select>
        </label>
        <label>
          Store
          <select v-model="selectedStore"
                  @change="handleStoreChange"
                  id="store-filter">
            <option value="all">All</option>
            <option v-for="shop in shops" :key="shop.id" :value="shop.id">{{ shop.name }}</option>
          </select>
        </label>

        <label>
          Bank
          <select v-model="selectedBank"
                  @change="handleBankChange"
                  id="bank-filter">
            <option value="all">All</option>
            <option v-for="bank in banks" :key="bank.id" :value="bank.id">{{ bank.name }}</option>
          </select>
        </label>

        <label for="card-filter">
          Card
          <select id="card-filter" @change="handleCardChange"
          >
            <option value="all">All</option>
            <option value="debit">Debit</option>
            <option value="credit">Credit</option>
          </select>
        </label>
        <label v-if="userEmail">
          <button class="new-card">new card</button>
        </label>
      </div>

      <div class="right-section">
        <div v-if="userEmail" class="user-info">
          <span class="user-name">{{ userEmail }}</span>
          <button @click="authStore.logout()" class="register-btn">Log Out</button>
        </div>
        <div v-else class="nav">
          <button @click="showLoginModal = true" class="register-btn">Log In</button>
          <button @click="showRegisterModal = true" class="register-btn">Sign Up</button>
        </div>

      </div>
    </div>
    <RegisterModal
        v-if="showRegisterModal"
        @close="showRegisterModal = false"
        @login="showRegisterModal = false;
                showLoginModal = true"/>
    <LoginModal v-if="showLoginModal" @close="showLoginModal = false"/>
  </header>
</template>

<script setup>
import {defineEmits, onMounted, ref} from 'vue';
import {watchEffect} from 'vue';

import axios from "axios";
import RegisterModal from "@/components/RegisterModal.vue";
import LoginModal from "@/components/LoginModal.vue";
import {useAuthStore} from "@/store";

const authStore = useAuthStore();
const userEmail = ref(authStore.userEmail);

watchEffect(() => {
  userEmail.value = authStore.userEmail;
  console.log('Updated userEmail:', userEmail.value);
});
const emit = defineEmits(['day-changed', 'store-changed', 'bank-changed', 'card-changed']);
const shops = ref([]);
const banks = ref([]);
const selectedStore = ref('all');
const selectedBank = ref("all");
const showRegisterModal = ref(false);
const showLoginModal = ref(false);
const getShops = async () => {
  try {
    const response = await
        axios.get('http://localhost:8081/shops');
    shops.value = await response.data;
  } catch (error) {
    console.error('Error fetching shops', error);
  }
};

const getBanks = async () => {
  try {
    const response = await
        axios.get('http://localhost:8081/banks');
    banks.value = await response.data;
  } catch (error) {
    console.error('Error fetching banks', error);
  }
};

function handleDayChange(event) {
  const selectedDay = event.target.value === 'all' ? null : event.target.value; // Преобразование значения
  emit('day-changed', selectedDay); // Передача значения в родительский компонент
}

function handleCardChange(event) {
  const selectedCard = event.target.value === 'all' ? null : event.target.value; // Преобразование значения
  emit('card-changed', selectedCard); // Передача значения в родительский компонент
}

function handleStoreChange() {
  emit('store-changed', selectedStore.value);
}

onMounted(getShops);

function handleBankChange() {
  emit('bank-changed', selectedBank.value);
}

onMounted(getBanks);
</script>
<style>

.register-btn {
  color: #4e4e4e;
  text-decoration: none;
  font-weight: normal;
  cursor: pointer;
  transition: all 0.3s;
  font-size: 16px;
  font-family: 'Poppins', sans-serif;
  padding: 0;
  white-space: nowrap;
  align-items: center;
  background: none;
  justify-content: flex-start;
}

.new-card {
  font-size: 16px;
}

button {
  white-space: nowrap;
  display: flex;
  min-width: 60px;
  font-size: 24px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  background-color: #3483fa;
  color: white;
  align-items: center;
  justify-content: center;
  padding: 10px 10px;
  border-radius: 5px;
  height: 18px;
  text-align: center;

}

header {
  background-color: #ffe602;
  width: 100%;
  display: flex;
  justify-content: center;
}

.header-content {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  width: 100%;
  box-sizing: border-box;
  height: 100px;
  display: flex;
  max-width: 1200px;
}

.filters-container {
  display: flex;
  gap: 10px;
  justify-content: space-between;
  align-items: flex-end;
  height: 100%;
  font-size: 14px;
  font-weight: normal;
  color: #4e4e4e;
  position: relative;
}

.filters-container select {
  min-width: 60px;
  background: #eae8eb;
  font-size: 14px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: all 0.3s;
  color: #4e4e4e;
}

.filters-container select:hover {
  background: #dcdadb;
}

.filters-container select:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
  color: #4e4e4e;
}


.logo-container {
  text-align: left;
  justify-content: flex-start;
}

.app-name {
  font-size: 2rem;
  margin: 0;
}

.slogan {
  font-size: 14px;
  margin-top: 5px;
  font-weight: normal;
  align-self: flex-end;
}

.right-section {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.user-info {
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: space-between;
  height: 100%;
  padding: 5px;
}

.user-name {
  font-size: 1rem;
}

.nav {
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  padding: 5px;
}

</style>
