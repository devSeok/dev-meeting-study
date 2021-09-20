import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const token = localStorage.getItem('accessToken');

const instance: AxiosInstance = axios.create({
  baseURL: 'http://api.dev-meeting-study.site',
  headers: {
    // 토큰이 있으면 토큰 추가, 없으면 null
    Authorization: token ? `Bearer ${token}` : null,
    'Content-Type': 'application/json',
  },
});

instance.interceptors.request.use(
  (config: AxiosRequestConfig) => {
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
