package br.com.azi.carrinhocompra.exceptions;

public class ClienteNaoExisteException extends Exception{
    @Override
    public String getMessage(){
        return "Este cliente não existe!";
    }
}

