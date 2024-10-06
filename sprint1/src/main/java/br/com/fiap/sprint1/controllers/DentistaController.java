package br.com.fiap.sprint1.controllers;

import br.com.fiap.sprint1.dtos.DentistaDTO;
import br.com.fiap.sprint1.models.Clinica;
import br.com.fiap.sprint1.models.Dentista;
import br.com.fiap.sprint1.service.ClinicaService;
import br.com.fiap.sprint1.service.DentistaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    private final DentistaService dentistaService;
    private final ClinicaService clinicaService;

    public DentistaController(DentistaService dentistaService, ClinicaService clinicaService) {
        this.dentistaService = dentistaService;
        this.clinicaService = clinicaService;
    }

    @PostMapping
    public ResponseEntity<DentistaDTO> criarDentista(@Valid @RequestBody DentistaDTO dentistaDTO) {

        if (dentistaDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        Clinica clinica = clinicaService.obterClinicaPorId(dentistaDTO.getClinicaId());
        if (clinica == null) {
            return ResponseEntity.badRequest().build();
        }

        Dentista dentista = new Dentista();
        dentista.setNome(dentistaDTO.getNome());
        dentista.setCro(dentistaDTO.getCro());
        dentista.setEmail(dentistaDTO.getEmail());
        dentista.setTelefone(dentistaDTO.getTelefone());
        dentista.setGenero(dentistaDTO.getGenero());
        dentista.setStatus(dentistaDTO.getStatus());
        dentista.setClinica(clinica);

        Dentista novoDentista = dentistaService.salvarDentista(dentista);

        DentistaDTO responseDTO = mapearDentistaParaDTO(novoDentista);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaDTO> buscarDentista(@PathVariable int id) {
        Dentista dentista = dentistaService.obterDentistaPorId(id);
        if (dentista != null) {
            DentistaDTO dentistaDTO = mapearDentistaParaDTO(dentista);
            return ResponseEntity.ok(dentistaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DentistaDTO>> listarDentistas() {
        List<Dentista> dentistas = dentistaService.listarTodosDentistas();
        List<DentistaDTO> dentistasDTO = dentistas.stream()
                .map(this::mapearDentistaParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dentistasDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaDTO> atualizarDentista(@PathVariable int id, @Valid @RequestBody DentistaDTO dentistaDTO) {

        Dentista dentistaExistente = dentistaService.obterDentistaPorId(id);
        if (dentistaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        dentistaExistente.setNome(dentistaDTO.getNome());
        dentistaExistente.setCro(dentistaDTO.getCro());
        dentistaExistente.setEmail(dentistaDTO.getEmail());
        dentistaExistente.setTelefone(dentistaDTO.getTelefone());
        dentistaExistente.setGenero(dentistaDTO.getGenero());
        dentistaExistente.setStatus(dentistaDTO.getStatus());

        if (dentistaDTO.getClinicaId() != null) {
            Clinica clinica = clinicaService.obterClinicaPorId(dentistaDTO.getClinicaId());
            if (clinica == null) {
                return ResponseEntity.badRequest().build();
            }
            dentistaExistente.setClinica(clinica);
        }

        Dentista dentistaAtualizado = dentistaService.salvarDentista(dentistaExistente);

        DentistaDTO responseDTO = mapearDentistaParaDTO(dentistaAtualizado);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDentista(@PathVariable int id) {
        Dentista dentistaExistente = dentistaService.obterDentistaPorId(id);
        if (dentistaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        dentistaService.deletarDentista(id);
        return ResponseEntity.noContent().build();
    }

    private DentistaDTO mapearDentistaParaDTO(Dentista dentista) {
        DentistaDTO dentistaDTO = new DentistaDTO();
        dentistaDTO.setId(dentista.getId());
        dentistaDTO.setNome(dentista.getNome());
        dentistaDTO.setCro(dentista.getCro());
        dentistaDTO.setEmail(dentista.getEmail());
        dentistaDTO.setTelefone(dentista.getTelefone());
        dentistaDTO.setGenero(dentista.getGenero());
        dentistaDTO.setStatus(dentista.getStatus());
        dentistaDTO.setClinicaId(dentista.getClinica().getId());
        return dentistaDTO;
    }
}
