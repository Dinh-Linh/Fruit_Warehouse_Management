package org.example.service;
import org.example.model.entity.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupplierService extends Remote {
    void createSupplier(Supplier Supplier) throws RemoteException;
    List<Supplier> getAllSupplier() throws RemoteException;
}