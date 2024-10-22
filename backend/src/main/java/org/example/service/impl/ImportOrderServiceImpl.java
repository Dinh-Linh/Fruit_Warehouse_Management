package org.example.service.impl;
import org.example.model.dao.ImportOrderDAO;
import org.example.model.entity.ImportOrder;
import org.example.service.ImportOrderService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ImportOrderServiceImpl extends UnicastRemoteObject implements ImportOrderService {
    private ImportOrderDAO importOrderDAO;
    public ImportOrderServiceImpl() throws RemoteException{
        importOrderDAO = new ImportOrderDAO();
    }
    @Override
    public void createImportOrder(ImportOrder importOrder) throws RemoteException {
        importOrderDAO.createImportOrder(importOrder);
    }

    @Override
    public List<ImportOrder> getAllImportOrder() throws RemoteException {
        return importOrderDAO.getAllImportOrder();
    }
}