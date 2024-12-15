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

public class KhoiPhucController {
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
    private TextField recoverCode;

    @FXML
    private void initialize() {

    }

    public void setCurrentAccount(Taikhoan currentAccount) {
        this.currentAccount = currentAccount;
    }

    public void clickBtnHuy(){
        navigateToMainScreen("FormDangNhap.fxml", currentAccount);
        System.out.println("Hello");
    }

    public void clickBtnXacNhan() throws RemoteException {
        if(this.registryClass.taiKhoan().checkRecoverCode(recoverCode.getText())){
            navigateToMainScreen("TrangChu.fxml", currentAccount);
        } else {
            new ShowAlert().showAlert("Thất bại", "Sai mã khôi phục");
        }
    }

    //Điều hướng màn hình dựa trên STATUS của tài khoản
    public void navigateToMainScreen(String xmlFile, Taikhoan currentAccount) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + xmlFile));
            Parent mainRoot = fxmlLoader.load();
            if(xmlFile.equals("FormDangNhap.fxml")){
                LoginController loginController = fxmlLoader.getController();
            } else if(xmlFile.equals("TrangChu.fxml")){
                HomeAdminController homeAdminController = fxmlLoader.getController();
                currentAccount.setStatus(STATUS.OFF);
                homeAdminController.setCurrentAccount(currentAccount);
            }
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 921, 548));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
