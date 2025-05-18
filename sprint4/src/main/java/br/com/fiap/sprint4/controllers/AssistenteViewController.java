package br.com.fiap.sprint4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assistente")
public class AssistenteViewController {

    @GetMapping
    public String assistentePage() {
        return "assistente/chat";
    }
}
