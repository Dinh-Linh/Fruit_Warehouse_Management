package com.example.demo.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrangChu.fxml"));
        Parent root = fxmlLoader.load();

        FXMLLoader donNhapLoader = new FXMLLoader(getClass().getResource("DonNhap.fxml"));
        Parent donNhapRoot = donNhapLoader.load();

        FXMLLoader taoDonMoiLoader = new FXMLLoader(getClass().getResource("TaoDonMoi.fxml"));
        Parent taoDonMoiRoot = taoDonMoiLoader.load();

        FXMLLoader chiTietDonLoader = new FXMLLoader(getClass().getResource("ChiTietDon.fxml"));
        Parent chiTietDonRoot = chiTietDonLoader.load();

        Scene scene = new Scene(root, 835, 548);
        scene.getStylesheets().add(getClass().getResource("styleTrangChu.css").toExternalForm());
        stage.setTitle("Quản lý kho hàng trái cây");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
