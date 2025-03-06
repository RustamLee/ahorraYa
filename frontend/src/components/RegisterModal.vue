<script setup>
import {defineEmits, ref, toRaw} from 'vue';
import api from '@/axios';
import {useToast} from 'vue-toastification';
const emit = defineEmits(['close', 'register-success', 'login']);
const form = ref({
  email: '',
  password: ''
});

const errorMessage = ref('');
const successMessage = ref("");
const showLogin = ref(false);
const toast = useToast();

const closeModal = () => {
  successMessage.value = "";
  errorMessage.value = '';
  showLogin.value = false;
  emit('close');
};

const handleSubmit = async () => {
  successMessage.value = "";
  errorMessage.value = "";
  showLogin.value = false;

  try {
    const rawData = toRaw(form.value);  // Убираем прокси
    console.log("Sending data:", rawData);

    await api.post("/users/register", {
      email: rawData.email,
      password: rawData.password
    });

    successMessage.value = "Registration successful!";
    toast.success("Successful registration! Log in!");
    setTimeout(() => {
      closeModal();
      emit("register-success");
    }, 3000);

  } catch (error) {
    if (error.response?.status === 409) {
      console.error("Registration error:", error.response);
      errorMessage.value = "The user already exists. Do you want to log in?";
      showLogin.value = true;
    } else {
      errorMessage.value = `Error registration: ${error.response?.data?.message || 'Unknown error'}`;
    }
  }
};
</script>

<template>
  <div class="modal" @click.self="closeModal">
    <div class="modal-content">
      <h2>Sign Up</h2>
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
          <button v-if="!showLogin" type="submit">Save</button>
          <button v-else @click="$emit('login')">Login</button>
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

.button-container {
  display: flex;
  justify-content: center;
}

.error {
  color: #c53838;
  font-size: 14px;
  margin-top: 20px;
  margin-bottom: 0;
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
