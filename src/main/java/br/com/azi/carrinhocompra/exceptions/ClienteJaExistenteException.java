package br.com.azi.carrinhocompra.exceptions;

public class ClienteJaExistenteException extends Exception {
    @Override
    public String getMessage(){
        return "Não foi possível criar um carrinho, já existe um cliente associado a um carrinho!";
    }
}
