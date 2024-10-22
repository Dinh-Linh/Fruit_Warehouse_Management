package org.example;
import org.example.service.ImportOrderService;
import org.example.service.SupplierService;
import org.example.service.impl.ImportOrderServiceImpl;
import org.example.service.impl.SupplierServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ImportOrderService importOrderService = new ImportOrderServiceImpl();
            SupplierService supplierService = new SupplierServiceImpl();
            registry.rebind("supplierService", supplierService);
            registry.rebind("ImportOrderService", importOrderService);
            System.out.println("RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
