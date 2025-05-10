package br.com.fiap.sprint4.dtos;

import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.models.Porte;
import br.com.fiap.sprint4.models.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

public class ClinicaDTO {

    @NotBlank(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "O nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Long telefone;

    private String porte;

    @NotNull(message = "O status é obrigatório")
    private String status;

    private LocalDate dataAbertura;

    @Valid
    private EnderecoDTO endereco;

    public ClinicaDTO() {
        this.endereco = new EnderecoDTO();
    }

    public ClinicaDTO(Clinica clinica) {
        this.cnpj = clinica.getCnpj();
        this.razaoSocial = clinica.getRazaoSocial();
        this.nomeFantasia = clinica.getNomeFantasia();
        this.email = clinica.getEmail();
        this.telefone = clinica.getTelefone();

        this.porte = clinica.getPorte() != null ? clinica.getPorte().name() : null;
        this.status = clinica.getStatus() != null ? clinica.getStatus().name() : null;

        this.dataAbertura = clinica.getDataAbertura();

        if (clinica.getEndereco() != null) {
            this.endereco = new EnderecoDTO(clinica.getEndereco());
        }
    }

    public Clinica toEntity() {
        Clinica clinica = new Clinica();
        clinica.setCnpj(this.cnpj);
        clinica.setRazaoSocial(this.razaoSocial);
        clinica.setNomeFantasia(this.nomeFantasia);
        clinica.setEmail(this.email);
        clinica.setTelefone(this.telefone);

        if (this.porte != null && !this.porte.isEmpty()) {
            clinica.setPorte(Porte.valueOf(this.porte));
        }

        if (this.status != null && !this.status.isEmpty()) {
            clinica.setStatus(Status.valueOf(this.status));
        }

        clinica.setDataAbertura(this.dataAbertura);

        if (this.endereco != null) {
            clinica.setEndereco(this.endereco.toEntity());
        }

        return clinica;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
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

    public String getPorte() {
        return porte;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClinicaDTO that = (ClinicaDTO) o;
        return Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }

    @Override
    public String toString() {
        return "ClinicaDTO{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", email='" + email + '\'' +
                ", telefone=" + telefone +
                ", porte=" + porte +
                ", status=" + status +
                ", dataAbertura=" + dataAbertura +
                '}';
    }
}