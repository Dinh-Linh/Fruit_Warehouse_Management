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
import java.util.List;

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

    //Kiểm tra tài khoản có tồn tại hay không theo username
    @Override
    public boolean getTaiKhoan(String username, EntityManager entityManager) throws RemoteException {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Taikhoan> query = entityManager.createQuery("select t from Taikhoan t where t.username = ?1", Taikhoan.class);
            query.setParameter(1, username);
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

    @Override
    public Taikhoan getTaiKhoan(String username) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Truy vấn tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery(
                    "select t from Taikhoan t where t.username = :username", Taikhoan.class);
            query.setParameter("username", username);

            // Lấy kết quả
            Taikhoan taiKhoan = query.getResultList().isEmpty() ? null : query.getSingleResult();

            transaction.commit();
            return taiKhoan; // Trả về đối tượng Taikhoan hoặc null nếu không tồn tại
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RemoteException("Lỗi khi lấy tài khoản", e);
        }
    }


    //Lấy toàn bộ danh sách tài khoản;
    @Override
    public List<Taikhoan> getAllTaiKhoan() throws RemoteException{
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Taikhoan> query = entityManager.createQuery("select s from Taikhoan s order by s.startDate desc ", Taikhoan.class);
            transaction.commit();
            return query.getResultList();
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

    @Override
    public boolean login(String username, String password) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Tìm tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery("select t from Taikhoan t where t.username = ?1", Taikhoan.class);
            query.setParameter(1, username);
            Taikhoan taikhoan = query.getResultList().isEmpty() ? null : query.getSingleResult();

            if (taikhoan == null) {
                return false;
            }

            // Kiểm tra trạng thái
            if (taikhoan.getStatus() == STATUS.LOCK) {
                return false; // Tài khoản bị khóa
            }

            // Kiểm tra mật khẩu
            if (taikhoan.getPassword().equals(password)) {
                // Đăng nhập thành công nếu trạng thái là "OFF" hoặc "FIRST"
                if (taikhoan.getStatus() == STATUS.OFF || taikhoan.getStatus() == STATUS.FIRST) {
                    // Reset loginAttempt về 0 khi đăng nhập thành công
                    taikhoan.setLoginAttempt(0);
                    taikhoan.setStatus(STATUS.ON);
                    transaction.commit();
                    return true; // Đăng nhập thành công
                } else {
                    return false; // Trạng thái không cho phép đăng nhập
                }
            } else {
                // Nếu mật khẩu sai, tăng loginAttempt lên 1
                taikhoan.setLoginAttempt(taikhoan.getLoginAttempt() + 1);
                if (taikhoan.getLoginAttempt() > 5) {
                    // Nếu loginAttempt > 5, khóa tài khoản
                    taikhoan.setStatus(STATUS.LOCK);
                }
                transaction.commit();
                return false; // Mật khẩu sai
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean logout(String username) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Tìm tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery("select t from Taikhoan t where t.username = ?1", Taikhoan.class);
            query.setParameter(1, username);
            Taikhoan taikhoan = query.getResultList().isEmpty() ? null : query.getSingleResult();

            if (taikhoan == null) {
                return false; // Username không tồn tại
            }

            // Kiểm tra trạng thái trước khi đăng xuất
            if (taikhoan.getStatus() != STATUS.ON) {
                return false; // Tài khoản không ở trạng thái ON, không thể đăng xuất
            }

            // Cập nhật trạng thái về OFF
            taikhoan.setStatus(STATUS.OFF);

            transaction.commit();
            return true; // Đăng xuất thành công
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean changePassword(String username, String newPassword) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Tìm tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery("select t from Taikhoan t where t.username = ?1", Taikhoan.class);
            query.setParameter(1, username);
            Taikhoan taikhoan = query.getResultList().isEmpty() ? null : query.getSingleResult();

            if (taikhoan == null) {
                return false; // Username không tồn tại
            }

            // Kiểm tra trạng thái tài khoản
            if (taikhoan.getStatus() != STATUS.ON) {
                return false; // Tài khoản không ở trạng thái ON, không thể đổi mật khẩu
            }

            // Cập nhật mật khẩu mới
            taikhoan.setPassword(newPassword);

            // Cam kết giao dịch
            transaction.commit();
            return true; // Đổi mật khẩu thành công
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

}
