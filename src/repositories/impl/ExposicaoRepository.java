package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Exposicao;
import repositories.IExposicaoRepository;

public class ExposicaoRepository implements IExposicaoRepository {
    
    @Override
    public void salvar(Exposicao exposicao) {
        if (exposicao == null) {
            throw new IllegalArgumentException("Exposição não pode ser nula");
        }
        
        // Primeiro salva o evento base
        String sqlEvento = "INSERT INTO eventos (titulo, descricao, data_hora, data_inicio, data_fim, " +
                          "local, endereco, cidade, estado, cep, preco, capacidade, ingressos_vendidos, " +
                          "categoria, status, imagem_url, organizador_id, local_id, categoria_id) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtEvento.setString(1, exposicao.getTitulo());
            stmtEvento.setString(2, exposicao.getDescricao());
            
            Timestamp dataHora = exposicao.getDataHora() != null ? 
                Timestamp.valueOf(exposicao.getDataHora()) : null;
            stmtEvento.setTimestamp(3, dataHora);
            
            java.sql.Date dataInicio = exposicao.getDataInicio() != null ? 
                java.sql.Date.valueOf(exposicao.getDataInicio()) : null;
            stmtEvento.setDate(4, dataInicio);
            
            java.sql.Date dataFim = exposicao.getDataFim() != null ? 
                java.sql.Date.valueOf(exposicao.getDataFim()) : null;
            stmtEvento.setDate(5, dataFim);
            
            stmtEvento.setString(6, exposicao.getLocal());
            stmtEvento.setString(7, exposicao.getEndereco());
            stmtEvento.setString(8, exposicao.getCidade());
            stmtEvento.setString(9, exposicao.getEstado());
            stmtEvento.setString(10, exposicao.getCep());
            stmtEvento.setBigDecimal(11, exposicao.getPreco());
            stmtEvento.setInt(12, exposicao.getCapacidade());
            stmtEvento.setInt(13, exposicao.getIngressosVendidos() != null ? exposicao.getIngressosVendidos() : 0);
            stmtEvento.setString(14, exposicao.getCategoria());
            stmtEvento.setString(15, exposicao.getStatus());
            stmtEvento.setString(16, exposicao.getImagemUrl());
            stmtEvento.setObject(17, null); // organizador_id
            stmtEvento.setObject(18, null); // local_id
            stmtEvento.setObject(19, null); // categoria_id
            
            stmtEvento.executeUpdate();
            
            Long eventoId;
            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoId = rs.getLong(1);
                    exposicao.setId(eventoId);
                } else {
                    throw new RuntimeException("Não foi possível obter o ID do evento");
                }
            }
            
            // Agora salva os dados específicos da exposição
            String sqlExposicao = "INSERT INTO eventos_exposicao (evento_id, curador, tipo_exposicao, " +
                                 "artistas, periodo_historico, tecnica_artistica, numero_obras, " +
                                 "catalogo_url, tem_visita_guiada, horario_visita) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmtExposicao = conn.prepareStatement(sqlExposicao)) {
                stmtExposicao.setLong(1, eventoId);
                stmtExposicao.setString(2, exposicao.getCurador());
                stmtExposicao.setString(3, exposicao.getTipoExposicao());
                stmtExposicao.setString(4, String.join(",", exposicao.getArtistas()));
                stmtExposicao.setString(5, exposicao.getPeriodoHistorico());
                stmtExposicao.setString(6, exposicao.getTecnicaArtistica());
                stmtExposicao.setObject(7, exposicao.getNumeroObras());
                stmtExposicao.setString(8, exposicao.getCatalogoUrl());
                stmtExposicao.setBoolean(9, exposicao.getTemVisitaGuiada());
                stmtExposicao.setString(10, exposicao.getHorarioVisita());
                
                stmtExposicao.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar exposição", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Remove primeiro os dados específicos da exposição
            String sqlExposicao = "DELETE FROM eventos_exposicao WHERE evento_id = ?";
            try (PreparedStatement stmtExposicao = conn.prepareStatement(sqlExposicao)) {
                stmtExposicao.setLong(1, id);
                stmtExposicao.executeUpdate();
            }
            
            // Depois remove o evento base
            String sqlEvento = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setLong(1, id);
                stmtEvento.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover exposição", e);
        }
    }
    
    @Override
    public void alterar(Exposicao exposicao) {
        if (exposicao == null) {
            throw new IllegalArgumentException("Exposição não pode ser nula");
        }
        if (exposicao.getId() == null) {
            throw new IllegalArgumentException("ID da exposição é obrigatório para alteração");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Atualiza o evento base
            String sqlEvento = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, data_inicio = ?, " +
                             "data_fim = ?, local = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, " +
                             "preco = ?, capacidade = ?, ingressos_vendidos = ?, categoria = ?, status = ?, " +
                             "imagem_url = ? WHERE id = ?";
            
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setString(1, exposicao.getTitulo());
                stmtEvento.setString(2, exposicao.getDescricao());
                
                Timestamp dataHora = exposicao.getDataHora() != null ? 
                    Timestamp.valueOf(exposicao.getDataHora()) : null;
                stmtEvento.setTimestamp(3, dataHora);
                
                java.sql.Date dataInicio = exposicao.getDataInicio() != null ? 
                    java.sql.Date.valueOf(exposicao.getDataInicio()) : null;
                stmtEvento.setDate(4, dataInicio);
                
                java.sql.Date dataFim = exposicao.getDataFim() != null ? 
                    java.sql.Date.valueOf(exposicao.getDataFim()) : null;
                stmtEvento.setDate(5, dataFim);
                
                stmtEvento.setString(6, exposicao.getLocal());
                stmtEvento.setString(7, exposicao.getEndereco());
                stmtEvento.setString(8, exposicao.getCidade());
                stmtEvento.setString(9, exposicao.getEstado());
                stmtEvento.setString(10, exposicao.getCep());
                stmtEvento.setBigDecimal(11, exposicao.getPreco());
                stmtEvento.setInt(12, exposicao.getCapacidade());
                stmtEvento.setInt(13, exposicao.getIngressosVendidos() != null ? exposicao.getIngressosVendidos() : 0);
                stmtEvento.setString(14, exposicao.getCategoria());
                stmtEvento.setString(15, exposicao.getStatus());
                stmtEvento.setString(16, exposicao.getImagemUrl());
                stmtEvento.setLong(17, exposicao.getId());
                
                stmtEvento.executeUpdate();
            }
            
            // Atualiza os dados específicos da exposição
            String sqlExposicao = "UPDATE eventos_exposicao SET curador = ?, tipo_exposicao = ?, " +
                                 "artistas = ?, periodo_historico = ?, tecnica_artistica = ?, " +
                                 "numero_obras = ?, catalogo_url = ?, tem_visita_guiada = ?, " +
                                 "horario_visita = ? WHERE evento_id = ?";
            
            try (PreparedStatement stmtExposicao = conn.prepareStatement(sqlExposicao)) {
                stmtExposicao.setString(1, exposicao.getCurador());
                stmtExposicao.setString(2, exposicao.getTipoExposicao());
                stmtExposicao.setString(3, String.join(",", exposicao.getArtistas()));
                stmtExposicao.setString(4, exposicao.getPeriodoHistorico());
                stmtExposicao.setString(5, exposicao.getTecnicaArtistica());
                stmtExposicao.setObject(6, exposicao.getNumeroObras());
                stmtExposicao.setString(7, exposicao.getCatalogoUrl());
                stmtExposicao.setBoolean(8, exposicao.getTemVisitaGuiada());
                stmtExposicao.setString(9, exposicao.getHorarioVisita());
                stmtExposicao.setLong(10, exposicao.getId());
                
                stmtExposicao.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar exposição", e);
        }
    }
    
    @Override
    public List<Exposicao> listar() {
        List<Exposicao> exposicoes = new ArrayList<>();
        String sql = "SELECT e.*, ee.* FROM eventos e " +
                    "INNER JOIN eventos_exposicao ee ON e.id = ee.evento_id " +
                    "ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Exposicao exposicao = criarExposicaoDoResultSet(rs);
                exposicoes.add(exposicao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar exposições", e);
        }
        
        return exposicoes;
    }
    
    @Override
    public Exposicao buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT e.*, ee.* FROM eventos e " +
                    "INNER JOIN eventos_exposicao ee ON e.id = ee.evento_id " +
                    "WHERE e.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarExposicaoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exposição por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Exposicao> buscarPorArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("Artista não pode ser nulo ou vazio");
        }
        String sql = "SELECT e.*, ee.* FROM eventos e " +
                    "INNER JOIN eventos_exposicao ee ON e.id = ee.evento_id " +
                    "WHERE ee.artistas LIKE ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + artista + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Exposicao> exposicoes = new ArrayList<>();
                while (rs.next()) {
                    exposicoes.add(criarExposicaoDoResultSet(rs));
                }
                return exposicoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exposições por artista", e);
        }
    }
    
    @Override
    public List<Exposicao> buscarPorTipoArte(String tipoArte) {
        if (tipoArte == null || tipoArte.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de arte não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ee.tecnica_artistica", tipoArte);
    }
    
    @Override
    public List<Exposicao> buscarPorTema(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new IllegalArgumentException("Tema não pode ser nulo ou vazio");
        }
        String sql = "SELECT e.*, ee.* FROM eventos e " +
                    "INNER JOIN eventos_exposicao ee ON e.id = ee.evento_id " +
                    "WHERE e.titulo LIKE ? OR e.descricao LIKE ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String busca = "%" + tema + "%";
            stmt.setString(1, busca);
            stmt.setString(2, busca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Exposicao> exposicoes = new ArrayList<>();
                while (rs.next()) {
                    exposicoes.add(criarExposicaoDoResultSet(rs));
                }
                return exposicoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exposições por tema", e);
        }
    }
    
    @Override
    public List<Exposicao> buscarPorCurador(String curador) {
        if (curador == null || curador.trim().isEmpty()) {
            throw new IllegalArgumentException("Curador não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ee.curador", curador);
    }
    
    @Override
    public List<Exposicao> buscarPorTipoExposicao(String tipoExposicao) {
        if (tipoExposicao == null || tipoExposicao.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de exposição não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ee.tipo_exposicao", tipoExposicao);
    }
    
    @Override
    public List<Exposicao> buscarPorFaixaEtaria(String faixaEtaria) {
        if (faixaEtaria == null || faixaEtaria.trim().isEmpty()) {
            throw new IllegalArgumentException("Faixa etária não pode ser nula ou vazia");
        }
        // Como não temos campo específico para faixa etária, vamos buscar por categoria
        return buscarPorCampo("e.categoria", faixaEtaria);
    }
    
    private List<Exposicao> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT e.*, ee.* FROM eventos e " +
                    "INNER JOIN eventos_exposicao ee ON e.id = ee.evento_id " +
                    "WHERE " + campo + " = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Exposicao> exposicoes = new ArrayList<>();
                while (rs.next()) {
                    exposicoes.add(criarExposicaoDoResultSet(rs));
                }
                return exposicoes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exposições por " + campo, e);
        }
    }
    
    private Exposicao criarExposicaoDoResultSet(ResultSet rs) throws SQLException {
        Exposicao exposicao = new Exposicao();
        
        // Campos do evento base
        exposicao.setId(rs.getLong("id"));
        exposicao.setTitulo(rs.getString("titulo"));
        exposicao.setDescricao(rs.getString("descricao"));
        
        Timestamp dataHora = rs.getTimestamp("data_hora");
        if (dataHora != null) {
            exposicao.setDataHora(dataHora.toLocalDateTime());
        }
        
        java.sql.Date dataInicio = rs.getDate("data_inicio");
        if (dataInicio != null) {
            exposicao.setDataInicio(dataInicio.toLocalDate());
        }
        
        java.sql.Date dataFim = rs.getDate("data_fim");
        if (dataFim != null) {
            exposicao.setDataFim(dataFim.toLocalDate());
        }
        
        exposicao.setLocal(rs.getString("local"));
        exposicao.setEndereco(rs.getString("endereco"));
        exposicao.setCidade(rs.getString("cidade"));
        exposicao.setEstado(rs.getString("estado"));
        exposicao.setCep(rs.getString("cep"));
        exposicao.setPreco(rs.getBigDecimal("preco"));
        exposicao.setCapacidade(rs.getInt("capacidade"));
        exposicao.setIngressosVendidos(rs.getInt("ingressos_vendidos"));
        exposicao.setCategoria(rs.getString("categoria"));
        exposicao.setStatus(rs.getString("status"));
        exposicao.setImagemUrl(rs.getString("imagem_url"));
        
        // Campos específicos da exposição
        exposicao.setCurador(rs.getString("curador"));
        exposicao.setTipoExposicao(rs.getString("tipo_exposicao"));
        
        String artistasStr = rs.getString("artistas");
        if (artistasStr != null && !artistasStr.isEmpty()) {
            exposicao.setArtistas(List.of(artistasStr.split(",")));
        }
        
        exposicao.setPeriodoHistorico(rs.getString("periodo_historico"));
        exposicao.setTecnicaArtistica(rs.getString("tecnica_artistica"));
        exposicao.setNumeroObras(rs.getInt("numero_obras"));
        exposicao.setCatalogoUrl(rs.getString("catalogo_url"));
        exposicao.setTemVisitaGuiada(rs.getBoolean("tem_visita_guiada"));
        exposicao.setHorarioVisita(rs.getString("horario_visita"));
        
        return exposicao;
    }
}