package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try{
//            Registry registry = LocateRegistry.getRegistry("localhost",1099);
//
//            NhaCungCapDAO ncc = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
//            System.out.println(ncc.getSupplier("NCC-01").getTenNcc());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
