import { useEffect, useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField } from '@mui/material';
import type { Child, ChildInput } from '../types';

interface Props {
  open: boolean;
  child?: Child;
  loading: boolean;
  onClose: () => void;
  onSubmit: (input: ChildInput) => void;
}

export function ChildDialog({ open, child, loading, onClose, onSubmit }: Props) {
  const [firstName, setFirstName] = useState('');
  const [age, setAge] = useState('0');
  useEffect(() => {
    if (open) { setFirstName(child?.firstName ?? ''); setAge(String(child?.age ?? 0)); }
  }, [open, child]);

  const submit = (event: React.FormEvent) => {
    event.preventDefault();
    const parsedAge = Number(age);
    if (!firstName.trim() || !Number.isInteger(parsedAge) || parsedAge < 0 || parsedAge > 18) return;
    onSubmit({ firstName: firstName.trim(), age: parsedAge });
  };

  return (
    <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="xs">
      <form onSubmit={submit}>
        <DialogTitle>{child ? 'Modifier le profil' : 'Ajouter un enfant'}</DialogTitle>
        <DialogContent><Stack spacing={2} sx={{ pt: 1 }}>
          <TextField autoFocus required label="Prénom" value={firstName} onChange={(e) => setFirstName(e.target.value)} inputProps={{ maxLength: 100 }} />
          <TextField required type="number" label="Âge" value={age} onChange={(e) => setAge(e.target.value)} inputProps={{ min: 0 }} />
        </Stack></DialogContent>
        <DialogActions sx={{ px: 3, pb: 2 }}>
          <Button onClick={onClose} disabled={loading}>Annuler</Button>
          <Button type="submit" variant="contained" disabled={loading || !firstName.trim()}>{child ? 'Enregistrer' : 'Ajouter'}</Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
