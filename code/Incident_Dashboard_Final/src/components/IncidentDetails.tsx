
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

interface Incident {
  id: number;
  title: string;
  description: string;
}

const IncidentDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [incident, setIncident] = useState<Incident | null>(null);

  useEffect(() => {
    const fetchIncident = async () => {
      try {
        const response = await fetch(`/src/data/incidents.json`);
        const data: Incident[] = await response.json();
        const selectedIncident = data.find((inc) => inc.id === Number(id));
        setIncident(selectedIncident || null);
      } catch (error) {
        console.error('Error fetching incident details:', error);
      }
    };

    fetchIncident();
  }, [id]);

  if (!incident) {
    return <div>Loading incident details...</div>;
  }

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">{incident.title}</h1>
      <p className="mt-4">{incident.description}</p>
    </div>
  );
};

export default IncidentDetails;
