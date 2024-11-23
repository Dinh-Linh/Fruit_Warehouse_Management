package com.example.demo.controller;

import com.example.demo.entity.DSDonNhap;
import dao.DAODonNhapHang;
import entity.Chitietdonnhap;
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
import util.RegistryClass;

import java.io.IOException;
import java.rmi.NotBoundException;
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
    private TableColumn<Donnhaphang, String> colIdDon;
    @FXML
    private TableColumn<Donnhaphang, Date> colNgayNhap;
    @FXML
    private TableColumn<Donnhaphang, String> colNhaCungCap;
    @FXML
    private TableColumn<Donnhaphang, String> colTrangThai;
    @FXML
    private TableColumn<Donnhaphang, Button> colThongTin;
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
        // Sự kiện khi nhấn vào btnTaoDonMoi để chuyển sang màn hình TaoDonMoi.fxml
        btnTaoDonMoi.setOnMouseClicked(event -> {
            loadScene("TaoDonMoi.fxml");
        });

        // Sự kiện khi nhấn vào labelTrangChu để chuyển đến trang chủ TrangChu.fxml
        labelTrangChu.setOnMouseClicked(event -> {
            loadScene("TrangChu.fxml");
        });

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

    public void loadScene(String xmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + xmlFile));
            Parent mainRoot = fxmlLoader.load();
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 921, 548));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class ButtonCellFactory implements Callback<TableColumn<Donnhaphang, Button>, TableCell<Donnhaphang, Button>> {
        @Override
        public TableCell<Donnhaphang, Button> call(TableColumn<Donnhaphang, Button> param) {
            return new TableCell<Donnhaphang, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Button button = new Button("Chi tiết");
                        button.setId("buttonChiTiet");

                        button.setOnAction(e -> {
                            // Lấy đối tượng DSDonNhap từ dòng hiện tại
                            Donnhaphang dsDonNhap = getTableView().getItems().get(getIndex());

                            // Kiểm tra đối tượng là Donnhaphang, không phải Chitietdonnhap hay Traicay
                            if (dsDonNhap instanceof Donnhaphang) {
                                String maDN = dsDonNhap.getMaDn();  // Lấy mã đơn nhập
                                System.out.println(dsDonNhap.getMaDn());

                                try {
                                    // Chuyển màn hình chi tiết đơn nhập với mã đơn nhập
                                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ChiTietDon.fxml"));
                                    Parent root = loader.load();

                                    // Truyền maDN tới controller của ChiTietDon.fxml
                                    ChiTietDonController chiTietDonController = loader.getController();
                                    chiTietDonController.getDonNhapWithMaDN(maDN);  // Gọi phương thức với maDN

                                    stage.setScene(new Scene(root));
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
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
            List<Donnhaphang> donnhaphangList = registryClass.donNhapHang().getAllDonnhaphang();
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
