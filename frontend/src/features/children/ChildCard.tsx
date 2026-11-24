import { Avatar, Button, Card, CardContent, IconButton, Stack, Typography } from '@mui/material';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import ArrowForwardRoundedIcon from '@mui/icons-material/ArrowForwardRounded';
import { Link } from 'react-router-dom';
import type { Child } from '../../types';

interface Props {
  child: Child;
  onEdit: () => void;
  onDelete: () => void;
}

export function ChildCard({ child, onEdit, onDelete }: Props) {
  const colors = ['#DBEAFE', '#FCE7F3', '#DCFCE7', '#FEF3C7'];
  const color = colors[child.id % colors.length];
  return (
    <Card className="child-card">
      <CardContent sx={{ p: 2.5 }}>
        <Stack direction="row" alignItems="center" spacing={2}>
          <Avatar sx={{ width: 56, height: 56, bgcolor: color, color: 'text.primary' }} aria-hidden="true">
            {child.firstName[0]?.toUpperCase()}
          </Avatar>
          <div className="child-details">
            <Typography variant="h3">{child.firstName}</Typography>
            <Typography variant="body2" color="text.secondary">{child.age} {child.age === 1 ? 'an' : 'ans'}</Typography>
          </div>
          <Stack direction="row" spacing={0.25}>
            <IconButton aria-label={`Modifier ${child.firstName}`} onClick={onEdit} size="small"><EditOutlinedIcon fontSize="small" /></IconButton>
            <IconButton aria-label={`Supprimer ${child.firstName}`} onClick={onDelete} size="small" color="error"><DeleteOutlineIcon fontSize="small" /></IconButton>
          </Stack>
        </Stack>
        <Button component={Link} to={`/children/${child.id}`} fullWidth variant="outlined" endIcon={<ArrowForwardRoundedIcon />} sx={{ mt: 2.5 }}>
          Voir sa liste
        </Button>
      </CardContent>
    </Card>
  );
}
