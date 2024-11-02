package br.com.fiap.sprint2.controllers;

import br.com.fiap.sprint2.dtos.EnderecoDTO;
import br.com.fiap.sprint2.models.Endereco;
import br.com.fiap.sprint2.service.EnderecoService;
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
@RequestMapping("/enderecos")
@RequiredArgsConstructor
@Slf4j
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<CollectionModel<EnderecoDTO>> listarEnderecos() {
        List<EnderecoDTO> enderecosDTO = enderecoService.listarTodosEnderecos().stream()
                .map(this::mapearEnderecoParaDTO)
                .map(dto -> {
                    dto.add(linkTo(methodOn(EnderecoController.class).buscarEndereco(dto.getId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<EnderecoDTO> collectionModel = CollectionModel.of(enderecosDTO);
        collectionModel.add(linkTo(methodOn(EnderecoController.class).listarEnderecos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable int id) {
        Endereco endereco = enderecoService.obterEnderecoPorId(id);
        if (endereco != null) {
            EnderecoDTO enderecoDTO = mapearEnderecoParaDTO(endereco);
            enderecoDTO.add(linkTo(methodOn(EnderecoController.class).buscarEndereco(id)).withSelfRel());
            enderecoDTO.add(linkTo(methodOn(EnderecoController.class).listarEnderecos()).withRel("todosEnderecos"));

            return ResponseEntity.ok(enderecoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable int id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        Endereco enderecoExistente = enderecoService.obterEnderecoPorId(id);
        if (enderecoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        enderecoExistente.setLogradouro(enderecoDTO.getLogradouro());
        enderecoExistente.setNumero(enderecoDTO.getNumero());
        enderecoExistente.setComplemento(enderecoDTO.getComplemento());
        enderecoExistente.setBairro(enderecoDTO.getBairro());
        enderecoExistente.setCidade(enderecoDTO.getCidade());
        enderecoExistente.setEstado(enderecoDTO.getEstado());
        enderecoExistente.setCep(enderecoDTO.getCep());

        Endereco enderecoAtualizado = enderecoService.salvarEndereco(enderecoExistente);
        EnderecoDTO responseDTO = mapearEnderecoParaDTO(enderecoAtualizado);

        responseDTO.add(linkTo(methodOn(EnderecoController.class).buscarEndereco(responseDTO.getId())).withSelfRel());
        responseDTO.add(linkTo(methodOn(EnderecoController.class).listarEnderecos()).withRel("todosEnderecos"));

        return ResponseEntity.ok(responseDTO);
    }

    private EnderecoDTO mapearEnderecoParaDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setCep(endereco.getCep());
        return enderecoDTO;
    }
}
