package br.vitor_costa_lemos.acervo.aplicacao;

import br.vitor_costa_lemos.acervo.entidade.Biblioteca;
import br.vitor_costa_lemos.acervo.entidade.Livro;
import br.vitor_costa_lemos.acervo.repositorio.BibliotecaRepository;
import br.vitor_costa_lemos.acervo.repositorio.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = obterOpcaoUsuario();
            processarOpcao(opcao);
        } while (opcao != 0);
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
        scanner.nextLine(); // Consumir quebra de linha
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

        if (titulo.isBlank() || autor.isBlank()) {
            System.out.println("Título e autor são obrigatórios.");
            return;
        }

        if (livroRepository.existsByTituloIgnoreCaseAndAutorIgnoreCase(titulo, autor)) {
            System.out.println("Livro já cadastrado.");
            return;
        }

        Biblioteca biblioteca = escolherBiblioteca();
        if (biblioteca == null) {
            System.out.println("Nenhuma biblioteca válida selecionada.");
            return;
        }

        livroRepository.save(new Livro(titulo, autor, ano, editora, biblioteca));
        System.out.println("Livro cadastrado com sucesso!");
    }

    private Biblioteca escolherBiblioteca() {
        List<Biblioteca> bibliotecas = bibliotecaRepository.findAll();
        if (bibliotecas.isEmpty()) {
            System.out.println("Nenhuma biblioteca encontrada. Cadastre pelo menos uma no banco.");
            return null;
        }

        System.out.println("\nEscolha a biblioteca:");
        for (int i = 0; i < bibliotecas.size(); i++) {
            Biblioteca b = bibliotecas.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, b.getNome(), b.getLocal());
        }

        int opcao;
        do {
            System.out.print("Digite o número correspondente à biblioteca: ");
            opcao = obterInteiro();
        } while (opcao < 1 || opcao > bibliotecas.size());

        return bibliotecas.get(opcao - 1);
    }

    private void listarLivros() {
        System.out.println("\n[Listagem Completa do Acervo]");
        System.out.println("ID | Título                          | Autor            | Ano | Editora       | Biblioteca");
        System.out.println("---------------------------------------------------------------------------------------------");
        livroRepository.findAll().forEach(l ->
                System.out.printf("%2d | %-30s | %-15s | %4d | %-13s | %s\n",
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getAnoPublicacao(),
                        l.getEditora(),
                        l.getBiblioteca() != null ? l.getBiblioteca().getNome() : "N/D")
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
        scanner.nextLine(); // Consumir quebra de linha
        return valor;
    }

    private void exibirLivrosEncontrados(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            livros.forEach(l -> System.out.printf("- %s, por %s (%d, %s) [Biblioteca: %s]\n",
                    l.getTitulo(), l.getAutor(), l.getAnoPublicacao(),
                    l.getEditora(),
                    l.getBiblioteca() != null ? l.getBiblioteca().getNome() : "N/D"));
        }
    }
}
