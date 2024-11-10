package com.example.demo.service;

import dao.DAODonNhapHang;
import dao.DAOLoaiTraiCay;
import dao.DAONhaCungCap;
import dao.DAOViTri;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIService {
    private static DAODonNhapHang dnh;
    private static DAOLoaiTraiCay ltc;
    private static DAONhaCungCap ncc;
    private static DAOViTri vt;

    static {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1009);
            dnh = (DAODonNhapHang) registry.lookup("DonNhapHang");
            ltc = (DAOLoaiTraiCay) registry.lookup("LoaiTraiCay");
            ncc = (DAONhaCungCap) registry.lookup("NhaCungCap");
            vt = (DAOViTri) registry.lookup("ViTri");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DAODonNhapHang getDnh() {
        return dnh;
    }

    public static DAOLoaiTraiCay getLtc() {
        return ltc;
    }

    public static DAONhaCungCap getNcc() {
        return ncc;
    }

    public static DAOViTri getVt() {
        return vt;
    }
}
