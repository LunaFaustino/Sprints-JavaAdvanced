package br.com.fiap.sprint1.repositories;

import br.com.fiap.sprint1.models.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica, Integer> {
}
