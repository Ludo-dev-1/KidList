import { Alert, Button, Stack } from '@mui/material';
import { getErrorMessage } from '../utils/errors';

export function ErrorState({ error, retry }: { error: unknown; retry?: () => void }) {
  return (
    <Stack spacing={2} alignItems="flex-start">
      <Alert severity="error">{getErrorMessage(error)}</Alert>
      {retry && <Button onClick={retry} variant="outlined">Réessayer</Button>}
    </Stack>
  );
}
