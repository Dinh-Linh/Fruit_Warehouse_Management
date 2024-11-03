package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {

    @FXML
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;
    @FXML
    private Button btnTaoDonMoi;
    @FXML
    private TableView<Object> tableDonNhap;
    @FXML
    private Button btnTaoDon;

    @FXML
    private void initialize() {

        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml");
        });

        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
        });


//        btnTaoDonMoi.setOnAction(event -> {
//            loadScene("TaoDonMoi.fxml");
//        });
        if (btnTaoDonMoi != null) {
            btnTaoDonMoi.setOnAction(event -> {
                loadScene("TaoDonMoi.fxml");
            });
        } else {
            System.out.println("btnTaoDonMoi is still null.");
        }

        if (btnTaoDon != null) {
            btnTaoDon.setOnAction(event -> {
                if (tableDonNhap != null && !tableDonNhap.getItems().isEmpty()) {
                    showAlert("Tạo đơn thành công", "Đơn đã được tạo thành công.");
                } else {
                    showAlert("Thông báo", "Nhập dữ liệu vào bảng.");
                }
            });
        } else {
            System.out.println("btnTaoDon is still null.");
        }
    }

    private void loadScene(String fxmlFile) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            if(!fxmlFile.equals("TaoDonMoi.fxml")){
                stage.setScene(new Scene(root, 835, 548));
            }
            else{
                stage.setScene(new Scene(root, 886, 550));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}