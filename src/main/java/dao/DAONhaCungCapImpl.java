package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.Nhacungcap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DAONhaCungCapImpl extends UnicastRemoteObject implements DAONhaCungCap {
    //Properties
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;

    //Constructor
    public DAONhaCungCapImpl() throws RemoteException {
        this.connectionStatic = new ConnectionStaticImpl();
    }

    //Lấy nhà cung cấp có tên chứa 1 đoạn ký tự được nhập vào
    @Override
    public Nhacungcap getNhaCungCap(String tenNCC) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Nhacungcap> query = entityManager.createQuery("select s from Nhacungcap s where LOWER(s.tenNcc) like ?1", Nhacungcap.class);
            query.setParameter(1, "%" + tenNCC.toLowerCase() + "%");
            transaction.commit();
            return (query.getSingleResult());
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

    //Lấy nhà cung cấp đúng hoàn toàn để kiểm tra nhà cung cấp có tồn tại không trước khi nhập
    //entityManager: phương thức này mở kết nối cùng phương thức kia
    @Override
    public boolean getNhaCungCap(String tenNCC, EntityManager entityManager) throws RemoteException {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Nhacungcap> query = entityManager.createQuery("select s from Nhacungcap s where s.tenNcc = ?1", Nhacungcap.class);
            query.setParameter(1, tenNCC);
            transaction.commit();
            if(query.getResultList().isEmpty()){
                return true;
            }
            return false;
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return true;
    }
}
