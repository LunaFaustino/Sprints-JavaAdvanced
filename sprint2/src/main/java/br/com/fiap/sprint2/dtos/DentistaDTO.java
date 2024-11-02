package br.com.fiap.sprint2.dtos;

import br.com.fiap.sprint2.models.Genero;
import br.com.fiap.sprint2.models.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class DentistaDTO extends RepresentationModel<DentistaDTO> {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotBlank(message = "O CRO é obrigatório")
    @Size(max = 9, message = "O CRO deve ter no máximo 9 caracteres")
    private String cro;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private Integer clinicaId;
}
