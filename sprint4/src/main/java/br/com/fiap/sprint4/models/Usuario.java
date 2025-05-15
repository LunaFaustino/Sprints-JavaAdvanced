package br.com.fiap.sprint4.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @Column(name = "nm_usuario", nullable = false, unique = true)
    private String username;

    @Column(name = "ds_senha", nullable = false)
    private String password;

    @Column(name = "nm_nome", nullable = false)
    private String nome;

    @Column(name = "ds_email", nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_perfil",
            joinColumns = @JoinColumn(name = "nm_usuario"),
            inverseJoinColumns = @JoinColumn(name = "nm_perfil"))
    private Set<Perfil> perfis = new HashSet<>();

    @Column(name = "st_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public Usuario() {
    }

    public Usuario(String username, String password, String nome, String email) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addPerfil(Perfil perfil) {
        if (this.perfis == null) {
            this.perfis = new HashSet<>();
        }
        this.perfis.add(perfil);
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
