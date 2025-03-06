<template>
  <header>
    <div class="header-content">
      <div class="logo-container">
        <h1 class="app-name">AhorraYa</h1>
        <p class="slogan" @click="isAboutModalOpen = true">About project <i class="ri-information-line"></i></p>
      </div>

      <!-- filters -->
      <div class="burger-menu" @click="isFiltersOpen = !isFiltersOpen">
        <i class="ri-menu-line"></i>
      </div>
      <div class="filters-container" :class="{ 'open': isFiltersOpen }">
        <label for="day-filter">Day&nbsp;
          <select
              id="day-filter"
              @change="handleDayChange"
              :disabled="!userEmail">
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
          <select
              v-model="selectedStore"
              @change="handleStoreChange"
              id="store-filter"
              :disabled="!userEmail">
            <option value="all">All</option>
            <option v-for="shop in shops" :key="shop.id" :value="shop.id">{{ shop.name }}</option>
          </select>
        </label>

        <label>
          Bank
          <select v-model="selectedBank"
                  @change="handleBankChange"
                  id="bank-filter"
                  :disabled="!userEmail">
            <option value="all">All</option>
            <option v-for="bank in banks" :key="bank.id" :value="bank.id">{{ bank.name }}</option>
          </select>
        </label>

        <label for="card-filter">
          Card
          <select
              id="card-filter"
              @change="handleCardChange"
              :disabled="!userEmail">
            <option value="all">All</option>
            <option value="debit">Debit</option>
            <option value="credit">Credit</option>
          </select>
        </label>
      </div>
      <label v-if="userEmail">
        <button class="new-card" @click="openModal">+ card</button>
      </label>
      <div class="right-section">
        <div v-if="userEmail" class="user-info">
          <span class="user-name">{{ userEmail }}</span>
          <button @click="authStore.logout()" class="register-btn">Log Out<i class="ri-logout-circle-r-line"></i></button>
        </div>
        <div v-else class="nav">
          <button @click="showLoginModal = true" class="register-btn">Log In<i class="ri-login-circle-line"></i></button>
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
    <DiscountCardNew v-if="isModalOpen"
        @close="isModalOpen = false"
        @new-discount="$emit('new-discount', $event)"
    />
    <AboutModal :isOpen="isAboutModalOpen" @close="isAboutModalOpen = false" />
  </header>
</template>

<script setup>
import {defineEmits, onMounted, ref} from 'vue';
import {watchEffect} from 'vue';
import RegisterModal from "@/components/RegisterModal.vue";
import LoginModal from "@/components/LoginModal.vue";
import {useAuthStore} from "@/store";
import DiscountCardNew from "@/components/DiscountCardNew.vue";
import api from "@/axios";
import AboutModal from "@/components/AboutModal.vue";
const isAboutModalOpen = ref(false);
const isModalOpen = ref(false);
const isFiltersOpen = ref(false);
const authStore = useAuthStore();
const userEmail = ref(authStore.userEmail);
const openModal = () => {
  isModalOpen.value = true;
};

watchEffect(() => {
  userEmail.value = authStore.userEmail;
  console.log('Updated userEmail:', userEmail.value);
});
const emit = defineEmits(['day-changed', 'store-changed', 'bank-changed', 'card-changed', 'new-discount']);
const shops = ref([]);
const banks = ref([]);
const selectedStore = ref('all');
const selectedBank = ref("all");
const showRegisterModal = ref(false);
const showLoginModal = ref(false);
const getShops = async () => {
  try {
    const response = await
        api.get('/shops');
    shops.value = await response.data;
  } catch (error) {
    console.error('Error fetching shops', error);
  }
};

const getBanks = async () => {
  try {
    const response = await
        api.get('/banks');
    banks.value = await response.data;
  } catch (error) {
    console.error('Error fetching banks', error);
  }
};

function handleDayChange(event) {
  const selectedDay = event.target.value === 'all' ? 'all' : event.target.value;
  emit('day-changed', selectedDay); // Передаем выбранный день в родительский компонент
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

<style scoped>

header {
  background-color: #ffe602;
  width: 100%;
  display: flex;
  justify-content: center;
  position: relative;
}

.header-content {
  max-width: 1200px;
  width: 100%;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  box-sizing: border-box;
}

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
  margin-right: 10px;
  margin-left: 10px;
  display: flex;
  margin-top: 40px;
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
  gap: 7px;
  transition: all 0.3s;
}

button i {
  font-size: 16px;
  line-height: 1;
}

.logo-container {
  text-align: left;
  margin-left: 0;
  margin-top: -10px;
}

.slogan {
  font-size: 16px;
  font-weight: normal;
  align-self: flex-end;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  gap: 7px;
}

.slogan:hover {
  color: #2869d6;
}

button:hover {
  color: #2869d6;
}

.slogan i {
  font-size: 1.1em;
  line-height: 1;
}

.app-name, .slogan {
  margin: 0;
}

.filters-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  gap: 10px;
  font-size: 14px;
  font-weight: normal;
  color: #4e4e4e;
  position: relative;
  height: 100%;
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

.app-name {
  font-size: 2rem;
  margin: 0;
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
  padding-top: 5px;
}

.user-name {
  font-size: 1rem;
  cursor: none;
  transition: all 0.3s;
}

.nav {
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  padding: 5px;
}

select:disabled {
  opacity: 0.5;
  cursor: default;
}

.burger-menu {
  padding: 10px;
  display: none;
  font-size: 30px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .filters-container {
    display: none;
    position: absolute;
    top: 60px;
    left: 0;
    width: 70%;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 10px;
    flex-direction: column;
    gap: 5px;
    z-index: 10;
    background: #fff;
  }

  .filters-container.open {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    width: 100%;
    height: fit-content;
  }

  .burger-menu {
    display: block;
  }

  .filters-container label {
    display: block;
    margin: 0;
    padding: 0;
  }

  .filters-container select {
    width: 50vw;
    box-sizing: border-box;
    margin: 0;
    padding: 8px;
  }

  .new-card {
    font-size: 16px;
    display: flex;
    width: 68px;
    height: 40px;
    margin-top: 10px;
  }

}



@media (max-width: 480px) {

  .slogan {
    font-size: 14px;
    gap: 5px;
    display: inline;
  }

  .new-card {
    font-size: 16px;
    display: flex;
    width: 40px;
    height: 30px;
    margin-top: 14px;
  }

  .app-name{
    font-size: 1.5rem;
    margin-top: 10px;
  }

}

@media (max-width: 415px) {

  .new-card {
    font-size: 14px;
    display: flex;
    width: 20px;
    height: 20px;
    margin-top: 14px;
  }

  .app-name{
    font-size: 1rem;
    margin-top: 20px;
  }

  .user-name{
    display: none;
  }

  .register-btn{
    margin-top: 10px;
    font-size: 14px;
  }

  .slogan i {
    display: none;
  }

}

</style>
