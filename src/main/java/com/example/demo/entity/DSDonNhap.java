package com.example.demo.entity;

import java.util.Date;

public class DSDonNhap {

    private String idDon;
    private Date ngayNhap;
    private String nhaCungCap;
    private String trangThai;



    // Constructor
    public DSDonNhap(String idDon, Date ngayNhap, String nhaCungCap, String trangThai) {
        this.idDon = idDon;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = nhaCungCap;
        this.trangThai = trangThai;

    }

    // Getters and Setters
    public String getIdDon() {
        return idDon;
    }

    public void setIdDon(String idDon) {
        this.idDon = idDon;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

//    public Button getThongTinButton() {
//        Button button = new Button("Chi tiết");
//        button.setOnAction(e -> {
//            // Tải trang ChiTietDon.fxml
//            HomeController.loadChiTietDon();
//        });
//        return button;
//    }

}
