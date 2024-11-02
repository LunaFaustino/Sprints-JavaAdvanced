package br.com.fiap.sprint2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tb_dentista")
@SequenceGenerator(name = "dentista", sequenceName = "sq_tb_dentista", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dentista {

    @Id
    @Column(name = "id_dentista")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dentista")
    private int id;

    @Column(name = "nm_dentista", nullable = false, length = 50)
    private String nome;

    @Column(name = "nr_cro", nullable = false, length = 9)
    private String cro;

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
}
