import axios from 'axios';

export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  message: string;
  path: string;
}

export const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000,
});
