package com.example.practice.controller;

import com.example.practice.calculate.DefaultFlightCalculator;
import com.example.practice.util.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.practice.format.FormatDouble;

import static com.example.practice.model.Constants.g;

public class FlightController {
    private final DefaultFlightCalculator flightCalculator = new DefaultFlightCalculator();

    @FXML
    private TextField angle_id;

    @FXML
    private Button btMain_id;

    @FXML
    private TextField initialSpeed_id;

    @FXML
    private LineChart<Number, Number> lineChart_id;

    @FXML
    private Label infoLabel_id;
    private ObservableList<XYChart.Series<Number, Number>> seriesList;
    @FXML
    private Button clearButton_id;

    @FXML
    public void initialize() {
        seriesList = FXCollections.observableArrayList();
    }
    private static int countGraph = 0;



    @FXML
    private void handleButtonClick() {
        if (countGraph < 6) {
            try {
                countGraph++;
                double angle = Double.parseDouble(angle_id.getText());
                double initialSpeed = Double.parseDouble(initialSpeed_id.getText());

                double horizontalDistance = flightCalculator.calculateHorizontalDistance(angle, initialSpeed);
                double maximumHeight = flightCalculator.calculateMaximumHeight(angle, initialSpeed);

                String labelText = "Горизонтальное расстояние: " + FormatDouble.format(horizontalDistance)  + " м" + "\nМаксимальная высота: " + FormatDouble.format(maximumHeight) + " м";

                infoLabel_id.setText(labelText);

                showGraph(angle, initialSpeed);
            }catch (NumberFormatException e) {
                ErrorHandler.showError("Проверьте валидность вводимые данные");
            }
        }else {
            ErrorHandler.showError("количество превышает допустимое");
        }
    }

    @FXML
    private void handleClearButtonClick() {
        countGraph = 0;
        infoLabel_id.setText("");
        lineChart_id.getData().clear();
        seriesList.clear();
    }


    private void showGraph(double angle, double initialSpeed) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Flight Path " + countGraph + ",\n angle = " + angle + ",\n v0 = " + initialSpeed);

        double totalTime = (2 * initialSpeed * Math.sin(Math.toRadians(angle))) / g;
        double timeStep = totalTime / 1000; // Увеличение количества точек

        for (double time = 0; time <= totalTime; time += timeStep) {
            double x = initialSpeed * Math.cos(Math.toRadians(angle)) * time;
            double y = initialSpeed * Math.sin(Math.toRadians(angle)) * time - 0.5 * g * Math.pow(time, 2);
            series.getData().add(new XYChart.Data<>(x, y));
        }

        seriesList.add(series);
        lineChart_id.setData(seriesList);

        // Убрать маркеры точек на графике
        for (XYChart.Series<Number, Number> s : lineChart_id.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                data.getNode().setVisible(false);
            }
        }

        countGraph++;
    }
}