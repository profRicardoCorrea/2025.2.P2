package services;

import entities.Categoria;
import java.util.List;

public interface ICategoriaService {
    void criarCategoria(Categoria categoria);
    void atualizarCategoria(Categoria categoria);
    void removerCategoria(Long id);
    Categoria buscarCategoriaPorId(Long id);
    List<Categoria> listarTodasCategorias();
    List<Categoria> buscarCategoriasPorNome(String nome);
    List<Categoria> buscarCategoriasPorTipo(String tipo);
    List<Categoria> buscarCategoriasPorDescricao(String descricao);
    List<Categoria> buscarCategoriasPorStatus(String status);
    List<Categoria> buscarCategoriasAtivas();
    Categoria buscarCategoriaPorNomeExato(String nome);
    boolean verificarSeCategoriaExiste(String nome);
    void ativarCategoria(Long id);
    void desativarCategoria(Long id);
    List<Categoria> buscarCategoriasPorPalavraChave(String palavraChave);
}


