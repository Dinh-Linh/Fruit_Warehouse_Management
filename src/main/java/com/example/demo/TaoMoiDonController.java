package com.example.demo;

import com.example.demo.entity.TraiCay;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TextField supplier;

    //Danh sách cột trong bảng
    @FXML
    private TableColumn soTT;
    @FXML
    private TableColumn clMaTc;
    @FXML
    private TableColumn clTenTc;
    @FXML
    private TableColumn clKichThuocTc;
    @FXML
    private TableColumn clTinhTrangTc;

    @FXML
    private void initialize() {

        //Set default value for ComboBox
        comboBoxSize.setValue("S");
        comboBoxFruitType.setValue("Loại 1");
        comboBoxTinhTrang.setValue("Chín");

        //Set value Fruit Field for ComboBox
        comboBoxSize.getItems().addAll("S", "M", "L", "XL", "XXL");
        comboBoxFruitType.getItems().addAll("Loại 1", "Loại 2", "Loại 3");
        comboBoxTinhTrang.getItems().addAll("Chín", "Chưa chín", "Sắp chín");

        if (btnTaoDon != null) {
            btnTaoDon.setOnAction(event -> {
                if (supplier.getText().isBlank() && !tableDonNhap.getItems().isEmpty()) {
                    showAlert("Thông báo", "Vui lòng chọn nhà cung cấp");
                }
                else if(tableDonNhap.getItems().isEmpty() && !supplier.getText().isBlank()){
                    showAlert("Thông báo", "Chưa co trái cây được chọn");
                }
                else if(supplier.getText().isBlank() && tableDonNhap.getItems().isEmpty()){
                    showAlert("Thông báo", "Nhập đầy đủ thông tin");
                }
                else {
                    showAlert("Tạo đơn thành công", "Đơn đã được tạo thành công.");
                }
            });
        } else {
            System.out.println("btnTaoDon is still null.");
        }

        clMaTc.setCellValueFactory(new PropertyValueFactory<>("maTc"));
        clTenTc.setCellValueFactory(new PropertyValueFactory<>("tenTc"));
        clKichThuocTc.setCellValueFactory(new PropertyValueFactory<>("size"));
        clTinhTrangTc.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));

        //Set fruit value into table
        tableDonNhap.setItems(FXCollections.observableArrayList());

        //Handle button Save fruit into Table
        if (btnSave != null) {
            btnSave.setOnAction(event -> {
                if (!checkField()){
                    showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin trái cây");
                }
                else {
                    getField();
                    handleSaveAction();
                }
            });
        } else {
            System.out.println("Không tồn tại btnSave");
        }
    }

    private Boolean checkField() {
        if (fruitName.getText().isBlank() || fruitOrigin.getText().isBlank() || fruitQuantity.getText().isBlank() || fruitPriceImport.getText().isBlank() || fruitPriceExport.getText().isBlank() || fruitDVT.getText().isBlank()) {
            return false;
        }
        else {
            return true;
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

    private void handleSaveAction(){
        if (!checkField()){
            showAlert("Tông báo", "Vui lòng nhập đầy đu thông tin trái cây");
        }
        String maTC = "TC" + (tableDonNhap.getItems().size()+1);
        String tenTC = fruitName.getText();
        String tinhTrang = comboBoxTinhTrang.getValue();
        String kichThuoc = comboBoxSize.getValue();
        TraiCay fruit = new TraiCay(maTC, tenTC, kichThuoc, tinhTrang);
        tableDonNhap.getItems().add(fruit);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
