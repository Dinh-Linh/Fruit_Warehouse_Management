package com.example.demo.controller;

import com.example.demo.entity.DSDonNhap;
import com.example.demo.entity.UserSession;
import com.example.demo.utils.CurrentAccount;
import dao.DAODonNhapHang;
import entity.Chitietdonnhap;
import entity.Donnhaphang;
import entity.Loaitraicay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Optional;

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
    @FXML
    private Label truocKhiNhap;
    @FXML
    private Label sauKhiNhap;
    @FXML
    private Button dangXuat;
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
        comboBoxLoaiTraiCay.setValue("Na Bồ Lý");
        updateWarehouseStatus(comboBoxLoaiTraiCay.getValue());
        comboBoxLoaiTraiCay.setOnAction(event -> {
            String selectedLoai = comboBoxLoaiTraiCay.getValue();
            if (selectedLoai != null){
                updateWarehouseStatus(selectedLoai);
            }
        });
        dangXuat.setOnAction(click ->{
            try {
                handleLogout(click);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

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
                "Loại 1", "Loại 2", "Loại 3", "Na Bồ Lý"
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

    // Phương thức đổi màu theo trạng thái
    private void setLabelColor(Label label, float ratio) {
        if (ratio < 2) {
            label.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        } else if (ratio >= 2 && ratio <= 20) {
            label.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        } else {
            label.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
    }

    private void updateWarehouseStatus(String tenLoaiTc){
        try {
            String maLoaiTc = "";
            List<Loaitraicay> loaitraicayList = registryClass.loaiTraiCay().getLoaiTraiCayList();
            for (Loaitraicay loaitraicay : loaitraicayList) {
                if (loaitraicay.getTenloaiTc().equalsIgnoreCase(tenLoaiTc)) {
                    maLoaiTc = loaitraicay.getMaloaiTc();
                    System.out.println(maLoaiTc);
                    break;
                }
            }
            float beforeRatio = registryClass.viTri().getBeforeReceivedRatioByFruitType(maLoaiTc);
            float afterRatio = registryClass.viTri().getAfterReceivedRatioByFruitType(maLoaiTc);
            // Kiểm tra NaN và gán mặc định 100 nếu cần
            if (Float.isNaN(beforeRatio) || Float.isNaN(afterRatio)) {
                truocKhiNhap.setText(String.format("Kho hàng còn %.2f%% không gian", 100.00));
                sauKhiNhap.setText(String.format("Kho hàng còn %.2f%% không gian", 100.00));
                setLabelColor(truocKhiNhap, 100);
                setLabelColor(sauKhiNhap, 100);
            }
            else {
                System.out.println(beforeRatio + " " + afterRatio);
                truocKhiNhap.setText(String.format("Kho hàng còn %.2f%% không gian", beforeRatio));
                sauKhiNhap.setText(String.format("Kho hàng còn %.2f%% không gian", afterRatio - beforeRatio));
                setLabelColor(truocKhiNhap, beforeRatio);
                setLabelColor(sauKhiNhap, afterRatio - beforeRatio);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent event) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất");
        Optional<ButtonType> results = alert.showAndWait();
        if (CurrentAccount.taikhoan != null) {
            System.out.println(CurrentAccount.taikhoan);
            if (results.isPresent() && results.get() == ButtonType.OK) {
                System.out.println("Đăng xuất thành công");
                registryClass.taiKhoan().logout(CurrentAccount.taikhoan.getUsername());
                UserSession.setCurrentAccount(null);
                loadScene("FormDangNhap.fxml");
            } else {
                System.out.println("Huỷ đăng xuất");
            }
        }
    }

}
