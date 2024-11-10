package com.example.demo;

import com.example.demo.entity.TraiCay;
import com.example.demo.utils.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaoDonMoiController {
    Validator validator = new Validator();

    @FXML
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;

    @FXML
    private TableView<TraiCay> tableDonNhap;  // Sửa kiểu dữ liệu từ Object thành TraiCay

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

    @FXML
    private TextField importDate;

    @FXML
    private Label total;

    //Danh sách cột trong bảng
    @FXML
    private TableColumn<TraiCay, String> soTT;
    @FXML
    private TableColumn<TraiCay, String> clMaTc;
    @FXML
    private TableColumn<TraiCay, String> clTenTc;
    @FXML
    private TableColumn<TraiCay, String> clKichThuocTc;
    @FXML
    private TableColumn<TraiCay, String> clTinhTrangTc;

    private List<TraiCay> traiCayList;

    @FXML
    private void initialize() {

        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 835, 548);
        });
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml", 835, 548);
        });


        //Set default value for ComboBox
        comboBoxSize.setValue("S");
        comboBoxFruitType.setValue("Loại 1");
        comboBoxTinhTrang.setValue("Chín");

        //Set value Fruit Field for ComboBox
        comboBoxSize.getItems().addAll("S", "M", "L", "XL", "XXL");
        comboBoxFruitType.getItems().addAll("Loại 1", "Loại 2", "Loại 3");
        comboBoxTinhTrang.getItems().addAll("Chín", "Chưa chín", "Sắp chín");
        importDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));

        if (btnTaoDon != null) {
            btnTaoDon.setOnAction(event -> {
                if (supplier.getText().isBlank() && !tableDonNhap.getItems().isEmpty()) {
                    showAlert("Thông báo", "Vui lòng chọn nhà cung cấp");
                } else if (tableDonNhap.getItems().isEmpty() && !supplier.getText().isBlank()) {
                    showAlert("Thông báo", "Chưa có trái cây được chọn");
                } else if (supplier.getText().isBlank() && tableDonNhap.getItems().isEmpty()) {
                    showAlert("Thông báo", "Nhập đầy đủ thông tin");
                } else {
                    showAlert("Tạo đơn thành công", "Đơn đã được tạo thành công.");
                }
            });
        } else {
            System.out.println("btnTaoDon is still null.");
        }

        // Set value of column soTT
        soTT.setCellFactory(col -> new TableCell<TraiCay, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        // Set value of other columns
        clMaTc.setCellValueFactory(new PropertyValueFactory<>("maTc"));
        clTenTc.setCellValueFactory(new PropertyValueFactory<>("tenTc"));
        clKichThuocTc.setCellValueFactory(new PropertyValueFactory<>("size"));
        clTinhTrangTc.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));

        // Set fruit value into table
        tableDonNhap.setItems(FXCollections.observableArrayList());

        // Handle button Save fruit into Table
        if (btnSave != null) {
            btnSave.setOnAction(event -> {
                if (!checkField()) {
                    showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin trái cây");
                } else {
                    getField();
                    handleSaveAction();
                }
            });
        } else {
            System.out.println("Không tồn tại btnSave");
        }
    }

    private void loadScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean checkField() {
        if (fruitName.getText().isBlank() || fruitOrigin.getText().isBlank() || fruitQuantity.getText().isBlank() || fruitPriceImport.getText().isBlank() || fruitPriceExport.getText().isBlank() || fruitDVT.getText().isBlank()) {
            return false;
        } else {
            if (!validator.isValidNumber(fruitPriceImport.getText())) {
                showAlert("Thông báo", "Giá nhập không hợp lệ");
                return false;
            } else if (!validator.isValidNumber(fruitPriceExport.getText())) {
                showAlert("Thông báo", "Giá xuất không hợp lệ");
                return false;
            } else if (!validator.isValidNumber(fruitQuantity.getText())) {
                showAlert("Thông báo", "Số lượng không hợp lệ");
                return false;
            } else return true;
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

    private void handleSaveAction() {
        if (!checkField()) {
            showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin trái cây");
        }
        String maTC = "TC" + (tableDonNhap.getItems().size() + 1);
        String tenTC = fruitName.getText();
        String tinhTrang = comboBoxTinhTrang.getValue();
        String kichThuoc = comboBoxSize.getValue();
        TraiCay fruit = new TraiCay(maTC, tenTC, kichThuoc, tinhTrang);

        // Set Fruit into Table
        tableDonNhap.getItems().add(fruit);
        clearDataField();
    }

    private void clearDataField() {
        // Xóa nội dung nhập sau khi lưu thành công
        fruitName.clear();
        fruitOrigin.clear();
        fruitQuantity.clear();
        fruitPriceImport.clear();
        fruitPriceExport.clear();
        fruitDVT.clear();
        comboBoxSize.setValue("S");
        comboBoxTinhTrang.setValue("Chín");
        comboBoxFruitType.setValue("Loại 1");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
