package com.example.acervo.aplicacao;

import com.example.acervo.entidade.Livro;
import com.example.acervo.repositorio.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    @Autowired
    private LivroRepository livroRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        try {
            int opcao;
            do {
                exibirMenu();
                opcao = obterOpcaoUsuario();
                processarOpcao(opcao);
            } while (opcao != 0);
        } finally {
            scanner.close(); // Fechando o scanner no final para evitar vazamentos de recursos
        }
    }

    private void exibirMenu() {
        System.out.println("\n========= Menu do Acervo =========");
        System.out.println("1. Cadastrar livro");
        System.out.println("2. Listar livros");
        System.out.println("3. Buscar por autor");
        System.out.println("4. Buscar por ano de publicação");
        System.out.println("5. Buscar por termo no título");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int obterOpcaoUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.println("Opção inválida. Digite um número.");
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        return opcao;
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarLivro();
            case 2 -> listarLivros();
            case 3 -> buscarPorAutor();
            case 4 -> buscarPorAno();
            case 5 -> buscarPorTitulo();
            case 0 -> System.out.println("Encerrando...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void cadastrarLivro() {
        System.out.println("\n[Cadastro de Livro]");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ano de publicação: ");
        int ano = obterInteiro();
        System.out.print("Editora: ");
        String editora = scanner.nextLine();

        // Validação de campos obrigatórios
        if (titulo.isBlank() || autor.isBlank()) {
            System.out.println("Título e autor são obrigatórios.");
            return;
        }

        if (livroRepository.existsByTituloIgnoreCaseAndAutorIgnoreCase(titulo, autor)) {
            System.out.println("Livro já cadastrado.");
            return;
        }

        livroRepository.save(new Livro(titulo, autor, ano, editora));
        System.out.println("Livro cadastrado com sucesso!");
    }

    private void listarLivros() {
        System.out.println("\n[Listagem Completa do Acervo]");
        System.out.println("ID | Título                          | Autor            | Ano | Editora");
        System.out.println("---------------------------------------------------------------------------");
        livroRepository.findAll().forEach(l ->
                System.out.printf("%2d | %-30s | %-15s | %4d | %s\n",
                        l.getId(), l.getTitulo(), l.getAutor(), l.getAnoPublicacao(), l.getEditora())
        );
    }

    private void buscarPorAutor() {
        System.out.print("\nDigite o nome do autor: ");
        String autor = scanner.nextLine();
        List<Livro> livros = livroRepository.findByAutorIgnoreCase(autor);
        exibirLivrosEncontrados(livros);
    }

    private void buscarPorAno() {
        System.out.print("\nDigite o ano desejado: ");
        int ano = obterInteiro();
        List<Livro> livros = livroRepository.findByAnoPublicacao(ano);
        exibirLivrosEncontrados(livros);
    }

    private void buscarPorTitulo() {
        System.out.print("\nDigite o termo desejado: ");
        String termo = scanner.nextLine();
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(termo);
        exibirLivrosEncontrados(livros);
    }

    private int obterInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Digite um número válido.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        return valor;
    }

    private void exibirLivrosEncontrados(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            livros.forEach(l ->
                    System.out.printf("- %s, por %s (%d, %s)\n", l.getTitulo(), l.getAutor(), l.getAnoPublicacao(), l.getEditora())
            );
        }
    }
}
