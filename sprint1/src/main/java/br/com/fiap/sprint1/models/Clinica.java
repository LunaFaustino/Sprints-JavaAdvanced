package br.com.fiap.sprint1.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clinica")
@SequenceGenerator(name = "clinica", sequenceName = "sq_tb_clinica", allocationSize = 1)
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinica")
    @Column(name = "id_clinica")
    private int id;

    @Column(name = "nm_razaoSocial", nullable = false)
    private String razaoSocial;

    @Column(name = "nm_nomeFantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "nr_cnpj", nullable = false, length = 14)
    private String cnpj;

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

    public Clinica(String razaoSocial, String nomeFantasia, String cnpj, String email, Long telefone, Porte porte, Status status, LocalDate dataAbertura, Endereco endereco) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.porte = porte;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
