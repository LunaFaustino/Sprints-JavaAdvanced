package br.com.fiap.sprint4.dtos;

import br.com.fiap.sprint4.models.Perfil;
import br.com.fiap.sprint4.models.Status;
import br.com.fiap.sprint4.models.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioDTO {

    @NotBlank(message = "O username é obrigatório")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    private String username;

    private String password;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmPassword;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Set<String> perfisIds = new HashSet<>();

    private Status status = Status.ATIVO;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.username = usuario.getUsername();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.status = usuario.getStatus();
        this.perfisIds = usuario.getPerfis().stream().map(Perfil::getNome).collect(Collectors.toSet());
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setUsername(this.username);
        usuario.setPassword(this.password);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setStatus(this.status);
        return usuario;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public Set<String> getPerfisIds() {
        return perfisIds;
    }

    public void setPerfisIds(Set<String> perfisIds) {
        this.perfisIds = perfisIds;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
