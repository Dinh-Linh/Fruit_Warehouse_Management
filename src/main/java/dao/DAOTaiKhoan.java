package dao;

import entity.Donnhaphang;
import entity.Taikhoan;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface DAOTaiKhoan extends Remote {


    public boolean getTaiKhoan(String username, EntityManager entityManager) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public ObservableList<Taikhoan> getAllTaiKhoan() throws RemoteException;

    boolean login(String username, String password) throws RemoteException;

    boolean logout(String username) throws RemoteException;

    boolean changePassword(String username, String newPassword) throws RemoteException;
}
