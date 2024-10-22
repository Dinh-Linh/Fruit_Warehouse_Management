package org.example.model.dao;

import org.example.model.entity.ImportOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ImportOrderDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("fruitWarehousePU");
    private EntityManager em = emf.createEntityManager();

    public void createImportOrder(ImportOrder importOrder){
        em.getTransaction().begin();
        em.persist(importOrder);
        em.getTransaction().commit();
    }

    public List<ImportOrder> getAllImportOrder(){
        return em.createQuery("select d from ImportOrder d", ImportOrder.class).getResultList();
    }

    public void close(){
        em.close();
        emf.close();
    }
}
