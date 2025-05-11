package br.com.fiap.sprint4.repositories;

import br.com.fiap.sprint4.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {
}
