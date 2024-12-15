package dao;

import entity.Taikhoan;
import jakarta.persistence.EntityManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOTaiKhoan extends Remote {


    public Taikhoan getTaiKhoan(String username) throws RemoteException;

    //Lấy toàn bộ danh sách tài khoản;
    public List<Taikhoan> getAllTaiKhoan() throws RemoteException;

    Taikhoan login(String username, String password) throws RemoteException;

    boolean logout(String username) throws RemoteException;

    boolean changePassword(String username, String newPassword) throws RemoteException;

    public boolean checkRecoverCode(String recoverCode) throws RemoteException;
}
