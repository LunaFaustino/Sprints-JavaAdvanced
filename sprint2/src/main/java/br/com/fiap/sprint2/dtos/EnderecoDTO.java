package br.com.fiap.sprint2.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnderecoDTO extends RepresentationModel<EnderecoDTO> {

    private Integer id;

    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    private Integer numero;

    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;
}
