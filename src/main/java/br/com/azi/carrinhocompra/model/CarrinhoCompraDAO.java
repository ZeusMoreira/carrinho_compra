package br.com.azi.carrinhocompra.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CarrinhoCompraDAO {

    private static CarrinhoCompraDAO instance;
    protected EntityManager entityManager;

    public static CarrinhoCompraDAO getInstance(){
        if (instance == null){
            instance = new CarrinhoCompraDAO();
        }
        return instance;
    }

    private CarrinhoCompraDAO() {
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

    public CarrinhoCompra getById(final Long id) {
        return entityManager.find(CarrinhoCompra.class, id);
    }

    public CarrinhoCompra getByCliente(Cliente cliente) {
        CarrinhoCompra carrinhoCompra = null;
        try {
            entityManager.getTransaction().begin();
            carrinhoCompra = entityManager.createQuery(
                    "SELECT cr from CarrinhoCompra cr join cr.cliente cl where cl.cpf = :cpf and cr.cliente = :cliente ", CarrinhoCompra.class)
                    .setParameter("cpf", cliente.getCpf())
                    .setParameter("cliente", cliente).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return carrinhoCompra;
    }

    public void persist(CarrinhoCompra carrinhoCompra) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(carrinhoCompra);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

}
