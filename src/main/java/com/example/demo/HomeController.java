package com.example.demo;

import com.example.demo.entity.DSDonNhap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class HomeController {

    @FXML
    private Label labelNhapHang;

    @FXML
    private ComboBox<String> comboBoxLoaiTraiCay;

    @FXML
    private TableView<DSDonNhap> tableDSDonNhap;

    @FXML
    private TableColumn<DSDonNhap, String> colIdDon;
    @FXML
    private TableColumn<DSDonNhap, Date> colNgayNhap;
    @FXML
    private TableColumn<DSDonNhap, String> colNhaCungCap;
    @FXML
    private TableColumn<DSDonNhap, String> colTrangThai;
    @FXML
    private TableColumn<DSDonNhap, Button> colThongTin;

    @FXML
    private void initialize() {
        // Thêm các loại trái cây phổ biến vào ComboBox
        ObservableList<String> options = FXCollections.observableArrayList(
                "Loại 1", "Loại 2", "Loại 3"
        );
        comboBoxLoaiTraiCay.setItems(options);

        // Cấu hình sự kiện click cho labelNhapHang
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
        });

        // Cấu hình các cột cho TableView
        colIdDon.setCellValueFactory(new PropertyValueFactory<>("idDon"));
        colNgayNhap.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory<>("nhaCungCap"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        // Thiết lập cột "Thông tin" để chứa Button
        colThongTin.setCellFactory(new ButtonCellFactory());

        // Dữ liệu mẫu vào TableView
        ObservableList<DSDonNhap> data = FXCollections.observableArrayList(
                new DSDonNhap("1", new Date(), "NCC A", "Đã nhập"),
                new DSDonNhap("2", new Date(), "NCC B", "Chưa nhập"),
                new DSDonNhap("3", new Date(), "NCC C", "Đã nhập"),
                new DSDonNhap("4", new Date(), "NCC D", "Chưa nhập"),
                new DSDonNhap("5", new Date(), "NCC E", "Đã nhập")
        );

        tableDSDonNhap.setItems(data);  // Điền dữ liệu vào TableView
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelNhapHang.getScene().getWindow();
            stage.setScene(new Scene(root, 835, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ButtonCellFactory implements Callback<TableColumn<DSDonNhap, Button>, TableCell<DSDonNhap, Button>> {
        @Override
        public TableCell<DSDonNhap, Button> call(TableColumn<DSDonNhap, Button> param) {
            return new TableCell<DSDonNhap, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Button button = new Button("Chi tiết");
                        button.setId("buttonChiTiet");  // Thiết lập ID cho Button
                        button.setOnAction(e -> {
                            // Gọi phương thức chuyển trang
                            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChiTietDon.fxml"));
                            try {
                                Parent root = loader.load();
                                stage.setScene(new Scene(root));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
                        setGraphic(button);
                    } else {
                        setGraphic(null);
                    }
                }
            };
        }
    }

}
