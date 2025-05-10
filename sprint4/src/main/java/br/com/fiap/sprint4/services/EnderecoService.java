package br.com.fiap.sprint4.services;

import br.com.fiap.sprint4.models.Endereco;

import java.util.List;

public interface EnderecoService {
    void salvarEndereco(Endereco endereco);
    void atualizarEndereco(Integer id, Endereco endereco);
    void deletarEndereco(Integer id);
    Endereco obterEnderecoPorId(Integer id);
    List<Endereco> listarTodosEnderecos();
}
