package br.com.fiap.sprint3.services.impl;

import br.com.fiap.sprint3.models.Clinica;
import br.com.fiap.sprint3.repositories.ClinicaRepository;
import br.com.fiap.sprint3.services.ClinicaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    private static final Logger logger = LoggerFactory.getLogger(ClinicaServiceImpl.class);

    private final ClinicaRepository clinicaRepository;

    public ClinicaServiceImpl(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    @Override
    public void salvarClinica(Clinica clinica) {
        logger.info("Salvando clínica com CNPJ {}: {}", clinica.getCnpj(), clinica);

        // Validações básicas
        if (!clinica.validarCnpj()) {
            throw new IllegalArgumentException("CNPJ inválido: " + clinica.getCnpj());
        }

        if (clinica.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + clinica.getEmail());
        }

        if (existeClinicaComCnpj(clinica.getCnpj())) {
            throw new IllegalArgumentException("Já existe uma clínica com o CNPJ: " + clinica.getCnpj());
        }

        clinicaRepository.save(clinica);
    }

    @Override
    public void atualizarClinica(String cnpj, Clinica clinica) {
        logger.info("Atualizando clínica com CNPJ {}: {}", cnpj, clinica);

        if (clinica.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + clinica.getEmail());
        }

        clinica.setCnpj(cnpj);

        clinicaRepository.save(clinica);
    }

    @Override
    public void deletarClinica(String cnpj) {
        logger.info("Deletando clínica com CNPJ {}", cnpj);
        clinicaRepository.deleteById(cnpj);
    }

    @Override
    public Clinica obterClinicaPorCnpj(String cnpj) {
        logger.info("Obtendo clínica com CNPJ {}", cnpj);
        return clinicaRepository.findById(cnpj)
                .orElseThrow(() -> new IllegalArgumentException("Clínica não encontrada com CNPJ: " + cnpj));
    }

    @Override
    public List<Clinica> listarTodasClinicas() {
        logger.info("Listando todas as clínicas");
        return clinicaRepository.findAll();
    }

    @Override
    public boolean existeClinicaComCnpj(String cnpj) {
        return clinicaRepository.existsByCnpj(cnpj);
    }
}