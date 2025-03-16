package br.com.fiap.sprint3.services.impl;

import br.com.fiap.sprint3.models.Clinica;
import br.com.fiap.sprint3.models.Dentista;
import br.com.fiap.sprint3.repositories.DentistaRepository;
import br.com.fiap.sprint3.services.ClinicaService;
import br.com.fiap.sprint3.services.DentistaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistaServiceImpl implements DentistaService {

    private static final Logger logger = LoggerFactory.getLogger(DentistaServiceImpl.class);

    private final DentistaRepository dentistaRepository;
    private final ClinicaService clinicaService;

    public DentistaServiceImpl(DentistaRepository dentistaRepository, ClinicaService clinicaService) {
        this.dentistaRepository = dentistaRepository;
        this.clinicaService = clinicaService;
    }

    @Override
    public void salvarDentista(Dentista dentista) {
        logger.info("Salvando dentista com CRO {}: {}", dentista.getCro(), dentista);

        if (!dentista.validarCro()) {
            throw new IllegalArgumentException("CRO inválido: " + dentista.getCro());
        }

        if (dentista.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + dentista.getEmail());
        }

        if (existeDentistaComCro(dentista.getCro())) {
            throw new IllegalArgumentException("Já existe um dentista com o CRO: " + dentista.getCro());
        }

        if (dentista.getClinica() == null || dentista.getClinica().getCnpj() == null) {
            throw new IllegalArgumentException("Clínica não informada para o dentista");
        }

        try {
            Clinica clinica = clinicaService.obterClinicaPorCnpj(dentista.getClinica().getCnpj());
            dentista.setClinica(clinica);
        } catch (Exception e) {
            throw new IllegalArgumentException("Clínica não encontrada: " + dentista.getClinica().getCnpj());
        }

        dentistaRepository.save(dentista);
    }

    @Override
    public void atualizarDentista(String cro, Dentista dentista) {
        logger.info("Atualizando dentista com CRO {}: {}", cro, dentista);

        Dentista dentistaExistente = obterDentistaPorCro(cro);

        if (dentista.validarEmail()) {
            throw new IllegalArgumentException("Email inválido: " + dentista.getEmail());
        }

        dentista.setCro(cro);

        if (dentista.getClinica() != null && dentista.getClinica().getCnpj() != null) {
            try {
                Clinica clinica = clinicaService.obterClinicaPorCnpj(dentista.getClinica().getCnpj());
                dentista.setClinica(clinica);
            } catch (Exception e) {
                throw new IllegalArgumentException("Clínica não encontrada: " + dentista.getClinica().getCnpj());
            }
        } else {
            dentista.setClinica(dentistaExistente.getClinica());
        }

        dentistaRepository.save(dentista);
    }

    @Override
    public void deletarDentista(String cro) {
        logger.info("Deletando dentista com CRO {}", cro);
        dentistaRepository.deleteById(cro);
    }

    @Override
    public Dentista obterDentistaPorCro(String cro) {
        logger.info("Obtendo dentista com CRO {}", cro);
        return dentistaRepository.findById(cro)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado com CRO: " + cro));
    }

    @Override
    public List<Dentista> listarTodosDentistas() {
        logger.info("Listando todos os dentistas");
        return dentistaRepository.findAll();
    }

    @Override
    public boolean existeDentistaComCro(String cro) {
        return dentistaRepository.existsByCro(cro);
    }
}