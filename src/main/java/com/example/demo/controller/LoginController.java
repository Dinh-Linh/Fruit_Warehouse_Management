package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.UserSession;
import com.example.demo.utils.ShowAlert;
import com.example.demo.utils.Validator;
import entity.Taikhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.RegistryClass;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginController {
    static Validator validator = new Validator();
    private RegistryClass registryClass;
    {
        try {
            registryClass = new RegistryClass();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
    private Taikhoan currentAccount;

    @FXML
    private Button btnLogin;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private void initialize() {
        btnLogin.setOnAction(actionEvent -> {
            String tenDangNhap = username.getText();
            String matKhau = password.getText();
            //Check username và password đúng định dạng
            if (validator.checkUsername(tenDangNhap) && validator.checkPassword(matKhau)) {
                try {
                    Boolean login = registryClass.taiKhoan().login(tenDangNhap, matKhau);
                    if (login) {
                        currentAccount = registryClass.taiKhoan().getTaiKhoan(tenDangNhap);
                        UserSession.setCurrentAccount(currentAccount);
                        if ("administrator".equals(tenDangNhap)) {
                            navigateToMainScreen("TrangChu.fxml", currentAccount);
                        } else {
                            navigateToMainScreen("TrangChuNV.fxml", currentAccount);
                        }
                    } else {
                        new ShowAlert().showAlert("Thông báo", "Tên đăng nhập hoặc mật khẩu không chính xác");
                        System.out.println(login);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new ShowAlert().showAlert("Thông báo", "Sai định dạng username và password");
            }
        });
    }

    //Điều hướng màn hình dựa trên STATUS của tài khoản
    public void navigateToMainScreen(String xmlFile, Taikhoan currentAccount) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + xmlFile));
            Parent mainRoot = fxmlLoader.load();
            //Get controller của Home
            if (currentAccount.getUsername().equals("administrator")) {
                HomeAdminController homeAdminController = fxmlLoader.getController();
                homeAdminController.setCurrentAccount(currentAccount);
            } else {
                TrangChuNVController trangChuNVController = fxmlLoader.getController();
                trangChuNVController.setCurrentAccount(currentAccount);
            }
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
