import { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from '@mui/material';

interface Props {
  open: boolean;
  giftTitle: string;
  loading: boolean;
  onClose: () => void;
  onSubmit: (firstName: string) => void;
}

export function ReservationDialog({ open, giftTitle, loading, onClose, onSubmit }: Props) {
  const [firstName, setFirstName] = useState('');
  const submit = (event: React.FormEvent) => {
    event.preventDefault();
    if (firstName.trim()) onSubmit(firstName.trim());
  };
  return (
    <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="xs">
      <form onSubmit={submit}>
        <DialogTitle>Réserver ce cadeau</DialogTitle>
        <DialogContent>
          <TextField autoFocus fullWidth required sx={{ mt: 1 }} label="Votre prénom" value={firstName}
            onChange={(event) => setFirstName(event.target.value)} inputProps={{ maxLength: 255 }}
            helperText={`Vous réservez « ${giftTitle} » pour Noël.`} />
        </DialogContent>
        <DialogActions sx={{ px: 3, pb: 2 }}>
          <Button onClick={onClose} disabled={loading}>Annuler</Button>
          <Button type="submit" variant="contained" disabled={loading || !firstName.trim()}>Valider</Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
