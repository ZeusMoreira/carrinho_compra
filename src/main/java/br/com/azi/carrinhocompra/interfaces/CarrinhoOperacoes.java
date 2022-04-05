package br.com.azi.carrinhocompra.interfaces;

import br.com.azi.carrinhocompra.exceptions.ItemNaoEstaNesseCarrinhoException;
import br.com.azi.carrinhocompra.model.CarrinhoCompra;
import br.com.azi.carrinhocompra.model.Cliente;
import br.com.azi.carrinhocompra.model.ItemCarrinho;

import java.math.BigDecimal;

public interface CarrinhoOperacoes {
    CarrinhoCompra criarCarrinhoCompra(Cliente cliente) throws Exception;
    void inserirItem(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho);
    void removerItem(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho) throws Exception;
    BigDecimal valorTotalCarrinho(CarrinhoCompra carrinhoCompra);
}
