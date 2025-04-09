package br.vitor_costa_lemos.acervo.entidade;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String editora;

    @ManyToOne
    @JoinColumn(name = "biblioteca_id")
    private Biblioteca biblioteca;

    public Livro(String titulo, String autor, int anoPublicacao, String editora, Biblioteca biblioteca) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.biblioteca = biblioteca;
    }
}
