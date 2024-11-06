package dao;

import entity.Loaitraicay;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOLoaiTraiCay extends Remote {
    public ObservableList<Loaitraicay> getLoaiTraiCayList() throws RemoteException;
    public Loaitraicay getLoaiTraiCay(String maLoaiTC) throws RemoteException;
}
