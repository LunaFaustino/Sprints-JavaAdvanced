package br.com.fiap.sprint2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_endereco")
@SequenceGenerator(name = "endereco", sequenceName = "sq_tb_endereco", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
    @Column(name = "id_endereco")
    private int id;

    @Column(name = "ds_logradouro", nullable = false)
    private String logradouro;

    @Column(name = "nr_numero")
    private int numero;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "ds_bairro", nullable = false)
    private String bairro;

    @Column(name = "ds_cidade", nullable = false)
    private String cidade;

    @Column(name = "ds_estado", nullable = false)
    private String estado;

    @Column(name = "nr_cep", nullable = false, length = 9)
    private String cep;
}
