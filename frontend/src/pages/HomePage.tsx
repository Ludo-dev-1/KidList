import { useState } from 'react';
import { Alert, Button, Stack, Typography } from '@mui/material';
import AddRoundedIcon from '@mui/icons-material/AddRounded';
import { ChildCard } from '../features/children/ChildCard';
import { ChildDialog } from '../components/ChildDialog';
import { ConfirmDialog } from '../components/ConfirmDialog';
import { ErrorState } from '../components/ErrorState';
import { LoadingCards } from '../components/LoadingState';
import { useChildren, useChildMutations } from '../hooks/useWishlist';
import { useNotify } from '../utils/notifications';
import { getErrorMessage } from '../utils/errors';
import type { Child } from '../types';

export function HomePage() {
  const { data: children, isLoading, error, refetch } = useChildren();
  const mutations = useChildMutations();
  const notify = useNotify();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingChild, setEditingChild] = useState<Child | undefined>();
  const [deletingChild, setDeletingChild] = useState<Child | undefined>();
  const pending = mutations.create.isPending || mutations.update.isPending;

  const saveChild = async (input: { firstName: string; age: number }) => {
    try {
      if (editingChild) await mutations.update.mutateAsync({ id: editingChild.id, input });
      else await mutations.create.mutateAsync(input);
      setDialogOpen(false); setEditingChild(undefined);
      notify(editingChild ? 'Profil modifié.' : 'Enfant ajouté.');
    } catch (mutationError) { notify(getErrorMessage(mutationError), 'error'); }
  };

  const removeChild = async () => {
    if (!deletingChild) return;
    try { await mutations.remove.mutateAsync(deletingChild.id); notify('Enfant supprimé.'); setDeletingChild(undefined); }
    catch (mutationError) { notify(getErrorMessage(mutationError), 'error'); }
  };

  return (
    <>
      <Stack direction={{ xs: 'column', sm: 'row' }} justifyContent="space-between" alignItems={{ xs: 'flex-start', sm: 'flex-end' }} spacing={2} sx={{ mb: 4 }}>
        <div>
          <Typography variant="overline" color="primary" fontWeight={700} letterSpacing="0.12em">NOËL EN FAMILLE</Typography>
          <Typography variant="h1" sx={{ mt: 0.5 }}>Les listes des enfants</Typography>
          <Typography color="text.secondary" sx={{ mt: 1 }}>Retrouvez les envies de chacun et organisez les cadeaux ensemble.</Typography>
        </div>
        <Button variant="contained" startIcon={<AddRoundedIcon />} onClick={() => { setEditingChild(undefined); setDialogOpen(true); }}>Ajouter un enfant</Button>
      </Stack>
      {isLoading ? <LoadingCards /> : error ? <ErrorState error={error} retry={() => void refetch()} /> : children?.length ? (
        <div className="card-grid">
          {children.map((child) => <ChildCard key={child.id} child={child}
            onEdit={() => { setEditingChild(child); setDialogOpen(true); }}
            onDelete={() => setDeletingChild(child)} />)}
        </div>
      ) : (
        <Alert severity="info" action={<Button color="inherit" size="small" onClick={() => setDialogOpen(true)}>Ajouter</Button>}>
          Aucun enfant pour le moment. Ajoutez un profil pour créer sa liste de Noël.
        </Alert>
      )}
      <ChildDialog open={dialogOpen} child={editingChild} loading={pending} onClose={() => setDialogOpen(false)} onSubmit={(input) => void saveChild(input)} />
      <ConfirmDialog open={Boolean(deletingChild)} title="Supprimer cet enfant ?"
        message={`La liste de cadeaux de ${deletingChild?.firstName ?? ''} sera également supprimée.`}
        loading={mutations.remove.isPending} onClose={() => setDeletingChild(undefined)} onConfirm={() => void removeChild()} />
    </>
  );
}
