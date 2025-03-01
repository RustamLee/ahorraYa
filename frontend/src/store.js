import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
    const userEmail = ref(localStorage.getItem("userEmail") || null);
    const userId = ref(localStorage.getItem("userId") || null);

    const setUserEmail = (email) => {
        userEmail.value = email;
        localStorage.setItem("userEmail", email);
    };

    const setUserId = (id) => {
        userId.value = id;
        localStorage.setItem("userId", id);
    };

    const logout = () => {
        userEmail.value = null;
        userId.value = null;
        localStorage.removeItem("userEmail");
        localStorage.removeItem("userId");
        localStorage.removeItem("AuthToken");
    };

    return { userEmail, userId, setUserEmail, setUserId, logout };
});
