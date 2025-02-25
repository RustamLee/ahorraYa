<template>
  <header>
    <div class="header-content">
      <div class="logo-container">
        <h1 class="app-name">AhorraYa</h1>
        <p class="slogan">Save money with us</p>
      </div>

      <!-- Фильтры -->
      <div class="filters-container">
        <label for="day-filter">Day:</label>
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
        <label>
          Store:
          <select v-model="selectedStore"
                  @change="handleStoreChange"
                  id="store-filter">
            <option value="all">All</option>
            <option v-for="shop in shops" :key="shop.id" :value="shop.id">{{ shop.name }}</option>
          </select>
        </label>

        <label>
          Bank:
          <select v-model="selectedBank"
                  @change="handleBankChange"
                  id="bank-filter">
            <option value="all">All</option>
            <option v-for="bank in banks" :key="bank.id" :value="bank.id">{{ bank.name }}</option>
          </select>
        </label>

        <label for="card-filter">
          Card:
          <select id="card-filter" @change="handleCardChange"
          >
            <option value="all">All</option>
            <option value="debit">Debit</option>
            <option value="credit">Credit</option>
          </select>
        </label> New card:
        <label>
        <button>+</button>
        </label>
      </div>

      <div class="right-section">
        <div class="user-info">
          <i class="ri-user-line user-icon"></i>
          <span class="user-name">Login</span>
        </div>
        <!-- Меню -->
        <nav class="nav">
          <ul>
            <li><button @click="showRegisterModal = true" class="register-btn">Sign Up</button></li>
          </ul>
        </nav>
      </div>
    </div>
    <RegisterModal v-if="showRegisterModal" @close="showRegisterModal = false" />
  </header></template>

<script setup>
import {defineEmits, onMounted, ref} from 'vue';
import axios from "axios";
import RegisterModal from "@/components/RegisterModal.vue";

const emit = defineEmits(['day-changed', 'store-changed', 'bank-changed', 'card-changed']);
const shops = ref([]);
const banks = ref([]);
const selectedStore = ref('all');
const selectedBank = ref("all");
const showRegisterModal = ref(false);
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

.register-btn{
  padding: 0;
  color: #4e4e4e;
  text-decoration: none;
  font-size: 16px;
  font-weight: normal;
  cursor: pointer;
  transition: all 0.3s;
  background: none;
  font-family: 'Poppins', sans-serif;
}

button {
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 60px;
  height: 38px;
  font-size: 24px;
  font-weight: bold;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  background-color: #3483fa;
  color: white;
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

button {
  background-color: #3483fa;
  font-weight: bold;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  min-width: 60px;
  height: 18px;
  display: flex;
  text-align: center;
  align-content: center;
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
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  height: 100%;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s;
}

.user-icon {
  border-radius: 50%;
  margin-right: 10px;
}

.user-name {
  font-size: 1rem;
}

/* Меню */
.nav ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  height: 100%;
  color: #4e4e4e;

}

.nav li {
  margin-left: 20px;
  color: #4e4e4e;
}


.nav a {
  text-decoration: none;
  font-size: 14px;
  font-weight: normal;
  color: #4e4e4e;
  cursor: pointer;
  transition: all 0.3s;
}
</style>
