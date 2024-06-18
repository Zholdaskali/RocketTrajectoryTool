package com.example.practice.util;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class DataWindow {

    public static void display(List<Double> times, List<Double> heights, List<Double> velocities, List<Double> distances) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Подробная информация");
        window.setWidth(600);
        window.setHeight(300);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        StringBuilder data = new StringBuilder();
        for (int j = 0; j < times.size(); j++) {
            data.append(String.format(j+1 + "  " +"Time: %.2f s, Height: %.2f m, Velocity: %.2f m/s, Distance: %.2f m%n",
                    times.get(j), heights.get(j), velocities.get(j), distances.get(j)));
        }
        textArea.setText(data.toString());

        VBox layout = new VBox(textArea);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

