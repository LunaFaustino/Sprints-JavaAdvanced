package br.com.fiap.sprint3.controllers;

import br.com.fiap.sprint3.dtos.EnderecoDTO;
import br.com.fiap.sprint3.models.Endereco;
import br.com.fiap.sprint3.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public String listarEnderecos(Model model) {
        List<EnderecoDTO> enderecos = enderecoService.listarTodosEnderecos().stream()
                .map(EnderecoDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("enderecos", enderecos);
        return "enderecos/lista";
    }

    @GetMapping("/novo")
    public String novoEndereco(Model model) {
        model.addAttribute("enderecoDTO", new EnderecoDTO());
        return "enderecos/form";
    }

    @PostMapping("/novo")
    public String salvarEndereco(@Valid @ModelAttribute EnderecoDTO enderecoDTO,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "enderecos/form";
        }

        try {
            Endereco endereco = enderecoDTO.toEntity();
            enderecoService.salvarEndereco(endereco);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Endereço cadastrado com sucesso!");
            return "redirect:/enderecos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar endereço: " + e.getMessage());
            return "redirect:/enderecos/novo";
        }
    }

    @GetMapping("/{id}")
    public String exibirEndereco(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Endereco endereco = enderecoService.obterEnderecoPorId(id);
            model.addAttribute("endereco", new EnderecoDTO(endereco));
            return "enderecos/detalhes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Endereço não encontrado.");
            return "redirect:/enderecos";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarEndereco(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Endereco endereco = enderecoService.obterEnderecoPorId(id);
            model.addAttribute("enderecoDTO", new EnderecoDTO(endereco));
            return "enderecos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Endereço não encontrado.");
            return "redirect:/enderecos";
        }
    }

    @PostMapping("/{id}/editar")
    public String atualizarEndereco(@PathVariable Integer id,
                                    @Valid @ModelAttribute EnderecoDTO enderecoDTO,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "enderecos/form";
        }

        try {
            Endereco endereco = enderecoDTO.toEntity();
            enderecoService.atualizarEndereco(id, endereco);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Endereço atualizado com sucesso!");
            return "redirect:/enderecos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar endereço: " + e.getMessage());
            return "redirect:/enderecos/" + id + "/editar";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluirEndereco(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            enderecoService.deletarEndereco(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Endereço excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir endereço: " + e.getMessage());
        }

        return "redirect:/enderecos";
    }
}
