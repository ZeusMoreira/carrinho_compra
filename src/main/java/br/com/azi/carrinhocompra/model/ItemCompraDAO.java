package br.com.azi.carrinhocompra.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ItemCompraDAO {

    private static ItemCompraDAO instance;
    protected EntityManager entityManager;

    public static ItemCompraDAO getInstance(){
        if (instance == null){
            instance = new ItemCompraDAO();
        }
        return instance;
    }

    private ItemCompraDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public ItemCompra getByDescricao(final String descricao) {
        return entityManager.find(ItemCompra.class, descricao);
    }


    public void persist(ItemCompra ItemCompra) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ItemCompra);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}