package com.example.practice.controller;

import com.example.practice.util.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    static String mainView = "/com/example/practice/view/default-flight-view.fxml";
    static String mainGraph = "/com/example/practice/view/rocket-flight-view.fxml";
    static String tempView = "/com/example/practice/view/docs-view.fxml";
    static String viewReal;

    @FXML
    private BorderPane border_id;

    @FXML
    private Button btMainView_id;

    @FXML
    private Button btTemp_id;

    @FXML
    private Button startParameterView_id;

    private static boolean confirmed = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView(mainView);
    }

    @FXML
    void showMainView(ActionEvent event) {
        checkAndLoadView(mainView);
    }

    @FXML
    void showStartParameterView(ActionEvent event) {
        checkAndLoadView(mainGraph);
    }

    @FXML
    void showTempView(ActionEvent event) {
        checkAndLoadView(tempView);
    }

    private void checkAndLoadView(String fxmlPath) {
        if (!Objects.equals(viewReal, fxmlPath)) {
            if (confirmed || viewReal == null) {
                loadView(fxmlPath);
            } else {
                boolean userConfirmed = ErrorHandler.showWarning("Все данные этой страницы будут утеряны. Продолжить?");
                if (userConfirmed) {
                    confirmed = true;
                    loadView(fxmlPath);
                }
            }
        }
    }

    private void loadView(String fxmlPath) {
        if (!Objects.equals(viewReal, fxmlPath)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                border_id.setCenter(root);
                viewReal = fxmlPath;
                confirmed = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
