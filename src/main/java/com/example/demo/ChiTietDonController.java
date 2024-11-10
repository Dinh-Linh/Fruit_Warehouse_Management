package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ChiTietDonController {

    @FXML
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;
    @FXML
    private Button quayLaiButton;

    @FXML
    private void initialize() {

        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 835, 548);
        });
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml", 835, 548);
        });
        // Thêm sự kiện cho nút "Quay lại"
        quayLaiButton.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 835, 548);
        });
    }

    private void loadScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) quayLaiButton.getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
