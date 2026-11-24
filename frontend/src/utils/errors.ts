import axios from 'axios';
import type { ApiErrorResponse } from '../api/client';

export function getErrorMessage(error: unknown): string {
  if (axios.isAxiosError<ApiErrorResponse>(error)) {
    return error.response?.data?.message ?? 'Une erreur est survenue. Vérifiez votre connexion puis réessayez.';
  }
  return error instanceof Error ? error.message : 'Une erreur inattendue est survenue.';
}
