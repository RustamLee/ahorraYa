<script setup>
import {defineEmits, ref} from 'vue';
import api from '@/axios';
import {useAuthStore} from "@/store";

const authStore = useAuthStore();
const emit = defineEmits(['close', 'login-success']);
const form = ref({
  email: '',
  password: ''
});

const errorMessage = ref('');
const successMessage = ref('');

const closeModal = () => {
  successMessage.value = '';
  errorMessage.value = '';
  emit('close');
};

console.log(authStore);
const handleSubmit = async () => {
  console.log('handleSubmit called');

  successMessage.value = '';
  errorMessage.value = '';

  try {
    const response = await api.post('/auth/login', {
      email: form.value.email,
      password: form.value.password
    });
    const token = response.data.token;
    const email = response.data.email;
    console.log('API response:', response.data);
    localStorage.setItem('AuthToken', token);
    authStore.setUserEmail(email);
    console.log('Saved email:', authStore.userEmail);
    successMessage.value = 'Login successful!';
    setTimeout(() => {
      closeModal();
      emit('login-success', token);
    }, 2000);
  } catch (error) {
    if (error.response?.status === 401) {
      errorMessage.value = 'Invalid email or password.';
    } else {
      errorMessage.value = 'Error logging in. Please try again.';
    }
  }
};


</script>

<template>
  <div class="modal" @click.self="closeModal">
    <div class="modal-content">
      <h2>Log In</h2>
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="form.email" required/>
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="form.password" required/>
        </div>
        <p v-if="successMessage" class="success">{{ successMessage }}</p>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
        <div class="button-container">
          <button @click="handleSubmit" type="submit">Login</button>
        </div>
      </form>
      <i class="ri-close-line close" @click="closeModal"></i>
    </div>
  </div>
</template>

<style scoped>
.success {
  color: green;
  font-size: 14px;
  margin-top: 10px;
}

.error {
  color: #c53838;
  font-size: 14px;
  margin-top: 10px;
}

.button-container {
  display: flex;
  justify-content: center;
}

input {
  margin-left: 10px;
  width: 350px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #4e4e4e;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  position: relative;
}

form div {
  margin-bottom: 10px;
  display: flex;
}

button {
  background-color: #3483fa;
  color: white;
  font-size: 14px;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
}
</style>
