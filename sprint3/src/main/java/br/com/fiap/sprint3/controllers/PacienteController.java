package br.com.fiap.sprint3.controllers;

import br.com.fiap.sprint3.dtos.ClinicaDTO;
import br.com.fiap.sprint3.dtos.PacienteDTO;
import br.com.fiap.sprint3.models.Genero;
import br.com.fiap.sprint3.models.Paciente;
import br.com.fiap.sprint3.models.Status;
import br.com.fiap.sprint3.services.ClinicaService;
import br.com.fiap.sprint3.services.PacienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService pacienteService;
    private final ClinicaService clinicaService;

    public PacienteController(PacienteService pacienteService, ClinicaService clinicaService) {
        this.pacienteService = pacienteService;
        this.clinicaService = clinicaService;
    }

    @GetMapping
    public String listarPacientes(Model model) {
        List<PacienteDTO> pacientes = pacienteService.listarTodosPacientes().stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista";
    }

    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("pacienteDTO", new PacienteDTO());
        model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                .map(ClinicaDTO::new)
                .collect(Collectors.toList()));
        model.addAttribute("generos", Genero.values());
        model.addAttribute("statusOptions", Status.values());
        return "pacientes/form";
    }

    @PostMapping("/novo")
    public String salvarPaciente(@Valid @ModelAttribute PacienteDTO pacienteDTO,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        logger.info("Iniciando método salvarPaciente no controller");
        logger.info("PacienteDTO recebido: {}", pacienteDTO);

        if (result.hasErrors()) {
            logger.warn("Erros de validação encontrados: {}", result.getAllErrors());
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "pacientes/form";
        }

        try {
            logger.info("Convertendo PacienteDTO para Paciente");
            Paciente paciente = pacienteDTO.toEntity();
            logger.info("Paciente após conversão: {}", paciente);

            logger.info("Chamando pacienteService.salvarPaciente()");
            Paciente pacienteSalvo = pacienteService.salvarPaciente(paciente);
            logger.info("Paciente salvo com sucesso: {}", pacienteSalvo);

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Paciente cadastrado com sucesso!");
            return "redirect:/pacientes";
        } catch (Exception e) {
            logger.error("Erro ao cadastrar paciente: ", e);
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar paciente: " + e.getMessage());
            return "redirect:/pacientes/novo";
        }
    }

    @GetMapping("/{cpf}")
    public String exibirPaciente(@PathVariable String cpf, Model model, RedirectAttributes redirectAttributes) {
        try {
            Paciente paciente = pacienteService.obterPacientePorCpf(cpf);
            model.addAttribute("paciente", new PacienteDTO(paciente));
            return "pacientes/detalhes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Paciente não encontrado.");
            return "redirect:/pacientes";
        }
    }

    @GetMapping("/{cpf}/editar")
    public String editarPaciente(@PathVariable String cpf, Model model, RedirectAttributes redirectAttributes) {
        try {
            Paciente paciente = pacienteService.obterPacientePorCpf(cpf);
            model.addAttribute("pacienteDTO", new PacienteDTO(paciente));
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "pacientes/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Paciente não encontrado.");
            return "redirect:/pacientes";
        }
    }

    @PostMapping("/{cpf}/editar")
    public String atualizarPaciente(@PathVariable String cpf,
                                    @Valid @ModelAttribute PacienteDTO pacienteDTO,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        if (!cpf.equals(pacienteDTO.getCpf())) {
            result.rejectValue("cpf", "error.pacienteDTO", "O CPF não pode ser alterado");
        }

        if (result.hasErrors()) {
            model.addAttribute("clinicas", clinicaService.listarTodasClinicas().stream()
                    .map(ClinicaDTO::new)
                    .collect(Collectors.toList()));
            model.addAttribute("generos", Genero.values());
            model.addAttribute("statusOptions", Status.values());
            return "pacientes/form";
        }

        try {
            Paciente paciente = pacienteDTO.toEntity();
            pacienteService.atualizarPaciente(cpf, paciente);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Paciente atualizado com sucesso!");
            return "redirect:/pacientes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar paciente: " + e.getMessage());
            return "redirect:/pacientes/" + cpf + "/editar";
        }
    }

    @PostMapping("/{cpf}/excluir")
    public String excluirPaciente(@PathVariable String cpf, RedirectAttributes redirectAttributes) {
        try {
            pacienteService.deletarPaciente(cpf);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Paciente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir paciente: " + e.getMessage());
        }

        return "redirect:/pacientes";
    }
}
