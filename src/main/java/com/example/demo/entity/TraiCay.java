package com.example.demo.entity;

import java.math.BigDecimal;

public class TraiCay {
    private String maTc;
    private String tenTc;
    private String size;
    private String tinhTrang;
    private String xuatXu;
    private BigDecimal giaNhap;
    private BigDecimal giaXuat;
    private int soLuongTc;

    public TraiCay(String maTC, String tenTC, String kichThuoc, String tinhTrang) {
        this.maTc = maTC;
        this.tenTc = tenTC;
        this.size = kichThuoc;
        this.tinhTrang = tinhTrang;
    }

    public TraiCay(String tenTc, String size, String tinhTrang, String xuatXu, BigDecimal giaNhap, BigDecimal giaXuat, int soLuongTc) {
        this.maTc = maTc;
        this.tenTc = tenTc;
        this.size = size;
        this.tinhTrang = tinhTrang;
        this.xuatXu = xuatXu;
        this.giaNhap = giaNhap;
        this.giaXuat = giaXuat;
        this.soLuongTc = soLuongTc;
    }

    public String getMaTc() {
        return maTc;
    }

    public void setMaTc(String maTc) {
        this.maTc = maTc;
    }

    public String getTenTc() {
        return tenTc;
    }

    public void setTenTc(String tenTc) {
        this.tenTc = tenTc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
