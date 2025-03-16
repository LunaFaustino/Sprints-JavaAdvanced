package br.com.fiap.sprint3.controllers;

import br.com.fiap.sprint3.dtos.ClinicaDTO;
import br.com.fiap.sprint3.models.Clinica;
import br.com.fiap.sprint3.models.Porte;
import br.com.fiap.sprint3.models.Status;
import br.com.fiap.sprint3.services.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clinicas")
public class ClinicaController {

    private final ClinicaService clinicaService;

    public ClinicaController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping
    public String listarClinicas(Model model) {
        List<ClinicaDTO> clinicas = clinicaService.listarTodasClinicas().stream()
                .map(ClinicaDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("clinicas", clinicas);
        return "clinicas/lista";
    }

    @GetMapping("/novo")
    public String novaClinica(Model model) {
        model.addAttribute("clinicaDTO", new ClinicaDTO());
        model.addAttribute("portes", Porte.values());
        model.addAttribute("statusOptions", Status.values());
        model.addAttribute("permitirEdicaoCnpj", true);
        return "clinicas/form";
    }

    @PostMapping("/novo")
    public String salvarClinica(@Valid @ModelAttribute ClinicaDTO clinicaDTO,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        System.out.println("DTO recebido: " + clinicaDTO);

        if (result.hasErrors()) {
            System.out.println("Erros de validação: " + result.getAllErrors());
            model.addAttribute("portes", Porte.values());
            model.addAttribute("statusOptions", Status.values());
            model.addAttribute("permitirEdicaoCnpj", true);
            return "clinicas/form";
        }

        try {
            Clinica clinica = clinicaDTO.toEntity();
            System.out.println("Entidade convertida: " + clinica);

            clinicaService.salvarClinica(clinica);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Clínica cadastrada com sucesso!");
            return "redirect:/clinicas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar clínica: " + e.getMessage());
            return "redirect:/clinicas/novo";
        }
    }

    @GetMapping("/{cnpj}")
    public String exibirClinica(@PathVariable String cnpj, Model model, RedirectAttributes redirectAttributes) {
        try {
            Clinica clinica = clinicaService.obterClinicaPorCnpj(cnpj);
            model.addAttribute("clinica", new ClinicaDTO(clinica));
            return "clinicas/detalhes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Clínica não encontrada.");
            return "redirect:/clinicas";
        }
    }

    @GetMapping("/{cnpj}/editar")
    public String editarClinica(@PathVariable String cnpj, Model model, RedirectAttributes redirectAttributes) {
        try {
            Clinica clinica = clinicaService.obterClinicaPorCnpj(cnpj);
            model.addAttribute("clinicaDTO", new ClinicaDTO(clinica));
            model.addAttribute("portes", Porte.values());
            model.addAttribute("statusOptions", Status.values());
            model.addAttribute("permitirEdicaoCnpj", false);
            return "clinicas/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Clínica não encontrada.");
            return "redirect:/clinicas";
        }
    }

    @PostMapping("/{cnpj}/editar")
    public String atualizarClinica(@PathVariable String cnpj,
                                   @Valid @ModelAttribute ClinicaDTO clinicaDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (!cnpj.equals(clinicaDTO.getCnpj())) {
            result.rejectValue("cnpj", "error.clinicaDTO", "O CNPJ não pode ser alterado");
        }

        if (result.hasErrors()) {
            model.addAttribute("portes", Porte.values());
            model.addAttribute("statusOptions", Status.values());
            model.addAttribute("permitirEdicaoCnpj", false);
            return "clinicas/form";
        }

        try {
            Clinica clinica = clinicaDTO.toEntity();
            clinicaService.atualizarClinica(cnpj, clinica);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Clínica atualizada com sucesso!");
            return "redirect:/clinicas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar clínica: " + e.getMessage());
            return "redirect:/clinicas/" + cnpj + "/editar";
        }
    }

    @PostMapping("/{cnpj}/excluir")
    public String excluirClinica(@PathVariable String cnpj, RedirectAttributes redirectAttributes) {
        try {
            clinicaService.deletarClinica(cnpj);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Clínica excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir clínica: " + e.getMessage());
        }

        return "redirect:/clinicas";
    }
}