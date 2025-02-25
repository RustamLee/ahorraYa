<script setup>
import {defineEmits, ref} from 'vue';

const emit = defineEmits(['close', 'register-success']);
const form = ref({
  username: '',
  email: '',
  password: ''
});

const closeModal = () => {
  emit('close');
};

const handleSubmit = () => {
  console.log('Form Submitted:', form.value);
  closeModal();
};
</script>

<template>
  <div class="modal" @click.self="closeModal">
    <div class="modal-content">
      <h2>Sign Up</h2>
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="form.email" required />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="form.password" required />
        </div>
        <button type="submit">Save</button>
      </form>
      <i class="ri-close-line close" @click="closeModal"></i>
    </div>
  </div>
</template>

<style scoped>

input{
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
  background-color: rgba(0, 0, 0, 0.5);
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