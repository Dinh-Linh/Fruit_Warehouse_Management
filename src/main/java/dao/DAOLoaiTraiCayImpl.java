package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.Loaitraicay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DAOLoaiTraiCayImpl extends UnicastRemoteObject implements DAOLoaiTraiCay {
    //Properties
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;

    //Constructor
    public DAOLoaiTraiCayImpl() throws RemoteException {
        this.connectionStatic = new ConnectionStaticImpl();
    }


    //Hàm trả về danh sách toàn bộ loại trái cây
    @Override
    public ObservableList<Loaitraicay> getLoaiTraiCayList() throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Loaitraicay> query = entityManager.createQuery("from Loaitraicay", Loaitraicay.class);
            transaction.commit();
            return FXCollections.observableArrayList(query.getResultList());
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return null;
    }

    //Hàm trả về loại trái cây thông qua mã loại trái cây: Không dùng nên chưa code
    @Override
    public Loaitraicay getLoaiTraiCay(String maLoaiTC) throws RemoteException {
        return null;
    }
}
