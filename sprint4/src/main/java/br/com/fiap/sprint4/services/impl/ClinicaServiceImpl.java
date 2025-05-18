package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.actuator.OdontoprevMetrics;
import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.repositories.ClinicaRepository;
import br.com.fiap.sprint4.services.ClinicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    private final ClinicaRepository clinicaRepository;

    private final OdontoprevMetrics odontoprevMetrics;

    public ClinicaServiceImpl(ClinicaRepository clinicaRepository, OdontoprevMetrics odontoprevMetrics) {
        this.clinicaRepository = clinicaRepository;
        this.odontoprevMetrics = odontoprevMetrics;
    }

    @Override
    public void salvarClinica(Clinica clinica) {

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
        odontoprevMetrics.incrementClinicaCadastro();
    }

    @Override
    public void atualizarClinica(String cnpj, Clinica clinica) {

        if (clinica.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + clinica.getEmail());
        }

        clinica.setCnpj(cnpj);

        clinicaRepository.save(clinica);
    }

    @Override
    public void deletarClinica(String cnpj) {
        clinicaRepository.deleteById(cnpj);
    }

    @Override
    public Clinica obterClinicaPorCnpj(String cnpj) {
        return clinicaRepository.findById(cnpj)
                .orElseThrow(() -> new IllegalArgumentException("Clínica não encontrada com CNPJ: " + cnpj));
    }

    @Override
    public List<Clinica> listarTodasClinicas() {
        return clinicaRepository.findAll();
    }

    @Override
    public boolean existeClinicaComCnpj(String cnpj) {
        return clinicaRepository.existsByCnpj(cnpj);
    }
}