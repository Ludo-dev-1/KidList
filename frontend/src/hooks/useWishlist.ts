import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { wishlistApi } from '../api/wishlistApi';
import type { ChildInput, GiftInput } from '../types';

export const childKeys = {
  all: ['children'] as const,
  detail: (id: number) => ['children', id] as const,
  gifts: (id: number) => ['children', id, 'gifts'] as const,
};

export function useChildren() {
  return useQuery({ queryKey: childKeys.all, queryFn: wishlistApi.getChildren });
}

export function useChild(id: number) {
  return useQuery({ queryKey: childKeys.detail(id), queryFn: () => wishlistApi.getChild(id), enabled: id > 0 });
}

export function useGifts(childId: number) {
  return useQuery({
    queryKey: childKeys.gifts(childId),
    queryFn: () => wishlistApi.getGifts(childId),
    enabled: childId > 0,
    refetchInterval: 5000,
  });
}

export function useChildMutations() {
  const client = useQueryClient();
  const refresh = () => client.invalidateQueries({ queryKey: childKeys.all });
  return {
    create: useMutation({ mutationFn: wishlistApi.createChild, onSuccess: refresh }),
    update: useMutation({ mutationFn: ({ id, input }: { id: number; input: ChildInput }) => wishlistApi.updateChild(id, input), onSuccess: refresh }),
    remove: useMutation({ mutationFn: wishlistApi.deleteChild, onSuccess: refresh }),
  };
}

export function useGiftMutations(childId: number) {
  const client = useQueryClient();
  const refresh = () => client.invalidateQueries({ queryKey: childKeys.gifts(childId) });
  return {
    create: useMutation({ mutationFn: (input: GiftInput) => wishlistApi.createGift(childId, input), onSuccess: refresh }),
    update: useMutation({ mutationFn: ({ id, input }: { id: number; input: GiftInput }) => wishlistApi.updateGift(id, input), onSuccess: refresh }),
    remove: useMutation({ mutationFn: wishlistApi.deleteGift, onSuccess: refresh }),
    reserve: useMutation({ mutationFn: ({ id, firstName }: { id: number; firstName: string }) => wishlistApi.reserveGift(id, firstName), onSettled: refresh }),
    cancel: useMutation({ mutationFn: wishlistApi.cancelReservation, onSettled: refresh }),
  };
}
