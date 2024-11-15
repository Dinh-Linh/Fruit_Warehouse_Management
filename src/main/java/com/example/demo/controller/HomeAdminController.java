package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HomeAdminController {

    @FXML
    private Label labelNhapHang;
    @FXML
    private Button dangXuat;
    private TaiKhoan currentAccount;

    public void setCurrentAccount(TaiKhoan account) {
        this.currentAccount = account;
    }

    @FXML
    private void initialize() {
        // Cấu hình sự kiện click cho labelNhapHang
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
        });
        dangXuat.setOnAction(actionEvent -> {
            handleLogout(actionEvent);
        });
    }
    public void handleLogout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất");
        Optional<ButtonType> results = alert.showAndWait();
        if (currentAccount != null){
            if (results.isPresent() && results.get() == ButtonType.OK){
                System.out.println("Đăng xuất thành công");
                currentAccount.logout();
                System.out.println(currentAccount);
                Platform.exit();
            }
            else {
                System.out.println("Huỷ đăng xuất");
            }
        }
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelNhapHang.getScene().getWindow();
            stage.setScene(new Scene(root, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
