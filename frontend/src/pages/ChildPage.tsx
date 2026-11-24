import { useState } from 'react';
import { Alert, Button, IconButton, Stack, Typography } from '@mui/material';
import ArrowBackRoundedIcon from '@mui/icons-material/ArrowBackRounded';
import AddRoundedIcon from '@mui/icons-material/AddRounded';
import { Link, useParams } from 'react-router-dom';
import { GiftCard } from '../features/gifts/GiftCard';
import { GiftDialog } from '../components/GiftDialog';
import { ReservationDialog } from '../components/ReservationDialog';
import { ConfirmDialog } from '../components/ConfirmDialog';
import { ErrorState } from '../components/ErrorState';
import { LoadingCards } from '../components/LoadingState';
import { useChild, useGiftMutations, useGifts } from '../hooks/useWishlist';
import { useNotify } from '../utils/notifications';
import { getErrorMessage } from '../utils/errors';
import type { Gift, GiftInput } from '../types';

export function ChildPage() {
  const id = Number(useParams().id);
  const childQuery = useChild(id);
  const giftsQuery = useGifts(id);
  const mutations = useGiftMutations(id);
  const notify = useNotify();
  const [giftDialogOpen, setGiftDialogOpen] = useState(false);
  const [editingGift, setEditingGift] = useState<Gift | undefined>();
  const [reservingGift, setReservingGift] = useState<Gift | undefined>();
  const [deletingGift, setDeletingGift] = useState<Gift | undefined>();

  const saveGift = async (input: GiftInput) => {
    try {
      if (editingGift) await mutations.update.mutateAsync({ id: editingGift.id, input });
      else await mutations.create.mutateAsync(input);
      setGiftDialogOpen(false); setEditingGift(undefined);
      notify(editingGift ? 'Cadeau modifié.' : 'Cadeau ajouté.');
    } catch (error) { notify(getErrorMessage(error), 'error'); }
  };
  const reserve = async (firstName: string) => {
    if (!reservingGift) return;
    try { await mutations.reserve.mutateAsync({ id: reservingGift.id, firstName }); setReservingGift(undefined); notify('Cadeau réservé. Merci !'); }
    catch (error) { notify(getErrorMessage(error), 'error'); }
  };
  const remove = async () => {
    if (!deletingGift) return;
    try { await mutations.remove.mutateAsync(deletingGift.id); setDeletingGift(undefined); notify('Cadeau supprimé.'); }
    catch (error) { notify(getErrorMessage(error), 'error'); }
  };
  const cancelReservation = async (gift: Gift) => {
    try { await mutations.cancel.mutateAsync(gift.id); notify('Réservation annulée.'); }
    catch (error) { notify(getErrorMessage(error), 'error'); }
  };

  if (!Number.isInteger(id) || id < 1) return <ErrorState error={new Error('Cette page n’existe pas.')} />;
  if (childQuery.isLoading) return <LoadingCards count={1} />;
  if (childQuery.error || !childQuery.data) return <ErrorState error={childQuery.error ?? new Error('Enfant introuvable.')} retry={() => void childQuery.refetch()} />;

  const child = childQuery.data;
  return (
    <>
      <Stack direction="row" alignItems="center" spacing={1} sx={{ mb: 3 }}>
        <IconButton component={Link} to="/" aria-label="Retour à l’accueil"><ArrowBackRoundedIcon /></IconButton>
        <Typography color="text.secondary" component={Link} to="/" sx={{ textDecoration: 'none' }}>Tous les enfants</Typography>
      </Stack>
      <Stack direction={{ xs: 'column', sm: 'row' }} justifyContent="space-between" alignItems={{ xs: 'flex-start', sm: 'flex-end' }} spacing={2} sx={{ mb: 4 }}>
        <div>
          <Typography variant="overline" color="primary" fontWeight={700} letterSpacing="0.12em">SA LISTE DE NOËL</Typography>
          <Typography variant="h1" sx={{ mt: 0.5 }}>Liste de Noël de {child.firstName}</Typography>
          <Typography color="text.secondary" sx={{ mt: 1 }}>{giftsQuery.data?.length ?? 0} cadeau{giftsQuery.data?.length === 1 ? '' : 'x'} souhaité{giftsQuery.data?.length === 1 ? '' : 's'}</Typography>
        </div>
        <Button variant="contained" startIcon={<AddRoundedIcon />} onClick={() => { setEditingGift(undefined); setGiftDialogOpen(true); }}>Ajouter un cadeau</Button>
      </Stack>
      {giftsQuery.isLoading ? <LoadingCards /> : giftsQuery.error ? <ErrorState error={giftsQuery.error} retry={() => void giftsQuery.refetch()} /> : giftsQuery.data?.length ? (
        <div className="gift-grid">
          {giftsQuery.data.map((gift) => <GiftCard key={gift.id} gift={gift}
            onReserve={() => setReservingGift(gift)}
            onEdit={() => { setEditingGift(gift); setGiftDialogOpen(true); }}
            onDelete={() => setDeletingGift(gift)}
            onCancelReservation={() => void cancelReservation(gift)} />)}
        </div>
      ) : (
        <Alert severity="info" action={<Button color="inherit" size="small" onClick={() => setGiftDialogOpen(true)}>Ajouter</Button>}>
          La liste est encore vide. Ajoutez le premier cadeau souhaité.
        </Alert>
      )}
      <GiftDialog open={giftDialogOpen} gift={editingGift} loading={mutations.create.isPending || mutations.update.isPending}
        onClose={() => setGiftDialogOpen(false)} onSubmit={(input) => void saveGift(input)} />
      <ReservationDialog open={Boolean(reservingGift)} giftTitle={reservingGift?.title ?? ''} loading={mutations.reserve.isPending}
        onClose={() => setReservingGift(undefined)} onSubmit={(firstName) => void reserve(firstName)} />
      <ConfirmDialog open={Boolean(deletingGift)} title="Supprimer ce cadeau ?"
        message={`« ${deletingGift?.title ?? ''} » sera retiré de cette liste.`}
        loading={mutations.remove.isPending} onClose={() => setDeletingGift(undefined)} onConfirm={() => void remove()} />
    </>
  );
}
