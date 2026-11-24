import { Card, CardActions, CardContent, CardMedia, Chip, IconButton, Link, Stack, Typography, Button } from '@mui/material';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import EventAvailableOutlinedIcon from '@mui/icons-material/EventAvailableOutlined';
import type { Gift } from '../../types';

interface Props {
  gift: Gift;
  onReserve: () => void;
  onEdit: () => void;
  onDelete: () => void;
  onCancelReservation: () => void;
}

export function GiftCard({ gift, onReserve, onEdit, onDelete, onCancelReservation }: Props) {
  return (
    <Card className="gift-card">
      {gift.imageUrl ? (
        <CardMedia component="img" image={gift.imageUrl} alt={gift.title} className="gift-image"
          onError={(event) => { event.currentTarget.style.display = 'none'; }} />
      ) : <div className="gift-placeholder" aria-hidden="true"><span>🎁</span></div>}
      <CardContent sx={{ flexGrow: 1, p: 2.5 }}>
        <Stack direction="row" justifyContent="space-between" alignItems="flex-start" gap={1}>
          <Typography variant="h3" component="h2">{gift.title}</Typography>
          {gift.reservedBy
            ? <Chip size="small" color="success" icon={<EventAvailableOutlinedIcon />} label="Déjà réservé" />
            : <Chip size="small" variant="outlined" label="Disponible" />}
        </Stack>
        {gift.description && <Typography variant="body2" color="text.secondary" sx={{ mt: 1.25, whiteSpace: 'pre-wrap' }}>{gift.description}</Typography>}
        {gift.productUrl && <Link href={gift.productUrl} target="_blank" rel="noopener noreferrer" underline="hover" sx={{ display: 'inline-block', mt: 1.5 }}>Voir chez le marchand ↗</Link>}
        {gift.reservedBy && <Typography variant="body2" sx={{ mt: 1.5 }}>Réservé par : <strong>{gift.reservedBy}</strong></Typography>}
      </CardContent>
      <CardActions sx={{ px: 2.5, pb: 2.5, pt: 0, justifyContent: 'space-between' }}>
        {gift.reservedBy
          ? <Button size="small" onClick={onCancelReservation}>Annuler la réservation</Button>
          : <Button size="small" variant="contained" onClick={onReserve}>Réserver</Button>}
        <Stack direction="row">
          <IconButton aria-label={`Modifier ${gift.title}`} onClick={onEdit} size="small"><EditOutlinedIcon fontSize="small" /></IconButton>
          <IconButton aria-label={`Supprimer ${gift.title}`} onClick={onDelete} size="small" color="error"><DeleteOutlineIcon fontSize="small" /></IconButton>
        </Stack>
      </CardActions>
    </Card>
  );
}
