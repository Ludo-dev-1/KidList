import { Card, CardContent, Skeleton, Stack } from '@mui/material';

export function LoadingCards({ count = 3 }: { count?: number }) {
  return (
    <div className="card-grid" aria-label="Chargement en cours" aria-busy="true">
      {Array.from({ length: count }, (_, index) => (
        <Card key={index}><CardContent><Skeleton width="45%" /><Skeleton height={38} /><Skeleton width="75%" /><Skeleton height={40} /></CardContent></Card>
      ))}
    </div>
  );
}

export function LoadingGiftCard() {
  return <Card><Skeleton variant="rectangular" height={190} /><CardContent><Stack spacing={1}><Skeleton width="60%" /><Skeleton /><Skeleton width="80%" /></Stack></CardContent></Card>;
}
