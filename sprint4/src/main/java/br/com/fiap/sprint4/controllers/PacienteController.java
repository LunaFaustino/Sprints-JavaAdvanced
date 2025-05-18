package br.com.fiap.sprint4.controllers;

import br.com.fiap.sprint4.dtos.ClinicaDTO;
import br.com.fiap.sprint4.dtos.PacienteDTO;
import br.com.fiap.sprint4.models.Genero;
import br.com.fiap.sprint4.models.Paciente;
import br.com.fiap.sprint4.models.Status;
import br.com.fiap.sprint4.services.ClinicaService;
import br.com.fiap.sprint4.services.PacienteService;
import br.com.fiap.sprint4.services.impl.NotificationProducerService;
import jakarta.validation.Valid;
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


    private final PacienteService pacienteService;
    private final ClinicaService clinicaService;
    private final NotificationProducerService notificationService;

    public PacienteController(PacienteService pacienteService, ClinicaService clinicaService, NotificationProducerService notificationService) {
        this.pacienteService = pacienteService;
        this.clinicaService = clinicaService;
        this.notificationService = notificationService;
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

            Paciente pacienteSalvo = pacienteService.salvarPaciente(paciente);

            notificationService.enviarNotificacaoPaciente(
                    pacienteSalvo.getCpf(),
                    "CADASTRO",
                    "Novo paciente cadastrado: " + pacienteSalvo.getNome()
            );

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Paciente cadastrado com sucesso!");
            return "redirect:/pacientes";
        } catch (Exception e) {
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

            notificationService.enviarNotificacaoPaciente(
                    paciente.getCpf(),
                    "ATUALIZAÇÃO",
                    "Paciente atualizado: " + paciente.getNome()
            );

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
            Paciente paciente = pacienteService.obterPacientePorCpf(cpf);
            String nomePaciente = paciente.getNome();

            pacienteService.deletarPaciente(cpf);

            notificationService.enviarNotificacaoPaciente(
                    paciente.getCpf(),
                    "EXCLUSÃO",
                    "Paciente excluído: " + nomePaciente
            );

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Paciente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir paciente: " + e.getMessage());
        }

        return "redirect:/pacientes";
    }
}
