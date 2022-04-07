package br.com.azi.carrinhocompra.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carrinhoCompra")
public class CarrinhoCompra {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "dataCriacao")
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "clienteCpf", referencedColumnName = "cpf")
    private Cliente cliente;

    @OneToMany
    @Column(name = "itemCarrinho")
    private List<ItemCarrinho> itemCarrinho = new ArrayList<ItemCarrinho>();

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemCarrinho> getItemCarrinho() {
        return itemCarrinho;
    }

    public boolean hasItemCarrinho(ItemCarrinho itemCarrinho) {
        for (ItemCarrinho carrinho : this.itemCarrinho) {
            if (carrinho.getItemCompra().getDescricao().equals(itemCarrinho.getItemCompra().getDescricao())) {
                return true;
            }
        }
        return false;
    }

    public void addItemCarrinho(ItemCarrinho itemCarrinho) {
        this.itemCarrinho.add(itemCarrinho);
    }

    public void removeItemCarrinho(ItemCarrinho itemCarrinho) {
        this.itemCarrinho.remove(itemCarrinho);
    }
}
