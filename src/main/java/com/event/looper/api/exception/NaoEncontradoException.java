package com.event.looper.api.exception;

public class NaoEncontradoException extends RuntimeException{
    public NaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
