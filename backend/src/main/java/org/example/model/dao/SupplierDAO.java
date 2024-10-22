package org.example.model.dao;
import org.example.model.entity.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("fruitWarehousePU");
    private EntityManager em = emf.createEntityManager();

    public void createSupplier(Supplier Supplier) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            System.out.println("Đang lưu..." + Supplier.getSupplierName());
            em.persist(Supplier);
            em.getTransaction().commit();
            System.out.println("Đã lưu nhà cung cấp: " + Supplier.getSupplierName());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.err.println("Lỗi khi lưu nhà cung cấp: " + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Supplier findSupplierById(String supplierId) {
        return em.find(Supplier.class, supplierId);
    }

    public List<Supplier> findAllSuppliers() {
        List<Supplier> Suppliers = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Suppliers = em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return Suppliers;
    }


    public void close() {
        em.close();
        if (emf.isOpen()) {
            emf.close();
        }
    }
}