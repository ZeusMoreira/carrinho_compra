package br.com.azi.carrinhocompra;

import br.com.azi.carrinhocompra.controller.CarrinhoCompraBO;
import br.com.azi.carrinhocompra.interfaces.CarrinhoOperacoes;
import br.com.azi.carrinhocompra.model.CarrinhoCompra;
import br.com.azi.carrinhocompra.model.Cliente;
import br.com.azi.carrinhocompra.model.ClienteDAO;
import br.com.azi.carrinhocompra.model.ItemCarrinho;
import br.com.azi.carrinhocompra.model.ItemCarrinhoDAO;
import br.com.azi.carrinhocompra.model.ItemCompra;
import br.com.azi.carrinhocompra.model.ItemCompraDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
       /*
           Interface para operações: CarrinhoOperacoes
           Fluxo: Criar cliente Cliente() -> Criar carrinho do cliente CarrinhoCompra() ->
                  Criar itens disponíveis para o cliente ItemCompra() ->
                  Criar item que será adicionado no carrinho ItemCarrinho()->
                  Inserir item no carrinho carrinhoOperacoes.inserirItem() ->
                  Remover carrinhoOperacoes.removerItem() ||
                  Inserir outro item no carrinho carrinhoOperacoes.inserirItem() ||
                  Retornar valor total do carrinho valorTotalCarrinho()
        */
//       CarrinhoOperacoes carrinhoOperacoes = new CarrinhoCompraBO();
//
//       Cliente cliente = new Cliente();
//       cliente.setNome("Zeus");
//       cliente.setCpf("077.802.481-43");
//
//       CarrinhoCompra carrinhoCompra = carrinhoOperacoes.criarCarrinhoCompra(cliente);
//
//       ItemCompra itemCompra = new ItemCompra();
//       itemCompra.setDescricao("leite condensado");
//       itemCompra.setPreco(BigDecimal.valueOf(5.99));
//       ItemCompra itemCompra2 = new ItemCompra();
//       itemCompra2.setDescricao("leite");
//       itemCompra2.setPreco(BigDecimal.valueOf(4.50));
//
//       ItemCarrinho itemCarrinho = new ItemCarrinho();
//       itemCarrinho.setItemCompra(itemCompra);
//       itemCarrinho.setQuantidade(2);
//       itemCarrinho.mudaValor(itemCompra.getPreco());
//
//       ItemCarrinho itemCarrinho2 = new ItemCarrinho();
//       itemCarrinho2.setItemCompra(itemCompra2);
//       itemCarrinho2.setQuantidade(12);
//       itemCarrinho2.mudaValor(itemCompra2.getPreco());
//
//       carrinhoOperacoes.inserirItem(itemCarrinho, carrinhoCompra);
//       carrinhoOperacoes.inserirItem(itemCarrinho2, carrinhoCompra);
//
//       carrinhoOperacoes.valorTotalCarrinho(carrinhoCompra);
//
//       carrinhoOperacoes.removerItem(itemCarrinho, carrinhoCompra);
//       carrinhoOperacoes.valorTotalCarrinho(carrinhoCompra);

    }
}
