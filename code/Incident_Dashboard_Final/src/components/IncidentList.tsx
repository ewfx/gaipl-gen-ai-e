import React from "react";
import { useNavigate } from "react-router-dom";

interface Incident {
  id: number;
  title: string;
  description: string;
}

interface Props {
  incidents: Incident[];
  selectedIncidents: number[];
  setSelectedIncidents: React.Dispatch<React.SetStateAction<number[]>>;
}

const IncidentList: React.FC<Props> = ({
  incidents,
  selectedIncidents,
  setSelectedIncidents,
}) => {
  const navigate = useNavigate();

  const toggleSelection = (id: number) => {
    setSelectedIncidents((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  return (
    <div className="w-2/3 p-4">
      <h2 className="text-xl font-bold">Incidents</h2>
      <ul>
        {incidents.map((incident) => (
          <li
            key={incident.id}
            className="p-4 border-b flex justify-between items-center hover:bg-gray-100 cursor-pointer"
            onClick={() => navigate(`/incident/${incident.id}`)}
          >
            <span>{incident.title}</span>
            <input
              type="checkbox"
              checked={selectedIncidents.includes(incident.id)}
              onChange={() => toggleSelection(incident.id)}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default IncidentList;
