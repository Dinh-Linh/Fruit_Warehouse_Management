package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAOViTri extends Remote {
    public float getBeforeReceivedRatioByFruitType(String maLoaiTC) throws RemoteException;
    public float getAfterReceivedRatioByFruitType(String maLoaiTC) throws RemoteException;
}
