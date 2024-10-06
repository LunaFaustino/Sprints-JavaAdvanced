package br.com.fiap.sprint1.service;

import br.com.fiap.sprint1.models.Clinica;

import java.util.List;

public interface ClinicaService {

    Clinica salvarClinica(Clinica clinica);;
    Clinica atualizarClinica(Integer id,Clinica clinica);
    void deletarClinica(Integer id);
    Clinica obterClinicaPorId(Integer id);
    List<Clinica> listarTodasClinicas();
}
