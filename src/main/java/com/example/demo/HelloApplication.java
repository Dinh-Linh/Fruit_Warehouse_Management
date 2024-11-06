package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Tải file TrangChu.fxml và thiết lập màn hình chính
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrangChu.fxml"));
        Parent root = fxmlLoader.load();

        // Tải các file FXML khác cho các màn hình khác nhau
        FXMLLoader donNhapLoader = new FXMLLoader(getClass().getResource("DonNhap.fxml"));
        Parent donNhapRoot = donNhapLoader.load();

        FXMLLoader taoDonMoiLoader = new FXMLLoader(getClass().getResource("TaoDonMoi.fxml"));
        Parent taoDonMoiRoot = taoDonMoiLoader.load();

        // Tạo scene cho các màn hình khác và thiết lập các thuộc tính cho stage
        Scene scene = new Scene(root, 835, 548);
        Scene donNhapScene = new Scene(donNhapRoot);
        stage.setScene(donNhapScene);

        Scene taoDonMoiScene = new Scene(taoDonMoiRoot);
        stage.setScene(taoDonMoiScene);

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


