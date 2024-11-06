package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;

    @FXML
    private void initialize() {
        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml");
        });

        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
        });
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