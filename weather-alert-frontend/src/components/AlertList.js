import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";

const List = styled.ul`
  list-style: none;
  padding: 0;
`;

const Item = styled.li`
  background-color: ${(props) => (props.triggered ? "#ffe6e6" : "#e6ffe6")};
  padding: 1rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
`;

const Status = styled.span`
  font-weight: bold;
  color: ${(props) => (props.triggered ? "#c0392b" : "#27ae60")};
`;

function AlertList() {
    const [alerts, setAlerts] = useState([]);

    const fetchAlerts = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/alerts");
            setAlerts(res.data);
        } catch (error) {
            console.error("Error fetching alerts:", error);
        }
    };

    useEffect(() => {
        fetchAlerts();
        const interval = setInterval(fetchAlerts, 5000);
        return () => clearInterval(interval);
    }, []);

    return (
        <List>
            {alerts.length === 0 ? (
                <p>No alerts created yet.</p>
            ) : (
                alerts.map((alert) => (
                    <Item key={alert.id} triggered={alert.triggered}>
                        📍 <strong>{alert.location}</strong> — {alert.parameter} {alert.operator} {alert.threshold}
                        <div>
                            <Status triggered={alert.triggered}>
                                {alert.triggered ? "🚨 Triggered" : "✅ All Clear"}
                            </Status>
                        </div>
                        {alert.description && <div><i>{alert.description}</i></div>}
                    </Item>
                ))
            )}
        </List>
    );
}

export default AlertList;
