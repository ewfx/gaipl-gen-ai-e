import React, { useState } from "react";
import pastIncidents from "../data/pastIncidents.json";

interface Props {
  incidentId: number;
}

const PastIncidentPopup: React.FC<Props> = ({ incidentId }) => {
  const [show, setShow] = useState(false);

  const relatedIncidents = pastIncidents.filter(
    (inc) => inc.relatedIncidentId === incidentId
  );

  return (
    <div>
      <button onClick={() => setShow(true)} className="p-2 bg-gray-300">
        View Past Incidents
      </button>
      {show && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded">
            <h3 className="text-lg">Past Incidents</h3>
            {relatedIncidents.map((inc) => (
              <p key={inc.id}>{inc.title}</p>
            ))}
            <button onClick={() => setShow(false)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default PastIncidentPopup;
