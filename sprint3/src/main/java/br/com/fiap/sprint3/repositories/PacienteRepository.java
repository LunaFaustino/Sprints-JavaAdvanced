package br.com.fiap.sprint3.repositories;

import br.com.fiap.sprint3.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
    boolean existsByCpf(String cpf);
}
