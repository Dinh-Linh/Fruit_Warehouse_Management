package dao;

import entity.Donnhaphang;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAODonNhapHang extends Remote {
    public ObservableList<Donnhaphang> getAllDonnhaphang() throws RemoteException;
    public boolean createDonNhapHang(Donnhaphang donNhapHang) throws RemoteException;
    public Donnhaphang getDonNhapHang(String maDN) throws RemoteException;
    public ObservableList<Donnhaphang> getDonNhapHangByFruitType(String maLoaiTC) throws RemoteException;
}
