import React from 'react';
import { useParams } from 'react-router-dom';
import IncidentDetails from '../components/IncidentDetails';

const IncidentPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-4">Incident Details</h1>
      {id ? <IncidentDetails /> : <p>No incident ID provided.</p>}
    </div>
  );
};

export default IncidentPage;