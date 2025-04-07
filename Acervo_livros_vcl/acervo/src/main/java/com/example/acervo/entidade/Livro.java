package com.example.acervo.entidade;

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

    @Id
    private Long id2;

    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String editora;

    public Livro(String titulo, String autor, int anoPublicacao, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
    }

}
