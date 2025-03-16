package br.com.fiap.sprint3.services;

import br.com.fiap.sprint3.models.Endereco;

import java.util.List;

public interface EnderecoService {
    void salvarEndereco(Endereco endereco);
    void atualizarEndereco(Integer id, Endereco endereco);
    void deletarEndereco(Integer id);
    Endereco obterEnderecoPorId(Integer id);
    List<Endereco> listarTodosEnderecos();
}
