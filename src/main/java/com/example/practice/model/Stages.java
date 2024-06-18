package com.example.practice.model;

public class Stages {
    private double m0;          // Начальная масса РН m0 [кг]
    private double m10;         // Начальная масса 1 ступени m01 [кг]
    private double m1t;         // Масса топлива 1 ступени [кг] mt1(1)
    private double m1k;         // Масса корпуса 1 ступени [кг] mл1(1)
    private double mu1k;        // Конечная относительная масса 1-ой ступени
    private double mr1;         // Расход топлива 1 ступени [кг/сек]
    private double wa1;         // Скорость истечения 1-ой ступени [м/сек]
    private double D1;          // Диаметр по миделю 1 ступени [м]
    private double S1;          // Площадь по миделю 1 ступени [м2]
    private double Ds1;         // Диаметр среза сопла 1-ой ступени [м]
    private double F1ard;       // Площаль сечения сопла 1-ой ступени [м2]
    private double Pa1;         // Давление на срезе сопла 1-ой ступени [Па]

    public Stages(
            double m0, double m10,
            double m1t, double mr1,
            double wa1, double D1,
            double Ds1, double Pa1) {
        this.m0 = m0;
        this.m10 = m10;
        this.m1t = m1t;
        this.m1k = m0 - m1t;
        this.mu1k = (m0 - m1t) / m0;
        this.mr1 = mr1;
        this.wa1 = wa1;
        this.D1 = D1;
        this.S1 = Math.PI * Math.pow(D1, 2) / 4;
        this.Ds1 = Ds1;
        this.F1ard = Math.PI * Math.pow(Ds1, 2) / 4;
        this.Pa1 = Pa1;
    }

    public double getM0() {
        return m0;
    }

    public double getM10() {
        return m10;
    }

    public double getM1t() {
        return m1t;
    }

    public double getM1k() {
        return m1k;
    }

    public double getMu1k() {
        return mu1k;
    }

    public double getMr1() {
        return mr1;
    }

    public double getWa1() {
        return wa1;
    }

    public double getD1() {
        return D1;
    }

    public double getS1() {
        return S1;
    }

    public double getDs1() {
        return Ds1;
    }

    public double getF1ard() {
        return F1ard;
    }

    public double getPa1() {
        return Pa1;
    }

    // Сеттеры
    public void setM0(double m0) {
        this.m0 = m0;
    }

    public void setM10(double m10) {
        this.m10 = m10;
    }

    public void setM1t(double m1t) {
        this.m1t = m1t;
    }

    public void setM1k(double m1k) {
        this.m1k = m1k;
    }

    public void setMu1k(double mu1k) {
        this.mu1k = mu1k;
    }

    public void setMr1(double mr1) {
        this.mr1 = mr1;
    }

    public void setWa1(double wa1) {
        this.wa1 = wa1;
    }

    public void setD1(double d1) {
        D1 = d1;
        S1 = Math.PI * Math.pow(d1, 2) / 4;
    }

    public void setDs1(double ds1) {
        Ds1 = ds1;
        F1ard = Math.PI * Math.pow(ds1, 2) / 4;
    }

    public void setPa1(double pa1) {
        Pa1 = pa1;
    }
}


