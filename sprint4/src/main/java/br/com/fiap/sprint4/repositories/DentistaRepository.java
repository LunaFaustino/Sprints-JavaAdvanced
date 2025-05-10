package br.com.fiap.sprint4.repositories;

import br.com.fiap.sprint4.models.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, String> {
    boolean existsByCro(String cro);
}
