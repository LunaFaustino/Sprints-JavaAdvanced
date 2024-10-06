package br.com.fiap.sprint1.service.impl;

import br.com.fiap.sprint1.models.Paciente;
import br.com.fiap.sprint1.repositories.PacienteRepository;
import br.com.fiap.sprint1.service.PacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente salvarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente atualizarPaciente(Integer id, Paciente paciente) {
        paciente.setId(id);
        return pacienteRepository.save(paciente);
    }

    @Override
    public void deletarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Paciente obterPacientePorId(Integer id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Paciente> listarTodosPacientes() {
        return pacienteRepository.findAll();
    }
}
