package dao;

import entity.Donnhaphang;
import entity.Taikhoan;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface DAOTaiKhoan extends Remote {

    public Taikhoan getTaiKhoan(String idTaiKhoan) throws RemoteException;

    public boolean getTaiKhoan(String idTaiKhoan, EntityManager entityManager) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public ObservableList<Taikhoan> getAllTaiKhoan() throws RemoteException;
}
