import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { useParams } from 'react-router-dom';
import IncidentDetails from '../components/IncidentDetails';
const IncidentPage = () => {
    const { id } = useParams();
    return (_jsxs("div", { className: "p-6", children: [_jsx("h1", { className: "text-3xl font-bold mb-4", children: "Incident Details" }), id ? _jsx(IncidentDetails, {}) : _jsx("p", { children: "No incident ID provided." })] }));
};
export default IncidentPage;
