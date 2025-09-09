package repositories.impl;

import entities.Categoria;
import repositories.ICategoriaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaRepository implements ICategoriaRepository {
    private List<Categoria> categorias;
    private Long proximoId;

    public CategoriaRepository() {
        this.categorias = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Categoria entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        categorias.add(entidade);
    }

    @Override
    public void remover(Long id) {
        categorias.removeIf(categoria -> categoria.getId().equals(id));
    }

    @Override
    public void alterar(Categoria entidade) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(entidade.getId())) {
                categorias.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Categoria> listar() {
        return new ArrayList<>(categorias);
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categorias.stream()
                .filter(categoria -> categoria.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Categoria> buscarPorNome(String nome) {
        return categorias.stream()
                .filter(categoria -> nome.equals(categoria.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Categoria> buscarPorTipo(String tipo) {
        return categorias.stream()
                .filter(categoria -> tipo.equals(categoria.getTipo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Categoria> buscarPorDescricao(String descricao) {
        return categorias.stream()
                .filter(categoria -> descricao.equals(categoria.getDescricao()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Categoria> buscarPorStatus(String status) {
        return categorias.stream()
                .filter(categoria -> status.equals(categoria.getStatus()))
                .collect(Collectors.toList());
    }
}

