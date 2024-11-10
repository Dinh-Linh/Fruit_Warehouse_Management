package test;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import dao.*;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Test {
    //Lấy toàn bộ đơn nhập hàng
    private static void testGetAllDonnhaphang(){
        try{
            DAODonNhapHang daoDonNhapHang = new DAODonNhapHangImpl();
            ObservableList<Donnhaphang> list = daoDonNhapHang.getAllDonnhaphang();
            for(Donnhaphang donnhaphang : list){
                System.out.println(donnhaphang);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra lấy tất cả loại trái cây
    private static void testGetLoaiTraiCayList(){
        try{
            DAOLoaiTraiCay daoLoaiTraiCay = new DAOLoaiTraiCayImpl();
            ObservableList<Loaitraicay> list = daoLoaiTraiCay.getLoaiTraiCayList();
            for(Loaitraicay item : list){
                System.out.println(item);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra lấy nhà cung cấp
    private static void testGetNhaCungCap(){
        try{
            DAONhaCungCap daoNhaCungCap = new DAONhaCungCapImpl();
            System.out.println(daoNhaCungCap.getNhaCungCap("g4"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra lưu trái cây
    private static void testCreateDonNhaHang(){
        try{
            //Lấy nhà cung cấp
            DAONhaCungCap daoNhaCungCap = new DAONhaCungCapImpl();
            Nhacungcap ncc = daoNhaCungCap.getNhaCungCap("g4");

            //Lấy loại trái cây ở đầu
            DAOLoaiTraiCay daoLoaiTraiCay = new DAOLoaiTraiCayImpl();
            Loaitraicay ltc = daoLoaiTraiCay.getLoaiTraiCayList().get(0);

            //Tạo trái cây
            //(Loại trái cây --> Trái cây)
            Traicay tc = Traicay.builder()
                    .loaiTraiCay_TraiCay(ltc)
                    .tenTc("Xoài Cát Chu Đồng Tháp - G4F")
                    .size("M")
                    .tinhTrang("P")
                    .xuatXu("Đồng Tháp")
                    .giaNhap(BigDecimal.valueOf(150000.0))
                    .giaXuat(BigDecimal.valueOf(170000.5))
                    .build();

            //Tạo trái cây
            //(Loại trái cây2 --> Trái cây)
            Traicay tc2 = Traicay.builder()
                    .loaiTraiCay_TraiCay(ltc)
                    .tenTc("Xoài Cát Chu Đồng Tháp - G4F")
                    .size("S")
                    .tinhTrang("P")
                    .xuatXu("Đồng Tháp")
                    .giaNhap(BigDecimal.valueOf(150000.0))
                    .giaXuat(BigDecimal.valueOf(170000.5))
                    .build();

            //Tạo set để lưu được trái cây vào nhà cung cấp
            //Lưu vào bảng nhacungcap_traicay
            //(Trái cây --> Nhà cung cấp)
//            Set<Traicay> traiCaySet = new HashSet<>();
//            traiCaySet.add(tc);
//            ncc.setTraiCayNhaCungCapSet(traiCaySet);

            //Tạo id chi tiết đơn nhập
            //ChitietdonnhapPK chitietdonnhapPK = ChitietdonnhapPK.builder().build();

            //Tạo đơn nhập hàng
            //(Nhà cung cấp --> Đơn nhập hàng
            LocalDate now = LocalDate.now();
            Donnhaphang donNhap = Donnhaphang.builder()
                    .nhaCungCapDonNhapHang(ncc)
                    .ngayTaoDon(Date.valueOf(now))
                    .tinhTrang("Chưa nhập")
                    .build();

            //Tạo chi tiết đơn nhập
            //(Trái cây --> Chi tiết đơn nhập)
            Chitietdonnhap chiTietDN = Chitietdonnhap.builder()
                    .soLuong(5)
                    .traiCay_DonNhapHang(tc)
                    .build();

            Chitietdonnhap chiTietDN2 = Chitietdonnhap.builder()
                    .soLuong(3)
                    .traiCay_DonNhapHang(tc2)
                    .build();

            //Tạo set để lưu được chi tiết đơn nhập trong đơn nhập hàng
            //(Chi tiết đơn nhập --> Đơn nhập hàng)
            Set<Chitietdonnhap> ctdnSet = new HashSet<>();
            ctdnSet.add(chiTietDN);
            ctdnSet.add(chiTietDN2);

            donNhap.setChiTietDonNhapSet(ctdnSet);

            DAODonNhapHang daoDonNhapHang = new DAODonNhapHangImpl();
            if (daoDonNhapHang.createDonNhapHang(donNhap))
                System.out.println("Tạo đơn nhập thành công");
            else
                System.out.println("Tạo đơn nhập không thành công");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra đơn nhập hàng theo mã loại
    private static void testGetDonNhapHangByFruitType(){
        try{
            DAODonNhapHang daoDonNhapHang = new DAODonNhapHangImpl();
            ObservableList<Donnhaphang> list = daoDonNhapHang.getDonNhapHangByFruitType("CA1");
            for (Donnhaphang d : list){
                System.out.println(d);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra tổng kho trước khi nhập theo loại trái cây
    private static void testGetBeforeReceivedRatioByFruitType(){
        try{
            DAOViTri daoViTri = new DAOViTriImpl();
            System.out.println(daoViTri.getBeforeReceivedRatioByFruitType("CA1"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Kiểm tra tổng kho trước khi nhập theo loại trái cây
    private static void testGetAfterReceivedRatioByFruitType(){
        try{
            DAOViTri daoViTri = new DAOViTriImpl();
            System.out.println(daoViTri.getAfterReceivedRatioByFruitType("CA1"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        testGetAllDonnhaphang();
//        testGetLoaiTraiCayList();
//        testGetNhaCungCap();
//        testCreateDonNhaHang();
//        testGetDonNhapHangByFruitType();
//        testGetBeforeReceivedRatioByFruitType();
        testGetAfterReceivedRatioByFruitType();
        System.exit(0);
    }
}
