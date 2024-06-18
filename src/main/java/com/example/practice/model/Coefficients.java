package com.example.practice.model;

public class Coefficients {
    // Коэффициенты лобовой и подъемной силы от М



    private double Cx;                        // нач коэф
    public double Cy;                        // нач коэф
    public double Cn;

    public Coefficients(double Cx, double Cy, double Cn){
        this.Cx = Cx;
        this.Cy = Cy;
        this.Cn = Cn;
    }

    public double getCx() {
        return Cx;
    }

    public void setCx(double cx) {
        Cx = cx;
    }

    public double getCy() {
        return Cy;
    }

    public void setCy(double cy) {
        Cy = cy;
    }

    public double getCn() {
        return Cn;
    }

    public void setCn(double cn) {
        Cn = cn;
    }
}
