package br.com.fiap.sprint2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tb_paciente")
@SequenceGenerator(name = "paciente", sequenceName = "sq_tb_paciente", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @Column(name = "id_paciente")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente")
    private int id;

    @Column(name = "nm_paciente", nullable = false, length = 50)
    private String nome;

    @Column(name = "nr_idade", nullable = false)
    private int idade;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nr_cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "ds_email", nullable = false)
    private String email;

    @Column(name = "nr_tel")
    private Long telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_genero", length = 10)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 10)
    private Status status;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false)
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_clinica", nullable = false)
    private Clinica clinica;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;
}
