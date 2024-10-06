package br.com.fiap.sprint1.repositories;

import br.com.fiap.sprint1.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
