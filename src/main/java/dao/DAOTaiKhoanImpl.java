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
import java.sql.Date;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
    public Taikhoan getTaiKhoan(String username) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Tạo truy vấn để tìm tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery("SELECT t FROM Taikhoan t WHERE t.username = :username", Taikhoan.class);
            query.setParameter("username", username);

            // Thực hiện truy vấn và lấy kết quả
            List<Taikhoan> resultList = query.getResultList();

            transaction.commit();

            // Nếu danh sách không trống, trả về tài khoản đầu tiên
            if (!resultList.isEmpty()) {
                return resultList.get(0);
            }

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Hoàn tác giao dịch nếu có lỗi
            }
            e.printStackTrace(); // In lỗi ra console
        } finally {
            // (Tùy chọn) Đảm bảo rằng entityManager được đóng nếu không còn sử dụng
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return null; // Trả về null nếu không tìm thấy tài khoản hoặc có lỗi
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

    @Override
    public Taikhoan login(String username, String password) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Tìm tài khoản theo username
            TypedQuery<Taikhoan> query = entityManager.createQuery("select t from Taikhoan t where t.username = ?1", Taikhoan.class);
            query.setParameter(1, username);
            Taikhoan taikhoan = query.getResultList().isEmpty() ? null : query.getSingleResult();

            if (taikhoan == null) {
                return null;
            }
            // Đã đúng tên tài khoản
            // Kiểm tra mật khẩu

//            checkAndUpdateAccountStatus(taikhoan);

            //entityManager quản lý tài khoản
            taikhoan = entityManager.find(Taikhoan.class, taikhoan.getIdTaiKhoan());

            /*FIX 15/12 */
            //Nếu tài khoản hết thời gian bị khóa thì cập nhật lại
            if (taikhoan.getStatus() == STATUS.LOCK && taikhoan.getLockTime() != null) {
                if (Timestamp.from(Instant.now()).after(taikhoan.getLockTime())) {
                    // Đã quá thời gian khóa, cập nhật trạng thái về OFF và reset loginAttempt
                    taikhoan.setStatus(STATUS.OFF);
                    taikhoan.setLoginAttempt(0); // Reset loginAttempt về 5
                }
            }

            if (taikhoan.getPassword().equals(password)) {
                // Kiểm tra trạng thái
                if (taikhoan.getStatus() == STATUS.LOCK || taikhoan.getStatus() == STATUS.QUIT ||taikhoan.getStatus() == STATUS.ON) {
                    transaction.commit();
                    return taikhoan;
                }
                // Đăng nhập thành công nếu trạng thái là "OFF" hoặc "FIRST"
                if (taikhoan.getStatus() == STATUS.OFF || taikhoan.getStatus() == STATUS.FIRST) {
                    // Reset loginAttempt về 0 khi đăng nhập thành công
                    taikhoan.setLoginAttempt(0);
                    taikhoan.setStatus(STATUS.ON);

//                    entityManager.merge(taikhoan);
                    System.out.println(entityManager.contains(taikhoan));
                    transaction.commit();
                    return taikhoan;
                }
            } else {
                // Nếu mật khẩu sai, tăng loginAttempt lên 1
                taikhoan.setLoginAttempt(taikhoan.getLoginAttempt() + 1);
                if (taikhoan.getLoginAttempt() > 5) {
                    // Nếu loginAttempt > 5, khóa tài khoản
                    taikhoan.setStatus(STATUS.LOCK);
                    taikhoan.setLockTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30))); // Khóa tài khoản trong 30 phút
                }
                transaction.commit();
                return taikhoan;
            }
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

    //Không dùng được do entityManager không quản lý
    // Phương thức kiểm tra và cập nhật trạng thái tài khoản
    public void checkAndUpdateAccountStatus(Taikhoan taikhoan) {
        /*FIX 15/12 sửa LockTime sang TimeStamp */
        // Kiểm tra nếu lockTime đã qua
        if (taikhoan.getStatus() == STATUS.LOCK && taikhoan.getLockTime() != null) {
            if (Timestamp.valueOf(String.valueOf(Instant.now())).after(taikhoan.getLockTime())) {
                // Đã quá thời gian khóa, cập nhật trạng thái về OFF và reset loginAttempt
                taikhoan.setStatus(STATUS.OFF);
                taikhoan.setLoginAttempt(5); // Reset loginAttempt về 5
            }
        }
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

    //Kiểm tra mã khôi phục
    @Override
    public boolean checkRecoverCode(String recoverCode) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            //Lấy tài khoản admin
            Taikhoan tkAd =  entityManager.find(Taikhoan.class, "TK-00000000");

            //Không có return false
            if(tkAd == null){
                transaction.commit();
                return false;
            }

            if(tkAd.getRecoveryCode().equals(recoverCode)) {
                tkAd.setLoginAttempt(0);
                tkAd.setStatus(STATUS.ON);
                transaction.commit();
                return true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if(this.entityManager.isOpen())
                this.entityManager.close();
        }

        return false;
    }

}
