package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.models.Perfil;
import br.com.fiap.sprint4.models.Status;
import br.com.fiap.sprint4.models.Usuario;
import br.com.fiap.sprint4.repositories.PerfilRepository;
import br.com.fiap.sprint4.repositories.UsuarioRepository;
import br.com.fiap.sprint4.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PerfilRepository perfilRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Tentando autenticar usuário: {}", username);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado: {}", username);
                    return new UsernameNotFoundException("Usuário não encontrado: " + username);
                });

        if (usuario.getStatus() == Status.INATIVO) {
            logger.error("Usuário inativo: {}", username);
            throw new UsernameNotFoundException("Usuário inativo: " + username);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Perfil perfil : usuario.getPerfis()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + perfil.getNome()));
        }

        logger.debug("Usuário {} encontrado com perfis: {}", username, authorities);

        return new User(usuario.getUsername(),
                usuario.getPassword(),
                true, true, true, true,
                authorities);
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        if (existeUsuarioComUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Já existe um usuário com este username: " + usuario.getUsername());
        }

        if (existeUsuarioComEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email: " + usuario.getEmail());
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public void atualizarUsuario(String username, Usuario usuarioAtualizado) {
        Usuario usuario = obterUsuarioPorUsername(username);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setStatus(usuarioAtualizado.getStatus());

        if (usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioAtualizado.getPassword()));
        }

        if (usuarioAtualizado.getPerfis() != null && !usuarioAtualizado.getPerfis().isEmpty()) {
            usuario.setPerfis(usuarioAtualizado.getPerfis());
        }

        usuarioRepository.save(usuario);
    }

    @Override
    public void deletarUsuario(String username) {
        usuarioRepository.deleteById(username);

    }

    @Override
    public Usuario obterUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));
    }

    @Override
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean existeUsuarioComUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existeUsuarioComEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
