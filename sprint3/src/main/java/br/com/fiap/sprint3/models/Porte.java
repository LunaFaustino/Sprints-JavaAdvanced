package br.com.fiap.sprint3.models;

public enum Porte {
    PEQUENA("Pequena"),
    MEDIA("Média"),
    GRANDE("Grande");

    private final String descricao;

    Porte(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }


}
