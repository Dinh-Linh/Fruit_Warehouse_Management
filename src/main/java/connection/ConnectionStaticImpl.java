package connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/*
    Thực hiện interface ConnectionPool dùng để quản lý kết nối
    Khai báo EntityManagerFactory dùng để tạo EntityManager
    Stack: Dùng để quản lý số lượng kết nối
 */
public class ConnectionStaticImpl implements ConnectionStatic {
    private static final EntityManagerFactory emf;

    static{
        //Tạo EntityManagerFactory sử dụng persistence
        emf = Persistence.createEntityManagerFactory("default");

    }
    public ConnectionStaticImpl() {

    }

    //Tạo kết nối
    @Override
    public EntityManager getConnection() {
            return this.emf.createEntityManager();
    }

}
