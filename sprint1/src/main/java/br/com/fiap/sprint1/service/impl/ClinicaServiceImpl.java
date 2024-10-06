package br.com.fiap.sprint1.service.impl;

import br.com.fiap.sprint1.models.Clinica;
import br.com.fiap.sprint1.repositories.ClinicaRepository;
import br.com.fiap.sprint1.service.ClinicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    private final ClinicaRepository clinicaRepository;

    public ClinicaServiceImpl(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    @Override
    public Clinica salvarClinica(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

    @Override
    public Clinica atualizarClinica(Integer id, Clinica clinica) {
        clinica.setId(id);
        return clinicaRepository.save(clinica);
    }

    @Override
    public void deletarClinica(Integer id) {
        clinicaRepository.deleteById(id);
    }

    @Override
    public Clinica obterClinicaPorId(Integer id) {
        return clinicaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Clinica> listarTodasClinicas() {
        return clinicaRepository.findAll();
    }
}
