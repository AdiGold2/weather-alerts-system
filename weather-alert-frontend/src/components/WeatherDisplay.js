import React, { useState } from "react";
import axios from "axios";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const InputRow = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
`;

const Input = styled.input`
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
`;

const Button = styled.button`
  padding: 0.5rem 1rem;
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;

  &:hover {
    background-color: #2980b9;
  }
`;

function WeatherDisplay() {
    const [weather, setWeather] = useState(null);
    const [city, setCity] = useState("Tel Aviv");

    const fetchWeather = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/weather?city=${city}`);
            setWeather(response.data);
        } catch (error) {
            console.error("Failed to fetch weather:", error);
            alert("Failed to fetch weather. Check backend connection.");
        }
    };

    // Optional: fetch once on mount
    // useEffect(() => {
    //     fetchWeather();
    // }, []);

    return (
        <Wrapper>
            <InputRow>
                <Input
                    type="text"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    placeholder="Enter city"
                />
                <Button onClick={fetchWeather}>Get Weather</Button>
            </InputRow>

            {weather ? (
                <div>
                    <p><strong>Temperature:</strong> {weather.temperature} °C</p>
                    <p><strong>Humidity:</strong> {weather.humidity} %</p>
                    <p><strong>Wind Speed:</strong> {weather.windSpeed} km/h</p>
                </div>
            ) : (
                <p>No weather data yet.</p>
            )}
        </Wrapper>
    );
}

export default WeatherDisplay;
