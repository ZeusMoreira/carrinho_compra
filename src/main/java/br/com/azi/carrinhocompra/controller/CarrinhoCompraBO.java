package br.com.azi.carrinhocompra.controller;

import br.com.azi.carrinhocompra.exceptions.ClienteJaExistenteException;
import br.com.azi.carrinhocompra.exceptions.ItemNaoEstaNesseCarrinhoException;
import br.com.azi.carrinhocompra.interfaces.CarrinhoOperacoes;
import br.com.azi.carrinhocompra.model.CarrinhoCompra;
import br.com.azi.carrinhocompra.model.CarrinhoCompraDAO;
import br.com.azi.carrinhocompra.model.Cliente;
import br.com.azi.carrinhocompra.model.ClienteDAO;
import br.com.azi.carrinhocompra.model.ItemCarrinho;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.azi.carrinhocompra.model.ItemCarrinhoDAO;
import br.com.azi.carrinhocompra.model.ItemCompraDAO;
import org.apache.commons.lang3.RandomStringUtils;

public class CarrinhoCompraBO implements CarrinhoOperacoes
{
    /*
    *  Implementar o método criar carrinho compra
    *  Esse metodo deve retornar um carrinho de compra, seguindo a seguinte regra:
    *  1-Cada cliente deve possuir apenas um carrinho de compra (use o CPF como chave única);
    *  2-Ao tentar criar um novo carrinho de compra para um cliente que já possui um carrinho vinculado,
    *    retornar uma exception informando;
    * */
    public CarrinhoCompra criarCarrinhoCompra(Cliente cliente) throws ClienteJaExistenteException {
        System.out.println(cliente.getNome());
        System.out.println(cliente.getCpf());
        if(ClienteDAO.getInstance().getByCpf(cliente.getCpf()) == null) {
            ClienteDAO.getInstance().persist(cliente);
            CarrinhoCompra carrinhoCompra = new CarrinhoCompra();
            carrinhoCompra.setCliente(cliente);
            carrinhoCompra.setDataCriacao(new Date());
            CarrinhoCompraDAO.getInstance().persist(carrinhoCompra);
            return carrinhoCompra;
        } else{
            throw new ClienteJaExistenteException();
        }
    }

    /*
    * Implementar a inserção de um item ao carrinho de compra de um cliente
    * 1-caso o item ja esteja presente no carrinho de compra, deve-se adicionar mais uma unidade ao item;
    * 2-use a descrição do item como chave única;
    * */
    public void inserirItem(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho) {
        if(!CarrinhoCompraDAO.getInstance().getById(carrinho.getId()).hasItemCarrinho(itemCarrinho)){
            insercaoItemNaoExistente(itemCarrinho,carrinho);
        } else{
            insercaoItemExistente(itemCarrinho, carrinho);
        }
    }

    private void insercaoItemNaoExistente(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho){
        if(ItemCompraDAO.getInstance().getByDescricao(itemCarrinho.getItemDescricao()) == null){
            ItemCompraDAO.getInstance().persist(itemCarrinho.getItemCompra());
        }
        itemCarrinho.setCarrinhoCompras(carrinho);
        carrinho.addItemCarrinho(itemCarrinho);
        ItemCarrinhoDAO.getInstance().persist(itemCarrinho);
        CarrinhoCompraDAO.getInstance().persist(carrinho);
    }

    private void insercaoItemExistente(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho){
        List<ItemCarrinho> itemCarrinhos = ItemCarrinhoDAO.getInstance().findAll();
        for (ItemCarrinho item: itemCarrinhos) {
            boolean mesmoCarrinho = item.getCarrinhoId().equals(carrinho.getId());
            boolean mesmoItemDescricao = item.getItemDescricao().equals(itemCarrinho.getItemDescricao());
            if(mesmoCarrinho && mesmoItemDescricao){
                ItemCarrinhoDAO.getInstance().update(item, itemCarrinho);
                break;
            }
        }
        CarrinhoCompraDAO.getInstance().persist(carrinho);
    }

//    /*
//    * Implementar a remoção de um item no carrinho
//    * */
    public void removerItem(ItemCarrinho itemCarrinho, CarrinhoCompra carrinhoCompra) throws Exception{
        if(CarrinhoCompraDAO.getInstance().getById(carrinhoCompra.getId()).hasItemCarrinho(itemCarrinho)){
            CarrinhoCompraDAO.getInstance().getById(carrinhoCompra.getId()).removeItemCarrinho(itemCarrinho);
            CarrinhoCompraDAO.getInstance().persist(carrinhoCompra);
            ItemCarrinhoDAO.getInstance().remove(itemCarrinho);
        } else{
            throw new ItemNaoEstaNesseCarrinhoException();
        }
    }

    /*
    * Deve-se somar o valor total do carrinho de compras
    * */
    public BigDecimal valorTotalCarrinho(CarrinhoCompra carrinhoCompra){
        List<ItemCarrinho> itensCarrinho = CarrinhoCompraDAO.getInstance().getById(carrinhoCompra.getId()).getItemCarrinho();
        BigDecimal valorTotalCarrinho = BigDecimal.ZERO;
        for (ItemCarrinho item: itensCarrinho) {
            valorTotalCarrinho = valorTotalCarrinho.add(ItemCarrinhoDAO.getInstance().getById(item.getId()).getValor());
        }
        System.out.println("Valor total: " + valorTotalCarrinho);
        return valorTotalCarrinho;
    }

}
