package com.example.chatbotapp;

public class ChatModal {

    private String mensagem;
    private String enviar;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEnviar() {
        return enviar;
    }

    public void setEnviar(String enviar) {
        this.enviar = enviar;
    }

    public ChatModal(String mensagem, String enviar) {
        this.mensagem = mensagem;
        this.enviar = enviar;
    }


}
