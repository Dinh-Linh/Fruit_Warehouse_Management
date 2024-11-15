package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.utils.ShowAlert;
import com.example.demo.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginController {
    static Validator validator = new Validator();

    @FXML
    private Button btnLogin;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private static List<TaiKhoan> taiKhoanList = new ArrayList<>();
    private TaiKhoan currentAccount;
    static Scanner sc = new Scanner(System.in);

    @FXML
    private void initialize() {
        // Tạo một số tài khoản giả định với dữ liệu ngẫu nhiên
        taiKhoanList.add(new TaiKhoan("nguoidung1", "User@123456789", "ON", Time.valueOf("00:00:00"), Date.valueOf("2024-01-01"), Date.valueOf("2025-01-01")));
        taiKhoanList.add(new TaiKhoan("nguoidung2", "User@123456789", "OFF", Time.valueOf("00:00:00"), Date.valueOf("2024-02-01"), Date.valueOf("2025-02-01")));
        taiKhoanList.add(new TaiKhoan("nguoidung3", "User@123456789", "LOCK", Time.valueOf("00:00:00"), Date.valueOf("2024-03-01"), Date.valueOf("2025-03-01")));
        taiKhoanList.add(new TaiKhoan("nguoidung4", "User@123456789", "FIRST", Time.valueOf("00:00:00"), Date.valueOf("2024-04-01"), Date.valueOf("2025-04-01")));
        taiKhoanList.add(new TaiKhoan("nguoidung5", "User@123456789", "QUIT", Time.valueOf("00:00:00"), Date.valueOf("2024-05-01"), Date.valueOf("2025-05-01")));
        taiKhoanList.add(new TaiKhoan("administrator", "Administrator@123", "OFF", Time.valueOf("00:00:00"), Date.valueOf("2024-05-01"), Date.valueOf("2025-05-01")));
        btnLogin.setOnAction(actionEvent -> {
            String tenDangNhap = username.getText();
            String matKhau = password.getText();
            TaiKhoan tk = TaiKhoan.findAccount(taiKhoanList, tenDangNhap);

            //Check username và password đúng định dạng
            if (validator.checkUsername(tenDangNhap) && validator.checkPassword(matKhau)) {
                if (tk != null) {
                    if (tk.checkLoginWithStatus(tenDangNhap, matKhau)) {
                        //Nếu tên tk là administrator thì chuyeenr đến trang chủ của admi
                        if ("administrator".equals(tenDangNhap)) {
                            navigateToMainScreen("TrangChu.fxml", tk);
                            System.out.println(tk);
                        } else {
                            //Ngược lại trên
                            navigateToMainScreen("TrangChuNV.fxml", tk);
                        }
                    } else {
                        new ShowAlert().showAlert("Thông báo", "Tên đăng nhập hoặc mật khẩu không chính xác");
                    }
                } else {
                    System.out.println("Không tồn tại tài khoản");
                    new ShowAlert().showAlert("Thông báo", "Không tồn tại tài khoản");
                }
            } else {
                new ShowAlert().showAlert("Thông báo", "Sai định dạng username và password");
            }
        });
    }

    //Điều hướng màn hình dựa trên STATUS của tài khoản


    public void navigateToMainScreen(String xmlFile, TaiKhoan account) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + xmlFile));
            Parent mainRoot = fxmlLoader.load();
            //Get controller của Home
            if (account.getUsername().equals("administrator")){
                HomeAdminController homeAdminController = fxmlLoader.getController();
                homeAdminController.setCurrentAccount(account);
            }
            else {
                TrangChuNVController trangChuNVController = fxmlLoader.getController();
                trangChuNVController.setCurrentAccount(account);
            }
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
