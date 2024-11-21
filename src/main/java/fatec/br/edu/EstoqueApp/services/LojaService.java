// LojaService.java
package fatec.br.edu.EstoqueApp.services;

import fatec.br.edu.EstoqueApp.models.Loja;
import fatec.br.edu.EstoqueApp.models.Produto;
import fatec.br.edu.EstoqueApp.repositories.LojaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    public Loja criarLoja(String nome) {
        Loja loja = new Loja(nome);
        return lojaRepository.save(loja);
    }

    public List<Loja> listarLojas() {
        return lojaRepository.findAll();
    }

    public Optional<Loja> buscarLojaPorId(Long id) {
        return lojaRepository.findById(id);
    }


    @Transactional
    public Double calcularValorTotalEstoque(Long lojaId) {
        Loja loja = lojaRepository.findById(lojaId).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        return loja.calcularValorTotalEstoque();
    }



}
