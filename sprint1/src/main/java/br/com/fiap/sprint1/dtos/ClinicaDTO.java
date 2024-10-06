package br.com.fiap.sprint1.dtos;

import br.com.fiap.sprint1.models.Porte;
import br.com.fiap.sprint1.models.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ClinicaDTO {

    private Integer id;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "O nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Long telefone;

    private Porte porte;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private LocalDate dataAbertura;

    @Valid
    private EnderecoDTO endereco;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "A razão social é obrigatória") String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(@NotBlank(message = "A razão social é obrigatória") String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public @NotBlank(message = "O nome fantasia é obrigatório") String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(@NotBlank(message = "O nome fantasia é obrigatório") String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public @NotBlank(message = "O CNPJ é obrigatório") @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "O CNPJ é obrigatório") @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank(message = "O e-mail é obrigatório") @Email(message = "Formato de e-mail inválido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "Formato de e-mail inválido") String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public @NotNull(message = "O status é obrigatório") Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "O status é obrigatório") Status status) {
        this.status = status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public @Valid EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(@Valid EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
