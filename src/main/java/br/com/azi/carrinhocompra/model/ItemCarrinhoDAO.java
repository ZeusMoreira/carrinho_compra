package br.com.azi.carrinhocompra.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ItemCarrinhoDAO {

    private static ItemCarrinhoDAO instance;
    protected EntityManager entityManager;

    public static ItemCarrinhoDAO getInstance(){
        if (instance == null){
            instance = new ItemCarrinhoDAO();
        }
        return instance;
    }

    private ItemCarrinhoDAO() {
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

    public ItemCarrinho getById(final Long id) {
        return entityManager.find(ItemCarrinho.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<ItemCarrinho> findAll() {
        return entityManager.createQuery("FROM " +
                ItemCarrinho.class.getName()).getResultList();
    }

    public void update(ItemCarrinho itemCarrinhoOld, ItemCarrinho itemCarrinho){
        try {
            entityManager.getTransaction().begin();
            Integer quantidade = itemCarrinho.getQuantidade() + itemCarrinhoOld.getQuantidade();
            BigDecimal valor = itemCarrinho.getValor().add(itemCarrinhoOld.getValor());

            StringBuilder updateHql = new StringBuilder();
            updateHql.append(" UPDATE " + ItemCarrinho.class.getName() + " ic");
            updateHql.append(" SET ic.quantidade = " + quantidade + ", ic.valor = " + valor);
            updateHql.append(" WHERE ic.id = " + itemCarrinhoOld.getId());
            itemCarrinhoOld.setQuantidade(quantidade);
            itemCarrinhoOld.setValor(valor);
            entityManager.createQuery(updateHql.toString()).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void persist(ItemCarrinho itemCarrinho) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(itemCarrinho);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(ItemCarrinho itemCarrinho) {
        try {
            entityManager.getTransaction().begin();
            itemCarrinho = entityManager.find(ItemCarrinho.class, itemCarrinho.getId());
            entityManager.remove(itemCarrinho);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

}
