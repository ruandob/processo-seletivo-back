package com.processo.seletivo.domain.mantedora;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Mantenedora implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mantenedora_id_seq")
    @SequenceGenerator(name = "mantenedora_id_seq", sequenceName = "mantenedora_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @NotEmpty
    @Size(max = 80)
    @Column(name = "nome")
    @Getter
    @Setter
    private String nome;

    @NotEmpty
    @Size(max = 10)
    @Column(name = "codigo", updatable = false)
    @Getter
    @Setter
    private String codigo;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "numero_fiscal")
    @Getter
    @Setter
    private String numeroFiscal;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "endereco")
    @Getter
    @Setter
    private String endereco;

//    @Size(max = 50)
//    @Column(name = "bairro")
//    @Getter
//    @Setter
//    private String bairro;
//
//    @Size(max = 80)
//    @Column(name = "logradouro")
//    @Getter
//    @Setter
//    private String logradouro;
//
//    @Size(max = 10)
//    @Column(name = "numero")
//    @Getter
//    @Setter
//    private String numero;
//
//    @Size(max = 20)
//    @Column(name = "caixa_postal")
//    @Getter
//    @Setter
//    private String caixaPostal;
//    @Valid
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
//    @Getter
//    @Setter
//    private Endereco endereco;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mantenedora")
//    @Getter
//    @Setter
//    private List<Instituicao> instituicoes;
//
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
//    @JoinColumn(name = "arquivo_id", referencedColumnName = "id")
//    @Getter
//    @Setter
//    private Arquivo arquivo;
}
