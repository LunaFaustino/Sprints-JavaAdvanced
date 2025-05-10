package br.com.fiap.sprint4.repositories;

import br.com.fiap.sprint4.models.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, String> {

    boolean existsByCnpj(String cnpj);
}
