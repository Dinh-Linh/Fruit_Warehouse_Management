package main;

import dao.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("localhost",1099);

            DAODonNhapHang dnh = (DAODonNhapHang) registry.lookup("DonNhapHang");
            DAOLoaiTraiCay ltc = (DAOLoaiTraiCay) registry.lookup("LoaiTraiCay");
            DAONhaCungCap ncc = (DAONhaCungCap) registry.lookup("NhaCungCap");
            DAOViTri vt = (DAOViTri) registry.lookup("ViTri");

            System.out.println(vt.getBeforeReceivedRatioByFruitType("CA1"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
