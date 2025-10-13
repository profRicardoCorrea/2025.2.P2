package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import config.DatabaseConfig;
import entities.Categoria;
import repositories.ICategoriaRepository;

public class CategoriaRepository implements ICategoriaRepository {
    
    @Override
    public void salvar(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        String sql = "INSERT INTO categorias (nome, descricao, cor, icone, slug, ativa, ordem, " +
                    "categoria_pai_id, metadados, numero_eventos, tags, imagem_url, destaque, tipo_categoria) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setString(3, categoria.getCor());
            stmt.setString(4, categoria.getIcone());
            stmt.setString(5, categoria.getSlug());
            stmt.setBoolean(6, categoria.getAtiva());
            stmt.setInt(7, categoria.getOrdem() != null ? categoria.getOrdem() : 0);
            stmt.setObject(8, categoria.getCategoriaPaiId());
            stmt.setString(9, categoria.getMetadados());
            stmt.setInt(10, categoria.getNumeroEventos() != null ? categoria.getNumeroEventos() : 0);
            stmt.setString(11, categoria.getTags());
            stmt.setString(12, categoria.getImagemUrl());
            stmt.setBoolean(13, categoria.getDestaque());
            stmt.setString(14, categoria.getTipoCategoria());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar categoria", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM categorias WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover categoria", e);
        }
    }
    
    @Override
    public void alterar(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        if (categoria.getId() == null) {
            throw new IllegalArgumentException("ID da categoria é obrigatório para alteração");
        }
        String sql = "UPDATE categorias SET nome = ?, descricao = ?, cor = ?, icone = ?, slug = ?, " +
                    "ativa = ?, ordem = ?, categoria_pai_id = ?, metadados = ?, numero_eventos = ?, " +
                    "tags = ?, imagem_url = ?, destaque = ?, tipo_categoria = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setString(3, categoria.getCor());
            stmt.setString(4, categoria.getIcone());
            stmt.setString(5, categoria.getSlug());
            stmt.setBoolean(6, categoria.getAtiva());
            stmt.setInt(7, categoria.getOrdem() != null ? categoria.getOrdem() : 0);
            stmt.setObject(8, categoria.getCategoriaPaiId());
            stmt.setString(9, categoria.getMetadados());
            stmt.setInt(10, categoria.getNumeroEventos() != null ? categoria.getNumeroEventos() : 0);
            stmt.setString(11, categoria.getTags());
            stmt.setString(12, categoria.getImagemUrl());
            stmt.setBoolean(13, categoria.getDestaque());
            stmt.setString(14, categoria.getTipoCategoria());
            stmt.setLong(15, categoria.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar categoria", e);
        }
    }
    
    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY ordem, nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = criarCategoriaDoResultSet(rs);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
        
        return categorias;
    }
    
    @Override
    public Categoria buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM categorias WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarCategoriaDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Categoria> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        return buscarPorCampo("nome", nome);
    }
    
    @Override
    public List<Categoria> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_categoria", tipo);
    }
    
    @Override
    public List<Categoria> buscarPorDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        return buscarPorCampo("descricao", descricao);
    }
    
    @Override
    public List<Categoria> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        boolean ativa = "ATIVA".equals(status) || "ATIVO".equals(status);
        return buscarPorCampo("ativa", ativa);
    }
    
    private List<Categoria> buscarPorCampo(String campo, Object valor) {
        String sql = "SELECT * FROM categorias WHERE " + campo + " = ? ORDER BY ordem, nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (valor instanceof String) {
                stmt.setString(1, (String) valor);
            } else if (valor instanceof Boolean) {
                stmt.setBoolean(1, (Boolean) valor);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Categoria> categorias = new ArrayList<>();
                while (rs.next()) {
                    categorias.add(criarCategoriaDoResultSet(rs));
                }
                return categorias;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categorias por " + campo, e);
        }
    }
    
    private Categoria criarCategoriaDoResultSet(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNome(rs.getString("nome"));
        categoria.setDescricao(rs.getString("descricao"));
        categoria.setCor(rs.getString("cor"));
        categoria.setIcone(rs.getString("icone"));
        categoria.setSlug(rs.getString("slug"));
        categoria.setAtiva(rs.getBoolean("ativa"));
        categoria.setOrdem(rs.getInt("ordem"));
        
        Long categoriaPaiId = rs.getLong("categoria_pai_id");
        if (!rs.wasNull()) {
            categoria.setCategoriaPaiId(categoriaPaiId);
        }
        
        categoria.setMetadados(rs.getString("metadados"));
        categoria.setNumeroEventos(rs.getInt("numero_eventos"));
        categoria.setTags(rs.getString("tags"));
        categoria.setImagemUrl(rs.getString("imagem_url"));
        categoria.setDestaque(rs.getBoolean("destaque"));
        categoria.setTipoCategoria(rs.getString("tipo_categoria"));
        
        return categoria;
    }
}