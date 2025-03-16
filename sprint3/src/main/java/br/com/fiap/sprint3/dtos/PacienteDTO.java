package br.com.fiap.sprint3.dtos;

import br.com.fiap.sprint3.models.Clinica;
import br.com.fiap.sprint3.models.Genero;
import br.com.fiap.sprint3.models.Paciente;
import br.com.fiap.sprint3.models.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class PacienteDTO {

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @Min(value = 0, message = "A idade não pode ser negativa")
    private Integer idade;

    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private Long telefone;

    private Genero genero;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    @NotBlank(message = "A clínica é obrigatória")
    private String clinicaCnpj;

    private String nomeClinica; // Campo auxiliar para exibir nome da clínica

    @Valid
    private EnderecoDTO endereco; // Corrigido para remover o cedilha

    // Construtor vazio necessário para formulários
    public PacienteDTO() {
    }

    public PacienteDTO(Paciente paciente) {
        this.cpf = paciente.getCpf();
        this.nome = paciente.getNome();
        this.idade = paciente.getIdade();
        this.dataNascimento = paciente.getDataNascimento();
        this.email = paciente.getEmail();
        this.telefone = paciente.getTelefone();
        this.genero = paciente.getGenero();
        this.status = paciente.getStatus();

        if (paciente.getClinica() != null) {
            this.clinicaCnpj = paciente.getClinica().getCnpj();
            this.nomeClinica = paciente.getClinica().getNomeFantasia();
        }

        if (paciente.getEndereco() != null) {
            this.endereco = new EnderecoDTO(paciente.getEndereco());
        }
    }

    public Paciente toEntity() {
        Paciente paciente = new Paciente();
        paciente.setCpf(this.cpf);
        paciente.setNome(this.nome);
        paciente.setDataNascimento(this.dataNascimento);
        paciente.setEmail(this.email);
        paciente.setTelefone(this.telefone);
        paciente.setGenero(this.genero);
        paciente.setStatus(this.status);

        if (this.idade != null) {
            paciente.setIdade(this.idade);
        } else if (this.dataNascimento != null) {
            int calculatedAge = Period.between(this.dataNascimento, LocalDate.now()).getYears();
            paciente.setIdade(calculatedAge);
        } else {
            paciente.setIdade(0);
        }

        paciente.setDataNascimento(this.dataNascimento);

        if (this.clinicaCnpj != null) {
            Clinica clinica = new Clinica();
            clinica.setCnpj(this.clinicaCnpj);
            paciente.setClinica(clinica);
        }

        if (this.endereco != null) {
            paciente.setEndereco(this.endereco.toEntity());
        }

        return paciente;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setClinicaCnpj(String clinicaCnpj) {
        this.clinicaCnpj = clinicaCnpj;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getClinicaCnpj() {
        return clinicaCnpj;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacienteDTO that = (PacienteDTO) o;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "PacienteDTO{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", dataNascimento=" + dataNascimento +
                ", email='" + email + '\'' +
                ", telefone=" + telefone +
                ", genero=" + genero +
                ", status=" + status +
                ", clinicaCnpj='" + clinicaCnpj + '\'' +
                ", nomeClinica='" + nomeClinica + '\'' +
                '}';
    }
}
