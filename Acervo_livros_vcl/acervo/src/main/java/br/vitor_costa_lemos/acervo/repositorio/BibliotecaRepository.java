package br.vitor_costa_lemos.acervo.repositorio;

import br.vitor_costa_lemos.acervo.entidade.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
}

