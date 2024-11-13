package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label labelNhapHang;

    @FXML
    private void initialize() {
        // Cấu hình sự kiện click cho labelNhapHang
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
        });
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelNhapHang.getScene().getWindow();
            stage.setScene(new Scene(root, 835, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
