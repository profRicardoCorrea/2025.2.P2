package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Danca;
import repositories.IDancaRepository;

public class DancaRepository implements IDancaRepository {
    
    @Override
    public void salvar(Danca danca) {
        if (danca == null) {
            throw new IllegalArgumentException("Dança não pode ser nula");
        }
        
        // Primeiro salva o evento base
        String sqlEvento = "INSERT INTO eventos (titulo, descricao, data_hora, data_inicio, data_fim, " +
                          "local, endereco, cidade, estado, cep, preco, capacidade, ingressos_vendidos, " +
                          "categoria, status, imagem_url, organizador_id, local_id, categoria_id) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtEvento.setString(1, danca.getTitulo());
            stmtEvento.setString(2, danca.getDescricao());
            
            Timestamp dataHora = danca.getDataHora() != null ? 
                Timestamp.valueOf(danca.getDataHora()) : null;
            stmtEvento.setTimestamp(3, dataHora);
            
            java.sql.Date dataInicio = danca.getDataInicio() != null ? 
                java.sql.Date.valueOf(danca.getDataInicio()) : null;
            stmtEvento.setDate(4, dataInicio);
            
            java.sql.Date dataFim = danca.getDataFim() != null ? 
                java.sql.Date.valueOf(danca.getDataFim()) : null;
            stmtEvento.setDate(5, dataFim);
            
            stmtEvento.setString(6, danca.getLocal());
            stmtEvento.setString(7, danca.getEndereco());
            stmtEvento.setString(8, danca.getCidade());
            stmtEvento.setString(9, danca.getEstado());
            stmtEvento.setString(10, danca.getCep());
            stmtEvento.setBigDecimal(11, danca.getPreco());
            stmtEvento.setInt(12, danca.getCapacidade());
            stmtEvento.setInt(13, danca.getIngressosVendidos() != null ? danca.getIngressosVendidos() : 0);
            stmtEvento.setString(14, danca.getCategoria());
            stmtEvento.setString(15, danca.getStatus());
            stmtEvento.setString(16, danca.getImagemUrl());
            stmtEvento.setObject(17, null); // organizador_id
            stmtEvento.setObject(18, null); // local_id
            stmtEvento.setObject(19, null); // categoria_id
            
            stmtEvento.executeUpdate();
            
            Long eventoId;
            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoId = rs.getLong(1);
                    danca.setId(eventoId);
                } else {
                    throw new RuntimeException("Não foi possível obter o ID do evento");
                }
            }
            
            // Agora salva os dados específicos da dança
            String sqlDanca = "INSERT INTO eventos_danca (evento_id, coreografo, companhia, estilo_danca, " +
                             "duracao_minutos, bailarinos, musica, compositor, cenografia, figurino, " +
                             "iluminacao, tem_solista, bailarino_solista, nivel_tecnico, tem_interacao_publico, " +
                             "tipo_apresentacao, restricao_idade, permite_entrada_infantil) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmtDanca = conn.prepareStatement(sqlDanca)) {
                stmtDanca.setLong(1, eventoId);
                stmtDanca.setString(2, danca.getCoreografo());
                stmtDanca.setString(3, danca.getCompanhia());
                stmtDanca.setString(4, danca.getEstiloDanca());
                stmtDanca.setObject(5, danca.getDuracaoMinutos());
                stmtDanca.setString(6, String.join(",", danca.getBailarinos()));
                stmtDanca.setString(7, danca.getMusica());
                stmtDanca.setString(8, danca.getCompositor());
                stmtDanca.setString(9, danca.getCenografia());
                stmtDanca.setString(10, danca.getFigurino());
                stmtDanca.setString(11, danca.getIluminacao());
                stmtDanca.setBoolean(12, danca.getTemSolista());
                stmtDanca.setString(13, danca.getBailarinoSolista());
                stmtDanca.setString(14, danca.getNivelTecnico());
                stmtDanca.setBoolean(15, danca.getTemInteracaoPublico());
                stmtDanca.setString(16, danca.getTipoApresentacao());
                stmtDanca.setString(17, danca.getRestricaoIdade());
                stmtDanca.setBoolean(18, danca.getPermiteEntradaInfantil());
                
                stmtDanca.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar dança", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Remove primeiro os dados específicos da dança
            String sqlDanca = "DELETE FROM eventos_danca WHERE evento_id = ?";
            try (PreparedStatement stmtDanca = conn.prepareStatement(sqlDanca)) {
                stmtDanca.setLong(1, id);
                stmtDanca.executeUpdate();
            }
            
            // Depois remove o evento base
            String sqlEvento = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setLong(1, id);
                stmtEvento.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover dança", e);
        }
    }
    
    @Override
    public void alterar(Danca danca) {
        if (danca == null) {
            throw new IllegalArgumentException("Dança não pode ser nula");
        }
        if (danca.getId() == null) {
            throw new IllegalArgumentException("ID da dança é obrigatório para alteração");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Atualiza o evento base
            String sqlEvento = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, data_inicio = ?, " +
                             "data_fim = ?, local = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, " +
                             "preco = ?, capacidade = ?, ingressos_vendidos = ?, categoria = ?, status = ?, " +
                             "imagem_url = ? WHERE id = ?";
            
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setString(1, danca.getTitulo());
                stmtEvento.setString(2, danca.getDescricao());
                
                Timestamp dataHora = danca.getDataHora() != null ? 
                    Timestamp.valueOf(danca.getDataHora()) : null;
                stmtEvento.setTimestamp(3, dataHora);
                
                java.sql.Date dataInicio = danca.getDataInicio() != null ? 
                    java.sql.Date.valueOf(danca.getDataInicio()) : null;
                stmtEvento.setDate(4, dataInicio);
                
                java.sql.Date dataFim = danca.getDataFim() != null ? 
                    java.sql.Date.valueOf(danca.getDataFim()) : null;
                stmtEvento.setDate(5, dataFim);
                
                stmtEvento.setString(6, danca.getLocal());
                stmtEvento.setString(7, danca.getEndereco());
                stmtEvento.setString(8, danca.getCidade());
                stmtEvento.setString(9, danca.getEstado());
                stmtEvento.setString(10, danca.getCep());
                stmtEvento.setBigDecimal(11, danca.getPreco());
                stmtEvento.setInt(12, danca.getCapacidade());
                stmtEvento.setInt(13, danca.getIngressosVendidos() != null ? danca.getIngressosVendidos() : 0);
                stmtEvento.setString(14, danca.getCategoria());
                stmtEvento.setString(15, danca.getStatus());
                stmtEvento.setString(16, danca.getImagemUrl());
                stmtEvento.setLong(17, danca.getId());
                
                stmtEvento.executeUpdate();
            }
            
            // Atualiza os dados específicos da dança
            String sqlDanca = "UPDATE eventos_danca SET coreografo = ?, companhia = ?, estilo_danca = ?, " +
                             "duracao_minutos = ?, bailarinos = ?, musica = ?, compositor = ?, cenografia = ?, " +
                             "figurino = ?, iluminacao = ?, tem_solista = ?, bailarino_solista = ?, " +
                             "nivel_tecnico = ?, tem_interacao_publico = ?, tipo_apresentacao = ?, " +
                             "restricao_idade = ?, permite_entrada_infantil = ? WHERE evento_id = ?";
            
            try (PreparedStatement stmtDanca = conn.prepareStatement(sqlDanca)) {
                stmtDanca.setString(1, danca.getCoreografo());
                stmtDanca.setString(2, danca.getCompanhia());
                stmtDanca.setString(3, danca.getEstiloDanca());
                stmtDanca.setObject(4, danca.getDuracaoMinutos());
                stmtDanca.setString(5, String.join(",", danca.getBailarinos()));
                stmtDanca.setString(6, danca.getMusica());
                stmtDanca.setString(7, danca.getCompositor());
                stmtDanca.setString(8, danca.getCenografia());
                stmtDanca.setString(9, danca.getFigurino());
                stmtDanca.setString(10, danca.getIluminacao());
                stmtDanca.setBoolean(11, danca.getTemSolista());
                stmtDanca.setString(12, danca.getBailarinoSolista());
                stmtDanca.setString(13, danca.getNivelTecnico());
                stmtDanca.setBoolean(14, danca.getTemInteracaoPublico());
                stmtDanca.setString(15, danca.getTipoApresentacao());
                stmtDanca.setString(16, danca.getRestricaoIdade());
                stmtDanca.setBoolean(17, danca.getPermiteEntradaInfantil());
                stmtDanca.setLong(18, danca.getId());
                
                stmtDanca.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar dança", e);
        }
    }
    
    @Override
    public List<Danca> listar() {
        List<Danca> dancas = new ArrayList<>();
        String sql = "SELECT e.*, ed.* FROM eventos e " +
                    "INNER JOIN eventos_danca ed ON e.id = ed.evento_id " +
                    "ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Danca danca = criarDancaDoResultSet(rs);
                dancas.add(danca);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar danças", e);
        }
        
        return dancas;
    }
    
    @Override
    public Danca buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT e.*, ed.* FROM eventos e " +
                    "INNER JOIN eventos_danca ed ON e.id = ed.evento_id " +
                    "WHERE e.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarDancaDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dança por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Danca> buscarPorEstilo(String estilo) {
        if (estilo == null || estilo.trim().isEmpty()) {
            throw new IllegalArgumentException("Estilo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ed.estilo_danca", estilo);
    }
    
    @Override
    public List<Danca> buscarPorNivel(String nivel) {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new IllegalArgumentException("Nível não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ed.nivel_tecnico", nivel);
    }
    
    @Override
    public List<Danca> buscarPorInstrutor(String instrutor) {
        if (instrutor == null || instrutor.trim().isEmpty()) {
            throw new IllegalArgumentException("Instrutor não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ed.coreografo", instrutor);
    }
    
    @Override
    public List<Danca> buscarPorDuracao(Integer duracaoMinima) {
        if (duracaoMinima == null) {
            throw new IllegalArgumentException("Duração mínima não pode ser nula");
        }
        String sql = "SELECT e.*, ed.* FROM eventos e " +
                    "INNER JOIN eventos_danca ed ON e.id = ed.evento_id " +
                    "WHERE ed.duracao_minutos >= ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, duracaoMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Danca> dancas = new ArrayList<>();
                while (rs.next()) {
                    dancas.add(criarDancaDoResultSet(rs));
                }
                return dancas;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar danças por duração", e);
        }
    }
    
    @Override
    public List<Danca> buscarPorTipoDanca(String tipoDanca) {
        if (tipoDanca == null || tipoDanca.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de dança não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ed.tipo_apresentacao", tipoDanca);
    }
    
    @Override
    public List<Danca> buscarPorMusica(String generoMusical) {
        if (generoMusical == null || generoMusical.trim().isEmpty()) {
            throw new IllegalArgumentException("Gênero musical não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ed.musica", generoMusical);
    }
    
    private List<Danca> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT e.*, ed.* FROM eventos e " +
                    "INNER JOIN eventos_danca ed ON e.id = ed.evento_id " +
                    "WHERE " + campo + " = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Danca> dancas = new ArrayList<>();
                while (rs.next()) {
                    dancas.add(criarDancaDoResultSet(rs));
                }
                return dancas;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar danças por " + campo, e);
        }
    }
    
    private Danca criarDancaDoResultSet(ResultSet rs) throws SQLException {
        Danca danca = new Danca();
        
        // Campos do evento base
        danca.setId(rs.getLong("id"));
        danca.setTitulo(rs.getString("titulo"));
        danca.setDescricao(rs.getString("descricao"));
        
        Timestamp dataHora = rs.getTimestamp("data_hora");
        if (dataHora != null) {
            danca.setDataHora(dataHora.toLocalDateTime());
        }
        
        java.sql.Date dataInicio = rs.getDate("data_inicio");
        if (dataInicio != null) {
            danca.setDataInicio(dataInicio.toLocalDate());
        }
        
        java.sql.Date dataFim = rs.getDate("data_fim");
        if (dataFim != null) {
            danca.setDataFim(dataFim.toLocalDate());
        }
        
        danca.setLocal(rs.getString("local"));
        danca.setEndereco(rs.getString("endereco"));
        danca.setCidade(rs.getString("cidade"));
        danca.setEstado(rs.getString("estado"));
        danca.setCep(rs.getString("cep"));
        danca.setPreco(rs.getBigDecimal("preco"));
        danca.setCapacidade(rs.getInt("capacidade"));
        danca.setIngressosVendidos(rs.getInt("ingressos_vendidos"));
        danca.setCategoria(rs.getString("categoria"));
        danca.setStatus(rs.getString("status"));
        danca.setImagemUrl(rs.getString("imagem_url"));
        
        // Campos específicos da dança
        danca.setCoreografo(rs.getString("coreografo"));
        danca.setCompanhia(rs.getString("companhia"));
        danca.setEstiloDanca(rs.getString("estilo_danca"));
        danca.setDuracaoMinutos(rs.getInt("duracao_minutos"));
        
        String bailarinosStr = rs.getString("bailarinos");
        if (bailarinosStr != null && !bailarinosStr.isEmpty()) {
            danca.setBailarinos(List.of(bailarinosStr.split(",")));
        }
        
        danca.setMusica(rs.getString("musica"));
        danca.setCompositor(rs.getString("compositor"));
        danca.setCenografia(rs.getString("cenografia"));
        danca.setFigurino(rs.getString("figurino"));
        danca.setIluminacao(rs.getString("iluminacao"));
        danca.setTemSolista(rs.getBoolean("tem_solista"));
        danca.setBailarinoSolista(rs.getString("bailarino_solista"));
        danca.setNivelTecnico(rs.getString("nivel_tecnico"));
        danca.setTemInteracaoPublico(rs.getBoolean("tem_interacao_publico"));
        danca.setTipoApresentacao(rs.getString("tipo_apresentacao"));
        danca.setRestricaoIdade(rs.getString("restricao_idade"));
        danca.setPermiteEntradaInfantil(rs.getBoolean("permite_entrada_infantil"));
        
        return danca;
    }
}