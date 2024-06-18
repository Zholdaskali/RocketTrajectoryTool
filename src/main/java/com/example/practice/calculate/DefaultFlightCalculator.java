package com.example.practice.calculate;

import static com.example.practice.model.Constants.g;

public class DefaultFlightCalculator {
    public final double calculateHorizontalDistance(double angle, double initialSpeed) {
        double time = (2 * initialSpeed * Math.sin(Math.toRadians(angle))) / g;
        return initialSpeed * Math.cos(Math.toRadians(angle)) * time;
    }

    public final double calculateMaximumHeight(double angle, double initialSpeed) {
        return (Math.pow(initialSpeed * Math.sin(Math.toRadians(angle)), 2)) / (2 * g);
    }
}
