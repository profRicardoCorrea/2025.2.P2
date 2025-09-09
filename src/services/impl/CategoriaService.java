package services.impl;

import entities.Categoria;
import repositories.ICategoriaRepository;
import services.ICategoriaService;
import java.util.List;

public class CategoriaService implements ICategoriaService {
    
    private final ICategoriaRepository categoriaRepository;
    
    public CategoriaService(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @Override
    public void criarCategoria(Categoria categoria) {
        validarCategoria(categoria);
        if (verificarSeCategoriaExiste(categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome");
        }
        categoriaRepository.salvar(categoria);
    }
    
    @Override
    public void atualizarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new IllegalArgumentException("ID da categoria é obrigatório para atualização");
        }
        validarCategoria(categoria);
        categoriaRepository.alterar(categoria);
    }
    
    @Override
    public void removerCategoria(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria é obrigatório");
        }
        Categoria categoria = categoriaRepository.buscarPorId(id);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        categoriaRepository.remover(id);
    }
    
    @Override
    public Categoria buscarCategoriaPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria é obrigatório");
        }
        return categoriaRepository.buscarPorId(id);
    }
    
    @Override
    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.listar();
    }
    
    @Override
    public List<Categoria> buscarCategoriasPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        return categoriaRepository.buscarPorNome(nome);
    }
    
    @Override
    public List<Categoria> buscarCategoriasPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da categoria é obrigatório");
        }
        return categoriaRepository.buscarPorTipo(tipo);
    }
    
    @Override
    public List<Categoria> buscarCategoriasPorDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da categoria é obrigatória");
        }
        return categoriaRepository.buscarPorDescricao(descricao);
    }
    
    @Override
    public List<Categoria> buscarCategoriasPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status da categoria é obrigatório");
        }
        return categoriaRepository.buscarPorStatus(status);
    }
    
    @Override
    public List<Categoria> buscarCategoriasAtivas() {
        return categoriaRepository.buscarPorStatus("ATIVA");
    }
    
    @Override
    public Categoria buscarCategoriaPorNomeExato(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        List<Categoria> categorias = categoriaRepository.buscarPorNome(nome);
        return categorias.stream()
                .filter(cat -> nome.equals(cat.getNome()))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean verificarSeCategoriaExiste(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        return buscarCategoriaPorNomeExato(nome) != null;
    }
    
    @Override
    public void ativarCategoria(Long id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        categoria.setStatus("ATIVA");
        categoriaRepository.alterar(categoria);
    }
    
    @Override
    public void desativarCategoria(Long id) {
        Categoria categoria = buscarCategoriaPorId(id);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        categoria.setStatus("INATIVA");
        categoriaRepository.alterar(categoria);
    }
    
    @Override
    public List<Categoria> buscarCategoriasPorPalavraChave(String palavraChave) {
        if (palavraChave == null || palavraChave.trim().isEmpty()) {
            throw new IllegalArgumentException("Palavra-chave é obrigatória");
        }
        
        return categoriaRepository.listar().stream()
                .filter(categoria -> 
                    (categoria.getNome() != null && 
                     categoria.getNome().toLowerCase().contains(palavraChave.toLowerCase())) ||
                    (categoria.getDescricao() != null && 
                     categoria.getDescricao().toLowerCase().contains(palavraChave.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
    }
    
    private void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        if (categoria.getTipo() == null || categoria.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da categoria é obrigatório");
        }
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da categoria é obrigatória");
        }
    }
}


