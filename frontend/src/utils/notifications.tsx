import { createContext, useCallback, useContext, useMemo, useState, type ReactNode } from 'react';
import { Alert, Snackbar } from '@mui/material';

type Notice = { message: string; severity: 'success' | 'error' };
type Notify = (message: string, severity?: Notice['severity']) => void;
const NotificationContext = createContext<Notify>(() => undefined);

export function NotificationProvider({ children }: { children: ReactNode }) {
  const [notice, setNotice] = useState<Notice | null>(null);
  const notify = useCallback<Notify>((message, severity = 'success') => setNotice({ message, severity }), []);
  const value = useMemo(() => notify, [notify]);

  return (
    <NotificationContext.Provider value={value}>
      {children}
      <Snackbar open={notice !== null} autoHideDuration={4500} onClose={() => setNotice(null)} anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}>
        {notice ? <Alert onClose={() => setNotice(null)} severity={notice.severity} variant="filled" sx={{ width: '100%' }}>{notice.message}</Alert> : undefined}
      </Snackbar>
    </NotificationContext.Provider>
  );
}

export function useNotify() {
  return useContext(NotificationContext);
}
