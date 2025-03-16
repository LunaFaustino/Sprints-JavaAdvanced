package br.com.fiap.sprint3.services.impl;

import br.com.fiap.sprint3.models.Endereco;
import br.com.fiap.sprint3.repositories.EnderecoRepository;
import br.com.fiap.sprint3.services.EnderecoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public void salvarEndereco(Endereco endereco) {
        logger.info("Salvando endereço: {}", endereco);

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
        logger.info("Atualizando endereço com ID {}: {}", id, novoEndereco);

        // Obter o endereço existente
        Endereco enderecoExistente = obterEnderecoPorId(id);

        // Validações básicas
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

        // Atualizar campos
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
        logger.info("Deletando endereço com ID {}", id);

        if (!enderecoRepository.existsById(id)) {
            throw new IllegalArgumentException("Endereço não encontrado com ID: " + id);
        }

        enderecoRepository.deleteById(id);
    }

    @Override
    public Endereco obterEnderecoPorId(Integer id) {
        logger.info("Obtendo endereço com ID {}", id);
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado com ID: " + id));
    }

    @Override
    public List<Endereco> listarTodosEnderecos() {
        logger.info("Listando todos os endereços");
        return enderecoRepository.findAll();
    }
}