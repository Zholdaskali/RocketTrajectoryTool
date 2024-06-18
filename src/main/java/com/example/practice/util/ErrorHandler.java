package com.example.practice.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorHandler {

    public static void showError(String message) {
        showAlert("Ошибка", message, AlertType.ERROR);
    }

    public static void showInfo(String message) {
        showAlert("Информация", message, AlertType.INFORMATION);
    }

    public static boolean showWarning(String message) {
        return showConfirmationAlert(message);
    }

    private static void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}


