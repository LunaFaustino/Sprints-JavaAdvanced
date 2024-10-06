package br.com.fiap.sprint1.dtos;


import br.com.fiap.sprint1.models.Genero;
import br.com.fiap.sprint1.models.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DentistaDTO {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotBlank(message = "O CRO é obrigatório")
    @Size(max = 9,message = "O CRO deve ter no máximo 9 caracteres")
    private String cro;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private Integer clinicaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "O nome é obrigatório") @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O CRO é obrigatório") @Size(max = 9, message = "O CRO deve ter no máximo 9 caracteres") String getCro() {
        return cro;
    }

    public void setCro(@NotBlank(message = "O CRO é obrigatório") @Size(max = 9, message = "O CRO deve ter no máximo 9 caracteres") String cro) {
        this.cro = cro;
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
}
