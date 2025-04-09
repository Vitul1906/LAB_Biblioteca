package br.vitor_costa_lemos.acervo.entidade;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String local;

    @OneToMany(mappedBy = "biblioteca")
    private List<Livro> livros;

    public Biblioteca(String nome, String local) {
        this.nome = nome;
        this.local = local;
    }
}