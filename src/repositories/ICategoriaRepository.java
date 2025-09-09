package repositories;

import entities.Categoria;
import java.util.List;

public interface ICategoriaRepository extends IRepository<Categoria> {
    List<Categoria> buscarPorNome(String nome);
    List<Categoria> buscarPorTipo(String tipo);
    List<Categoria> buscarPorDescricao(String descricao);
    List<Categoria> buscarPorStatus(String status);
}

