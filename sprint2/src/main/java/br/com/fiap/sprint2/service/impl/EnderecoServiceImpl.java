package br.com.fiap.sprint2.service.impl;

import br.com.fiap.sprint2.models.Endereco;
import br.com.fiap.sprint2.repositories.EnderecoRepository;
import br.com.fiap.sprint2.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco salvarEndereco(Endereco endereco) {
        log.info("Salvando endereço: {}", endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco atualizarEndereco(Integer id, Endereco endereco) {
        endereco.setId(id);
        log.info("Atualizando endereço com ID {}: {}", id, endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    public void deletarEndereco(Integer id) {
        log.info("Deletando endereço com ID {}", id);
        enderecoRepository.deleteById(id);
    }

    @Override
    public Endereco obterEnderecoPorId(Integer id) {
        log.info("Obtendo endereço com ID {}", id);
        return enderecoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Endereco> listarTodosEnderecos() {
        log.info("Listando todos os endereços");
        return enderecoRepository.findAll();
    }
}
