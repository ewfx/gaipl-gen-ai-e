import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import IncidentTable from "./components/IncidentTable";  // Import the table
import "./styles/globals.css";

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Add the IncidentTable to the main route */}
        <Route path="/" element={<div><IncidentTable /></div>} />
      </Routes>
    </Router>
  );
};

export default App;
