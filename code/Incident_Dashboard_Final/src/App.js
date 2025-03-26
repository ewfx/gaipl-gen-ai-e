import { jsx as _jsx } from "react/jsx-runtime";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IncidentTable from "./components/IncidentTable"; // Import the table
import "./styles/globals.css";
const App = () => {
    return (_jsx(Router, { children: _jsx(Routes, { children: _jsx(Route, { path: "/", element: _jsx("div", { children: _jsx(IncidentTable, {}) }) }) }) }));
};
export default App;
