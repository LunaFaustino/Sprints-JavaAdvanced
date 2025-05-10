package br.com.fiap.sprint4.services;

import br.com.fiap.sprint4.models.Clinica;

import java.util.List;

public interface ClinicaService {

    void salvarClinica(Clinica clinica);
    void atualizarClinica(String cnpj, Clinica clinica);
    void deletarClinica(String cnpj);
    Clinica obterClinicaPorCnpj(String cnpj);
    List<Clinica> listarTodasClinicas();
    boolean existeClinicaComCnpj(String cnpj);
}
