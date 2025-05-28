import React, { useState } from "react";
import axios from "axios";
import styled from "styled-components";

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
`;

const Input = styled.input`
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
`;

const Select = styled.select`
  ${Input}
`;

const Button = styled.button`
  background-color: #3498db;
  color: #fff;
  border: none;
  padding: 0.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;

  &:hover {
    background-color: #2980b9;
  }
`;

function AlertForm({ onAlertCreated }) {
    const [formData, setFormData] = useState({
        location: "",
        parameter: "temperature",
        operator: ">",
        threshold: "",
        description: "",
    });

    const handleChange = (e) => {
        setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8080/api/alerts", {
                ...formData,
                threshold: parseFloat(formData.threshold),
            });
            alert("✅ Alert created");
            setFormData({ location: "", parameter: "temperature", operator: ">", threshold: "", description: "" });
            if (onAlertCreated) onAlertCreated();
        } catch (error) {
            console.error("Error creating alert:", error);
            alert("❌ Failed to create alert");
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Input name="location" value={formData.location} onChange={handleChange} placeholder="Location" required />
            <Select name="parameter" value={formData.parameter} onChange={handleChange}>
                <option value="temperature">Temperature</option>
                <option value="humidity">Humidity</option>
                <option value="windSpeed">Wind Speed</option>
            </Select>
            <Select name="operator" value={formData.operator} onChange={handleChange}>
                <option value=">">{'>'}</option>
                <option value="<">{'<'}</option>
                <option value="=">=</option>
            </Select>
            <Input name="threshold" value={formData.threshold} onChange={handleChange} placeholder="Threshold" type="number" required />
            <Input name="description" value={formData.description} onChange={handleChange} placeholder="Description (optional)" />
            <Button type="submit">Create Alert</Button>
        </Form>
    );
}

export default AlertForm;
