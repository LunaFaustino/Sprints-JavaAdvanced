package br.com.fiap.sprint4.config;

import br.com.fiap.sprint4.models.Perfil;
import br.com.fiap.sprint4.models.Status;
import br.com.fiap.sprint4.models.Usuario;
import br.com.fiap.sprint4.repositories.PerfilRepository;
import br.com.fiap.sprint4.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(PerfilRepository perfilRepository,
                                      UsuarioRepository usuarioRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Iniciando a criação de dados iniciais...");

            Perfil adminPerfil = null;
            Perfil userPerfil = null;

            if (perfilRepository.count() == 0) {
                System.out.println("Criando perfis...");
                adminPerfil = perfilRepository.save(new Perfil("ADMIN", "Administrador"));
                userPerfil = perfilRepository.save(new Perfil("USER", "Usuário Comum"));
                System.out.println("Perfis criados com sucesso!");
            } else {
                adminPerfil = perfilRepository.findById("ADMIN").orElse(null);
                userPerfil = perfilRepository.findById("USER").orElse(null);
            }

            if (adminPerfil == null || userPerfil == null) {
                System.out.println("ERRO: Perfis não encontrados!");
                return;
            }

            if (!usuarioRepository.existsByUsername("admin")) {
                System.out.println("Criando usuário admin...");

                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setNome("Administrador");
                admin.setEmail("admin@sistema.com");
                admin.setStatus(Status.ATIVO);

                Set<Perfil> perfisAdmin = new HashSet<>();
                perfisAdmin.add(adminPerfil);
                admin.setPerfis(perfisAdmin);

                Usuario savedAdmin = usuarioRepository.save(admin);
                System.out.println("Usuário admin criado com sucesso!");
            }

            if (!usuarioRepository.existsByUsername("user")) {
                System.out.println("Criando usuário comum...");

                Usuario user = new Usuario();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setNome("Usuário Padrão");
                user.setEmail("user@sistema.com");
                user.setStatus(Status.ATIVO);

                Set<Perfil> perfisUser = new HashSet<>();
                perfisUser.add(userPerfil);
                user.setPerfis(perfisUser);

                Usuario savedUser = usuarioRepository.save(user);
                System.out.println("Usuário comum criado com sucesso!");
            }

            System.out.println("Usuários cadastrados:");
            for (Usuario u : usuarioRepository.findAll()) {
                System.out.println("Usuário: " + u.getUsername() + ", Perfis: " + u.getPerfis().size());
            }
        };
    }
}