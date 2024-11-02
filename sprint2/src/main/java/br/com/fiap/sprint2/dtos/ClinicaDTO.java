package br.com.fiap.sprint2.dtos;

import br.com.fiap.sprint2.models.Porte;
import br.com.fiap.sprint2.models.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClinicaDTO extends RepresentationModel<ClinicaDTO> {

    private Integer id;

    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "O nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Long telefone;

    private Porte porte;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    private LocalDate dataAbertura;

    @Valid
    private EnderecoDTO endereco;
}
