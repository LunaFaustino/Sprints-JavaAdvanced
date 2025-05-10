package br.com.fiap.sprint4.dtos;

import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.models.Dentista;
import br.com.fiap.sprint4.models.Genero;
import br.com.fiap.sprint4.models.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class DentistaDTO {

    @NotBlank(message = "O CRO é obrigatório")
    @Size(max = 9, message = "O CRO deve ter no máximo 9 caracteres")
    private String cro;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    @NotBlank(message = "A clínica é obrigatória")
    private String clinicaCnpj;

    private String nomeClinica;

    public DentistaDTO() {
    }

    public DentistaDTO(Dentista dentista) {
        this.cro = dentista.getCro();
        this.nome = dentista.getNome();
        this.email = dentista.getEmail();
        this.telefone = dentista.getTelefone();
        this.genero = dentista.getGenero();
        this.status = dentista.getStatus();

        if (dentista.getClinica() != null) {
            this.clinicaCnpj = dentista.getClinica().getCnpj();
            this.nomeClinica = dentista.getClinica().getNomeFantasia();
        }
    }

    public Dentista toEntity() {
        Dentista dentista = new Dentista();
        dentista.setCro(this.cro);
        dentista.setNome(this.nome);
        dentista.setEmail(this.email);
        dentista.setTelefone(this.telefone);
        dentista.setGenero(this.genero);
        dentista.setStatus(this.status);

        if (this.clinicaCnpj != null) {
            Clinica clinica = new Clinica();
            clinica.setCnpj(this.clinicaCnpj);
            dentista.setClinica(clinica);
        }

        return dentista;
    }

    public String getCro() {
        return cro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getClinicaCnpj() {
        return clinicaCnpj;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public void setClinicaCnpj(String clinicaCnpj) {
        this.clinicaCnpj = clinicaCnpj;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DentistaDTO that = (DentistaDTO) o;
        return Objects.equals(cro, that.cro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cro);
    }

    @Override
    public String toString() {
        return "DentistaDTO{" +
                "cro='" + cro + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone=" + telefone +
                ", genero=" + genero +
                ", status=" + status +
                ", clinicaCnpj='" + clinicaCnpj + '\'' +
                ", nomeClinica='" + nomeClinica + '\'' +
                '}';
    }
}
