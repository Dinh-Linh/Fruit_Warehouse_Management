package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DonNhapController {
    @FXML
    private Label labelTrangChu;
    @FXML
    private Button btnTaoDonMoi;

    @FXML
    private void initialize() {
        if (btnTaoDonMoi != null) {
            btnTaoDonMoi.setOnAction(event -> {
                loadScene("TaoDonMoi.fxml");
            });
        } else {
            System.out.println("btnTaoDonMoi is still null.");
        }
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            if (!fxmlFile.equals("TaoDonMoi.fxml")) {
                stage.setScene(new Scene(root, 835, 548));
            } else {
                stage.setScene(new Scene(root, 886, 550));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
