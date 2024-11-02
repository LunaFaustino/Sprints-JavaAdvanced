package br.com.fiap.sprint2.service;

import br.com.fiap.sprint2.models.Paciente;

import java.util.List;

public interface PacienteService {

    Paciente salvarPaciente(Paciente paciente);
    Paciente atualizarPaciente(Integer id,Paciente paciente);
    void deletarPaciente(Integer id);
    Paciente obterPacientePorId(Integer id);
    List<Paciente> listarTodosPacientes();
}
