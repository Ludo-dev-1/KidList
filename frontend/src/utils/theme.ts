import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    mode: 'light',
    background: { default: '#F8F9FA', paper: '#FFFFFF' },
    text: { primary: '#111827', secondary: '#6B7280' },
    primary: { main: '#2563EB' },
    success: { main: '#16A34A' },
  },
  shape: { borderRadius: 14 },
  typography: {
    fontFamily: 'Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif',
    h1: { fontSize: 'clamp(2rem, 5vw, 3rem)', fontWeight: 750, letterSpacing: '-0.045em' },
    h2: { fontSize: '1.65rem', fontWeight: 700, letterSpacing: '-0.035em' },
    h3: { fontSize: '1.1rem', fontWeight: 650 },
    button: { textTransform: 'none', fontWeight: 600 },
  },
  components: {
    MuiCard: { styleOverrides: { root: { border: '1px solid #E8EBF0', boxShadow: '0 4px 20px rgba(17,24,39,.035)' } } },
    MuiButton: { styleOverrides: { root: { borderRadius: 10, paddingInline: 16 } } },
  },
});
