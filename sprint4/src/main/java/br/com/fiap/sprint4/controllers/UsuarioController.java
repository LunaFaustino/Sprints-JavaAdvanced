package br.com.fiap.sprint4.controllers;

import br.com.fiap.sprint4.dtos.UsuarioDTO;
import br.com.fiap.sprint4.models.Status;
import br.com.fiap.sprint4.models.Usuario;
import br.com.fiap.sprint4.repositories.PerfilRepository;
import br.com.fiap.sprint4.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilRepository perfilRepository;

    public UsuarioController(UsuarioService usuarioService, PerfilRepository perfilRepository) {
        this.usuarioService = usuarioService;
        this.perfilRepository = perfilRepository;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosUsuarios().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        model.addAttribute("perfis", perfilRepository.findAll());
        model.addAttribute("statusOptions", Status.values());
        return "usuarios/form";
    }

    @PostMapping("/novo")
    public String salvarUsuario(@Valid @ModelAttribute UsuarioDTO usuarioDTO,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (!usuarioDTO.getPassword().equals(usuarioDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.usuarioDTO", "As senhas não coincidem");
        }

        if (result.hasErrors()) {
            model.addAttribute("perfis", perfilRepository.findAll());
            model.addAttribute("statusOptions", Status.values());
            return "usuarios/form";
        }

        try {
            Usuario usuario = usuarioDTO.toEntity();

            if (usuarioDTO.getPerfisIds() != null) {
                for (String perfilId : usuarioDTO.getPerfisIds()) {
                    perfilRepository.findById(perfilId).ifPresent(usuario::addPerfil);
                }
            }

            usuarioService.salvarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar usuário: " + e.getMessage());
            return "redirect:/usuarios/novo";
        }
    }

    @GetMapping("/{username}")
    public String exibirUsuario(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obterUsuarioPorUsername(username);
            model.addAttribute("usuario", new UsuarioDTO(usuario));
            return "usuarios/detalhes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Usuário não encontrado.");
            return "redirect:/usuarios";
        }
    }

    @GetMapping("/{username}/editar")
    public String editarUsuario(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obterUsuarioPorUsername(username);
            model.addAttribute("usuarioDTO", new UsuarioDTO(usuario));
            model.addAttribute("perfis", perfilRepository.findAll());
            model.addAttribute("statusOptions", Status.values());
            return "usuarios/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Usuário não encontrado.");
            return "redirect:/usuarios";
        }
    }

    @PostMapping("/{username}/editar")
    public String atualizarUsuario(@PathVariable String username,
                                   @Valid @ModelAttribute UsuarioDTO usuarioDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (!username.equals(usuarioDTO.getUsername())) {
            result.rejectValue("username", "error.usuarioDTO", "O username não pode ser alterado");
        }

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty() ||
                usuarioDTO.getConfirmPassword() != null && !usuarioDTO.getConfirmPassword().isEmpty()) {

            if (!usuarioDTO.getPassword().equals(usuarioDTO.getConfirmPassword())) {
                result.rejectValue("confirmPassword", "error.usuarioDTO", "As senhas não coincidem");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("perfis", perfilRepository.findAll());
            model.addAttribute("statusOptions", Status.values());
            return "usuarios/form";
        }

        try {
            Usuario usuario = usuarioDTO.toEntity();

            usuario.getPerfis().clear();
            if (usuarioDTO.getPerfisIds() != null) {
                for (String perfilId : usuarioDTO.getPerfisIds()) {
                    perfilRepository.findById(perfilId).ifPresent(usuario::addPerfil);
                }
            }

            usuarioService.atualizarUsuario(username, usuario);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário atualizado com sucesso!");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar usuário: " + e.getMessage());
            return "redirect:/usuarios/" + username + "/editar";
        }
    }

    @PostMapping("/{username}/excluir")
    public String excluirUsuario(@PathVariable String username, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deletarUsuario(username);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir usuário: " + e.getMessage());
        }

        return "redirect:/usuarios";
    }
}
