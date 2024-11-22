package com.example.demo.controller;

import com.example.demo.entity.TaiKhoan;
import entity.Taikhoan;
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
import util.RegistryClass;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;

public class TrangChuNVController {
    private Taikhoan currentAccount;
    @FXML
    private Label labelTrangChu;
    @FXML
    private Button doiMk;
    @FXML
    private Button logout;
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

    @FXML
    public void initialize(){
        doiMk.setOnMouseClicked(event -> {
            loadScene("DoiMKNV.fxml", currentAccount);
        });
        logout.setOnAction(actionEvent -> {
            try {
                handleLogout(actionEvent);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setCurrentAccount(Taikhoan account) {
        this.currentAccount = account;
    }
    public void handleLogout(ActionEvent event) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất");
        Optional<ButtonType> results = alert.showAndWait();
        if (currentAccount != null){
            if (results.isPresent() && results.get() == ButtonType.OK){
                System.out.println("Đăng xuất thành công");
                registryClass.taiKhoan().logout(currentAccount.getUsername());
                System.out.println(currentAccount);
                Platform.exit();
            }
            else {
                System.out.println("Huỷ đăng xuất");
            }
        }
    }

    private void loadScene(String fxmlFile, Taikhoan account) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = fxmlLoader.load();
            DoiMkController doiMkController = fxmlLoader.getController();
            doiMkController.setCurrentAccount(account);
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            stage.setScene(new Scene(root, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
