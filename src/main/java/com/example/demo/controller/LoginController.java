package com.example.demo.controller;

import com.example.demo.entity.UserSession;
import com.example.demo.utils.CurrentAccount;
import com.example.demo.utils.ShowAlert;
import com.example.demo.utils.Validator;
import entity.STATUS;
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
import java.sql.Timestamp;
import java.time.Instant;

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
                    /*FIX 28/11/2024*/
//                    Taikhoan login = registryClass.taiKhoan().login(tenDangNhap, matKhau);

                    Taikhoan login = registryClass.taiKhoan().getTaiKhoan(tenDangNhap);
                    System.out.println(login);
                    if (login != null) {

                        /*FIX 15/12/2024*/
                        //Nếu tài khoản hết thời gian bị khóa thì cập nhật lại (tạm thời sử dụng biến login) - chỉ với nhân viên
                        if (login.getStatus() == STATUS.LOCK && login.getLockTime() != null && !login.getUsername().equals("administrator")) {
                            if (Timestamp.from(Instant.now()).after(login.getLockTime())) {
                                // Đã quá thời gian khóa, cập nhật trạng thái về OFF và reset loginAttempt
                                login.setStatus(STATUS.OFF);
                                System.out.println("Flag1");
                                System.out.println(login);
                            }
                        }

                        if(login.getUsername().equals("administrator") && login.getStatus().equals(STATUS.LOCK)){
                            new ShowAlert().showAlert("Thông báo", "Nhập mã khôi phục để mở khóa tài khoản");
                            currentAccount = login;
                            navigateToMainScreen("FormMaKhoiPhuc.fxml", currentAccount);
                            return;
                        }

                        if (!matKhau.equals(login.getPassword())) {
                            if (login.getStatus() == STATUS.OFF) {
                                new ShowAlert().showAlert("Thông báo", "Bạn đã nhập sai mật khẩu. Vui lòng kiểm tra lại");
                                registryClass.taiKhoan().login(tenDangNhap, matKhau);
                            }
                        } else {
                            /*FIX 28/11/2024*/
//                            currentAccount = registryClass.taiKhoan().getTaiKhoan(tenDangNhap);
                            switch (login.getStatus()) {
                                case OFF -> {
                                    if ("administrator".equals(tenDangNhap)) {
                                        currentAccount = registryClass.taiKhoan().login(tenDangNhap, matKhau);
                                        UserSession.setCurrentAccount(currentAccount);
                                        navigateToMainScreen("TrangChu.fxml", currentAccount);
                                    } else {
                                        currentAccount = registryClass.taiKhoan().login(tenDangNhap, matKhau);
                                        UserSession.setCurrentAccount(currentAccount);
                                        navigateToMainScreen("TrangChuNV.fxml", currentAccount);
                                    }
                                }
                                case FIRST -> {
                                    currentAccount = registryClass.taiKhoan().login(tenDangNhap, matKhau);
                                    UserSession.setCurrentAccount(currentAccount);
                                    navigateToMainScreen("DoiMKNV.fxml", currentAccount);
                                }
                                case ON -> {
                                    new ShowAlert().showAlert("Thông báo", "Tài khoản đang đăng nhập ở nơi khác");
                                    System.out.println(login);
                                }
                                case QUIT -> {
                                    new ShowAlert().showAlert("Thông báo", "Bạn đã nghỉ việc. Không thể đăng nhập");
                                }
                                case LOCK -> {
//                                    if(login.getUsername().equals("administrator")){
//                                        new ShowAlert().showAlert("Thông báo", "Nhập mã khôi phục để mở khóa tài khoản");
//                                        currentAccount = login;
//                                        navigateToMainScreen("FormMaKhoiPhuc.fxml", currentAccount);
//                                        return;
//                                    }
                                    new ShowAlert().showAlert("Thông báo", "Tài khoản của bạn đang bị khoá. Mở sau " + login.getLockTime() + "...");
                                }
                            }
                        }
                    } else {
                        new ShowAlert().showAlert("Thông báo", "Tài khoản không tồn tại");
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
//            if (currentAccount.getUsername().equals("administrator")) {
//                HomeAdminController homeAdminController = fxmlLoader.getController();
//                homeAdminController.setCurrentAccount(currentAccount);
//                CurrentAccount.taikhoan = currentAccount;
//            } else {
//                TrangChuNVController trangChuNVController = fxmlLoader.getController();
//                trangChuNVController.setCurrentAccount(currentAccount);
//                CurrentAccount.taikhoan = currentAccount;
//            }

            // Get controller dựa trên màn hình hiện tại
            if ("DoiMKNV.fxml".equals(xmlFile)) {
                // Đổi mật khẩu
                DoiMkController doiMkController = fxmlLoader.getController();
                doiMkController.setCurrentAccount(currentAccount);
                CurrentAccount.taikhoan = currentAccount;
            } else if ("TrangChuNV.fxml".equals(xmlFile)) {
                // Trang chủ nhân viên
                TrangChuNVController trangChuNVController = fxmlLoader.getController();
                trangChuNVController.setCurrentAccount(currentAccount);
                CurrentAccount.taikhoan = currentAccount;
            } else if ("TrangChu.fxml".equals(xmlFile)) {
                // Trang chủ admin
                HomeAdminController homeAdminController = fxmlLoader.getController();
                homeAdminController.setCurrentAccount(currentAccount);
                CurrentAccount.taikhoan = currentAccount;
            } else if ("FormMaKhoiPhuc.fxml".equals(xmlFile)) {
                //Màn hình khôi phục
                KhoiPhucController khoiPhucController = fxmlLoader.getController();
                khoiPhucController.setCurrentAccount(currentAccount);
                CurrentAccount.taikhoan = currentAccount;
            }
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
