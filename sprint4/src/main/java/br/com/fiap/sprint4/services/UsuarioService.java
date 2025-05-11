package br.com.fiap.sprint4.services;

import br.com.fiap.sprint4.models.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario salvarUsuario(Usuario usuario);
    void atualizarUsuario(String username, Usuario usuario);
    void deletarUsuario(String username);
    Usuario obterUsuarioPorUsername(String username);
    List<Usuario> listarTodosUsuarios();
    boolean existeUsuarioComUsername(String username);
    boolean existeUsuarioComEmail(String email);
}
