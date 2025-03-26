import React, { useState } from "react";
import IncidentList from "../components/IncidentList";
import Sidebar from "../components/Sidebar";
import incidentsData from "../data/incidents.json";

const Dashboard = () => {
  const [selectedIncidents, setSelectedIncidents] = useState<number[]>([]);

  return (
    <div className="flex h-screen">
      <IncidentList
        incidents={incidentsData}
        selectedIncidents={selectedIncidents}
        setSelectedIncidents={setSelectedIncidents}
      />
      <Sidebar selectedIncidents={selectedIncidents} />
    </div>
  );
};

export default Dashboard;
