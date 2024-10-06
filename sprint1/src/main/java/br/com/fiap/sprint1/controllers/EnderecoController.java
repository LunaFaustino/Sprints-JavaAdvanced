package br.com.fiap.sprint1.controllers;

import br.com.fiap.sprint1.dtos.EnderecoDTO;
import br.com.fiap.sprint1.models.Endereco;
import br.com.fiap.sprint1.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() {
        List<Endereco> enderecos = enderecoService.listarTodosEnderecos();
        List<EnderecoDTO> enderecosDTO = enderecos.stream()
                .map(this::mapearEnderecoParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enderecosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@PathVariable int id) {
        Endereco endereco = enderecoService.obterEnderecoPorId(id);
        if (endereco != null) {
            EnderecoDTO enderecoDTO = mapearEnderecoParaDTO(endereco);
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
