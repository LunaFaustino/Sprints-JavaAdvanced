package br.com.fiap.sprint1.service;

import br.com.fiap.sprint1.models.Dentista;

import java.util.List;

public interface DentistaService {

    Dentista salvarDentista(Dentista dentista);
    Dentista atualizarDentista(Integer id,Dentista dentista);
    void deletarDentista(Integer id);
    Dentista obterDentistaPorId(Integer id);
    List<Dentista> listarTodosDentistas();
}
