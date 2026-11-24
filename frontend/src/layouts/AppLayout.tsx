import { AppBar, Box, Container, Toolbar, Typography } from '@mui/material';
import AcUnitRoundedIcon from '@mui/icons-material/AcUnitRounded';
import { Link, Outlet } from 'react-router-dom';

export function AppLayout() {
  return (
    <Box minHeight="100vh">
      <AppBar position="static" color="transparent" elevation={0} sx={{ borderBottom: '1px solid #E8EBF0', backgroundColor: 'rgba(255,255,255,.82)', backdropFilter: 'blur(12px)' }}>
        <Toolbar sx={{ maxWidth: 1200, width: '100%', mx: 'auto', px: { xs: 2, sm: 3 } }}>
          <AcUnitRoundedIcon color="primary" sx={{ mr: 1.25 }} />
          <Typography component={Link} to="/" variant="h3" color="text.primary" sx={{ textDecoration: 'none', flexGrow: 1 }}>
            Christmas Wishlist
          </Typography>
          <Typography variant="body2" color="text.secondary">Les fêtes, en famille</Typography>
        </Toolbar>
      </AppBar>
      <Container maxWidth="lg" sx={{ py: { xs: 4, md: 7 } }}>
        <Outlet />
      </Container>
    </Box>
  );
}
