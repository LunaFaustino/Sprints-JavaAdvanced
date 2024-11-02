package br.com.fiap.sprint2.controllers;

import br.com.fiap.sprint2.dtos.DentistaDTO;
import br.com.fiap.sprint2.models.Clinica;
import br.com.fiap.sprint2.models.Dentista;
import br.com.fiap.sprint2.service.ClinicaService;
import br.com.fiap.sprint2.service.DentistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/dentistas")
@RequiredArgsConstructor
@Slf4j
public class DentistaController {

    private final DentistaService dentistaService;
    private final ClinicaService clinicaService;

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
        responseDTO.add(linkTo(methodOn(DentistaController.class).buscarDentista(responseDTO.getId())).withSelfRel());

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaDTO> buscarDentista(@PathVariable int id) {
        Dentista dentista = dentistaService.obterDentistaPorId(id);
        if (dentista != null) {
            DentistaDTO dentistaDTO = mapearDentistaParaDTO(dentista);
            dentistaDTO.add(linkTo(methodOn(DentistaController.class).buscarDentista(id)).withSelfRel());
            dentistaDTO.add(linkTo(methodOn(DentistaController.class).listarDentistas()).withRel("todosDentistas"));
            return ResponseEntity.ok(dentistaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DentistaDTO>> listarDentistas() {
        List<DentistaDTO> dentistasDTO = dentistaService.listarTodosDentistas().stream()
                .map(dentista -> {
                    DentistaDTO dto = mapearDentistaParaDTO(dentista);
                    dto.add(linkTo(methodOn(DentistaController.class).buscarDentista(dto.getId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<DentistaDTO> dentistasCollection = CollectionModel.of(dentistasDTO);
        dentistasCollection.add(linkTo(methodOn(DentistaController.class).listarDentistas()).withSelfRel());

        return ResponseEntity.ok(dentistasCollection);
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
        responseDTO.add(linkTo(methodOn(DentistaController.class).buscarDentista(responseDTO.getId())).withSelfRel());
        responseDTO.add(linkTo(methodOn(DentistaController.class).listarDentistas()).withRel("todosDentistas"));

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
