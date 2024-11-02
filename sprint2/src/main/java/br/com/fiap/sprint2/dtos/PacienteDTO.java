package br.com.fiap.sprint2.dtos;

import br.com.fiap.sprint2.models.Genero;
import br.com.fiap.sprint2.models.Status;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class PacienteDTO extends RepresentationModel<PacienteDTO> {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private Integer idade;

    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private Integer clinicaId;

    private EnderecoDTO endereco;
}
