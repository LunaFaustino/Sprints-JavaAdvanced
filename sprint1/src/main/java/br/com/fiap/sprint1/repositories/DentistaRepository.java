package br.com.fiap.sprint1.repositories;

import br.com.fiap.sprint1.models.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistaRepository extends JpaRepository<Dentista, Integer> {
}
