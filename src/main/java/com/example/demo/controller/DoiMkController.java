package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.execute_field.ExecuteChangePassword;
import entity.Taikhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class DoiMkController {
    private Taikhoan currentAccount;
    @FXML
    private Label labelTrangChu;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField reTypeNewPassword;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;

    public void setCurrentAccount(Taikhoan currentAccount) {
        this.currentAccount = currentAccount;
    }

    @FXML
    private void initialize() {
        labelTrangChu.setOnMouseClicked(click -> {
            loadScene("TrangChuNV.fxml");
        });
        confirm.setOnAction(click -> {
            new ExecuteChangePassword().executeButton(newPassword.getText(), reTypeNewPassword.getText());
        });
        cancel.setOnAction(click -> {
            newPassword.setText("");
            reTypeNewPassword.setText("");
        });
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = fxmlLoader.load();
            // Nếu quay lại màn Trang Chủ, đảm bảo truyền lại currentAccount
            if (fxmlFile.equals("TrangChuNV.fxml")) {
                TrangChuNVController controller = fxmlLoader.getController();
                controller.setCurrentAccount(currentAccount); // Truyền currentAccount trở lại
            }
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            stage.setScene(new Scene(root, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
