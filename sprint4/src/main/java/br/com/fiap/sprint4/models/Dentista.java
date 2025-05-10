package br.com.fiap.sprint4.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tb_dentista")
public class Dentista {

    @Id
    @Column(name = "nr_cro", nullable = false, length = 9)
    private String cro;

    @Column(name = "nm_dentista", nullable = false, length = 50)
    private String nome;

    @Column(name = "ds_email", nullable = false)
    private String email;

    @Column(name = "nr_tel")
    private Long telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_genero", length = 10)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 10)
    private Status status;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false)
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_clinica", nullable = false)
    private Clinica clinica;

    public Dentista() {}

    public boolean validarCro() {
        // Formato esperado: UF-NNNNN (ex: SP-12345)
        String croRegex = "^[A-Z]{2}-\\d{5}$";
        return cro != null && cro.matches(croRegex);
    }

    public boolean validarEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email == null || !email.matches(emailRegex);
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        if (cro == null || cro.trim().isEmpty()) {
            throw new IllegalArgumentException("CRO não pode ser vazio");
        }
        this.cro = cro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
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

    public Genero getGenero() {
        return genero;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        this.status = status;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        if (clinica == null) {
            throw new IllegalArgumentException("Clínica não pode ser nula");
        }
        this.clinica = clinica;
    }

    @Override
    public String toString() {
        return "Dentista{" +
                "cro='" + cro + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone=" + telefone +
                ", genero=" + genero +
                ", status=" + status +
                ", dataCadastro=" + dataCadastro +
                ", clinica=" + (clinica != null ? clinica.getNomeFantasia() : "null") +
                '}';
    }
}
