package br.com.fiap.sprint3.services;

import br.com.fiap.sprint3.models.Clinica;

import java.util.List;

public interface ClinicaService {

    void salvarClinica(Clinica clinica);
    void atualizarClinica(String cnpj, Clinica clinica);
    void deletarClinica(String cnpj);
    Clinica obterClinicaPorCnpj(String cnpj);
    List<Clinica> listarTodasClinicas();
    boolean existeClinicaComCnpj(String cnpj);
}
