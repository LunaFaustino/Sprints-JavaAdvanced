package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.models.Endereco;
import br.com.fiap.sprint4.repositories.EnderecoRepository;
import br.com.fiap.sprint4.services.EnderecoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public void salvarEndereco(Endereco endereco) {

        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }

        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia");
        }

        if (endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser vazio");
        }

        if (endereco.validarCep()) {
            throw new IllegalArgumentException("CEP inválido: " + endereco.getCep());
        }

        enderecoRepository.save(endereco);
    }

    @Override
    public void atualizarEndereco(Integer id, Endereco novoEndereco) {

        Endereco enderecoExistente = obterEnderecoPorId(id);

        if (novoEndereco.getLogradouro() == null || novoEndereco.getLogradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio");
        }

        if (novoEndereco.getCidade() == null || novoEndereco.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia");
        }

        if (novoEndereco.getEstado() == null || novoEndereco.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser vazio");
        }

        if (novoEndereco.validarCep()) {
            throw new IllegalArgumentException("CEP inválido: " + novoEndereco.getCep());
        }

        enderecoExistente.setLogradouro(novoEndereco.getLogradouro());
        enderecoExistente.setNumero(novoEndereco.getNumero());
        enderecoExistente.setComplemento(novoEndereco.getComplemento());
        enderecoExistente.setBairro(novoEndereco.getBairro());
        enderecoExistente.setCidade(novoEndereco.getCidade());
        enderecoExistente.setEstado(novoEndereco.getEstado());
        enderecoExistente.setCep(novoEndereco.getCep());

        enderecoRepository.save(enderecoExistente);
    }

    @Override
    public void deletarEndereco(Integer id) {

        if (!enderecoRepository.existsById(id)) {
            throw new IllegalArgumentException("Endereço não encontrado com ID: " + id);
        }

        enderecoRepository.deleteById(id);
    }

    @Override
    public Endereco obterEnderecoPorId(Integer id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com ID: " + id));
    }

    @Override
    public List<Endereco> listarTodosEnderecos() {
        return enderecoRepository.findAll();
    }
}