package br.com.fiap.sprint3.repositories;

import br.com.fiap.sprint3.models.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, String> {

    boolean existsByCnpj(String cnpj);
}
