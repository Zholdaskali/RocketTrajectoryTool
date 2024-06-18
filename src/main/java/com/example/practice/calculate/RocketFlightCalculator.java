package com.example.practice.calculate;

import com.example.practice.model.Coefficients;
import com.example.practice.model.Angle;
import com.example.practice.model.Stages;

import java.util.ArrayList;
import static com.example.practice.model.Constants.*;
import static com.example.practice.model.Parameters.*;

public class RocketFlightCalculator {

//rocketFlightSimulation
    // Основные переменные
    static double dt = 0.1; // шаг
    static double t = dt; // время
    public static double maxH;

    // Массивы для хранения результатов
    public static ArrayList<Double> times = new ArrayList<>();
    public static ArrayList<Double> heights = new ArrayList<>();
    public static ArrayList<Double> velocities = new ArrayList<>();
    public static ArrayList<Double> distances = new ArrayList<>();

    public static void rocketFlightSimulation(double angle1, Stages stage1, int n, Coefficients coefficients) {

//        int n = 2968;
        Angle angle = new Angle(angle1); // Создаем объект Engle с начальными значениями углов
//        Stage stage = new Stage(
//                140, 140,
//                110, 16.67,
//                1400, 0.2,
//                0.18, 5000); // Создаем объект Stage с начальными параметрами
//    private double Cx = 0.126;                        // нач коэф
//    public double Cy = 0.126;                        // нач коэф
//    public double Cn = 0.2;                          // нач коэф


        double m0 = stage1.getM0();
        double mr1 = stage1.getMr1();
        double wa1 = stage1.getWa1();
        double F1ard = stage1.getF1ard();
        double Pa1 = stage1.getPa1();
        double S1 = stage1.getS1();
        double mu1k = stage1.getMu1k();

        double Cx = coefficients.getCx();
        double Cy = coefficients.getCy();
        double Cn = coefficients.getCn();

        double m1 = m0 - mr1 * dt; // текущая масса 1-ой ступени
        double P = wa1 * mr1 + F1ard * (Pa1 - Pn); // Тяга 1-ой ступени
        double q1 = 0; // скоростной напор
        double X1 = (Cx * S1 * q1); // лоб сопротивление
        double Y1 = (Cy * S1 * q1); // подъемная сила

        // Приращение скорости \\ ускорение
        double A1 = (P * Math.cos(angle.getAlfa1()) - X1 - m1 * g * Math.sin(angle.getTetta1())) / m1;
        double V1 = A1 * dt; // нач. скорость
        double V1x = V1 * Math.cos(angle.getTetta1()); // скорость по оси Х
        double V1y = V1 * Math.sin(angle.getTetta1()); // скорость по оси У
        double H1 = V1 * dt; // Начальная высота над У.М.
        double r = Rz + H1; // Расстояние от центра Земли до ЛА

        // Приращение угл. скор.
        double weps = ((P * Math.sin(angle.getAlfa1()) + Y1 - m1 * g * Math.cos(angle.getTetta1())) / m1 + (Math.pow(V1, 2)) * Math.cos(angle.getTetta1()) / r) / V1;

        double l1 = angle.getEtta1() * r; // Ортодромная дальность l=etta(1)*r(1)
        double n1 = P / (m1 * g); // Тяговооруженность
        double mu1 = m1 / m0; // относительная масса ЛА
        double M = V1 / az; // Число Маха

        times.add(t);
        heights.add(H1);
        velocities.add(V1);
        distances.add(l1);

        int i = 0;
        while (i < n) {
            t += dt;
            if (maxH < H1){
                maxH = H1;
            }
            // Расчет полета 1-ой ступени
            if (H1 > 0) {
                Pn = Pn0 * Math.exp(-mv * g * H1 / R / Ty);   // Давление на высоте Н
                double ro = Pn * 12 * 1e-6;                   // Плотность воздуха на Н
                if (H1 <= 11000) {                            // Высота до 11000
                    Ty = T0 - H1 * 0.006;                     // текущая темп-ра воздуха
                    az = Math.sqrt((kv * k * Ty) / mv1);      // Скорость звука
                } else if (H1 > 11000 && H1 <= 20000) {       // Высота от 11000 до 20000
                    Ty = Ty;                                  // текущая темпер-ра воздуха
                    az = Math.sqrt((kv * k * Ty) / mv1);      // Скорость звука
                } else {                                      // Выше 20000
                    Ty = Ty;                                  // текущая температура воздуха
                    az = Math.sqrt((kv * k * Ty) / mv1);      // Скорость звука
                }

                if (mu1 >= mu1k) {                            // 1-ая ступень активна
                    m1 -= mr1 * dt;                           // текущая масса ЛА
                    P = wa1 * mr1 + F1ard * (Pa1 - Pn);       // Тяга двигателя
//                    System.out.println("Активный полет 1-ой ступени");
                } else {
                    m1 = m1;                                  // Масса пустая
                    P = 0;                                    // Тяга нулевая
//                    System.out.println("Автономный полет 1-ой ступени по Баллистической траектории");
                }

                mu1 = m1 / m0;                                // относительная масса ЛА
                double fi1 = angle.getTetta1() - angle.getEtta1() - angle.getAlfa1();
                double Tyaga1 = P * Math.cos(angle.getAlfa1());
                double Vozduh1 = -X1;
                double G1 = -m1 * g * Math.sin(angle.getTetta1());
                // Приращение линейной скорости \\ ускорение
                A1 = (P * Math.cos(angle.getAlfa1()) - X1 - m1 * g * Math.sin(angle.getTetta1())) / m1;
                V1 += A1 * dt;                                 // скорость ЛА
                V1x = V1 * Math.cos(angle.getTetta1());        // скорость по оси Х
                V1y = V1 * Math.sin(angle.getTetta1());        // скорость по оси У
                r += V1y * dt;                                 // Расстояние от центра Земли до ЛА
                angle.setEtta1(angle.getEtta1() + V1x / r * dt); // Текущий угол etta
                // Приращение угловой скорости \\ ускорение.
                weps = ((P * Math.sin(angle.getAlfa1()) + Y1 - m1 * g * Math.cos(angle.getTetta1())) / m1 + (Math.pow(V1, 2)) * Math.cos(angle.getTetta1()) / r) / V1;
                angle.setTetta1(angle.getTetta1() + weps * dt); // Угол между вектором скорости и линией местного горизонта
                H1 = r - Rz;                                   // Высота над У.М. 1 ступени
                angle.setAlfa1(0);                             // Угол атаки, рад
                q1 = ro * (Math.pow(V1, 2)) / 2;               // Скоросной напор
                M = V1 / az;                                    // Число Маха
                X1 = Cx * S1 * q1 * Math.cos(angle.getAlfa1()); // Лоб сопротивление
                Y1 = Cy * S1 * q1 * Math.sin(angle.getAlfa1()); // Подъемная сила
                l1 = angle.getEtta1() * r;                      // Ортодромная дальность l=etta(1)*r(1)
                n1 = P / (m1 * g);                              // Тяговооруженность 1 ступени

            } else {
                m1 = m1;                                        // Масса пустая ступени
                H1 = 0;                                         // Высота
                heights.add(H1);
                distances.add(l1);
                velocities.add(V1);
                times.add(t);
                Ty = Ty;                                        // Температура
            }

            times.add(t);
            heights.add(H1);
            velocities.add(V1);
            distances.add(l1);

            i++;
//            System.out.println(i);
        }
        // Вывод графиков (можно использовать JFreeChart или другой библиотечный инструмент для построения графиков)
        // Примерный вывод данных:

    }
}

