import { Button, Stack, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export function NotFoundPage() {
  return (
    <Stack spacing={2} alignItems="flex-start">
      <Typography variant="h1">Page introuvable</Typography>
      <Typography color="text.secondary">Cette page n’existe pas ou a été déplacée.</Typography>
      <Button component={Link} to="/" variant="contained">Retour à l’accueil</Button>
    </Stack>
  );
}
