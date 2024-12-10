package com.example.demo;

import com.example.demo.entity.UserSession;
import entity.Taikhoan;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.RegistryClass;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloApplication extends Application {
    private RegistryClass registryClass;

    @Override
    public void init() {
        try {
            registryClass = new RegistryClass();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FormDangNhap.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 860, 548);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        stage.setTitle("Quản lý kho hàng trái cây");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Taikhoan currentAccount = UserSession.getCurrentAccount();
                registryClass.taiKhoan().logout(currentAccount.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    public static void main(String[] args) {
        launch();
    }
}
