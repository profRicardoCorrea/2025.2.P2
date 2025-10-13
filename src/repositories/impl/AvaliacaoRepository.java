package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import config.DatabaseConfig;
import entities.Avaliacao;
import repositories.IAvaliacaoRepository;

public class AvaliacaoRepository implements IAvaliacaoRepository {
    
    @Override
    public void salvar(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new IllegalArgumentException("Avaliação não pode ser nula");
        }
        String sql = "INSERT INTO avaliacoes (evento_id, usuario_id, nota, comentario, " +
                    "categoria_avaliacao, data_avaliacao, anonima, moderada, status, util, numero_votos_util) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, avaliacao.getEventoId());
            stmt.setLong(2, avaliacao.getUsuarioId());
            stmt.setInt(3, avaliacao.getNota());
            stmt.setString(4, avaliacao.getComentario());
            stmt.setString(5, avaliacao.getCategoriaAvaliacao());
            
            Timestamp timestamp = avaliacao.getDataAvaliacao() != null ? 
                Timestamp.valueOf(avaliacao.getDataAvaliacao()) : Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(6, timestamp);
            
            stmt.setBoolean(7, avaliacao.getAnonima());
            stmt.setBoolean(8, avaliacao.getModerada());
            stmt.setString(9, avaliacao.getStatus());
            stmt.setBoolean(10, avaliacao.getUtil());
            stmt.setInt(11, avaliacao.getNumeroVotosUtil());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar avaliação", e);
        }
    }

    public void salvarReduzido(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new IllegalArgumentException("Avaliação não pode ser nula");
        }
        String sql = "INSERT INTO avaliacoes (evento_id, usuario_id, nota) " +
                    "VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, avaliacao.getEventoId());
            stmt.setLong(2, avaliacao.getUsuarioId());
            stmt.setInt(3, avaliacao.getNota());
            
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar avaliação reduzida", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM avaliacoes WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover avaliação", e);
        }
    }
    
    @Override
    public void alterar(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new IllegalArgumentException("Avaliação não pode ser nula");
        }
        if (avaliacao.getId() == null) {
            throw new IllegalArgumentException("ID da avaliação é obrigatório para alteração");
        }
        String sql = "UPDATE avaliacoes SET evento_id = ?, usuario_id = ?, nota = ?, comentario = ?, " +
                    "categoria_avaliacao = ?, data_avaliacao = ?, anonima = ?, moderada = ?, status = ?, " +
                    "util = ?, numero_votos_util = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, avaliacao.getEventoId());
            stmt.setLong(2, avaliacao.getUsuarioId());
            stmt.setInt(3, avaliacao.getNota());
            stmt.setString(4, avaliacao.getComentario());
            stmt.setString(5, avaliacao.getCategoriaAvaliacao());
            
            Timestamp timestamp = avaliacao.getDataAvaliacao() != null ? 
                Timestamp.valueOf(avaliacao.getDataAvaliacao()) : Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(6, timestamp);
            
            stmt.setBoolean(7, avaliacao.getAnonima());
            stmt.setBoolean(8, avaliacao.getModerada());
            stmt.setString(9, avaliacao.getStatus());
            stmt.setBoolean(10, avaliacao.getUtil());
            stmt.setInt(11, avaliacao.getNumeroVotosUtil());
            stmt.setLong(12, avaliacao.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar avaliação", e);
        }
    }
    
    @Override
    public List<Avaliacao> listar() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Avaliacao avaliacao = criarAvaliacaoDoResultSet(rs);
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar avaliações", e);
        }
        
        return avaliacoes;
    }
    
    @Override
    public Avaliacao buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM avaliacoes WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarAvaliacaoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Avaliacao> buscarPorEvento(Long eventoId) {
        if (eventoId == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo");
        }
        return buscarPorCampo("evento_id", eventoId);
    }
    
    @Override
    public List<Avaliacao> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        return buscarPorCampo("usuario_id", usuarioId);
    }
    
    @Override
    public List<Avaliacao> buscarPorNota(Integer nota) {
        if (nota == null) {
            throw new IllegalArgumentException("Nota não pode ser nula");
        }
        return buscarPorCampo("nota", nota);
    }
    
    @Override
    public List<Avaliacao> buscarPorNotaMinima(Integer notaMinima) {
        if (notaMinima == null) {
            throw new IllegalArgumentException("Nota mínima não pode ser nula");
        }
        String sql = "SELECT * FROM avaliacoes WHERE nota >= ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, notaMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Avaliacao> avaliacoes = new ArrayList<>();
                while (rs.next()) {
                    avaliacoes.add(criarAvaliacaoDoResultSet(rs));
                }
                return avaliacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações por nota mínima", e);
        }
    }
    
    @Override
    public List<Avaliacao> buscarPorData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        String sql = "SELECT * FROM avaliacoes WHERE DATE(data_avaliacao) = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(data));
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Avaliacao> avaliacoes = new ArrayList<>();
                while (rs.next()) {
                    avaliacoes.add(criarAvaliacaoDoResultSet(rs));
                }
                return avaliacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações por data", e);
        }
    }
    
    @Override
    public List<Avaliacao> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        return buscarPorCampo("status", status);
    }
    
    private List<Avaliacao> buscarPorCampo(String campo, Object valor) {
        String sql = "SELECT * FROM avaliacoes WHERE " + campo + " = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (valor instanceof Long) {
                stmt.setLong(1, (Long) valor);
            } else if (valor instanceof Integer) {
                stmt.setInt(1, (Integer) valor);
            } else if (valor instanceof String) {
                stmt.setString(1, (String) valor);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Avaliacao> avaliacoes = new ArrayList<>();
                while (rs.next()) {
                    avaliacoes.add(criarAvaliacaoDoResultSet(rs));
                }
                return avaliacoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações por " + campo, e);
        }
    }
    
    private Avaliacao criarAvaliacaoDoResultSet(ResultSet rs) throws SQLException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(rs.getLong("id"));
        avaliacao.setEventoId(rs.getLong("evento_id"));
        avaliacao.setUsuarioId(rs.getLong("usuario_id"));
        avaliacao.setNota(rs.getInt("nota"));
        avaliacao.setComentario(rs.getString("comentario"));
        avaliacao.setCategoriaAvaliacao(rs.getString("categoria_avaliacao"));
        
        Timestamp timestamp = rs.getTimestamp("data_avaliacao");
        if (timestamp != null) {
            avaliacao.setDataAvaliacao(timestamp.toLocalDateTime());
        }
        
        avaliacao.setAnonima(rs.getBoolean("anonima"));
        avaliacao.setModerada(rs.getBoolean("moderada"));
        avaliacao.setStatus(rs.getString("status"));
        avaliacao.setUtil(rs.getBoolean("util"));
        avaliacao.setNumeroVotosUtil(rs.getInt("numero_votos_util"));
        
        return avaliacao;
    }
}


