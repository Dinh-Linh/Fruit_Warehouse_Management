package test;

import dao.DAODonNhapHang;
import dao.DAODonNhapHangImpl;
import entity.Donnhaphang;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;

public class Test {
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

    public static void main(String[] args) {
        testGetAllDonnhaphang();

        System.exit(0);
    }
}
