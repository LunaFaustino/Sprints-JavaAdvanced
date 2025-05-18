package br.com.fiap.sprint4.controllers;

import br.com.fiap.sprint4.models.PerguntaResposta;
import br.com.fiap.sprint4.services.impl.AssistenteIAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistente")
public class AssistenteIAController {

    private final AssistenteIAService assistenteIAService;

    public AssistenteIAController(AssistenteIAService assistenteIAService) {
        this.assistenteIAService = assistenteIAService;
    }

    @PostMapping("/perguntar")
    public ResponseEntity<PerguntaResposta> fazerPergunta(@RequestBody PerguntaResposta pergunta) {
        PerguntaResposta resposta = assistenteIAService.obterResposta(pergunta.getPergunta());
        return ResponseEntity.ok(resposta);
    }
}
