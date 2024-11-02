package br.com.fiap.sprint2.controllers;

import br.com.fiap.sprint2.dtos.ClinicaDTO;
import br.com.fiap.sprint2.dtos.EnderecoDTO;
import br.com.fiap.sprint2.models.Clinica;
import br.com.fiap.sprint2.models.Endereco;
import br.com.fiap.sprint2.service.ClinicaService;
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
@RequestMapping("/clinicas")
@RequiredArgsConstructor
@Slf4j
public class ClinicaController {

    private final ClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<ClinicaDTO> criarClinica(@Valid @RequestBody ClinicaDTO clinicaDTO) {
        if (clinicaDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        Clinica clinica = mapearDTOParaClinica(clinicaDTO);
        Clinica novaClinica = clinicaService.salvarClinica(clinica);
        ClinicaDTO clinicaRespostaDTO = mapearClinicaParaDTO(novaClinica);

        clinicaRespostaDTO.add(linkTo(methodOn(ClinicaController.class).buscarClinica(clinicaRespostaDTO.getId())).withSelfRel());
        clinicaRespostaDTO.add(linkTo(methodOn(ClinicaController.class).listarClinicas()).withRel("todasClinicas"));

        return ResponseEntity.status(201).body(clinicaRespostaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicaDTO> buscarClinica(@PathVariable int id) {
        Clinica clinica = clinicaService.obterClinicaPorId(id);
        if (clinica != null) {
            ClinicaDTO clinicaDTO = mapearClinicaParaDTO(clinica);
            clinicaDTO.add(linkTo(methodOn(ClinicaController.class).buscarClinica(id)).withSelfRel());
            clinicaDTO.add(linkTo(methodOn(ClinicaController.class).listarClinicas()).withRel("todasClinicas"));

            return ResponseEntity.ok(clinicaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ClinicaDTO>> listarClinicas() {
        List<ClinicaDTO> clinicasDTO = clinicaService.listarTodasClinicas().stream()
                .map(this::mapearClinicaParaDTO)
                .map(dto -> {
                    dto.add(linkTo(methodOn(ClinicaController.class).buscarClinica(dto.getId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<ClinicaDTO> collectionModel = CollectionModel.of(clinicasDTO);
        collectionModel.add(linkTo(methodOn(ClinicaController.class).listarClinicas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicaDTO> atualizarClinica(@PathVariable int id, @Valid @RequestBody ClinicaDTO clinicaDTO) {
        Clinica clinicaExistente = clinicaService.obterClinicaPorId(id);
        if (clinicaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        clinicaExistente.setRazaoSocial(clinicaDTO.getRazaoSocial());
        clinicaExistente.setNomeFantasia(clinicaDTO.getNomeFantasia());
        clinicaExistente.setCnpj(clinicaDTO.getCnpj());
        clinicaExistente.setEmail(clinicaDTO.getEmail());
        clinicaExistente.setTelefone(clinicaDTO.getTelefone());
        clinicaExistente.setPorte(clinicaDTO.getPorte());
        clinicaExistente.setStatus(clinicaDTO.getStatus());
        clinicaExistente.setDataAbertura(clinicaDTO.getDataAbertura());

        Clinica clinicaAtualizada = clinicaService.salvarClinica(clinicaExistente);
        ClinicaDTO clinicaRespostaDTO = mapearClinicaParaDTO(clinicaAtualizada);

        clinicaRespostaDTO.add(linkTo(methodOn(ClinicaController.class).buscarClinica(clinicaRespostaDTO.getId())).withSelfRel());
        clinicaRespostaDTO.add(linkTo(methodOn(ClinicaController.class).listarClinicas()).withRel("todasClinicas"));

        return ResponseEntity.ok(clinicaRespostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarClinica(@PathVariable int id) {
        Clinica clinicaExistente = clinicaService.obterClinicaPorId(id);
        if (clinicaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        clinicaService.deletarClinica(id);
        return ResponseEntity.noContent().build();
    }

    private Clinica mapearDTOParaClinica(ClinicaDTO clinicaDTO) {
        Clinica clinica = new Clinica();
        clinica.setRazaoSocial(clinicaDTO.getRazaoSocial());
        clinica.setNomeFantasia(clinicaDTO.getNomeFantasia());
        clinica.setCnpj(clinicaDTO.getCnpj());
        clinica.setEmail(clinicaDTO.getEmail());
        clinica.setTelefone(clinicaDTO.getTelefone());
        clinica.setPorte(clinicaDTO.getPorte());
        clinica.setStatus(clinicaDTO.getStatus());
        clinica.setDataAbertura(clinicaDTO.getDataAbertura());

        if (clinicaDTO.getEndereco() != null) {
            EnderecoDTO enderecoDTO = clinicaDTO.getEndereco();
            Endereco endereco = new Endereco();
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setEstado(enderecoDTO.getEstado());
            endereco.setCep(enderecoDTO.getCep());
            clinica.setEndereco(endereco);
        }

        return clinica;
    }

    private ClinicaDTO mapearClinicaParaDTO(Clinica clinica) {
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setId(clinica.getId());
        clinicaDTO.setRazaoSocial(clinica.getRazaoSocial());
        clinicaDTO.setNomeFantasia(clinica.getNomeFantasia());
        clinicaDTO.setCnpj(clinica.getCnpj());
        clinicaDTO.setEmail(clinica.getEmail());
        clinicaDTO.setTelefone(clinica.getTelefone());
        clinicaDTO.setPorte(clinica.getPorte());
        clinicaDTO.setStatus(clinica.getStatus());
        clinicaDTO.setDataAbertura(clinica.getDataAbertura());

        if (clinica.getEndereco() != null) {
            Endereco endereco = clinica.getEndereco();
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setId(endereco.getId());
            enderecoDTO.setLogradouro(endereco.getLogradouro());
            enderecoDTO.setNumero(endereco.getNumero());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setBairro(endereco.getBairro());
            enderecoDTO.setCidade(endereco.getCidade());
            enderecoDTO.setEstado(endereco.getEstado());
            enderecoDTO.setCep(endereco.getCep());
            clinicaDTO.setEndereco(enderecoDTO);
        }

        return clinicaDTO;
    }
}
