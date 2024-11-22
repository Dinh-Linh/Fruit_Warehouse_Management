package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.Donnhaphang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DAOViTriImpl extends UnicastRemoteObject implements DAOViTri {
    //Properties
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;

    //Constructor
    public DAOViTriImpl() throws RemoteException {
        this.connectionStatic = new ConnectionStaticImpl();
    }


    //Tỷ lệ tồn kho trước khi nhập
    @Override
    public float getBeforeReceivedRatioByFruitType(String maLoaiTC) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            //Tổng vị trí chiếm kho của loại trái cây
            TypedQuery<Long> query1 = entityManager.createQuery("select count(*) from Vitri s where s.loaiTraiCayViTri.maloaiTc = ?1", Long.class);
            query1.setParameter(1, maLoaiTC);
            int total = query1.getSingleResult().intValue();

            //Tổng vị trí kho của loại trái cây đã được dùng
            TypedQuery<Long> query2 = entityManager.createQuery("select count(*) from Vitri s join s.traicayVitri tc where s.loaiTraiCayViTri.maloaiTc = ?1 and s.traicayVitri is not null", Long.class);
            query2.setParameter(1, maLoaiTC);
            int part = query2.getSingleResult().intValue();

            transaction.commit();
            return ((float)part/total * 100);
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return 0;
    }

    //Tỷ lệ tồn kho sau khi nhập
    @Override
    public float getAfterReceivedRatioByFruitType(String maLoaiTC) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();

            //Tổng vị trí chiếm kho của loại trái cây
            TypedQuery<Long> query1 = entityManager.createQuery("select count(*) from Vitri s where s.loaiTraiCayViTri.maloaiTc = ?1", Long.class);
            query1.setParameter(1, maLoaiTC);
            int total = query1.getSingleResult().intValue();

            //Tổng vị trí kho của loại trái cây đã được dùng
            TypedQuery<Long> query2 = entityManager.createQuery("select count(*) from Vitri s join s.traicayVitri tc where s.loaiTraiCayViTri.maloaiTc = ?1 and s.traicayVitri is not null", Long.class);
            query2.setParameter(1, maLoaiTC);
            int part1 = query2.getSingleResult().intValue();

            //Tổng vị trí kho của loại trái cây chuẩn bị nhập
            TypedQuery<Long> query3 = entityManager.createQuery("select sum(ct.soLuong) from Chitietdonnhap ct join ct.traiCay_DonNhapHang tc where tc.loaiTraiCay_TraiCay.maloaiTc = ?1 group by tc.loaiTraiCay_TraiCay.maloaiTc", Long.class);
            query2.setParameter(1, maLoaiTC);
            int part2 = query2.getSingleResult().intValue();

            transaction.commit();
            return ((float)(part1+part2)/total * 100);
        } catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return 0;
    }
}
