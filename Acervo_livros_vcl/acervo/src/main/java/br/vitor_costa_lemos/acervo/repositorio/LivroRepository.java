package br.vitor_costa_lemos.acervo.repositorio;

import br.vitor_costa_lemos.acervo.entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    //@Query("SELECT l FROM Livro l WHERE LOWER(l.anoPublicacao) = LOWER(:titulo) AND LOWER(l.autor) = LOWER(:autor)")
    boolean existsByTituloIgnoreCaseAndAutorIgnoreCase(String titulo, String autor);

    //@Query(value = "SELECT l FROM Livro l WHERE LOWER(l.titulo) = LOWER(:titulo) AND LOWER(l.autor) = LOWER(:autor)", nativeQuery = true)
    List<Livro> findByAutorIgnoreCase(String autor);

    List<Livro> findByAnoPublicacao(int ano);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

}

