package com.example.practice.model;

import static com.example.practice.model.Constants.*;

public class Parameters {
    // Начальные параметры
    public static double H1 = 0;                            // Высота начальная H1 над У.М. [м]
    public static double Pn = Pn0 * Math.pow((1 - 0.0000226 * H1), 5.25); // атмосферное давление
    public static double ro = Pn * 12 * 1e-6;               // плотность воздуха
    public static double Ty = T0 - H1 * 0.006;              // текущая температура воздуха
    public static double az = Math.sqrt((kv * k * Ty) / mv1); // Скорость звука
}
