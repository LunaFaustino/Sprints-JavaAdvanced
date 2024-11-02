package br.com.fiap.sprint2.service.impl;

import br.com.fiap.sprint2.models.Clinica;
import br.com.fiap.sprint2.repositories.ClinicaRepository;
import br.com.fiap.sprint2.service.ClinicaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClinicaServiceImpl implements ClinicaService {

    private final ClinicaRepository clinicaRepository;

    @Override
    public Clinica salvarClinica(Clinica clinica) {
        log.info("Salvando clínica: {}", clinica);
        return clinicaRepository.save(clinica);
    }

    @Override
    public Clinica atualizarClinica(Integer id, Clinica clinica) {
        clinica.setId(id);
        log.info("Atualizando clínica com ID {}: {}", id, clinica);
        return clinicaRepository.save(clinica);
    }

    @Override
    public void deletarClinica(Integer id) {
        log.info("Deletando clínica com ID {}", id);
        clinicaRepository.deleteById(id);
    }

    @Override
    public Clinica obterClinicaPorId(Integer id) {
        log.info("Obtendo clínica com ID {}", id);
        return clinicaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Clinica> listarTodasClinicas() {
        log.info("Listando todas as clínicas");
        return clinicaRepository.findAll();
    }
}
