package br.com.fiap.sprint4.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_endereco")
@SequenceGenerator(name = "endereco", sequenceName = "sq_tb_endereco", allocationSize = 1)
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

    public Endereco() {}



    public boolean validarCep() {
        // Formato esperado: 00000-000 ou 00000000
        String cepRegex = "^\\d{5}-?\\d{3}$";
        return cep == null || !cep.matches(cepRegex);
    }

    public String getEnderecoResumido() {
        return String.format("%s, %d - %s, %s/%s",
                logradouro, numero, bairro, cidade, estado);
    }

    public int getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }
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
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro não pode ser vazio");
        }
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia");
        }
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser vazio");
        }
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser vazio");
        }
        this.cep = cep.replaceAll("[^0-9-]", "");
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", numero=" + numero +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
