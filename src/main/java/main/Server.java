package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
//            Registry registry = LocateRegistry.createRegistry(1099);
//            NhaCungCapDAO ncc = new NhaCungCapDAOImpl();
//            registry.rebind("NhaCungCapDAO", ncc);
//            System.out.println("RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
