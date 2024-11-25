package fatec.br.edu.EstoqueApp.services;

import fatec.br.edu.EstoqueApp.models.Loja;
import fatec.br.edu.EstoqueApp.models.Produto;
import fatec.br.edu.EstoqueApp.repositories.LojaRepository;
import fatec.br.edu.EstoqueApp.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    private Loja lojaAtual;

    //Adiciona produto no banco de dados
    public void adicionarProduto(Produto novoProduto) {
        produtoRepository.save(novoProduto);
    }

    //Buscar produto na loja por nome
    public List<Produto> buscarPorNomeLoja(String nome) {
        Loja lojaAtual = getLojaAtual();
        return produtoRepository.findByNomeAndLoja(nome, lojaAtual.getId());
    }
    //Buscar produto no banco de dados por nome
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
    //Buscar produto na loja por codigo
    public Produto buscarProdutoPorCodigo(String codigo) {
        Loja lojaAtual = getLojaAtual();
        return produtoRepository.findByCodigoAndLoja(codigo, lojaAtual.getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + codigo));
    }
    public List<Produto> listarProdutosOrdenadosPorNome() {
        Loja lojaAtual = getLojaAtual();
        return produtoRepository.findAllByLojaOrderByNome(lojaAtual.getId());
    }
    public List<Produto> listarProdutosOrdenadosPorPreco() {
        return produtoRepository.findAllOrderByPreco();
    }
    public List<Produto> listarProdutosOrdenadosPorPrecoLoja(Long id) {
        Loja lojaAtual = getLojaAtual();
        return produtoRepository.findAllByLojaOrderByPreco(lojaAtual.getId());
    }
    public Double calcularValorTotalEstoque() {
        return produtoRepository.obterValorTotalEstoque();
    }
    public void selecionarLoja(Long lojaId) {
        lojaAtual = produtoRepository.findByIdWithProdutos(lojaId)
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada: " + lojaId));
    }

    // Remover Produto da Loja
    @Transactional
    public void removerProdutoDaLoja(String codigoProduto) {
        if (lojaAtual == null) {
            throw new IllegalStateException("Nenhuma loja selecionada.");
        }

        // Encontrar o produto pelo código
        Produto produto = produtoRepository.findByCodigo(codigoProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + codigoProduto));

        // Inicializar as coleções associadas
        Hibernate.initialize(produto.getLojas()); // Garante que as lojas do produto estão carregadas
        Hibernate.initialize(lojaAtual.getProdutos()); // Garante que os produtos da loja estão carregados

        // Remover a loja da lista de lojas do produto
        if (produto.getLojas().contains(lojaAtual)) {
            produto.getLojas().remove(lojaAtual);
        }

        // Remover o produto da lista de produtos da loja
        if (lojaAtual.getProdutos().contains(produto)) {
            lojaAtual.getProdutos().remove(produto);
        }

        // Salvar as alterações no banco de dados
        produtoRepository.save(produto); // Persiste a alteração no produto
        lojaRepository.save(lojaAtual);  // Persiste a alteração na loja
    }

    @Transactional
    public void removerProdutoDoBanco(String codigoProduto) {
        Produto produto = produtoRepository.findByCodigo(codigoProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + codigoProduto));

        // Remove as relações com as lojas
        for (Loja loja : produto.getLojas()) {
            loja.getProdutos().remove(produto);
        }

        produtoRepository.delete(produto); // Remove o produto do banco de dados
    }

    public List<Produto> listarProdutosDaLoja(Long lojaId) {
        return produtoRepository.listarProdutosPorLoja(lojaId);
    }

    public Double calcularValorEstoquePorLoja(Long lojaId) {
        return produtoRepository.calcularValorEstoquePorLoja(lojaId);
    }

    public Produto buscarProdutoNaLoja(Long lojaId, String codigo) {
        return produtoRepository.buscarProdutoPorCodigoNaLoja(lojaId, codigo);
    }
    @Transactional
    public void associarProdutoALoja(Long produtoId, Long lojaId) {
        Produto produto = produtoRepository.findById(String.valueOf(produtoId))
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        Loja loja = lojaRepository.findById(lojaId)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        produto.getLojas().add(loja);
        produtoRepository.save(produto); // Persistir relação no banco
    }

    public Loja getLojaAtual() {
        if (lojaAtual == null) {
            throw new IllegalStateException("Nenhuma loja está selecionada atualmente.");
        }
        return lojaAtual;
    }

    public void setLojaAtual(Loja loja) {
        this.lojaAtual = loja;
    }

    public void sairDaLoja() {
        this.lojaAtual = null;
    }
}



