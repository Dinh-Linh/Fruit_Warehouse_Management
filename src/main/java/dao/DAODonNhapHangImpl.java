package dao;

import connection.ConnectionStatic;
import connection.ConnectionStaticImpl;
import entity.*;
import generator.MaTCGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class DAODonNhapHangImpl extends UnicastRemoteObject implements DAODonNhapHang {
    //Properties
    private ConnectionStatic connectionStatic;
    private EntityManager entityManager;
    private MaTCGenerator maTCGenerator;

    //Constructor
    public DAODonNhapHangImpl() throws RemoteException {
        this.connectionStatic = new ConnectionStaticImpl();
        this.maTCGenerator = new MaTCGenerator();
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

    //Lấy đơn nhập hàng theo mã đơn nhập
    public Donnhaphang getDonNhapHang(String maDN) throws RemoteException{
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            //Tìm đơn nhập đúng mã đơn nhập
            Donnhaphang dnh = entityManager.find(Donnhaphang.class, maDN);

            //fetch Lazy nên cần lấy size để nó load dữ liệu
            dnh.getChiTietDonNhapSet().size();
            transaction.commit();
            return dnh;
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

    //Lấy các đơn nhập hàng theo loại trái cây
    @Override
    public ObservableList<Donnhaphang> getDonNhapHangByFruitType(String maLoaiTC) throws RemoteException {
        this.entityManager = connectionStatic.getConnection();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            TypedQuery<Donnhaphang> query = entityManager.createQuery("select s from Donnhaphang s join s.chiTietDonNhapSet d join d.traiCay_DonNhapHang f where f.loaiTraiCay_TraiCay.maloaiTc = ?1 order by s.ngayTaoDon desc, s.tinhTrang desc ", Donnhaphang.class);
            query.setParameter(1, maLoaiTC);
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

    //Lưu đơn nhập hàng
    @Override
    public boolean createDonNhapHang(Donnhaphang donNhapHang) throws RemoteException {
        //Khởi tạo nhà daoNhaCungCap
        DAONhaCungCap daoNhaCungCap = new DAONhaCungCapImpl();

        //Lấy kết nối
        this.entityManager = connectionStatic.getConnection();

        //Nếu tên nhà cung cấp không đúng thì không lưu
        if(daoNhaCungCap.getNhaCungCap(donNhapHang.getNhaCungCapDonNhapHang().getTenNcc(), this.entityManager))
            return false;

        //Tạo transaction
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            //Mở transaction
            transaction.begin();

            //Lưu ---> donnhaphang
            //entityManager quản lý: DonNhapHang
            entityManager.persist(donNhapHang);

            //Set để lưu tạm trái cây
            Set<Traicay> traiCaySet = new HashSet<>();

            //Set chứa chi tiết đơn nhập
            Set<Chitietdonnhap> chiTietDNSet = donNhapHang.getChiTietDonNhapSet();

            for (Chitietdonnhap item : chiTietDNSet) {
                //Khởi tạo traiCay
                Traicay traiCay = new Traicay();

                //Tìm trái cây đã tồn tại chưa
                //entityManager quản lý: TraiCay
                traiCay = entityManager.find(Traicay.class, maTCGenerator.getMaTC(item.getTraiCay_DonNhapHang()));

                //Nếu chưa tồn tại thì
                //Lưu ---> traicay
                //entityManager quản lý: TraiCay
                if(traiCay == null){
                    entityManager.persist(item.getTraiCay_DonNhapHang());
                    traiCay = item.getTraiCay_DonNhapHang();
                }

                //Thêm: traicay được quản lý vào set
                traiCaySet.add(traiCay);

                //Nối chi tiết đơn nhập với: donnhaphang và traicay được quản lý
                item.setTraiCay_DonNhapHang(traiCay);
                item.setChiTietDonNhap_DonNhapHang(donNhapHang);

                //Xác định khóa
                ChitietdonnhapPK pk = new ChitietdonnhapPK(donNhapHang.getMaDn(), item.getTraiCay_DonNhapHang().getMaTc());
                item.setId(pk);

                //Lưu ---> chitietdonnhap
                //entityManager quản lý: ChiTietDonNhap
                entityManager.persist(item);
            }

            //Lưu ---> nhacungcap_traicay
            //entityManager quản lý: NhaCungCap
            Nhacungcap ncc = entityManager.find(Nhacungcap.class, donNhapHang.getNhaCungCapDonNhapHang().getMaNcc());
            ncc.setTraiCayNhaCungCapSet(traiCaySet);

            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction.isActive())
                transaction.rollback();
            e.printStackTrace();
        } finally {
            this.entityManager.close();
        }
        return false;
    }
}
