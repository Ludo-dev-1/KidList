import { Route, Routes } from 'react-router-dom';
import { AppLayout } from './layouts/AppLayout';
import { HomePage } from './pages/HomePage';
import { ChildPage } from './pages/ChildPage';
import { NotFoundPage } from './pages/NotFoundPage';

export default function App() {
  return (
    <Routes>
      <Route element={<AppLayout />}>
        <Route index element={<HomePage />} />
        <Route path="children/:id" element={<ChildPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
}
