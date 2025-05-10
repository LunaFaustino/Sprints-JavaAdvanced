package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.models.Paciente;
import br.com.fiap.sprint4.repositories.PacienteRepository;
import br.com.fiap.sprint4.services.ClinicaService;
import br.com.fiap.sprint4.services.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    private static final Logger logger = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;
    private final ClinicaService clinicaService;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, ClinicaService clinicaService) {
        this.pacienteRepository = pacienteRepository;
        this.clinicaService = clinicaService;
    }

    @Override
    @Transactional
    public Paciente salvarPaciente(Paciente paciente) {
        logger.info("Iniciando método salvarPaciente no service");
        logger.info("Paciente recebido: {}", paciente);

        logger.info("Validando CPF");
        if (!paciente.validarCpf()) {
            logger.error("CPF inválido: {}", paciente.getCpf());
            throw new IllegalArgumentException("CPF inválido: " + paciente.getCpf());
        }

        logger.info("Validando email");
        if (paciente.validarEmail()) {
            logger.error("Email inválido: {}", paciente.getEmail());
            throw new IllegalArgumentException("Email inválido: " + paciente.getEmail());
        }

        logger.info("Verificando se já existe paciente com CPF: {}", paciente.getCpf());
        if (existePacienteComCpf(paciente.getCpf())) {
            logger.error("Já existe um paciente com o CPF: {}", paciente.getCpf());
            throw new IllegalArgumentException("Já existe um paciente com o CPF: " + paciente.getCpf());
        }

        if (paciente.getDataNascimento() != null) {
            logger.info("Calculando idade baseada na data de nascimento: {}", paciente.getDataNascimento());
            paciente.calcularIdade();
            logger.info("Idade calculada: {}", paciente.getIdade());
        }

        if (paciente.getClinica() == null || paciente.getClinica().getCnpj() == null) {
            logger.error("Clínica não informada para o paciente");
            throw new IllegalArgumentException("Clínica não informada para o paciente");
        }

        try {
            logger.info("Verificando clínica com CNPJ: {}", paciente.getClinica().getCnpj());
            Clinica clinica = clinicaService.obterClinicaPorCnpj(paciente.getClinica().getCnpj());
            logger.info("Clínica encontrada: {}", clinica.getNomeFantasia());
            paciente.setClinica(clinica);
        } catch (Exception e) {
            logger.error("Erro ao verificar clínica: ", e);
            throw new IllegalArgumentException("Clínica não encontrada: " + paciente.getClinica().getCnpj());
        }

        logger.info("Todas as validações passaram. Chamando repository.save()");
        try {
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            logger.info("Paciente salvo com sucesso: {}", pacienteSalvo);
            return pacienteSalvo;
        } catch (Exception e) {
            logger.error("Erro ao salvar paciente no repositório: ", e);
            throw e;
        }
    }

    @Override
    public void atualizarPaciente(String cpf, Paciente paciente) {
        logger.info("Atualizando paciente com CPF {}: {}", cpf, paciente);

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
        logger.info("Deletando paciente com CPF {}", cpf);
        pacienteRepository.deleteById(cpf);
    }

    @Override
    public Paciente obterPacientePorCpf(String cpf) {
        logger.info("Obtendo paciente com CPF {}", cpf);
        return pacienteRepository.findById(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com CPF: " + cpf));
    }

    @Override
    public List<Paciente> listarTodosPacientes() {
        logger.info("Listando todos os pacientes");
        List<Paciente> pacientes = pacienteRepository.findAll();
        logger.info("Encontrados {} pacientes", pacientes.size());
        for (Paciente p : pacientes) {
            logger.debug("Paciente encontrado: {}", p);
        }
        return pacientes;
    }

    @Override
    public boolean existePacienteComCpf(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }
}
