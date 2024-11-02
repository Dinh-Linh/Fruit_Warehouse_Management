package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TrangChu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 835, 548);
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
