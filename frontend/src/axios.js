import axios from "axios";

const api = axios.create({
    baseURL: '/api',

});

const AUTH_HEADER_KEY = 'Authorization';

api.interceptors.request.use((config) => {
    try {
        const token = localStorage.getItem('AuthToken');
        if (token) {
            config.headers[AUTH_HEADER_KEY] = `Bearer ${token}`;
            console.log('Token in header:', config.headers[AUTH_HEADER_KEY]);
        } else {
            console.log('No token found in local storage.');
        }
    } catch (error) {
        console.error('Error setting auth header:', error);
    }
    return config;
});

export default api;
