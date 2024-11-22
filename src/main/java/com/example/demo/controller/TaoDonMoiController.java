package com.example.demo.controller;

import com.example.demo.entity.TraiCay;
import com.example.demo.utils.Validator;
import dao.DAODonNhapHang;
import dao.DAONhaCungCap;
import entity.*;
import generator.MaTCGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.RegistryClass;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaoDonMoiController {
    Validator validator = new Validator();

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
    private Label labelTrangChu;
    @FXML
    private Label labelNhapHang;

    @FXML
    private TableView<Traicay> tableDonNhap;  // Sửa kiểu dữ liệu từ Object thành TraiCay

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
            loadScene("TrangChu.fxml");
        });
        labelNhapHang.setOnMouseClicked(event -> {
            loadScene("DonNhap.fxml");
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
                    createDNHFromUI();
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

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/" + fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) labelTrangChu.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDNHFromUI() {
        try {
            //Get Data from UI
            String ncc = supplier.getText();
            java.sql.Date ngayTaoDon = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(importDate.getText()).getTime());
            String trangThai = "Đang xử lý";
            Donnhaphang donnhaphang = Donnhaphang.builder().ngayTaoDon(ngayTaoDon).tinhTrang(trangThai).build();

            //Search Supplier
            Nhacungcap nhacungcapObj = registryClass.nhaCungCap().getNhaCungCap(ncc);
            if (nhacungcapObj != null) {
                donnhaphang.setNhaCungCapDonNhapHang(nhacungcapObj);
                System.out.println(nhacungcapObj);
            } else {
                System.out.println("Không tìm thấy nhà cung cấp với tên " + ncc);
            }
            Set<Chitietdonnhap> chitietdonnhapSet = new HashSet<>();
            for (Traicay tc : tableDonNhap.getItems()) {
                // Kiểm tra trạng thái của Traicay
                if (tc.getMaTc() == null || tc.getLoaiTraiCay_TraiCay() == null || tc.getXuatXu() == null) {
                    System.out.println("TraiCay chưa đầy đủ thông tin. Bỏ qua!");
                    continue;
                }

                // Tạo ChitietdonnhapPK
                ChitietdonnhapPK id = new ChitietdonnhapPK();
                id.setMaDnCTDN(donnhaphang.getMaDn());
                id.setMaTCCTDN(tc.getMaTc());

                // Tạo Chitietdonnhap
                Chitietdonnhap details = new Chitietdonnhap();
                details.setId(id);
                details.setTraiCay_DonNhapHang(tc);
                details.setChiTietDonNhap_DonNhapHang(donnhaphang);

                // Debug thông tin
                System.out.println("ChiTietDonNhap: " + details);

                chitietdonnhapSet.add(details);
            }

            // Set chiTietDonNhap vào Donnhaphang
            donnhaphang.setChiTietDonNhapSet(chitietdonnhapSet);

            // Persist Donnhaphang
            if (registryClass.donNhapHang().createDonNhapHang(donnhaphang)) {
                showAlert("Thông báo", "Đơn đã được tạo thành công.");
            } else {
                showAlert("Thông báo", "Lỗi khi lưu");
            }

        } catch (Exception e) {
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
        String donViTinh = fruitDVT.getText();
        String tinhTrang = comboBoxTinhTrang.getValue();
        String kichThuoc = comboBoxSize.getValue();
        String loai = comboBoxFruitType.getValue();
        BigDecimal giaNhap = new BigDecimal(fruitPriceImport.getText());
        BigDecimal giaXuat = new BigDecimal(fruitPriceExport.getText());
        System.out.printf("Tên trái cây: %s, Xuất xứ: %s, Số lượng: %s, Giá nhập: %s, Giá xuất: %s, Tình trạng: %s, Đơn vị tính: %s, Kích thước: %s, Loại: %s%n",
                tenTC, xuatXuTC, soLuongTC, giaNhap, giaXuat, tinhTrang, donViTinh, kichThuoc, loai);
    }

    private void handleSaveAction() {
        if (!checkField()) {
            showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin trái cây");
        }
//        String maTC = maTCGenerator.getMaTC(tableDonNhap.getItems().get(1));
        String tenTC = fruitName.getText();
        String tinhTrang = comboBoxTinhTrang.getValue();
        String loaiTc = comboBoxFruitType.getValue();
        String kichThuoc = comboBoxSize.getValue();
        Traicay fruit = Traicay.builder().tenTc(tenTC).tinhTrang(tinhTrang).size(kichThuoc).build();

        fruit.setLoaiTraiCay_TraiCay(Loaitraicay.builder().build());
        fruit.setMaTc(new MaTCGenerator().getMaTC(fruit));
        System.out.println(new MaTCGenerator().getMaTC(fruit));
        ;
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
