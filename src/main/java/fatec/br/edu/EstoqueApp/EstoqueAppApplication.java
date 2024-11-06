package fatec.br.edu.EstoqueApp;

import fatec.br.edu.EstoqueApp.models.Loja;
import fatec.br.edu.EstoqueApp.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import static fatec.br.edu.EstoqueApp.models.Loja.*;

@SpringBootApplication
public class EstoqueAppApplication implements CommandLineRunner {
@Autowired
    private EstoqueService estoqueService;
	public static void main(String[] args) {
		SpringApplication.run(EstoqueAppApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
         Scanner scanner = new Scanner(System.in);
        Scanner scanCod = new Scanner(System.in);
         Loja loja = new Loja();
         String cod;

        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1.  Adicionar Produto no Banco de dados e na Loja");
            System.out.println("2.  Buscar Produto por Nome");
            System.out.println("3.  Buscar Produto por Código");
            System.out.println("4.  Buscar Produto por Nome e Código");
            System.out.println("5.  Listar Produtos Ordenados por Nome");
            System.out.println("6.  Listar Produtos Ordenados por Preço");
            System.out.println("7.  Calcular Valor Total do Estoque banco");
            System.out.println("8.  Remover Produto na Loja");
            System.out.println("10. Buscar Produtos na Loja");
            System.out.println("11. Calcular estoque Loja");
            System.out.println("12. Mostrar estoque Loja");
            System.out.println("13. Sair \n");
            System.out.print("Escolha uma opção: \n");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (escolha) {
                case 1:
                    // Adicionar Produto
                    adicionarProduto(scanner, estoqueService);
                    break;
                case 2:
                    // Buscar Produto por Nome
                    buscarProdutoPorNome(scanner, estoqueService);
                    break;
                case 3:
                    // Buscar Produto por Código
                    buscarProdutoPorCodigo(scanner, estoqueService);
                    break;
                case 4:
                    // Buscar Produto por Nome e Código
                    buscarProdutoPorNomeECodigo(scanner, estoqueService);
                    break;
                case 5:
                    // Listar Produtos Ordenados por Nome
                    listarProdutosOrdenadosPorNome(estoqueService);
                    break;
                case 6:
                    // Listar Produtos Ordenados por Preço
                    listarProdutosOrdenadosPorPreco(estoqueService);
                    break;
                case 7:
                    // Calcular Valor Total do Estoque
                    calcularValorTotalEstoque(estoqueService);
                    break;
                case 9:
                    // Remover Produto na Loja
                    System.out.println("Digite o codigo do produto a ser removido");
                    cod = scanCod.nextLine();
                    loja.removerProduto(cod);
                    break;
                case 10:
                    // Buscar produto na loja
                    System.out.println("Digite o codigo do produto a ser buscado");
                    cod = scanCod.nextLine();
                    System.out.println(loja.buscarProduto(cod));
                    break;
                case 11:
                    // Calcular estoque loja
                    System.out.println(loja.calcularValorTotalEstoque());
                    break;
                case 12:
                    // Listar produtos loja
                    loja.exibirProdutos();
                    break;
                case 13:
                    // Sair
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

  }
