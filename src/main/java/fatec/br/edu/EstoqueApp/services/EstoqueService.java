package fatec.br.edu.EstoqueApp.services;

import fatec.br.edu.EstoqueApp.models.Produto;
import fatec.br.edu.EstoqueApp.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto adicionarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    public void removerProduto(String codigo) {
        produtoRepository.deleteById(codigo);
    }

    public Produto buscarProdutoPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo).stream().findFirst().orElse(null);
    }

    public List<Produto> listarProdutosOrdenadosPorNome() {
        return produtoRepository.listarTodosOrdenadosPorNome();
    }

    public List<Produto> listarProdutosOrdenadosPorPreco() {
        return produtoRepository.listarTodosOrdenadosPorPreco();
    }

    public Double calcularValorTotalEstoque() {
        return produtoRepository.obterValorTotalEstoque();
    }
    
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    public List<Produto> buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }
    
    public List<Produto> findByNomeAndCodigo(String nome, String codigo){
        return produtoRepository.findByNomeAndCodigo(nome, codigo);
  }
}

