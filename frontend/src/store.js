import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        userEmail: localStorage.getItem('userEmail') || null,  // Инициализация значения email из localStorage
    }),
    actions: {
        setUserEmail(email) {
            console.log('setUserEmail called with:', email);
            this.userEmail = email;
            localStorage.setItem('userEmail', email);  // Сохраняем email в localStorage
        },
        logout() {
            this.userEmail = null;
            localStorage.removeItem('userEmail');
            localStorage.removeItem('AuthToken');
        }
    }
});
