package br.com.azi.carrinhocompra.exceptions;

public class ItemNaoEstaNesseCarrinhoException extends Exception{
    @Override
    public String getMessage(){
        return "Não foi possível remover, este item não está nesse carrinho!";
    }
}
