import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';

interface Props {
  open: boolean;
  title: string;
  message: string;
  loading?: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

export function ConfirmDialog({ open, title, message, loading, onClose, onConfirm }: Props) {
  return (
    <Dialog open={open} onClose={loading ? undefined : onClose} aria-labelledby="confirm-title">
      <DialogTitle id="confirm-title">{title}</DialogTitle>
      <DialogContent><DialogContentText>{message}</DialogContentText></DialogContent>
      <DialogActions sx={{ px: 3, pb: 2 }}>
        <Button onClick={onClose} disabled={loading}>Annuler</Button>
        <Button onClick={onConfirm} color="error" variant="contained" disabled={loading}>Supprimer</Button>
      </DialogActions>
    </Dialog>
  );
}
