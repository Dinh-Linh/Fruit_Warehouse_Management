package dao;

import entity.Donnhaphang;
import entity.Taikhoan;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface DAOTaiKhoan extends Remote {


    public Taikhoan getTaiKhoan(String username) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public ObservableList<Taikhoan> getAllTaiKhoan() throws RemoteException;

    Taikhoan login(String username, String password) throws RemoteException;


    boolean logout(String username) throws RemoteException;

    boolean changePassword(String username, String newPassword) throws RemoteException;
}
