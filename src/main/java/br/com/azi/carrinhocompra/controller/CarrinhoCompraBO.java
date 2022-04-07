package br.com.azi.carrinhocompra.controller;

import br.com.azi.carrinhocompra.exceptions.ClienteJaExistenteException;
import br.com.azi.carrinhocompra.exceptions.ClienteNaoExisteException;
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
import br.com.azi.carrinhocompra.model.ItemCompra;
import br.com.azi.carrinhocompra.model.ItemCompraDAO;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@ManagedBean(name="carrinhoController", eager = true)
@SessionScoped
public class CarrinhoCompraBO implements CarrinhoOperacoes
{
    private ItemCompra itemCompra;
    private ItemCarrinho itemCarrinho;
    private CarrinhoCompra carrinhoCompra;
    private Cliente cliente;
    private String msg;
    private List<ItemCompra> listaItemCompra;
    private List<Cliente> listaCliente;
    private List<ItemCarrinho> carrinhoCliente;
    /*
    *  Implementar o método criar carrinho compra
    *  Esse metodo deve retornar um carrinho de compra, seguindo a seguinte regra:
    *  1-Cada cliente deve possuir apenas um carrinho de compra (use o CPF como chave única);
    *  2-Ao tentar criar um novo carrinho de compra para um cliente que já possui um carrinho vinculado,
    *    retornar uma exception informando;
    * */

    public ItemCarrinho getItemCarrinho() {
        return itemCarrinho;
    }

    public void setItemCarrinho(ItemCarrinho itemCarrinho) {
        this.itemCarrinho = itemCarrinho;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ItemCompra getItemCompra() {
        return itemCompra;
    }

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    public void criarItemCompra() {
        ItemCompraDAO.getInstance().persist(itemCompra);
        setMsg("Item criado!");
        itemCompra = new ItemCompra();
        this.refresh();
    }

    public List<ItemCarrinho> getCarrinhoCliente(){
        try {
            Cliente client = ClienteDAO.getInstance().getByCpf(cliente.getCpf());
            CarrinhoCompra carrinhoCompra = CarrinhoCompraDAO.getInstance().getByCliente(client);
            carrinhoCliente = carrinhoCompra.getItemCarrinho();
            setMsg("Cliente encontrado!");
            return carrinhoCliente;
        } catch (Exception e){
            setMsg("Cliente não registrado!");
            return null;
        }
    }

    public void adicionarItemAoCarrinho(){
        try {
            Cliente client = ClienteDAO.getInstance().getByCpf(cliente.getCpf());
            CarrinhoCompra carrinho = CarrinhoCompraDAO.getInstance().getByCliente(client);
            ItemCompra item = ItemCompraDAO.getInstance().getByDescricao(itemCompra.getDescricao());
            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setItemCompra(item);
            itemCarrinho.setQuantidade(1);
            itemCarrinho.mudaValor(item.getPreco());
            itemCarrinho.setCarrinhoCompras(carrinho);
            inserirItem(itemCarrinho, carrinho);
            setMsg("Produto adicionado ao carrinho!");
            cliente = new Cliente();
            itemCompra = new ItemCompra();
        } catch (Exception e){
            setMsg("Dados inválidos!");
        }
    }

    public void criarCarrinhoCompra() throws ClienteJaExistenteException {
        if(ClienteDAO.getInstance().getByCpf(cliente.getCpf()) == null) {
            ClienteDAO.getInstance().persist(cliente);
            carrinhoCompra.setCliente(cliente);
            carrinhoCompra.setDataCriacao(new Date());
            CarrinhoCompraDAO.getInstance().persist(carrinhoCompra);
            setMsg("Cliente registrado!");
            cliente = new Cliente();
            carrinhoCompra = new CarrinhoCompra();
        } else{
            setMsg("Não foi possível criar um carrinho, já existe um cliente associado a um carrinho!");
        }
    }

    public String menuPrincipal(){
        setMsg("");
        return "index?faces-redirect=true";
    }

    public String listaProdutos() {
        setMsg("");
        return "lista-itemcompra?faces-redirect=true";
    }

    public String adicionarCarrinho() {
        setMsg("");
        return "item-ao-carrinho?faces-redirect=true";
    }

    public String cadastroClientes() {
        setMsg("");
        return "cadastro-clientes?faces-redirect=true";
    }

    public String cadastroItemCompra() {
        setMsg("");
        return "cadastro-itemcompra?faces-redirect=true";
    }

    public String listaClientes() {
        setMsg("");
        return "lista-clientes?faces-redirect=true";
    }

    public String listaCarrinhoClienteResult(){
        getCarrinhoCliente();
        return "lista-carrinho-cliente-result?faces-redirect=true";
    }

    public String listaCarrinhoCliente(){
        setMsg("");
        return "lista-carrinho-cliente?faces-redirect=true";
    }

    /*
    * Implementar a inserção de um item ao carrinho de compra de um cliente
    * 1-caso o item ja esteja presente no carrinho de compra, deve-se adicionar mais uma unidade ao item;
    * 2-use a descrição do item como chave única;
    * */
    public void inserirItem(ItemCarrinho itemCarrinho, CarrinhoCompra carrinho) {
        if(CarrinhoCompraDAO.getInstance().getById(carrinho.getId()).hasItemCarrinho(itemCarrinho)){
            insercaoItemExistente(itemCarrinho, carrinho);
        } else{
            insercaoItemNaoExistente(itemCarrinho,carrinho);
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

    public void excluirProduto(ItemCarrinho itemCarrinho){
        System.out.println(itemCarrinho.getItemDescricao());
        CarrinhoCompraDAO.getInstance().getById(itemCarrinho.getCarrinhoCompras().getId()).removeItemCarrinho(itemCarrinho);
        CarrinhoCompraDAO.getInstance().persist(itemCarrinho.getCarrinhoCompras());
        ItemCarrinho item = ItemCarrinhoDAO.getInstance().getById(itemCarrinho.getId());
        ItemCarrinhoDAO.getInstance().remove(item);
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

    public List<Cliente> getListaClientes(){
        return ClienteDAO.getInstance().getClients();
    }

    public List<ItemCompra> getListaItemCompra(){
        return ItemCompraDAO.getInstance().getItensCompra();
    }


    @PostConstruct
    public void init() {
        cliente = new Cliente();
        itemCompra = new ItemCompra();
        itemCarrinho = new ItemCarrinho();
        carrinhoCompra = new CarrinhoCompra();
    }

}
