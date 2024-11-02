package br.com.fiap.sprint2.repositories;

import br.com.fiap.sprint2.models.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Integer> {
}
