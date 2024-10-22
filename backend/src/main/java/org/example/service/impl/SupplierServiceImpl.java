package org.example.service.impl;
import org.example.model.dao.SupplierDAO;
import org.example.model.entity.Supplier;
import org.example.service.SupplierService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class SupplierServiceImpl extends UnicastRemoteObject implements SupplierService {
    private SupplierDAO supplierDAO;
    public SupplierServiceImpl() throws RemoteException{
        supplierDAO = new SupplierDAO();
    }
    @Override
    public void createSupplier(Supplier Supplier) throws RemoteException {
        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/fruit_warehouse_management?allowMultiQueries=true", "root", "123456");
//            PreparedStatement ps = con.prepareStatement("select * from NHACUNGCAP");
//            ResultSet rs = ps.executeQuery();
            supplierDAO.createSupplier(Supplier);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Override
    public List<Supplier> getAllSupplier() throws RemoteException{
        return supplierDAO.findAllSuppliers();
    }

    public void createSupplierV2(Supplier supplier) throws RemoteException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/fruit_warehouse_management?allowMultiQueries=true", "root", "123456");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
