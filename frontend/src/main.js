import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import Toast, { POSITION } from 'vue-toastification';
import 'vue-toastification/dist/index.css';

const app = createApp(App);

app.use(createPinia());

app.use(Toast, {
    position: POSITION.TOP_RIGHT,
    timeout: 3000,
});

app.mount('#app');
