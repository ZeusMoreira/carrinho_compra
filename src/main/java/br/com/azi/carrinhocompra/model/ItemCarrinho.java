package br.com.azi.carrinhocompra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "itemCarrinho")
public class ItemCarrinho {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "itemdescricao", referencedColumnName = "descricao")
    private ItemCompra itemCompra;

    @ManyToOne
    @JoinColumn(name = "carrinhoCompra", referencedColumnName = "id")
    private CarrinhoCompra carrinhoCompra;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "quantidade")
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemCompra getItemCompra() {
        return itemCompra;
    }

    public Long getCarrinhoId(){
        return getCarrinhoCompras().getId();
    }

    public String getItemDescricao(){
        return itemCompra.getDescricao();
    }

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    public CarrinhoCompra getCarrinhoCompras() {
        return carrinhoCompra;
    }

    public void setCarrinhoCompras(CarrinhoCompra carrinhoCompras) {
        this.carrinhoCompra = carrinhoCompras;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void mudaValor(BigDecimal valor) {
        this.valor = valor.multiply(BigDecimal.valueOf(quantidade));
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;

    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
