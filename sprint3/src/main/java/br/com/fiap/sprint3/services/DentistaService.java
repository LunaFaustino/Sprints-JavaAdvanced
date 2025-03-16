package br.com.fiap.sprint3.services;

import br.com.fiap.sprint3.models.Dentista;

import java.util.List;

public interface DentistaService {
    void salvarDentista(Dentista dentista);
    void atualizarDentista(String cro, Dentista dentista);
    void deletarDentista(String cro);
    Dentista obterDentistaPorCro(String cro);
    List<Dentista> listarTodosDentistas();
    boolean existeDentistaComCro(String cro); // Mantido para validação
}
