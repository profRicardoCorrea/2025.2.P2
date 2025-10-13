package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Teatro;
import repositories.ITeatroRepository;

public class TeatroRepository implements ITeatroRepository {

    @Override
    public void salvar(Teatro teatro) {
        if (teatro == null) {
            throw new IllegalArgumentException("Teatro não pode ser nulo");
        }
        
        // Primeiro salva o evento base
        String sqlEvento = "INSERT INTO eventos (titulo, descricao, data_hora, data_inicio, data_fim, " +
                          "local, endereco, cidade, estado, cep, preco, capacidade, ingressos_vendidos, " +
                          "categoria, status, imagem_url, organizador_id, local_id, categoria_id) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtEvento.setString(1, teatro.getTitulo());
            stmtEvento.setString(2, teatro.getDescricao());
            
            Timestamp dataHora = teatro.getDataHora() != null ? 
                Timestamp.valueOf(teatro.getDataHora()) : null;
            stmtEvento.setTimestamp(3, dataHora);
            
            java.sql.Date dataInicio = teatro.getDataInicio() != null ? 
                java.sql.Date.valueOf(teatro.getDataInicio()) : null;
            stmtEvento.setDate(4, dataInicio);
            
            java.sql.Date dataFim = teatro.getDataFim() != null ? 
                java.sql.Date.valueOf(teatro.getDataFim()) : null;
            stmtEvento.setDate(5, dataFim);
            
            stmtEvento.setString(6, teatro.getLocal());
            stmtEvento.setString(7, teatro.getEndereco());
            stmtEvento.setString(8, teatro.getCidade());
            stmtEvento.setString(9, teatro.getEstado());
            stmtEvento.setString(10, teatro.getCep());
            stmtEvento.setBigDecimal(11, teatro.getPreco());
            stmtEvento.setInt(12, teatro.getCapacidade());
            stmtEvento.setInt(13, teatro.getIngressosVendidos() != null ? teatro.getIngressosVendidos() : 0);
            stmtEvento.setString(14, teatro.getCategoria());
            stmtEvento.setString(15, teatro.getStatus());
            stmtEvento.setString(16, teatro.getImagemUrl());
            stmtEvento.setObject(17, null); // organizador_id
            stmtEvento.setObject(18, null); // local_id
            stmtEvento.setObject(19, null); // categoria_id
            
            stmtEvento.executeUpdate();
            
            Long eventoId;
            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoId = rs.getLong(1);
                    teatro.setId(eventoId);
                } else {
                    throw new RuntimeException("Não foi possível obter o ID do evento");
                }
            }
            
            // Agora salva os dados específicos do teatro
            String sqlTeatro = "INSERT INTO eventos_teatro (evento_id, diretor, autor, companhia, " +
                              "genero_teatral, duracao_minutos, numero_atos, elenco, sinopse, " +
                              "cenografia, figurino, iluminacao, sonoplastia, tem_intervalo, " +
                              "duracao_intervalo, classificacao_indicativa, permite_entrada_infantil, " +
                              "tipo_teatro) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmtTeatro = conn.prepareStatement(sqlTeatro)) {
                stmtTeatro.setLong(1, eventoId);
                stmtTeatro.setString(2, teatro.getDiretor());
                stmtTeatro.setString(3, teatro.getAutor());
                stmtTeatro.setString(4, teatro.getCompanhia());
                stmtTeatro.setString(5, teatro.getGeneroTeatral());
                stmtTeatro.setObject(6, teatro.getDuracaoMinutos());
                stmtTeatro.setObject(7, teatro.getNumeroAtos());
                stmtTeatro.setString(8, String.join(",", teatro.getElenco()));
                stmtTeatro.setString(9, teatro.getSinopse());
                stmtTeatro.setString(10, teatro.getCenografia());
                stmtTeatro.setString(11, teatro.getFigurino());
                stmtTeatro.setString(12, teatro.getIluminacao());
                stmtTeatro.setString(13, teatro.getSonoplastia());
                stmtTeatro.setBoolean(14, teatro.getTemIntervalo());
                stmtTeatro.setObject(15, teatro.getDuracaoIntervalo());
                stmtTeatro.setString(16, teatro.getClassificacaoIndicativa());
                stmtTeatro.setBoolean(17, teatro.getPermiteEntradaInfantil());
                stmtTeatro.setString(18, teatro.getTipoTeatro());
                
                stmtTeatro.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar teatro", e);
        }
    }

    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Remove primeiro os dados específicos do teatro
            String sqlTeatro = "DELETE FROM eventos_teatro WHERE evento_id = ?";
            try (PreparedStatement stmtTeatro = conn.prepareStatement(sqlTeatro)) {
                stmtTeatro.setLong(1, id);
                stmtTeatro.executeUpdate();
            }
            
            // Depois remove o evento base
            String sqlEvento = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setLong(1, id);
                stmtEvento.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover teatro", e);
        }
    }

    @Override
    public void alterar(Teatro teatro) {
        if (teatro == null) {
            throw new IllegalArgumentException("Teatro não pode ser nulo");
        }
        if (teatro.getId() == null) {
            throw new IllegalArgumentException("ID do teatro é obrigatório para alteração");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Atualiza o evento base
            String sqlEvento = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, data_inicio = ?, " +
                             "data_fim = ?, local = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, " +
                             "preco = ?, capacidade = ?, ingressos_vendidos = ?, categoria = ?, status = ?, " +
                             "imagem_url = ? WHERE id = ?";
            
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setString(1, teatro.getTitulo());
                stmtEvento.setString(2, teatro.getDescricao());
                
                Timestamp dataHora = teatro.getDataHora() != null ? 
                    Timestamp.valueOf(teatro.getDataHora()) : null;
                stmtEvento.setTimestamp(3, dataHora);
                
                java.sql.Date dataInicio = teatro.getDataInicio() != null ? 
                    java.sql.Date.valueOf(teatro.getDataInicio()) : null;
                stmtEvento.setDate(4, dataInicio);
                
                java.sql.Date dataFim = teatro.getDataFim() != null ? 
                    java.sql.Date.valueOf(teatro.getDataFim()) : null;
                stmtEvento.setDate(5, dataFim);
                
                stmtEvento.setString(6, teatro.getLocal());
                stmtEvento.setString(7, teatro.getEndereco());
                stmtEvento.setString(8, teatro.getCidade());
                stmtEvento.setString(9, teatro.getEstado());
                stmtEvento.setString(10, teatro.getCep());
                stmtEvento.setBigDecimal(11, teatro.getPreco());
                stmtEvento.setInt(12, teatro.getCapacidade());
                stmtEvento.setInt(13, teatro.getIngressosVendidos() != null ? teatro.getIngressosVendidos() : 0);
                stmtEvento.setString(14, teatro.getCategoria());
                stmtEvento.setString(15, teatro.getStatus());
                stmtEvento.setString(16, teatro.getImagemUrl());
                stmtEvento.setLong(17, teatro.getId());
                
                stmtEvento.executeUpdate();
            }
            
            // Atualiza os dados específicos do teatro
            String sqlTeatro = "UPDATE eventos_teatro SET diretor = ?, autor = ?, companhia = ?, " +
                              "genero_teatral = ?, duracao_minutos = ?, numero_atos = ?, elenco = ?, " +
                              "sinopse = ?, cenografia = ?, figurino = ?, iluminacao = ?, sonoplastia = ?, " +
                              "tem_intervalo = ?, duracao_intervalo = ?, classificacao_indicativa = ?, " +
                              "permite_entrada_infantil = ?, tipo_teatro = ? WHERE evento_id = ?";
            
            try (PreparedStatement stmtTeatro = conn.prepareStatement(sqlTeatro)) {
                stmtTeatro.setString(1, teatro.getDiretor());
                stmtTeatro.setString(2, teatro.getAutor());
                stmtTeatro.setString(3, teatro.getCompanhia());
                stmtTeatro.setString(4, teatro.getGeneroTeatral());
                stmtTeatro.setObject(5, teatro.getDuracaoMinutos());
                stmtTeatro.setObject(6, teatro.getNumeroAtos());
                stmtTeatro.setString(7, String.join(",", teatro.getElenco()));
                stmtTeatro.setString(8, teatro.getSinopse());
                stmtTeatro.setString(9, teatro.getCenografia());
                stmtTeatro.setString(10, teatro.getFigurino());
                stmtTeatro.setString(11, teatro.getIluminacao());
                stmtTeatro.setString(12, teatro.getSonoplastia());
                stmtTeatro.setBoolean(13, teatro.getTemIntervalo());
                stmtTeatro.setObject(14, teatro.getDuracaoIntervalo());
                stmtTeatro.setString(15, teatro.getClassificacaoIndicativa());
                stmtTeatro.setBoolean(16, teatro.getPermiteEntradaInfantil());
                stmtTeatro.setString(17, teatro.getTipoTeatro());
                stmtTeatro.setLong(18, teatro.getId());
                
                stmtTeatro.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar teatro", e);
        }
    }

    @Override
    public List<Teatro> listar() {
        List<Teatro> teatros = new ArrayList<>();
        String sql = "SELECT e.*, et.* FROM eventos e " +
                    "INNER JOIN eventos_teatro et ON e.id = et.evento_id " +
                    "ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Teatro teatro = criarTeatroDoResultSet(rs);
                teatros.add(teatro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar teatros", e);
        }
        
        return teatros;
    }

    @Override
    public Teatro buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT e.*, et.* FROM eventos e " +
                    "INNER JOIN eventos_teatro et ON e.id = et.evento_id " +
                    "WHERE e.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarTeatroDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar teatro por ID", e);
        }
        
        return null;
    }

    @Override
    public List<Teatro> buscarPorGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("Gênero não pode ser nulo ou vazio");
        }
        return buscarPorCampo("et.genero_teatral", genero);
    }

    @Override
    public List<Teatro> buscarPorDiretor(String diretor) {
        if (diretor == null || diretor.trim().isEmpty()) {
            throw new IllegalArgumentException("Diretor não pode ser nulo ou vazio");
        }
        return buscarPorCampo("et.diretor", diretor);
    }

    @Override
    public List<Teatro> buscarPorElenco(String ator) {
        if (ator == null || ator.trim().isEmpty()) {
            throw new IllegalArgumentException("Ator não pode ser nulo ou vazio");
        }
        String sql = "SELECT e.*, et.* FROM eventos e " +
                    "INNER JOIN eventos_teatro et ON e.id = et.evento_id " +
                    "WHERE et.elenco LIKE ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + ator + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Teatro> teatros = new ArrayList<>();
                while (rs.next()) {
                    teatros.add(criarTeatroDoResultSet(rs));
                }
                return teatros;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar teatros por elenco", e);
        }
    }

    @Override
    public List<Teatro> buscarPorDuracao(Integer duracaoMinima) {
        if (duracaoMinima == null) {
            throw new IllegalArgumentException("Duração mínima não pode ser nula");
        }
        String sql = "SELECT e.*, et.* FROM eventos e " +
                    "INNER JOIN eventos_teatro et ON e.id = et.evento_id " +
                    "WHERE et.duracao_minutos >= ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, duracaoMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Teatro> teatros = new ArrayList<>();
                while (rs.next()) {
                    teatros.add(criarTeatroDoResultSet(rs));
                }
                return teatros;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar teatros por duração", e);
        }
    }

    @Override
    public List<Teatro> buscarPorTipoTeatro(String tipoTeatro) {
        if (tipoTeatro == null || tipoTeatro.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de teatro não pode ser nulo ou vazio");
        }
        return buscarPorCampo("et.tipo_teatro", tipoTeatro);
    }

    @Override
    public List<Teatro> buscarPorClassificacaoIndicativa(String classificacao) {
        if (classificacao == null || classificacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Classificação indicativa não pode ser nula ou vazia");
        }
        return buscarPorCampo("et.classificacao_indicativa", classificacao);
    }
    
    private List<Teatro> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT e.*, et.* FROM eventos e " +
                    "INNER JOIN eventos_teatro et ON e.id = et.evento_id " +
                    "WHERE " + campo + " = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Teatro> teatros = new ArrayList<>();
                while (rs.next()) {
                    teatros.add(criarTeatroDoResultSet(rs));
                }
                return teatros;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar teatros por " + campo, e);
        }
    }
    
    private Teatro criarTeatroDoResultSet(ResultSet rs) throws SQLException {
        Teatro teatro = new Teatro();
        
        // Campos do evento base
        teatro.setId(rs.getLong("id"));
        teatro.setTitulo(rs.getString("titulo"));
        teatro.setDescricao(rs.getString("descricao"));
        
        Timestamp dataHora = rs.getTimestamp("data_hora");
        if (dataHora != null) {
            teatro.setDataHora(dataHora.toLocalDateTime());
        }
        
        java.sql.Date dataInicio = rs.getDate("data_inicio");
        if (dataInicio != null) {
            teatro.setDataInicio(dataInicio.toLocalDate());
        }
        
        java.sql.Date dataFim = rs.getDate("data_fim");
        if (dataFim != null) {
            teatro.setDataFim(dataFim.toLocalDate());
        }
        
        teatro.setLocal(rs.getString("local"));
        teatro.setEndereco(rs.getString("endereco"));
        teatro.setCidade(rs.getString("cidade"));
        teatro.setEstado(rs.getString("estado"));
        teatro.setCep(rs.getString("cep"));
        teatro.setPreco(rs.getBigDecimal("preco"));
        teatro.setCapacidade(rs.getInt("capacidade"));
        teatro.setIngressosVendidos(rs.getInt("ingressos_vendidos"));
        teatro.setCategoria(rs.getString("categoria"));
        teatro.setStatus(rs.getString("status"));
        teatro.setImagemUrl(rs.getString("imagem_url"));
        
        // Campos específicos do teatro
        teatro.setDiretor(rs.getString("diretor"));
        teatro.setAutor(rs.getString("autor"));
        teatro.setCompanhia(rs.getString("companhia"));
        teatro.setGeneroTeatral(rs.getString("genero_teatral"));
        teatro.setDuracaoMinutos(rs.getInt("duracao_minutos"));
        teatro.setNumeroAtos(rs.getInt("numero_atos"));
        
        String elencoStr = rs.getString("elenco");
        if (elencoStr != null && !elencoStr.isEmpty()) {
            teatro.setElenco(List.of(elencoStr.split(",")));
        }
        
        teatro.setSinopse(rs.getString("sinopse"));
        teatro.setCenografia(rs.getString("cenografia"));
        teatro.setFigurino(rs.getString("figurino"));
        teatro.setIluminacao(rs.getString("iluminacao"));
        teatro.setSonoplastia(rs.getString("sonoplastia"));
        teatro.setTemIntervalo(rs.getBoolean("tem_intervalo"));
        teatro.setDuracaoIntervalo(rs.getInt("duracao_intervalo"));
        teatro.setClassificacaoIndicativa(rs.getString("classificacao_indicativa"));
        teatro.setPermiteEntradaInfantil(rs.getBoolean("permite_entrada_infantil"));
        teatro.setTipoTeatro(rs.getString("tipo_teatro"));
        
        return teatro;
    }
}