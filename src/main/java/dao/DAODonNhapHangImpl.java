package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.Donnhaphang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DAODonNhapHangImpl extends UnicastRemoteObject implements DAODonNhapHang {
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;

    public DAODonNhapHangImpl() throws RemoteException {
        this.connectionStatic = new ConnectionStaticImpl();
    }

    //Lấy toàn bộ ds đơn nhập
    @Override
    public ObservableList<Donnhaphang> getAllDonnhaphang() throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Donnhaphang> query = entityManager.createQuery("select s from Donnhaphang s order by s.ngayTaoDon desc, s.tinhTrang desc ", Donnhaphang.class);
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
}
