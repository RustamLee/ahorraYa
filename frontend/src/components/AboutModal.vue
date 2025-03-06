<template>
  <div v-if="isOpen" class="modal" @click.self="closeModal">
    <div class="modal-content">
      <i class="ri-close-line close" @click="closeModal"></i>
      <h2>About this App</h2>
      <p>
        <strong>AhorraYa</strong> is a web application designed to help users save money by
        providing up-to-date information on discounts and cashback offers available in various supermarkets.
      </p>
      <p>
        The idea for this application came from the need to keep track of current promotional offers
        in one place. I wanted to create something simple yet functional, making it easy to manage discounts efficiently.
      </p>
      <p>The platform allows users to add, view, edit, and delete promotions seamlessly. It follows a classic CRUD architecture and is built using the following technologies:</p>
      <ul>
        <li><strong>Backend:</strong> Spring Boot with Spring Security (JWT-based authentication).</li>
        <li><strong>Database:</strong> PostgreSQL.</li>
        <li><strong>Frontend:</strong> Vue.js.</li>
        <li><strong>Deployment:</strong> Hosted on Digital Ocean.</li>
      </ul>
      <p>If you have any questions, feel free to contact me:</p>
      <ul>
        <li><strong>LinkedIn:</strong> <a href="https://www.linkedin.com/in/rustam-lee" target="_blank">linkedin.com/in/rustam-lee</a></li>
        <li><strong>GitHub:</strong> <a href="https://github.com/RustamLee" target="_blank">github.com/RustamLee</a></li>
        <li><strong>Email:</strong> <a href="mailto:rustiksagad@gmail.com">rustiksagad@gmail.com</a></li>
      </ul>
      <p><em>*The name "AhorraYa" has Spanish roots and means "Save now"</em></p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  isOpen: Boolean
});

const emit = defineEmits(['close']);
const closeModal = () => {
  emit('close');
};
</script>

<style scoped>
h2 {
  margin-top: 0;
  text-align: center;
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
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  text-align: left;
  width: 100%;
}

.modal-content::-webkit-scrollbar {
  width: 6px;
}

.modal-content::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}

.modal-content::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0, 0, 0, 0.4);
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

p {
  margin: 10px 0;
}

ul {
  padding-left: 20px;
}

ul li {
  margin-bottom: 5px;
}

a {
  color: #3483fa;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
