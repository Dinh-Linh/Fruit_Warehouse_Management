package test;

import entity.*;
import util.RegistryClass;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Test {
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

    //Lấy toàn bộ đơn nhập hàng
    private void testGetAllDonnhaphang() throws RemoteException {
        List<Donnhaphang> list = registryClass.donNhapHang().getAllDonnhaphang();
        System.out.println(list.size());
        for (Donnhaphang donnhaphang : list) {
            System.out.println(donnhaphang);
        }
    }

    //Kiểm tra lấy tất cả loại trái cây
    private void testGetLoaiTraiCayList() throws RemoteException {
        List<Loaitraicay> list = registryClass.loaiTraiCay().getLoaiTraiCayList();
        for (Loaitraicay item : list) {
            System.out.println(item);
        }
    }

    //Kiểm tra lấy nhà cung cấp
    private void testGetNhaCungCap() throws RemoteException {
        System.out.println(registryClass.nhaCungCap().getNhaCungCap("g4"));
    }


    //Kiểm tra đơn nhập hàng theo mã loại
    private void testGetDonNhapHangByFruitType() throws RemoteException {
        List<Donnhaphang> list = registryClass.donNhapHang().getDonNhapHangByFruitType("CA1");
        for (Donnhaphang d : list) {
            System.out.println(d);
        }

    }

    //Kiểm tra tổng kho trước khi nhập theo loại trái cây
    private void testGetBeforeReceivedRatioByFruitType() throws RemoteException {
        System.out.println(registryClass.viTri().getBeforeReceivedRatioByFruitType("CA1"));
    }

    //Kiểm tra tổng kho trước khi nhập theo loại trái cây
    private void testGetAfterReceivedRatioByFruitType() throws RemoteException {
        System.out.println(registryClass.viTri().getAfterReceivedRatioByFruitType("CA1"));
    }

    public static void main(String[] args) throws RemoteException {
        Test test = new Test();
        test.testGetAllDonnhaphang();
//        testGetLoaiTraiCayList();
//        testGetNhaCungCap();
//        testCreateDonNhaHang();
//        testGetDonNhapHangByFruitType();
//        testGetBeforeReceivedRatioByFruitType();
//        testGetAfterReceivedRatioByFruitType();
        System.exit(0);
    }
}
