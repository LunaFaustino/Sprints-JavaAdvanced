package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.actuator.OdontoprevMetrics;
import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.models.Paciente;
import br.com.fiap.sprint4.repositories.PacienteRepository;
import br.com.fiap.sprint4.services.ClinicaService;
import br.com.fiap.sprint4.services.PacienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    private final ClinicaService clinicaService;

    private final OdontoprevMetrics odontoprevMetrics;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, ClinicaService clinicaService, OdontoprevMetrics odontoprevMetrics) {
        this.pacienteRepository = pacienteRepository;
        this.clinicaService = clinicaService;
        this.odontoprevMetrics = odontoprevMetrics;
    }

    @Override
    @Transactional
    public Paciente salvarPaciente(Paciente paciente) {
        if (!paciente.validarCpf()) {
            throw new IllegalArgumentException("CPF inválido: " + paciente.getCpf());
        }

        if (paciente.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + paciente.getEmail());
        }

        if (existePacienteComCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("Já existe um paciente com o CPF: " + paciente.getCpf());
        }

        if (paciente.getDataNascimento() != null) {
            paciente.calcularIdade();
        }

        if (paciente.getClinica() == null || paciente.getClinica().getCnpj() == null) {
            throw new IllegalArgumentException("Clínica não informada para o paciente");
        }

        try {
            Clinica clinica = clinicaService.obterClinicaPorCnpj(paciente.getClinica().getCnpj());
            paciente.setClinica(clinica);
        } catch (Exception e) {
            throw new IllegalArgumentException("Clínica não encontrada: " + paciente.getClinica().getCnpj());
        }

        odontoprevMetrics.incrementPacienteCadastro();

        return pacienteRepository.save(paciente);
    }

    @Override
    public void atualizarPaciente(String cpf, Paciente paciente) {
        Paciente pacienteExistente = obterPacientePorCpf(cpf);

        if (paciente.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + paciente.getEmail());
        }

        pacienteExistente.setNome(paciente.getNome());
        pacienteExistente.setEmail(paciente.getEmail());
        pacienteExistente.setTelefone(paciente.getTelefone());
        pacienteExistente.setGenero(paciente.getGenero());
        pacienteExistente.setStatus(paciente.getStatus());

        if (paciente.getDataNascimento() != null) {
            pacienteExistente.setDataNascimento(paciente.getDataNascimento());
        }

        if (paciente.getEndereco() != null) {
            pacienteExistente.setEndereco(paciente.getEndereco());
        }

        if (paciente.getClinica() != null && paciente.getClinica().getCnpj() != null) {
            try {
                Clinica clinica = clinicaService.obterClinicaPorCnpj(paciente.getClinica().getCnpj());
                pacienteExistente.setClinica(clinica);
            } catch (Exception e) {
                throw new IllegalArgumentException("Clínica não encontrada: " + paciente.getClinica().getCnpj());
            }
        }

        pacienteRepository.save(pacienteExistente);
    }

    @Override
    public void deletarPaciente(String cpf) {
        pacienteRepository.deleteById(cpf);
    }

    @Override
    public Paciente obterPacientePorCpf(String cpf) {
        return pacienteRepository.findById(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com CPF: " + cpf));
    }

    @Override
    public List<Paciente> listarTodosPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public boolean existePacienteComCpf(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }
}