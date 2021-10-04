import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const instance: AxiosInstance = axios.create({
  baseURL: 'http://api.dev-meeting-study.site/api',
});

instance.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const token = localStorage.getItem('accessToken');
    // 토큰이 없으면 null
    config.headers['Authorization'] = token ? `Bearer ${token}` : null;
    config.headers['Content-Type'] = 'application/json';
    console.log('config', config);
    return config;
  },
  (err) => Promise.reject(err),
);

instance.interceptors.response.use(
  (response) => {
    console.log('response', response);
    return response;
  },
  (err) => Promise.reject(err),
);

export default instance;
