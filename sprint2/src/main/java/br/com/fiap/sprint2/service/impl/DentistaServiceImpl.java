package br.com.fiap.sprint2.service.impl;

import br.com.fiap.sprint2.models.Dentista;
import br.com.fiap.sprint2.repositories.DentistaRepository;
import br.com.fiap.sprint2.service.DentistaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DentistaServiceImpl implements DentistaService {

    private final DentistaRepository dentistaRepository;

    @Override
    public Dentista salvarDentista(Dentista dentista) {
        log.info("Salvando dentista: {}", dentista);
        return dentistaRepository.save(dentista);
    }

    @Override
    public Dentista atualizarDentista(Integer id, Dentista dentista) {
        dentista.setId(id);
        log.info("Atualizando dentista com ID {}: {}", id, dentista);
        return dentistaRepository.save(dentista);
    }

    @Override
    public void deletarDentista(Integer id) {
        log.info("Deletando dentista com ID {}", id);
        dentistaRepository.deleteById(id);
    }

    @Override
    public Dentista obterDentistaPorId(Integer id) {
        log.info("Obtendo dentista com ID {}", id);
        return dentistaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dentista> listarTodosDentistas() {
        log.info("Listando todos os dentistas");
        return dentistaRepository.findAll();
    }
}
