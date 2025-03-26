import React, { useEffect, useState } from "react";
import { DataGrid, GridColDef, GridRowSelectionModel, GridEventListener, GridFilterModel } from "@mui/x-data-grid";
import {
  Box, Typography, List, ListItem, ListItemText, Paper, Grid, Divider
} from "@mui/material";

interface Incident {
  id: string;
  incidentId: string;
  priority: string;
  category: string;
  assignmentGroup: string;
  assignedTo: string;
  description: string;
  state: string;
  openedAt: string;
  resolvedAt: string | null;
  duration: string;
  impact: string;
  urgency: string;
  resolutionNotes: string;
}

interface PastIncident {
  id: string;
  incidentId: string;
  description: string;
  comments: string[];
  resolutionNotes: string;
}

interface Recommendation {
  incidentId: string;
  suggestion: string;
}

const IncidentTable = () => {
  const [incidents, setIncidents] = useState<Incident[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedRows, setSelectedRows] = useState<string[]>([]);
  const [recommendations, setRecommendations] = useState<Recommendation[]>([]);
  const [pastIncidents, setPastIncidents] = useState<PastIncident[]>([]);
  const [selectedIncident, setSelectedIncident] = useState<Incident | null>(null);

  const backendUrl = import.meta.env.VITE_BACKEND_API_URL;

  useEffect(() => {
    const fetchIncidents = async () => {
      try {
        const response = await fetch(`${backendUrl}/incidents`);
        if (!response.ok) {
          throw new Error(`Failed to fetch incidents. Status: ${response.status}`);
        }
        const data = await response.json();
        const incidentsWithId = data.map((incident: Incident, index: number) => ({
          ...incident,
          id: incident.incidentId || `incident-${index}`
        }));
        setIncidents(incidentsWithId);
      } catch (error) {
        setError("Failed to fetch incidents. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchIncidents();
  }, [backendUrl]);

  const fetchPastIncidents = async (incidentDescription: string) => {
    try {
      const response = await fetch(`${backendUrl}/incidents/related?incidentDescription=${incidentDescription}`);
      if (!response.ok) {
        throw new Error(`Failed to fetch past incidents. Status: ${response.status}`);
      }
      const data = await response.json();
      setPastIncidents(data);
    } catch (error) {
      console.error("Error fetching past incidents:", error);
    }
  };

  const fetchRecommendations = async (incidentDescription: string) => {
    try {
      const response = await fetch(`${backendUrl}/recommendations?incidentDescription=${incidentDescription}`);
      if (!response.ok) {
        throw new Error(`Failed to fetch recommendations. Status: ${response.status}`);
      }
      const data = await response.json();
      setRecommendations(data);
    } catch (error) {
      console.error("Error fetching recommendations:", error);
    }
  };

  const handleRowClick: GridEventListener<"rowClick"> = async (params) => {
    const incidentId = params.row.incidentId;
    const incidentDescription = params.row.description;

    if (selectedRows.includes(incidentId)) {
      setSelectedIncident(null);
      setPastIncidents([]);
      setRecommendations([]);
      setSelectedRows([]);
    } else {
      await Promise.all([
        fetchPastIncidents(incidentDescription),
        fetchRecommendations(incidentDescription)
      ]);

      const selectedIncidentData = incidents.find((incident) => incident.incidentId === incidentId) || null;
      setSelectedIncident(selectedIncidentData);
      setSelectedRows([incidentId]);
    }
  };

  const handleRowSelection = async (selection: GridRowSelectionModel) => {
    const selectedIds = selection as string[];
    setSelectedRows(selectedIds);

    if (selectedIds.length === 1) {
      const incidentId = selectedIds[0];
      const selectedIncidentData = incidents.find((incident) => incident.incidentId === incidentId) || null;
      setSelectedIncident(selectedIncidentData);

      if (selectedIncidentData) {
        await Promise.all([
          fetchPastIncidents(selectedIncidentData.description),
          fetchRecommendations(selectedIncidentData.description)
        ]);
      }
    } else {
      setSelectedIncident(null);
      setPastIncidents([]);
      setRecommendations([]);
    }
  };

  const columns: GridColDef[] = [
    { field: "incidentId", headerName: "Incident ID", width: 150, sortable: true },
    { field: "priority", headerName: "Priority", width: 120, sortable: true },
    { field: "category", headerName: "Category", width: 150, sortable: true },
    { field: "assignmentGroup", headerName: "Assignment Group", width: 180, sortable: true },
    { field: "assignedTo", headerName: "Assigned To", width: 180, sortable: true },
    { field: "openedAt", headerName: "Opened At", width: 180, sortable: true },
    { field: "state", headerName: "State", width: 120, sortable: true },
  ];

  // Apply default filter model to show only "New" incidents on load
  const filterModel: GridFilterModel = {
    items: [
      { columnField: "state", operatorValue: "equals", value: "New" }
    ]
  };

  return (
    <Box sx={{ height: "100vh", display: "flex" }}>
      <Grid container spacing={2} sx={{ height: "100%" }}>

        {/* Sidebar */}
        {selectedIncident && (
          <Grid item xs={3} sx={{ display: "flex", flexDirection: "column", height: "100%" }}>

            {/* Past Incidents */}
            <Paper sx={{ flex: 1, boxShadow: 3, overflow: "auto", mb: 2 }}>
              <Box sx={{ position: "sticky", top: 0, zIndex: 1, bgcolor: "#1976d2", color: "#fff", p: 2 }}>
                <Typography variant="h6">Past Incidents ({pastIncidents.length})</Typography>
              </Box>
              <List>
                {pastIncidents.map((incident, index) => (
                  <ListItem key={index} sx={{ borderBottom: "1px solid #ccc" }}>
                    <Typography variant="body1" sx={{ fontWeight: "bold", mr: 1 }}>{index + 1}.</Typography>
                    <ListItemText primary={incident.description} secondary={incident.resolutionNotes} />
                  </ListItem>
                ))}
              </List>
            </Paper>

            {/* Recommendations */}
            <Paper sx={{ flex: 1, boxShadow: 3, overflow: "auto" }}>
              <Box sx={{ position: "sticky", top: 0, zIndex: 1, bgcolor: "#388e3c", color: "#fff", p: 2 }}>
                <Typography variant="h6">Recommendations ({recommendations.length})</Typography>
              </Box>
              <List>
                {recommendations.map((rec, index) => (
                  <ListItem key={index}>
                    <Typography variant="body1" sx={{ fontWeight: "bold", mr: 1 }}>{index + 1}.</Typography>
                    <ListItemText primary={rec.suggestion} />
                  </ListItem>
                ))}
              </List>
            </Paper>
          </Grid>
        )}

        {/* Main Table */}
        <Grid item xs={selectedIncident ? 9 : 12} sx={{ display: "flex", flexDirection: "column", height: "100%" }}>
          <Paper sx={{ p: 2, boxShadow: 3, mb: 2, flex: 1, overflow: "auto" }}>
            <Typography variant="h4" sx={{ mb: 2 }}>Incident Dashboard</Typography>
            <DataGrid
              rows={incidents}
              columns={columns}
              checkboxSelection
              filterModel={filterModel}
              onRowSelectionModelChange={handleRowSelection}
              onRowClick={handleRowClick}
              pagination
            />
          </Paper>

          {selectedIncident && (
            <Paper sx={{ p: 3, boxShadow: 3, maxHeight: "300px", overflowY: "auto" }}>
              <Typography variant="h5" sx={{ color: "#1976d2" }}>Incident Details</Typography>
              <Divider sx={{ mb: 2 }} />
              {Object.entries(selectedIncident).map(([key, value]) => (
                <Typography key={key}><strong>{key}:</strong> {String(value)}</Typography>
              ))}
            </Paper>
          )}
        </Grid>
      </Grid>
    </Box>
  );
};

export default IncidentTable;
