import { useEffect, useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField } from '@mui/material';
import type { Gift, GiftInput } from '../types';

interface Props {
  open: boolean;
  gift?: Gift;
  loading: boolean;
  onClose: () => void;
  onSubmit: (input: GiftInput) => void;
}

export function GiftDialog({ open, gift, loading, onClose, onSubmit }: Props) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [productUrl, setProductUrl] = useState('');
  useEffect(() => {
    if (open) {
      setTitle(gift?.title ?? ''); setDescription(gift?.description ?? '');
      setImageUrl(gift?.imageUrl ?? ''); setProductUrl(gift?.productUrl ?? '');
    }
  }, [open, gift]);
  const submit = (event: React.FormEvent) => {
    event.preventDefault();
    if (!title.trim()) return;
    onSubmit({ title: title.trim(), description: description.trim() || null, imageUrl: imageUrl.trim() || null, productUrl: productUrl.trim() || null });
  };

  return (
    <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="sm">
      <form onSubmit={submit}>
        <DialogTitle>{gift ? 'Modifier le cadeau' : 'Ajouter un cadeau'}</DialogTitle>
        <DialogContent><Stack spacing={2} sx={{ pt: 1 }}>
          <TextField autoFocus required label="Nom du cadeau" value={title} onChange={(e) => setTitle(e.target.value)} inputProps={{ maxLength: 255 }} />
          <TextField label="Description" value={description} onChange={(e) => setDescription(e.target.value)} multiline minRows={2} />
          <TextField label="Lien de l’image" type="url" value={imageUrl} onChange={(e) => setImageUrl(e.target.value)} />
          <TextField label="Lien marchand" type="url" value={productUrl} onChange={(e) => setProductUrl(e.target.value)} />
        </Stack></DialogContent>
        <DialogActions sx={{ px: 3, pb: 2 }}>
          <Button onClick={onClose} disabled={loading}>Annuler</Button>
          <Button type="submit" variant="contained" disabled={loading || !title.trim()}>{gift ? 'Enregistrer' : 'Ajouter'}</Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
