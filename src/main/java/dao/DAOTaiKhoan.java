package dao;

import entity.Donnhaphang;
import entity.Taikhoan;
import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
<<<<<<< HEAD
public interface DAOTaiKhoan extends Remote {


    public Taikhoan getTaiKhoan(String username, EntityManager entityManager) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public ObservableList<Taikhoan> getAllTaiKhoan() throws RemoteException;

    Taikhoan login(String username, String password) throws RemoteException;

=======
import java.util.List;

public interface DAOTaiKhoan extends Remote {


    public boolean getTaiKhoan(String username, EntityManager entityManager) throws RemoteException;

    public Taikhoan getTaiKhoan(String username) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public List<Taikhoan> getAllTaiKhoan() throws RemoteException;

    boolean login(String username, String password) throws RemoteException;
>>>>>>> Sprint3_be

    boolean logout(String username) throws RemoteException;

    boolean changePassword(String username, String newPassword) throws RemoteException;
}
