import React, { useState } from "react";
import styled from "styled-components";
import WeatherDisplay from "./components/WeatherDisplay";
import AlertForm from "./components/AlertForm";
import AlertList from "./components/AlertList";

const Container = styled.div`
  font-family: Arial, sans-serif;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f8f9fa;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
`;

const Title = styled.h1`
  text-align: center;
  color: #2c3e50;
`;

const Section = styled.section`
  margin-bottom: 2rem;
`;

const TwoColumnLayout = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
`;

const Column = styled.div`
  flex: 1;
  min-width: 300px;
`;

function App() {
  const [refresh, setRefresh] = useState(false);

  return (
    <Container>
      <Title>🌦️ Weather Alert System</Title>

      <Section>
        <h2>Current Weather</h2>
        <WeatherDisplay />
      </Section>

      <TwoColumnLayout>
        <Column>
          <h2>Create New Alert</h2>
          <AlertForm onAlertCreated={() => setRefresh(!refresh)} />
        </Column>

        <Column>
          <h2>All Alerts</h2>
          <AlertList key={refresh} />
        </Column>
      </TwoColumnLayout>
    </Container>
  );
}

export default App;
