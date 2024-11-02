package br.com.fiap.sprint2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clinica")
@SequenceGenerator(name = "clinica", sequenceName = "sq_tb_clinica", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinica")
    @Column(name = "id_clinica")
    private int id;

    @Column(name = "nm_razaoSocial", nullable = false)
    private String razaoSocial;

    @Column(name = "nm_nomeFantasia", nullable = false)
    private String nomeFantasia;

    @Column(name = "nr_cnpj", nullable = false, length = 14)
    private String cnpj;

    @Column(name = "ds_email", nullable = false)
    private String email;

    @Column(name = "nr_tel")
    private Long telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_porte", length = 10)
    private Porte porte;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", nullable = false, length = 10)
    private Status status;

    @Column(name = "dt_abertura")
    private LocalDate dataAbertura;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false)
    private LocalDate dataCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Paciente> pacientes = new ArrayList<>();

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Dentista> dentistas = new ArrayList<>();
}
