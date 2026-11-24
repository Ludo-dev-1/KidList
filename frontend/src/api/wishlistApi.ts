import { apiClient } from './client';
import type { Child, ChildInput, Gift, GiftInput } from '../types';

export const wishlistApi = {
  async getChildren() {
    return (await apiClient.get<Child[]>('/children')).data;
  },
  async getChild(id: number) {
    return (await apiClient.get<Child>(`/children/${id}`)).data;
  },
  async createChild(input: ChildInput) {
    return (await apiClient.post<Child>('/children', input)).data;
  },
  async updateChild(id: number, input: ChildInput) {
    return (await apiClient.put<Child>(`/children/${id}`, input)).data;
  },
  async deleteChild(id: number) {
    await apiClient.delete(`/children/${id}`);
  },
  async getGifts(childId: number) {
    return (await apiClient.get<Gift[]>(`/children/${childId}/gifts`)).data;
  },
  async createGift(childId: number, input: GiftInput) {
    return (await apiClient.post<Gift>(`/children/${childId}/gifts`, input)).data;
  },
  async updateGift(id: number, input: GiftInput) {
    return (await apiClient.put<Gift>(`/gifts/${id}`, input)).data;
  },
  async deleteGift(id: number) {
    await apiClient.delete(`/gifts/${id}`);
  },
  async reserveGift(id: number, reservedBy: string) {
    return (await apiClient.post<Gift>(`/gifts/${id}/reserve`, { reservedBy })).data;
  },
  async cancelReservation(id: number) {
    return (await apiClient.delete<Gift>(`/gifts/${id}/reserve`)).data;
  },
};
