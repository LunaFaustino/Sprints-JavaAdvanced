package br.com.fiap.sprint3.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clinica")
public class Clinica {

    @Id
    @Column(name = "nr_cnpj", nullable = false, length = 14)
    private String cnpj;

    @Column(name = "nm_razaoSocial", nullable = false)
    private String razaoSocial;

    @Column(name = "nm_nomeFantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "ds_email", nullable = false)
    private String email;

    @Column(name = "nr_tel")
    private Long telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_porte", length = 10)
    private Porte porte;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 10)
    private Status status;

    @Column(name = "dt_abertura")
    private LocalDate dataAbertura;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false)
    private LocalDate dataCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Paciente> pacientes = new ArrayList<>();

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Dentista> dentistas = new ArrayList<>();

    public Clinica() {}

    public Clinica(String cnpj, String razaoSocial, String nomeFantasia, String email, Status status) {
        this.setCnpj(cnpj);
        this.setRazaoSocial(razaoSocial);
        this.setNomeFantasia(nomeFantasia);
        this.setEmail(email);
        this.status = status;
    }

    public Clinica(String cnpj, String razaoSocial, String nomeFantasia, String email,
                   Long telefone, Porte porte, Status status, LocalDate dataAbertura, Endereco endereco) {
        this.setCnpj(cnpj);
        this.setRazaoSocial(razaoSocial);
        this.setNomeFantasia(nomeFantasia);
        this.setEmail(email);
        this.telefone = telefone;
        this.porte = porte;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.endereco = endereco;
    }

    public boolean validarCnpj() {
        if (this.cnpj == null) return false;

        String cnpjLimpo = this.cnpj.replaceAll("[^0-9]", "");

        return cnpjLimpo.length() == 14;
    }

    public boolean validarEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return this.email == null || !this.email.matches(emailRegex);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj.replaceAll("[^0-9]", "");
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
            throw new IllegalArgumentException("Razão Social não pode ser vazia");
        }
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        if (nomeFantasia == null || nomeFantasia.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome Fantasia não pode ser vazio");
        }
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    @Override
    public String toString() {
        return "Clinica{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", email='" + email + '\'' +
                ", telefone=" + telefone +
                ", porte=" + porte +
                ", status=" + status +
                ", dataAbertura=" + dataAbertura +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}