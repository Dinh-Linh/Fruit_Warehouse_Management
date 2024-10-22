package org.example.service;

import org.example.model.entity.ImportOrder;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ImportOrderService extends Remote {
    void createImportOrder(ImportOrder importOrder) throws RemoteException;
    List<ImportOrder> getAllImportOrder() throws RemoteException;
}
