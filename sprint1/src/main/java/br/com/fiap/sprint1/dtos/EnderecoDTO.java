package br.com.fiap.sprint1.dtos;


import jakarta.validation.constraints.NotBlank;

public class EnderecoDTO {

    private Integer id;

    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    private Integer numero;

    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "O cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "O logradouro é obrigatório") String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(@NotBlank(message = "O logradouro é obrigatório") String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public @NotBlank(message = "O bairro é obrigatório") String getBairro() {
        return bairro;
    }

    public void setBairro(@NotBlank(message = "O bairro é obrigatório") String bairro) {
        this.bairro = bairro;
    }

    public @NotBlank(message = "O cidade é obrigatória") String getCidade() {
        return cidade;
    }

    public void setCidade(@NotBlank(message = "O cidade é obrigatória") String cidade) {
        this.cidade = cidade;
    }

    public @NotBlank(message = "O CEP é obrigatório") String getCep() {
        return cep;
    }

    public void setCep(@NotBlank(message = "O CEP é obrigatório") String cep) {
        this.cep = cep;
    }

    public @NotBlank(message = "O estado é obrigatório") String getEstado() {
        return estado;
    }

    public void setEstado(@NotBlank(message = "O estado é obrigatório") String estado) {
        this.estado = estado;
    }
}
