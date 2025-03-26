import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { useState } from "react";
import IncidentList from "../components/IncidentList";
import Sidebar from "../components/Sidebar";
import incidentsData from "../data/incidents.json";
const Dashboard = () => {
    const [selectedIncidents, setSelectedIncidents] = useState([]);
    return (_jsxs("div", { className: "flex h-screen", children: [_jsx(IncidentList, { incidents: incidentsData, selectedIncidents: selectedIncidents, setSelectedIncidents: setSelectedIncidents }), _jsx(Sidebar, { selectedIncidents: selectedIncidents })] }));
};
export default Dashboard;
