package com.example.practice.controller;

import com.example.practice.calculate.RocketFlightCalculator;
import com.example.practice.format.FormatDouble;
import com.example.practice.generator.PDFGenerator;
import com.example.practice.model.*;
import com.example.practice.util.DataWindow;
import com.example.practice.util.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.example.practice.calculate.RocketFlightCalculator.*;
import static com.example.practice.format.FormatDocs.*;

public class RocketFlightController {

    @FXML
    private TextField angle2_id;

    @FXML
    private Button btClear_id;

    @FXML
    private Button btMain_id;

    @FXML
    private Button btSave_id;

    @FXML
    private TextField d1_id;

    @FXML
    private TextField ds1_id;

    @FXML
    private TextField m0_id;

    @FXML
    private TextField m10_id;

    @FXML
    private TextField m1t_id;

    @FXML
    private LineChart<Number, Number> main_lineChart_id;

    @FXML
    private TextField mr1_id;

    @FXML
    private TextField n_id;

    @FXML
    private TextField pa1_id;

    @FXML
    private TextField wa1_id;

    @FXML
    private TextField Cn_id;

    @FXML
    private TextField Cx_id;

    @FXML
    private TextField Cy_id;

    private ObservableList<XYChart.Series<Number, Number>> seriesList1;
    private final PDFGenerator pdfGenerator = new PDFGenerator();
    private double resultH, resultDistance, angle;
    private static int mainCountGraph = 1;
    private int N;
    private static boolean confirmed = false;

//    public static ArrayList<Double> resultDistanceArray = new ArrayList<>();
//    public static ArrayList<Double> resultHArray = new ArrayList<>();
    //    public static ArrayList<Double> heightsResult = new ArrayList<>();
//    public static ArrayList<Double> velocitiesResult = new ArrayList<>();
//    public static ArrayList<Double> distancesResult = new ArrayList<>();
    //    private double angle1;

    @FXML
    public void initialize() {
        seriesList1 = FXCollections.observableArrayList();
    }

    @FXML
    void handleButtonClick(ActionEvent event) {
        if (mainCountGraph < 4) {
            try {
                double angle1 = Double.parseDouble(angle2_id.getText());
                if (angle1 > 90){
                    ErrorHandler.showError("Угол не может быть больше 90 градуссов");
                } else {
                    double m0 = 140;
                    double m1t = 110;
                    double mr1 = 16.67;
                    double wa1 = 1400;
                    double D1 = 0.2;
                    double Ds1 = 0.18;
                    double Pa1 = 5000;
                    int n = Integer.parseInt(n_id.getText());
                    double Cn = 0.2;
                    double Cx = 0.126;
                    double Cy = 0.126;
                    N = n;
                    angle = angle1;
                    Stages stage = new Stages(m0, m0, m1t, mr1, wa1, D1, Ds1, Pa1);
                    Coefficients coefficients = new Coefficients(Cx, Cy, Cn);
                    updateChart(angle1, stage, n, coefficients);
                    mainCountGraph++;
                }
            } catch (NumberFormatException e) {
                mainCountGraph = 1;
                ErrorHandler.showError("Проверьте валидность вводимые данные");
            }
        }
        else {
            ErrorHandler.showError("количество превышает допустимое");
        }
    }

    @FXML
    void handleClearButtonClick(ActionEvent event) {
        angle2_id.clear();
        d1_id.clear();
        ds1_id.clear();
        m0_id.clear();
        m10_id.clear();
        m1t_id.clear();
        mr1_id.clear();
        n_id.clear();
        pa1_id.clear();
        wa1_id.clear();
        Cn_id.clear();
        Cx_id.clear();
        Cy_id.clear();
        seriesList1.clear();
//        resultDistanceArray.clear();
//        resultHArray.clear();
        mainCountGraph = 1;
    }

    @FXML
    void handleSaveButtonClick(ActionEvent event) {
        if (mainCountGraph > 1) {
            boolean userConfirmed = ErrorHandler.showWarning("Сохраняется только последний результат. Продолжить?");
            if (userConfirmed) {
                confirmed = true;
                File file = showSaveFileDialog();
                if (file != null) {
//            (String content, String maxHText, double maxH, String distanceText, double maxDistance, String angleText, double angle, String filePath)
                    pdfGenerator.generatePDF(strDocs, strH, resultH, strDistance,resultDistance, strAngle, angle, file.getAbsolutePath());
                    ErrorHandler.showInfo("Успешно сохранено");
                }
            }
        }
        else {
            ErrorHandler.showError("У вас пока нет расчетов");
        }
    }

    private File showSaveFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить PDF файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        return fileChooser.showSaveDialog(new Stage());
    }


    private void updateChart(double angle1, Stages stage, int n, Coefficients coefficients) {
        RocketFlightCalculator.rocketFlightSimulation(angle1, stage, n, coefficients);
        // Очищаем предыдущие данные
//        main_lineChart_id.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        series.setName("Flight Path " + mainCountGraph + ";\n"
                + "angle = " + angle1 + ";\n"
                + "distance = " + FormatDouble.format(distances.get(distances.size() - 1)) + ";\n"
                + "max h = " + FormatDouble.format(maxH) + ";");

        for (int i = 0; i < N; i++) {
            double y = heights.get(i);
            double x = distances.get(i);
            series.getData().add(new XYChart.Data<>(x, y));
        }

        seriesList1.add(series);
        main_lineChart_id.setData(seriesList1);

        for (XYChart.Series<Number, Number> s : main_lineChart_id.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                data.getNode().setVisible(false);
            }
        }
        DataWindow.display(times, heights, velocities, distances);
        resultH = maxH;
        resultDistance = distances.get(distances.size() - 1);
        heights.clear();
        distances.clear();
        velocities.clear();
        times.clear();
        maxH = 0;
    }
}
