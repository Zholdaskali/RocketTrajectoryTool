package com.example.practice.model;

public class Angle {
    private double fi1;
    private double tetta1;
    private double alfa1;         // Угол атаки
    private double etta1;         // полярный угол

    public Angle(double engle) {
        this.fi1 = engle * Math.PI / 180;
        this.tetta1 = fi1;
        this.alfa1 = 0 * Math.PI / 180;
        this.etta1 = 0 * Math.PI / 180;
    }

    public double getFi1() {
        return fi1;
    }

    public double getTetta1() {
        return tetta1;
    }

    public void setTetta1(double tetta1) {
        this.tetta1 = tetta1;
    }

    public double getAlfa1() {
        return alfa1;
    }

    public void setAlfa1(double alfa1) {
        this.alfa1 = alfa1;
    }

    public double getEtta1() {
        return etta1;
    }

    public void setEtta1(double etta1) {
        this.etta1 = etta1;
    }
}


