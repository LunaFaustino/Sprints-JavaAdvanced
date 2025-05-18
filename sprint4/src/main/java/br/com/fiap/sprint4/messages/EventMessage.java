package br.com.fiap.sprint4.messages;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EventMessage implements Serializable {

    private String id;
    private String tipo;
    private String acao;
    private LocalDateTime dataHora;
    private String detalhes;

    public EventMessage() {
    }

    public EventMessage(String id, String tipo, String acao, String detalhes) {
        this.id = id;
        this.tipo = tipo;
        this.acao = acao;
        this.dataHora = LocalDateTime.now();
        this.detalhes = detalhes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", acao='" + acao + '\'' +
                ", dataHora=" + dataHora +
                ", detalhes='" + detalhes + '\'' +
                '}';
    }
}
