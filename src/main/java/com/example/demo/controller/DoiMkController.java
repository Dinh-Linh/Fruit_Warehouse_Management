package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.execute_field.ExecuteChangePassword;
import com.example.demo.utils.CurrentAccount;
import com.example.demo.utils.ShowAlert;
import com.example.demo.utils.Validator;
import entity.Taikhoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.RegistryClass;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class DoiMkController {
    private RegistryClass registryClass;

    {
        try {
            registryClass = new RegistryClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    private Button logout;

    public void setCurrentAccount(Taikhoan currentAccount) {
        this.currentAccount = currentAccount;
    }

    @FXML
    private void initialize() {
        labelTrangChu.setOnMouseClicked(click -> {
            loadScene("TrangChuNV.fxml");
        });
        //Button Change Password
        confirm.setOnAction(click -> {
            if (!new Validator().checkPassword(newPassword.getText())) {
                System.out.println("Mật khẩu không đúng định dạng");
                new ShowAlert().showAlert("Thông báo", "Mật khẩu không đúng định dạng");
            } else {
                if (newPassword.getText().equals(reTypeNewPassword.getText())) {
                    executeChangePassword(currentAccount.getUsername(), newPassword.getText());
                } else {
                    new ShowAlert().showAlert("Thông báo", "Không trùng khớp. Hãy kiểm tra lại");
                }
            }
        });
        cancel.setOnAction(click -> {
            newPassword.setText("");
            reTypeNewPassword.setText("");
        });
        logout.setOnAction(click -> {
            try {
                registryClass.taiKhoan().logout(currentAccount.getUsername());
                loadScene("FormDangNhap.fxml");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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

    public void executeChangePassword(String username, String newPassword) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Xác nhận đổi mật khẩu");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.isPresent() && results.get() == ButtonType.OK) {
            try {
                boolean isSuccess = registryClass.taiKhoan().changePassword(username, newPassword);
                if (isSuccess) {
                    System.out.println("Đổi mật khẩu thành công");
                    new ShowAlert().showAlert("Thông báo", "Đối mật khẩu thành công");
                } else {
                    new ShowAlert().showAlert("Thông báo", "Đổi mật khẩu thất bại");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            alert.close();
        }
    }
}
