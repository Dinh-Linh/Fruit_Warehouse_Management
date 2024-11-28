package dao;

import entity.Donnhaphang;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAODonNhapHang extends Remote {
    public List<Donnhaphang> getAllDonnhaphang() throws RemoteException;
    public boolean createDonNhapHang(Donnhaphang donNhapHang) throws RemoteException;
    public Donnhaphang getDonNhapHang(String maDN) throws RemoteException;
    public List<Donnhaphang> getDonNhapHangByFruitType(String maLoaiTC) throws RemoteException;
    public List<Donnhaphang> getDonNhapHangByTaiKhoan(String idTaiKhoan) throws RemoteException;
}
