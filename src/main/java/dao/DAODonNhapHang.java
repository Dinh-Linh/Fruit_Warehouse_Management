package dao;

import entity.Donnhaphang;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAODonNhapHang extends Remote {
    public ObservableList<Donnhaphang> getAllDonnhaphang() throws RemoteException;
}
