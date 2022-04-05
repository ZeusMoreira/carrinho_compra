package br.com.azi.carrinhocompra.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteDAO {

    private static ClienteDAO instance;
    protected EntityManager entityManager;

    public static ClienteDAO getInstance(){
        if (instance == null){
            instance = new ClienteDAO();
        }
        return instance;
    }

    private ClienteDAO() {
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

    public Cliente getByCpf(final String cpf) {
        return entityManager.find(Cliente.class, cpf);
    }

    public void persist(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
