package br.com.fiap.sprint4.services;

import br.com.fiap.sprint4.models.Paciente;

import java.util.List;

public interface PacienteService {
    Paciente salvarPaciente(Paciente paciente);
    void atualizarPaciente(String cpf, Paciente paciente);
    void deletarPaciente(String cpf);
    Paciente obterPacientePorCpf(String cpf);
    List<Paciente> listarTodosPacientes();
    boolean existePacienteComCpf(String cpf);
}
