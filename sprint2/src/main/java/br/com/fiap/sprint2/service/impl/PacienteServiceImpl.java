package br.com.fiap.sprint2.service.impl;

import br.com.fiap.sprint2.models.Paciente;
import br.com.fiap.sprint2.repositories.PacienteRepository;
import br.com.fiap.sprint2.service.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public Paciente salvarPaciente(Paciente paciente) {
        log.info("Salvando paciente: {}", paciente);
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente atualizarPaciente(Integer id, Paciente paciente) {
        paciente.setId(id);
        log.info("Atualizando paciente com ID {}: {}", id, paciente);
        return pacienteRepository.save(paciente);
    }

    @Override
    public void deletarPaciente(Integer id) {
        log.info("Deletando paciente com ID {}", id);
        pacienteRepository.deleteById(id);
    }

    @Override
    public Paciente obterPacientePorId(Integer id) {
        log.info("Obtendo paciente com ID {}", id);
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Paciente> listarTodosPacientes() {
        log.info("Listando todos os pacientes");
        return pacienteRepository.findAll();
    }
}
