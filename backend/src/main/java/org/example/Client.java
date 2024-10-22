package org.example;
import org.example.model.entity.Supplier;
import org.example.service.SupplierService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            SupplierService supplierService = (SupplierService) registry.lookup("supplierService");
            Supplier Supplier = new Supplier();
            Supplier.setSupplierId("123543534s");
            Supplier.setSupplierName("CTyTNHH1TV");
            Supplier.setSupplierEmail("abc@gmail.com");
            Supplier.setSupplierPhone("0987654321");
            Supplier.setSupplierAddress("tatyerryter");
            supplierService.createSupplier(Supplier);
            List<Supplier> Suppliers = supplierService.getAllSupplier();
            System.out.println("Danh sách nhà cung cấp:");
            if (Suppliers.isEmpty()) {
                System.out.println("Danh sach nha cung cap null");
            } else {
                for (Supplier s : Suppliers) {
                    System.out.println("ID: " + s.getSupplierId() + ", Tên: " + s.getSupplierName() +
                            ", Email: " + s.getSupplierEmail() + ", SĐT: " + s.getSupplierPhone() +
                            ", Địa chỉ: " + s.getSupplierAddress());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}