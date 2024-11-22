package com.example.demo.controller;

import com.example.demo.entity.TraiCay;
import com.example.demo.utils.ShowAlert;
import entity.Chitietdonnhap;
import entity.Donnhaphang;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.RegistryClass;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChiTietDonController {

    @FXML
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;
    @FXML
    private Button quayLaiButton;
    @FXML
    private TableView<Chitietdonnhap> chiTietDon;
    @FXML
    private TableColumn<TraiCay, Integer> stt;
    @FXML
    private TableColumn<TraiCay, String> fruitName;
    @FXML
    private TableColumn<TraiCay, String> size;
    @FXML
    private TableColumn<TraiCay, String> origin;
    @FXML
    private TableColumn<TraiCay, String> status;
    @FXML
    private TableColumn<TraiCay, String> priceImport;
    @FXML
    private TableColumn<TraiCay, String> priceExport;
    private RegistryClass registryClass;
    {
        try {
            registryClass = new RegistryClass();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {

        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml", 921, 548);
        });
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml", 921, 548);
        });
        // Thêm sự kiện cho nút "Quay lại"
        quayLaiButton.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml", 921, 548);
        });
    }

    public void getDonNhapWithMaDN(String maDN) {
        try {
            // Gọi backend để lấy đơn nhập hàng
            Donnhaphang donnhaphang = registryClass.donNhapHang().getDonNhapHang(maDN);

            if (donnhaphang != null) {
                // Chuyển Set<Chitietdonnhap> sang ObservableList
                ObservableList<Chitietdonnhap> chiTietList = FXCollections.observableArrayList(donnhaphang.getChiTietDonNhapSet());
                chiTietDon.setItems(chiTietList);

                // Liên kết cột với dữ liệu
                stt.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1).asObject());
                fruitName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenTc()));
                size.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));
                origin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getXuatXu()));
                status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTinhTrang()));
                priceImport.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGiaNhap())));
                priceExport.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGiaXuat())));
            } else {
                new ShowAlert().showAlert("Thông báo", "Không tìm thấy đơn nhập hàng!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            new ShowAlert().showAlert("Lỗi", "Không thể lấy dữ liệu từ server.");
        }
    }


    private void loadScene(String fxmlFile, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/"+ fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) quayLaiButton.getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
