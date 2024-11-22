package util;


import dao.*;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RegistryClass {

    private static final java.rmi.registry.Registry registry;
    private static final DAODonNhapHang dnh;
    private static final DAOLoaiTraiCay ltc;
    private static final DAONhaCungCap ncc;
    private static final DAOViTri vt;
    private static final DAOTaiKhoan tk;


    static{
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            dnh = (DAODonNhapHang) registry.lookup("DonNhapHang");
            ltc = (DAOLoaiTraiCay) registry.lookup("LoaiTraiCay");
            ncc = (DAONhaCungCap) registry.lookup("NhaCungCap");
            vt = (DAOViTri) registry.lookup("ViTri");
            tk = (DAOTaiKhoan) registry.lookup("TaiKhoan");
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        } catch (NotBoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public RegistryClass() throws RemoteException, AccessException, NotBoundException {

    }

    public DAODonNhapHang donNhapHang() throws RemoteException {
        return dnh;
    }

    public DAOLoaiTraiCay loaiTraiCay() throws RemoteException {
        return ltc;
    }

    public DAONhaCungCap nhaCungCap() throws RemoteException {
        return ncc;
    }

    public DAOViTri viTri() throws RemoteException {
        return vt;
    }

    public DAOTaiKhoan taiKhoan() throws RemoteException {
        return tk;
    }

}
