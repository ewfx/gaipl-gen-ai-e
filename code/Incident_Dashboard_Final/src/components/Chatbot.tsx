import React from "react";

interface Props {
  incidents: number[];
}

const Chatbot: React.FC<Props> = ({ incidents }) => {
  return (
    <div>
      <h3 className="text-md font-semibold">Chat with AI</h3>
      {incidents.length > 0 ? (
        <button className="p-2 bg-blue-500 text-white rounded mt-2">
          Chat with AI on {incidents.length} incidents
        </button>
      ) : (
        <p>No incidents selected.</p>
      )}
    </div>
  );
};

export default Chatbot;
