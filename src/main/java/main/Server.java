package main;

import dao.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            DAODonNhapHang dnh = new DAODonNhapHangImpl();
            DAOLoaiTraiCay ltc = new DAOLoaiTraiCayImpl();
            DAONhaCungCap ncc = new DAONhaCungCapImpl();
            DAOViTri vt = new DAOViTriImpl();

            registry.rebind("DonNhapHang", dnh);
            registry.rebind("LoaiTraiCay", ltc);
            registry.rebind("NhaCungCap", ncc);
            registry.rebind("ViTri", vt);
            System.out.println("RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
