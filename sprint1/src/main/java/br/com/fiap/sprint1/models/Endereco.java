package br.com.fiap.sprint1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_endereco")
@SequenceGenerator(name = "endereco", sequenceName = "sq_tb_endereco",allocationSize = 1)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
    @Column(name = "id_endereco")
    private int id;

    @Column(name = "ds_logradouro", nullable = false)
    private String logradouro;

    @Column(name = "nr_numero")
    private int numero;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "ds_bairro", nullable = false)
    private String bairro;

    @Column(name = "ds_cidade", nullable = false)
    private String cidade;

    @Column(name = "ds_estado", nullable = false)
    private String estado;

    @Column(name = "nr_cep", nullable = false, length = 9)
    private String cep;

    public Endereco() {
    }

    public Endereco(String logradouro, int numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
