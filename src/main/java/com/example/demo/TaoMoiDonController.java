package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TaoMoiDonController {
    @FXML
    private Label labelTrangChu;
    @FXML
    private TableView<Object> tableDonNhap;
    @FXML
    private Button btnTaoDon;
    @FXML
    private ComboBox<String> comboBoxTinhTrang;
    @FXML
    private ComboBox<String> comboBoxSize;
    @FXML
    private ComboBox<String> comboBoxFruitType;
    @FXML
    private Button btnSave;
    @FXML
    private TextField fruitName;
    @FXML
    private TextField fruitOrigin;
    @FXML
    private TextField fruitQuantity;
    @FXML
    private TextField fruitPriceImport;
    @FXML
    private TextField fruitPriceExport;
    @FXML
    private TextField fruitDVT;

    @FXML
    private TextField labelNgayNhap;

    @FXML
    private void initialize(){
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

        if (comboBoxSize != null) {
            //Set default value
            comboBoxSize.setValue("S");
            comboBoxFruitType.setValue("Loại 1");
            comboBoxTinhTrang.setValue("Chín");
            comboBoxSize.getItems().addAll("S", "M", "L", "XL", "XXL");
            comboBoxFruitType.getItems().addAll("Loại 1", "Loại 2", "Loại 3");
            comboBoxTinhTrang.getItems().addAll("Chín", "Chưa chín", "Sắp chín");
            btnSave.setOnAction(event -> {
                getField();
            });
        } else {
            System.out.println("Null cmnr");
        }
    }

    private void getField() {
        String tenTC = fruitName.getText();
        String xuatXuTC = fruitOrigin.getText();
        String soLuongTC = fruitQuantity.getText();
        String giaNhapTC = fruitPriceImport.getText();
        String giaXuatTC = fruitPriceExport.getText();
        String donViTinh = fruitDVT.getText();
        String tinhTrang = comboBoxTinhTrang.getValue();
        String kichThuoc = comboBoxSize.getValue();
        String loai = comboBoxFruitType.getValue();
        System.out.printf("Tên trái cây: %s, Xuất xứ: %s, Số lượng: %s, Giá nhập: %s, Giá xuất: %s, Tình trạng: %s, Đơn vị tính: %s, Kích thước: %s, Loại: %s%n",
                tenTC, xuatXuTC, soLuongTC, giaNhapTC, giaXuatTC, tinhTrang, donViTinh, kichThuoc, loai);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
