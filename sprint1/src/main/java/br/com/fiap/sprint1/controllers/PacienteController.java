package br.com.fiap.sprint1.controllers;

import br.com.fiap.sprint1.dtos.EnderecoDTO;
import br.com.fiap.sprint1.dtos.PacienteDTO;
import br.com.fiap.sprint1.models.Clinica;
import br.com.fiap.sprint1.models.Endereco;
import br.com.fiap.sprint1.models.Paciente;
import br.com.fiap.sprint1.service.ClinicaService;
import br.com.fiap.sprint1.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private final ClinicaService clinicaService;

    public PacienteController(PacienteService pacienteService, ClinicaService clinicaService) {
        this.pacienteService = pacienteService;
        this.clinicaService = clinicaService;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> criarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {

        if (pacienteDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        Clinica clinica = clinicaService.obterClinicaPorId(pacienteDTO.getClinicaId());
        if (clinica == null) {
            return ResponseEntity.badRequest().build(); // Ou lançar uma exceção personalizada
        }

        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setIdade(pacienteDTO.getIdade());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setTelefone(pacienteDTO.getTelefone());
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setStatus(pacienteDTO.getStatus());
        paciente.setClinica(clinica);

        EnderecoDTO enderecoDTO = pacienteDTO.getEndereco();
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());

        paciente.setEndereco(endereco);

        Paciente novoPaciente = pacienteService.salvarPaciente(paciente);

        PacienteDTO responseDTO = mapearPacienteParaDTO(novoPaciente);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente(@PathVariable int id) {
        Paciente paciente = pacienteService.obterPacientePorId(id);
        if (paciente != null) {
            PacienteDTO pacienteDTO = mapearPacienteParaDTO(paciente);
            return ResponseEntity.ok(pacienteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarTodosPacientes();
        List<PacienteDTO> pacientesDTO = pacientes.stream()
                .map(this::mapearPacienteParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientesDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(@PathVariable int id, @Valid @RequestBody PacienteDTO pacienteDTO) {

        Paciente pacienteExistente = pacienteService.obterPacientePorId(id);
        if (pacienteExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pacienteExistente.setNome(pacienteDTO.getNome());
        pacienteExistente.setIdade(pacienteDTO.getIdade());
        pacienteExistente.setDataNascimento(pacienteDTO.getDataNascimento());
        pacienteExistente.setCpf(pacienteDTO.getCpf());
        pacienteExistente.setEmail(pacienteDTO.getEmail());
        pacienteExistente.setTelefone(pacienteDTO.getTelefone());
        pacienteExistente.setGenero(pacienteDTO.getGenero());
        pacienteExistente.setStatus(pacienteDTO.getStatus());

        if (pacienteDTO.getClinicaId() != null) {
            Clinica clinica = clinicaService.obterClinicaPorId(pacienteDTO.getClinicaId());
            if (clinica == null) {
                return ResponseEntity.badRequest().build();
            }
            pacienteExistente.setClinica(clinica);
        }

        Paciente pacienteAtualizado = pacienteService.salvarPaciente(pacienteExistente);

        PacienteDTO responseDTO = mapearPacienteParaDTO(pacienteAtualizado);

        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable int id) {
        Paciente pacienteExistente = pacienteService.obterPacientePorId(id);
        if (pacienteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    private PacienteDTO mapearPacienteParaDTO(Paciente paciente) {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNome(paciente.getNome());
        pacienteDTO.setIdade(paciente.getIdade());
        pacienteDTO.setDataNascimento(paciente.getDataNascimento());
        pacienteDTO.setCpf(paciente.getCpf());
        pacienteDTO.setEmail(paciente.getEmail());
        pacienteDTO.setTelefone(paciente.getTelefone());
        pacienteDTO.setGenero(paciente.getGenero());
        pacienteDTO.setStatus(paciente.getStatus());
        pacienteDTO.setClinicaId(paciente.getClinica().getId());

        Endereco endereco = paciente.getEndereco();
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setCep(endereco.getCep());

        pacienteDTO.setEndereco(enderecoDTO);

        return pacienteDTO;
    }

}
