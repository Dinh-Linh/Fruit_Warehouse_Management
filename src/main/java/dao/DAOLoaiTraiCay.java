package dao;

import entity.Loaitraicay;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DAOLoaiTraiCay extends Remote {
    public List<Loaitraicay> getLoaiTraiCayList() throws RemoteException;
    public Loaitraicay getLoaiTraiCay(String maLoaiTC) throws RemoteException;
}
