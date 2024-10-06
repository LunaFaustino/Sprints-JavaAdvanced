package br.com.fiap.sprint1.dtos;

import br.com.fiap.sprint1.models.Genero;
import br.com.fiap.sprint1.models.Status;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public class PacienteDTO {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50,message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private Integer idade;

    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11,max = 11,message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private Integer clinicaId;

    private EnderecoDTO endereco;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "O nome é obrigatório") @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres") String nome) {
        this.nome = nome;
    }

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    public Integer getIdade() {
        return idade;
    }

    public void setIdade(@NotNull(message = "A idade é obrigatória") @Min(value = 0, message = "A idade não pode ser negativa") Integer idade) {
        this.idade = idade;
    }

    public @PastOrPresent(message = "A data de nascimento não pode ser no futuro") LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@PastOrPresent(message = "A data de nascimento não pode ser no futuro") LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @NotBlank(message = "O CPF é obrigatório") @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos") String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank(message = "O CPF é obrigatório") @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos") String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public @NotNull(message = "O status é obrigatório") Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "O status é obrigatório") Status status) {
        this.status = status;
    }

    public Integer getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(Integer clinicaId) {
        this.clinicaId = clinicaId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
