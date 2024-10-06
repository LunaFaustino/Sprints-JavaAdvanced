package br.com.fiap.sprint1.repositories;

import br.com.fiap.sprint1.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
