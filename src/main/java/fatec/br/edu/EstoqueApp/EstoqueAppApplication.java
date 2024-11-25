package fatec.br.edu.EstoqueApp;

import fatec.br.edu.EstoqueApp.models.Loja;
import fatec.br.edu.EstoqueApp.models.Produto;
import fatec.br.edu.EstoqueApp.services.EstoqueService;
import fatec.br.edu.EstoqueApp.services.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

import static fatec.br.edu.EstoqueApp.models.Produto.adicionarProduto;

@SpringBootApplication
public class EstoqueAppApplication implements CommandLineRunner {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private LojaService lojaService;

    public static void main(String[] args) {
        SpringApplication.run(EstoqueAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int menu = 0;

        while (menu == 0) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Criar nova loja");
            System.out.println("2. Selecionar loja para operações");
            System.out.println("3. Adicionar Produto ao Banco de Dados");
            System.out.println("4. Adicionar Produto à Loja Selecionada");
            System.out.println("5. Buscar Produto por Nome no Banco de Dados");
            System.out.println("6. Buscar Produto por Código no Banco de Dados");
            System.out.println("7. Listar Produtos Ordenados por Nome (Banco de Dados)");
            System.out.println("8. Listar Produtos Ordenados por Preço (Banco de Dados)");
            System.out.println("9. Calcular Valor Total do Estoque (Banco de Dados)");
            System.out.println("10. Remover Produto do Banco de Dados");
            System.out.println("11. Remover Produto da Loja Selecionada");
            System.out.println("12. Buscar Produto na Loja Selecionada por codigo");
            System.out.println("13. Buscar Produto na Loja Selecionada por nome");
            System.out.println("14. Calcular Estoque da Loja Selecionada");
            System.out.println("15. Mostrar Estoque da Loja Selecionada");
            System.out.println("16. Mostrar Estoque da Loja Selecionada ordenado por Preço");
            System.out.println("17. Sair da Loja Selecionada");
            System.out.println("18. Sair\n");

            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (escolha) {
                case 1:
                    // Criar nova loja
                    System.out.print("Digite o nome da nova loja: ");
                    String nomeLoja = scanner.nextLine();
                    Loja novaLoja = lojaService.criarLoja(nomeLoja);
                    System.out.println("Loja criada: " + novaLoja);
                    break;

                case 2:
                    // Selecionar loja para operações
                    List<Loja> lojas = lojaService.listarLojas();
                    if (lojas.isEmpty()) {
                        System.out.println("Não há lojas cadastradas. Crie uma loja primeiro.");
                    } else {
                        System.out.println("Lojas disponíveis:");
                        for (int i = 0; i < lojas.size(); i++) {
                            System.out.println((i + 1) + ". " + lojas.get(i).getNome());
                        }
                        System.out.print("Escolha o número da loja: ");
                        int escolhaLoja = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer
                        if (escolhaLoja > 0 && escolhaLoja <= lojas.size()) {
                            // Usando o método do LojaService para selecionar a loja
                            Long lojaId = lojas.get(escolhaLoja - 1).getId(); // Pegando o ID da loja selecionada
                            estoqueService.selecionarLoja(lojaId); // Seleciona a loja no LojaService
                            System.out.println("Loja selecionada: " + lojas.get(escolhaLoja - 1).getNome());
                        } else {
                            System.out.println("Escolha inválida.");
                        }
                    }
                    break;

                case 3:
                    // Adicionar Produto ao Banco de Dados
                    Produto novoProduto = adicionarProduto(scanner);
                    estoqueService.adicionarProduto(novoProduto);
                    System.out.println("Produto adicionado ao banco de dados.");
                    break;

                    //Associar produto a uma loja
                case 4:
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                    } else {
                        System.out.print("Digite o ID do produto a ser adicionado à loja: ");
                        long produtoId = scanner.nextLong();
                        estoqueService.associarProdutoALoja(produtoId, estoqueService.getLojaAtual().getId());
                        System.out.println("Produto associado à loja.");
                    }
                    break;

                case 5:
                    // Buscar Produto por Nome no Banco de Dados
                    System.out.print("Digite o nome do produto para buscar no Banco de Dados: ");
                    String nome = scanner.nextLine();
                    estoqueService.buscarPorNome(nome).forEach(System.out::println);
                    break;
                case 6:
                    // Buscar Produto por Código no Banco de Dados
                    System.out.print("Digite o código do produto para buscar: ");
                    String cod = scanner.nextLine();
                    Produto produtoPorCodigo = estoqueService.buscarProdutoPorCodigo(cod);
                    System.out.println(produtoPorCodigo != null ? produtoPorCodigo : "Produto não encontrado.");
                    break;

                case 7:
                    // Listar Produtos Ordenados por Nome (Banco de Dados)
                    estoqueService.listarProdutosOrdenadosPorNome().forEach(System.out::println);
                    break;

                case 8:
                    // Listar Produtos Ordenados por Preço (Banco de Dados)
                    estoqueService.listarProdutosOrdenadosPorPreco().forEach(System.out::println);
                    break;

                case 9:
                    // Calcular Valor Total do Estoque (Banco de Dados)
                    System.out.println("Valor total do estoque (banco): " + estoqueService.calcularValorTotalEstoque());
                    break;
                case 10:
                    // Remover o produto do banco de dados
                    System.out.print("Digite o código do produto a ser removido do banco de dados: ");
                    cod = scanner.nextLine();
                    estoqueService.removerProdutoDoBanco(cod);
                    System.out.println("Produto removido do banco de dados.");
                    break;

                case 11:
                    // Remover Produto da Loja Selecionada
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    } else {
                        System.out.print("Digite o código do produto a ser removido: ");
                        cod = scanner.nextLine();
                        estoqueService.removerProdutoDaLoja(cod);
                        System.out.println("Produto removido da loja.");
                    }
                    break;
                //Buscar Produto por codigo na Loja
                case 12:
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    } else {
                        System.out.print("Digite o código do produto a buscar na loja: ");
                        String codigoProduto = scanner.nextLine();
                        Produto produto = estoqueService.buscarProdutoNaLoja(estoqueService.getLojaAtual().getId(), codigoProduto);
                        System.out.println(produto != null ? produto : "Produto não encontrado na loja.");
                    }
                    break;

                case 13:
                    // Buscar Produto por Nome na Loja Selecionada
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    }
                    else {
                        System.out.print("Digite o nome do produto para buscar na Loja: ");
                        nome = scanner.nextLine();
                        estoqueService.buscarPorNomeLoja(nome).forEach(System.out::println);
                        break;
                    }
                    //Calcular valor do estoque da Loja Selecionada
                case 14:
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    } else {
                        Double valorTotal = estoqueService.calcularValorEstoquePorLoja(estoqueService.getLojaAtual().getId());
                        System.out.println("Valor total do estoque da loja: R$ " + valorTotal);
                    }
                    break;
                //Listar produtos da loja selecionada
                case 15:
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    } else {
                        List<Produto> produtos = estoqueService.listarProdutosDaLoja(estoqueService.getLojaAtual().getId());
                        produtos.forEach(System.out::println);
                    }
                    break;
                case 16:
                    if (estoqueService.getLojaAtual() == null) {
                        System.out.println("Selecione uma loja primeiro.");
                        break;
                    } else {
                        List<Produto> produtos = estoqueService.listarProdutosOrdenadosPorPrecoLoja(estoqueService.getLojaAtual().getId());
                        produtos.forEach(System.out::println);
                    }
                    break;
                case 17:
                    //Sair da loja selecionada
                    estoqueService.sairDaLoja();
                    break;
                case 18:
                    // Sair
                    System.out.println("Saindo...");
                    scanner.close();
                    menu = 1;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
