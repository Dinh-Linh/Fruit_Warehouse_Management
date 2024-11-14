package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.*;
import generator.IdTaiKhoanGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DAOTaiKhoanImpl extends UnicastRemoteObject implements DAOTaiKhoan {
    //Properties
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;
    private IdTaiKhoanGenerator idTaiKhoanGenerator;

    //Constructor
    public DAOTaiKhoanImpl() throws RemoteException{
        this.connectionStatic = new ConnectionStaticImpl();
        this.idTaiKhoanGenerator = new IdTaiKhoanGenerator();
    }

    @Override
    public Taikhoan getTaiKhoan(String idTaiKhoan) throws RemoteException {
        return null;
    }

    @Override
    public boolean getTaiKhoan(String idTaiKhoan, EntityManager entityManager) throws RemoteException {
        return false;
    }

    //Lấy toàn bộ danh sách tài khoản;
    @Override
    public ObservableList<Taikhoan> getAllTaiKhoan() throws RemoteException{
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Taikhoan> query = entityManager.createQuery("select s from Taikhoan s order by s.startDate desc ", Taikhoan.class);
            transaction.commit();
            return FXCollections.observableArrayList(query.getResultList());
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }
    public ObservableList<Donnhaphang> getDonNhapHangByTaiKhoan(String idTaiKhoan) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Tạo truy vấn để lấy danh sách các đơn nhập hàng theo id tài khoản
            TypedQuery<Donnhaphang> query = entityManager.createQuery(
                    "SELECT d FROM Donnhaphang d WHERE d.taiKhoan.id = :idTaiKhoan ORDER BY d.ngayTaoDon DESC", Donnhaphang.class);
            query.setParameter("idTaiKhoan", idTaiKhoan);

            // Cam kết giao dịch
            transaction.commit();

            // Trả về danh sách dưới dạng ObservableList
            return FXCollections.observableArrayList(query.getResultList());

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }


}
