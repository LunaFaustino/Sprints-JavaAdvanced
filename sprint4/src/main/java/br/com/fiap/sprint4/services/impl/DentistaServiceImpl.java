package br.com.fiap.sprint4.services.impl;

import br.com.fiap.sprint4.actuator.OdontoprevMetrics;
import br.com.fiap.sprint4.models.Clinica;
import br.com.fiap.sprint4.models.Dentista;
import br.com.fiap.sprint4.repositories.DentistaRepository;
import br.com.fiap.sprint4.services.ClinicaService;
import br.com.fiap.sprint4.services.DentistaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistaServiceImpl implements DentistaService {

    private final DentistaRepository dentistaRepository;

    private final ClinicaService clinicaService;

    private final OdontoprevMetrics odontoprevMetrics;


    public DentistaServiceImpl(DentistaRepository dentistaRepository, ClinicaService clinicaService, OdontoprevMetrics odontoprevMetrics) {
        this.dentistaRepository = dentistaRepository;
        this.clinicaService = clinicaService;
        this.odontoprevMetrics = odontoprevMetrics;
    }

    @Override
    public void salvarDentista(Dentista dentista) {

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
        odontoprevMetrics.incrementDentistaCadastro();
    }

    @Override
    public void atualizarDentista(String cro, Dentista dentista) {

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
        dentistaRepository.deleteById(cro);
    }

    @Override
    public Dentista obterDentistaPorCro(String cro) {
        return dentistaRepository.findById(cro)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado com CRO: " + cro));
    }

    @Override
    public List<Dentista> listarTodosDentistas() {
        return dentistaRepository.findAll();
    }

    @Override
    public boolean existeDentistaComCro(String cro) {
        return dentistaRepository.existsByCro(cro);
    }
}