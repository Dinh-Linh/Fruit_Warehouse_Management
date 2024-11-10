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
    private Button btnTaoDonMoi;


    @FXML
    private Label labelTrangChu;

    @FXML
    private void initialize() {
        // Sự kiện khi nhấn vào btnTaoDonMoi để chuyển sang màn hình TaoDonMoi.fxml
        btnTaoDonMoi.setOnMouseClicked(event -> {
            loadScene("TaoDonMoi.fxml", 886, 550);
        });

        // Sự kiện khi nhấn vào labelTrangChu để chuyển đến trang chủ TrangChu.fxml
        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 835, 548);
        });
    }

    private void loadScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btnTaoDonMoi.getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
