package br.com.fiap.sprint2.service;

import br.com.fiap.sprint2.models.Endereco;

import java.util.List;

public interface EnderecoService {

    Endereco salvarEndereco(Endereco endereco);
    Endereco atualizarEndereco(Integer id,Endereco endereco);
    void deletarEndereco(Integer id);
    Endereco obterEnderecoPorId(Integer id);
    List<Endereco> listarTodosEnderecos();
}
