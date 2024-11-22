package dao;

import entity.Nhacungcap;
import jakarta.persistence.EntityManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAONhaCungCap extends Remote {
    public Nhacungcap getNhaCungCap(String tenNCC) throws RemoteException;
    public boolean getNhaCungCap(String tenNCC, EntityManager entityManager) throws RemoteException;
}
