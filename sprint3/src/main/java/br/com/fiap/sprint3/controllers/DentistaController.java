package br.com.fiap.sprint3.controllers;

import br.com.fiap.sprint3.dtos.ClinicaDTO;
import br.com.fiap.sprint3.dtos.DentistaDTO;
import br.com.fiap.sprint3.models.Dentista;
import br.com.fiap.sprint3.models.Genero;
import br.com.fiap.sprint3.models.Status;
import br.com.fiap.sprint3.services.ClinicaService;
import br.com.fiap.sprint3.services.DentistaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dentistas")
public class DentistaController {

    private final DentistaService dentistaService;
    private final ClinicaService clinicaService;

    public DentistaController(DentistaService dentistaService, ClinicaService clinicaService) {
        this.dentistaService = dentistaService;
        this.clinicaService = clinicaService;
    }

    @GetMapping
    public String listarDentistas(Model model) {
        List<DentistaDTO> dentistas = dentistaService.listarTodosDentistas().stream()
                .map(DentistaDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("dentistas", dentistas);
        return "dentistas/lista";
    }

    @GetMapping("/novo")
    public String novoDentista(Model model) {
        model.addAttribute("dentistaDTO", new DentistaDTO());
        model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                .map(ClinicaDTO::new)
                .collect(Collectors.toList()));
        model.addAttribute("generos", Genero.values());
        model.addAttribute("statusOptions", Status.values());
        return "dentistas/form";
    }

    @PostMapping("/novo")
    public String salvarDentista(@Valid @ModelAttribute DentistaDTO dentistaDTO,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (result.hasErrors()) {
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "dentistas/form";
        }

        try {
            Dentista dentista = dentistaDTO.toEntity();
            dentistaService.salvarDentista(dentista);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Dentista cadastrado com sucesso!");
            return "redirect:/dentistas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar dentista: " + e.getMessage());
            return "redirect:/dentistas/novo";
        }
    }

    @GetMapping("/{cro}")
    public String exibirDentista(@PathVariable String cro, Model model, RedirectAttributes redirectAttributes) {
        try {
            Dentista dentista = dentistaService.obterDentistaPorCro(cro);
            model.addAttribute("dentista", new DentistaDTO(dentista));
            return "dentistas/detalhes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Dentista não encontrado.");
            return "redirect:/dentistas";
        }
    }

    @GetMapping("/{cro}/editar")
    public String editarDentista(@PathVariable String cro, Model model, RedirectAttributes redirectAttributes) {
        try {
            Dentista dentista = dentistaService.obterDentistaPorCro(cro);
            model.addAttribute("dentistaDTO", new DentistaDTO(dentista));
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "dentistas/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Dentista não encontrado.");
            return "redirect:/dentistas";
        }
    }

    @PostMapping("/{cro}/editar")
    public String atualizarDentista(@PathVariable String cro,
                                    @Valid @ModelAttribute DentistaDTO dentistaDTO,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        if (!cro.equals(dentistaDTO.getCro())) {
            result.rejectValue("cro", "error.dentistaDTO", "O CRO não pode ser alterado");
        }

        if (result.hasErrors()) {
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "dentistas/form";
        }

        try {
            Dentista dentista = dentistaDTO.toEntity();
            dentistaService.atualizarDentista(cro, dentista);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Dentista atualizado com sucesso!");
            return "redirect:/dentistas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar dentista: " + e.getMessage());
            return "redirect:/dentistas/" + cro + "/editar";
        }
    }

    @PostMapping("/{cro}/excluir")
    public String excluirDentista(@PathVariable String cro, RedirectAttributes redirectAttributes) {
        try {
            dentistaService.deletarDentista(cro);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Dentista excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir dentista: " + e.getMessage());
        }

        return "redirect:/dentistas";
    }
}
