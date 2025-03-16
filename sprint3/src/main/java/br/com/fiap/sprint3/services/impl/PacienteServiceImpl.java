package br.com.fiap.sprint3.services.impl;

import br.com.fiap.sprint3.models.Clinica;
import br.com.fiap.sprint3.models.Paciente;
import br.com.fiap.sprint3.repositories.PacienteRepository;
import br.com.fiap.sprint3.services.ClinicaService;
import br.com.fiap.sprint3.services.PacienteService;
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
    @Transactional // Adicione esta anotação se ainda não estiver presente
    public Paciente salvarPaciente(Paciente paciente) {
        logger.info("Iniciando método salvarPaciente no service");
        logger.info("Paciente recebido: {}", paciente);

        // Validações de negócio antes de salvar
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

        // Calcular idade baseado na data de nascimento
        if (paciente.getDataNascimento() != null) {
            logger.info("Calculando idade baseada na data de nascimento: {}", paciente.getDataNascimento());
            paciente.calcularIdade();
            logger.info("Idade calculada: {}", paciente.getIdade());
        }

        // Verifica se a clínica existe
        if (paciente.getClinica() == null || paciente.getClinica().getCnpj() == null) {
            logger.error("Clínica não informada para o paciente");
            throw new IllegalArgumentException("Clínica não informada para o paciente");
        }

        // Garantir que a clínica exista
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

        // Verificar se o paciente existe
        Paciente pacienteExistente = obterPacientePorCpf(cpf);

        // Validações de negócio
        if (paciente.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + paciente.getEmail());
        }

        // Atualizar campos
        pacienteExistente.setNome(paciente.getNome());
        pacienteExistente.setEmail(paciente.getEmail());
        pacienteExistente.setTelefone(paciente.getTelefone());
        pacienteExistente.setGenero(paciente.getGenero());
        pacienteExistente.setStatus(paciente.getStatus());

        // Atualizar data de nascimento e recalcular idade
        if (paciente.getDataNascimento() != null) {
            pacienteExistente.setDataNascimento(paciente.getDataNascimento());
        }

        // Atualizar endereço se fornecido
        if (paciente.getEndereco() != null) {
            pacienteExistente.setEndereco(paciente.getEndereco());
        }

        // Verifica se há uma nova clínica
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
