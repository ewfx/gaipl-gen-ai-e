import React from "react";
import Chatbot from "./Chatbot";

interface Props {
  selectedIncidents: number[];
}

const Sidebar: React.FC<Props> = ({ selectedIncidents }) => {
  return (
    <div className="w-1/3 bg-gray-100 p-4">
      <h2 className="text-lg font-bold">Sidebar</h2>
      <Chatbot incidents={selectedIncidents} />
    </div>
  );
};

export default Sidebar;
