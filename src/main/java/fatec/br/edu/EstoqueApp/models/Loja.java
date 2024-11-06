package fatec.br.edu.EstoqueApp.models;
import fatec.br.edu.EstoqueApp.repositories.ProdutoRepository;
import fatec.br.edu.EstoqueApp.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Loja {
    private static List<Produto> produtos;

    public Loja() {
        this.produtos = new ArrayList<>();
    }

    public static void adicionarProduto(Scanner scanner, EstoqueService estoqueService)
    {
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        // Verificar se o produto já existe no estoque
        Produto produtoExistente = estoqueService.buscarProdutoPorCodigo(codigo);  // Método para buscar produto pelo código
        if (produtoExistente != null) {
            System.out.println("Produto com o código " + codigo + " já existe no estoque.");
            return;  // Não adiciona o produto
        }
        String nome;
        while (true) {
            System.out.print("Digite o nome do produto: ");
            nome = scanner.nextLine();
            if (!nome.isEmpty()) break;
            System.out.println("Nome não pode ser vazio. Tente novamente.");
        }
        int quantidade = -1;
        while (quantidade <= 0) {
            System.out.print("Digite a quantidade do produto: ");
            if (scanner.hasNextInt()) {
                quantidade = scanner.nextInt();
                if (quantidade <= 0) System.out.println("A quantidade deve ser positiva. Tente novamente.");
            } else {
                System.out.println("Quantidade inválida. Digite um número inteiro positivo.");
                scanner.nextLine();  // Limpar buffer
            }
        }
        double preco = -1;
        while (preco <= 0) {
            System.out.print("Digite o preço do produto: ");
            if (scanner.hasNextDouble()) {
                preco = scanner.nextDouble();
                if (preco <= 0) System.out.println("O preço deve ser positivo. Tente novamente.");
            } else {
                System.out.println("Preço inválido. Digite um número positivo.");
                scanner.nextLine();  // Limpar buffer
            }
        }

        // Limpar buffer após a leitura de preço
        scanner.nextLine();

        Produto novoProduto = new Produto(codigo, nome, quantidade, preco);
        estoqueService.adicionarProduto(novoProduto);
        produtos.add(novoProduto);
        System.out.println("Produto adicionado com sucesso!");
    }

    public void removerProduto(String codigo) {
        produtos.removeIf(produto -> produto.getCodigo().equals(codigo));
    }

    public Produto buscarProduto(String codigo) {
        return produtos.stream()
                .filter(produto -> produto.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public void exibirProdutos() {
        produtos.forEach(Produto::exibirInformacoes);
    }

    public double calcularValorTotalEstoque() {
        return produtos.stream()
                .mapToDouble(Produto::calcularValorEstoque)
                .sum();
    }
    public static void buscarProdutoPorNome(Scanner scanner, EstoqueService estoqueService) {
        System.out.print("Digite o nome do produto para buscar: ");
        String nome = scanner.nextLine();
        List<Produto> produtos = estoqueService.buscarPorNome(nome);
        produtos.forEach(System.out::println);
    }

    public static void buscarProdutoPorCodigo(Scanner scanner, EstoqueService estoqueService) {
        System.out.print("Digite o código do produto para buscar: ");
        String codigo = scanner.nextLine();
        List<Produto> produtos = estoqueService.buscarPorCodigo(codigo);
        produtos.forEach(System.out::println);
    }

    public static void buscarProdutoPorNomeECodigo(Scanner scanner, EstoqueService estoqueService) {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();
        List<Produto> produtos = estoqueService.findByNomeAndCodigo(nome, codigo);
        produtos.forEach(System.out::println);
    }

    public static void listarProdutosOrdenadosPorNome(EstoqueService estoqueService) {
        System.out.println("Produtos ordenados por nome:");
        estoqueService.listarProdutosOrdenadosPorNome().forEach(Produto::exibirInformacoes);
    }

    public static void listarProdutosOrdenadosPorPreco(EstoqueService estoqueService) {
        System.out.println("Produtos ordenados por preço:");
        estoqueService.listarProdutosOrdenadosPorPreco().forEach(Produto::exibirInformacoes);
    }

    public static void calcularValorTotalEstoque(EstoqueService estoqueService) {
        System.out.println("Valor total do estoque: " + estoqueService.calcularValorTotalEstoque());
    }

}
