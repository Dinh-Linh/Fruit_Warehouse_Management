package com.example.demo.controller;

import com.example.demo.entity.DSDonNhap;
import dao.DAODonNhapHang;
import dao.DAODonNhapHangImpl;
import entity.Donnhaphang;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonNhapController {

    @FXML
    private Button btnTaoDonMoi;


    @FXML
    private Label labelTrangChu;
    @FXML
    private ComboBox<String> comboBoxLoaiTraiCay;
    @FXML
    private TableView<Donnhaphang> tableDSDonNhap;

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
    private DAODonNhapHang dnhService = new DAODonNhapHangImpl();

    public DonNhapController() throws RemoteException {
    }

    @FXML
    private void initialize() {
        // Sự kiện khi nhấn vào btnTaoDonMoi để chuyển sang màn hình TaoDonMoi.fxml
        btnTaoDonMoi.setOnMouseClicked(event -> {
            loadScene("TaoDonMoi.fxml", 886, 550);
        });

        // Sự kiện khi nhấn vào labelTrangChu để chuyển đến trang chủ TrangChu.fxml
        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 835, 548);
        });
        // Dữ liệu mẫu vào TableView
        ObservableList<DSDonNhap> data = FXCollections.observableArrayList(
                new DSDonNhap("1", new Date(), "NCC A", "Đã nhập"),
                new DSDonNhap("2", new Date(), "NCC B", "Chưa nhập"),
                new DSDonNhap("3", new Date(), "NCC C", "Đã nhập"),
                new DSDonNhap("4", new Date(), "NCC D", "Chưa nhập"),
                new DSDonNhap("5", new Date(), "NCC E", "Đã nhập")
        );

//        tableDSDonNhap.setItems(data);
        setUpTableCol();
        setUpComboBox();
        loadDonNhapData();
    }

    private void setUpTableCol() {
        // Cấu hình các cột cho TableView
        colIdDon.setCellValueFactory(new PropertyValueFactory<>("maDn"));
        colNgayNhap.setCellValueFactory(new PropertyValueFactory<>("ngayTaoDon"));
        colNhaCungCap.setCellValueFactory(new PropertyValueFactory<>("tenNhaCungCap"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));

        // Thiết lập cột "Thông tin" để chứa Button
        colThongTin.setCellFactory(new DonNhapController.ButtonCellFactory());
    }

    private void setUpComboBox() {
        // Thêm các loại trái cây phổ biến vào ComboBox
        ObservableList<String> options = FXCollections.observableArrayList(
                "Loại 1", "Loại 2", "Loại 3"
        );
        comboBoxLoaiTraiCay.setItems(options);
    }

    private void loadScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btnTaoDonMoi.getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
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

    private void loadDonNhapData() {
        try {
            List<Donnhaphang> donnhaphangList = dnhService.getAllDonnhaphang();
            if (donnhaphangList == null){
                donnhaphangList = new ArrayList<>();
            }
            ObservableList<Donnhaphang> donnhaphangs = FXCollections.observableArrayList(donnhaphangList);
            tableDSDonNhap.setItems(donnhaphangs);
            if (donnhaphangs.size() == 0) {
                System.out.println("Danh sách đơn nhập hàng trống");
            } else System.out.println(donnhaphangs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
