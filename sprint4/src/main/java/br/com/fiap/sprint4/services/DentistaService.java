package br.com.fiap.sprint4.services;

import br.com.fiap.sprint4.models.Dentista;

import java.util.List;

public interface DentistaService {
    void salvarDentista(Dentista dentista);
    void atualizarDentista(String cro, Dentista dentista);
    void deletarDentista(String cro);
    Dentista obterDentistaPorCro(String cro);
    List<Dentista> listarTodosDentistas();
    boolean existeDentistaComCro(String cro);
}
