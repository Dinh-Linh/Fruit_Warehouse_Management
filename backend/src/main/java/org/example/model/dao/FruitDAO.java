package org.example.model.dao;

import org.example.model.entity.Fruit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FruitDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("fruitWarehousePU");
    private EntityManager em = emf.createEntityManager();
    public Fruit findFruitById(String fruitId){
        return em.find(Fruit.class, fruitId);
    }

    public void close(){
        em.close();
        emf.close();
    }
}