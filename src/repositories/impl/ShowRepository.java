package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Show;
import repositories.IShowRepository;

public class ShowRepository implements IShowRepository {

    @Override
    public void salvar(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show não pode ser nulo");
        }
        
        // Primeiro salva o evento base
        String sqlEvento = "INSERT INTO eventos (titulo, descricao, data_hora, data_inicio, data_fim, " +
                          "local, endereco, cidade, estado, cep, preco, capacidade, ingressos_vendidos, " +
                          "categoria, status, imagem_url, organizador_id, local_id, categoria_id) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtEvento.setString(1, show.getTitulo());
            stmtEvento.setString(2, show.getDescricao());
            
            Timestamp dataHora = show.getDataHora() != null ? 
                Timestamp.valueOf(show.getDataHora()) : null;
            stmtEvento.setTimestamp(3, dataHora);
            
            java.sql.Date dataInicio = show.getDataInicio() != null ? 
                java.sql.Date.valueOf(show.getDataInicio()) : null;
            stmtEvento.setDate(4, dataInicio);
            
            java.sql.Date dataFim = show.getDataFim() != null ? 
                java.sql.Date.valueOf(show.getDataFim()) : null;
            stmtEvento.setDate(5, dataFim);
            
            stmtEvento.setString(6, show.getLocal());
            stmtEvento.setString(7, show.getEndereco());
            stmtEvento.setString(8, show.getCidade());
            stmtEvento.setString(9, show.getEstado());
            stmtEvento.setString(10, show.getCep());
            stmtEvento.setBigDecimal(11, show.getPreco());
            stmtEvento.setInt(12, show.getCapacidade());
            stmtEvento.setInt(13, show.getIngressosVendidos() != null ? show.getIngressosVendidos() : 0);
            stmtEvento.setString(14, show.getCategoria());
            stmtEvento.setString(15, show.getStatus());
            stmtEvento.setString(16, show.getImagemUrl());
            stmtEvento.setObject(17, null); // organizador_id
            stmtEvento.setObject(18, null); // local_id
            stmtEvento.setObject(19, null); // categoria_id
            
            stmtEvento.executeUpdate();
            
            Long eventoId;
            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoId = rs.getLong(1);
                    show.setId(eventoId);
                } else {
                    throw new RuntimeException("Não foi possível obter o ID do evento");
                }
            }
            
            // Agora salva os dados específicos do show
            String sqlShow = "INSERT INTO eventos_show (evento_id, artista_principal, artistas_convidados, " +
                            "genero_musical, duracao_minutos, tipo_show, tem_abertura, artista_abertura, " +
                            "equipamento_especial, tem_vip, preco_vip, restricao_idade, permite_entrada_infantil) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmtShow = conn.prepareStatement(sqlShow)) {
                stmtShow.setLong(1, eventoId);
                stmtShow.setString(2, show.getArtistaPrincipal());
                stmtShow.setString(3, String.join(",", show.getArtistasConvidados()));
                stmtShow.setString(4, show.getGeneroMusical());
                stmtShow.setObject(5, show.getDuracaoMinutos());
                stmtShow.setString(6, show.getTipoShow());
                stmtShow.setBoolean(7, show.getTemAbertura());
                stmtShow.setString(8, show.getArtistaAbertura());
                stmtShow.setString(9, show.getEquipamentoEspecial());
                stmtShow.setBoolean(10, show.getTemVip());
                stmtShow.setObject(11, show.getPrecoVip());
                stmtShow.setString(12, show.getRestricaoIdade());
                stmtShow.setBoolean(13, show.getPermiteEntradaInfantil());
                
                stmtShow.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar show", e);
        }
    }

    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Remove primeiro os dados específicos do show
            String sqlShow = "DELETE FROM eventos_show WHERE evento_id = ?";
            try (PreparedStatement stmtShow = conn.prepareStatement(sqlShow)) {
                stmtShow.setLong(1, id);
                stmtShow.executeUpdate();
            }
            
            // Depois remove o evento base
            String sqlEvento = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setLong(1, id);
                stmtEvento.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover show", e);
        }
    }

    @Override
    public void alterar(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show não pode ser nulo");
        }
        if (show.getId() == null) {
            throw new IllegalArgumentException("ID do show é obrigatório para alteração");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Atualiza o evento base
            String sqlEvento = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, data_inicio = ?, " +
                             "data_fim = ?, local = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, " +
                             "preco = ?, capacidade = ?, ingressos_vendidos = ?, categoria = ?, status = ?, " +
                             "imagem_url = ? WHERE id = ?";
            
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setString(1, show.getTitulo());
                stmtEvento.setString(2, show.getDescricao());
                
                Timestamp dataHora = show.getDataHora() != null ? 
                    Timestamp.valueOf(show.getDataHora()) : null;
                stmtEvento.setTimestamp(3, dataHora);
                
                java.sql.Date dataInicio = show.getDataInicio() != null ? 
                    java.sql.Date.valueOf(show.getDataInicio()) : null;
                stmtEvento.setDate(4, dataInicio);
                
                java.sql.Date dataFim = show.getDataFim() != null ? 
                    java.sql.Date.valueOf(show.getDataFim()) : null;
                stmtEvento.setDate(5, dataFim);
                
                stmtEvento.setString(6, show.getLocal());
                stmtEvento.setString(7, show.getEndereco());
                stmtEvento.setString(8, show.getCidade());
                stmtEvento.setString(9, show.getEstado());
                stmtEvento.setString(10, show.getCep());
                stmtEvento.setBigDecimal(11, show.getPreco());
                stmtEvento.setInt(12, show.getCapacidade());
                stmtEvento.setInt(13, show.getIngressosVendidos() != null ? show.getIngressosVendidos() : 0);
                stmtEvento.setString(14, show.getCategoria());
                stmtEvento.setString(15, show.getStatus());
                stmtEvento.setString(16, show.getImagemUrl());
                stmtEvento.setLong(17, show.getId());
                
                stmtEvento.executeUpdate();
            }
            
            // Atualiza os dados específicos do show
            String sqlShow = "UPDATE eventos_show SET artista_principal = ?, artistas_convidados = ?, " +
                            "genero_musical = ?, duracao_minutos = ?, tipo_show = ?, tem_abertura = ?, " +
                            "artista_abertura = ?, equipamento_especial = ?, tem_vip = ?, preco_vip = ?, " +
                            "restricao_idade = ?, permite_entrada_infantil = ? WHERE evento_id = ?";
            
            try (PreparedStatement stmtShow = conn.prepareStatement(sqlShow)) {
                stmtShow.setString(1, show.getArtistaPrincipal());
                stmtShow.setString(2, String.join(",", show.getArtistasConvidados()));
                stmtShow.setString(3, show.getGeneroMusical());
                stmtShow.setObject(4, show.getDuracaoMinutos());
                stmtShow.setString(5, show.getTipoShow());
                stmtShow.setBoolean(6, show.getTemAbertura());
                stmtShow.setString(7, show.getArtistaAbertura());
                stmtShow.setString(8, show.getEquipamentoEspecial());
                stmtShow.setBoolean(9, show.getTemVip());
                stmtShow.setObject(10, show.getPrecoVip());
                stmtShow.setString(11, show.getRestricaoIdade());
                stmtShow.setBoolean(12, show.getPermiteEntradaInfantil());
                stmtShow.setLong(13, show.getId());
                
                stmtShow.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar show", e);
        }
    }

    @Override
    public List<Show> listar() {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT e.*, es.* FROM eventos e " +
                    "INNER JOIN eventos_show es ON e.id = es.evento_id " +
                    "ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Show show = criarShowDoResultSet(rs);
                shows.add(show);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar shows", e);
        }
        
        return shows;
    }

    @Override
    public Show buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT e.*, es.* FROM eventos e " +
                    "INNER JOIN eventos_show es ON e.id = es.evento_id " +
                    "WHERE e.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarShowDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar show por ID", e);
        }
        
        return null;
    }

    @Override
    public List<Show> buscarPorArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("Artista não pode ser nulo ou vazio");
        }
        String sql = "SELECT e.*, es.* FROM eventos e " +
                    "INNER JOIN eventos_show es ON e.id = es.evento_id " +
                    "WHERE es.artista_principal LIKE ? OR es.artistas_convidados LIKE ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String busca = "%" + artista + "%";
            stmt.setString(1, busca);
            stmt.setString(2, busca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Show> shows = new ArrayList<>();
                while (rs.next()) {
                    shows.add(criarShowDoResultSet(rs));
                }
                return shows;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar shows por artista", e);
        }
    }

    @Override
    public List<Show> buscarPorGeneroMusical(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("Gênero musical não pode ser nulo ou vazio");
        }
        return buscarPorCampo("es.genero_musical", genero);
    }

    @Override
    public List<Show> buscarPorTipoShow(String tipoShow) {
        if (tipoShow == null || tipoShow.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de show não pode ser nulo ou vazio");
        }
        return buscarPorCampo("es.tipo_show", tipoShow);
    }

    @Override
    public List<Show> buscarPorDuracao(Integer duracaoMinima) {
        if (duracaoMinima == null) {
            throw new IllegalArgumentException("Duração mínima não pode ser nula");
        }
        String sql = "SELECT e.*, es.* FROM eventos e " +
                    "INNER JOIN eventos_show es ON e.id = es.evento_id " +
                    "WHERE es.duracao_minutos >= ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, duracaoMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Show> shows = new ArrayList<>();
                while (rs.next()) {
                    shows.add(criarShowDoResultSet(rs));
                }
                return shows;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar shows por duração", e);
        }
    }

    @Override
    public List<Show> buscarPorFaixaEtaria(String faixaEtaria) {
        if (faixaEtaria == null || faixaEtaria.trim().isEmpty()) {
            throw new IllegalArgumentException("Faixa etária não pode ser nula ou vazia");
        }
        return buscarPorCampo("es.restricao_idade", faixaEtaria);
    }

    @Override
    public List<Show> buscarPorTipoIngresso(String tipoIngresso) {
        if (tipoIngresso == null || tipoIngresso.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de ingresso não pode ser nulo ou vazio");
        }
        // Como não temos campo específico para tipo de ingresso no show, vamos buscar por categoria
        return buscarPorCampo("e.categoria", tipoIngresso);
    }
    
    private List<Show> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT e.*, es.* FROM eventos e " +
                    "INNER JOIN eventos_show es ON e.id = es.evento_id " +
                    "WHERE " + campo + " = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Show> shows = new ArrayList<>();
                while (rs.next()) {
                    shows.add(criarShowDoResultSet(rs));
                }
                return shows;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar shows por " + campo, e);
        }
    }
    
    private Show criarShowDoResultSet(ResultSet rs) throws SQLException {
        Show show = new Show();
        
        // Campos do evento base
        show.setId(rs.getLong("id"));
        show.setTitulo(rs.getString("titulo"));
        show.setDescricao(rs.getString("descricao"));
        
        Timestamp dataHora = rs.getTimestamp("data_hora");
        if (dataHora != null) {
            show.setDataHora(dataHora.toLocalDateTime());
        }
        
        java.sql.Date dataInicio = rs.getDate("data_inicio");
        if (dataInicio != null) {
            show.setDataInicio(dataInicio.toLocalDate());
        }
        
        java.sql.Date dataFim = rs.getDate("data_fim");
        if (dataFim != null) {
            show.setDataFim(dataFim.toLocalDate());
        }
        
        show.setLocal(rs.getString("local"));
        show.setEndereco(rs.getString("endereco"));
        show.setCidade(rs.getString("cidade"));
        show.setEstado(rs.getString("estado"));
        show.setCep(rs.getString("cep"));
        show.setPreco(rs.getBigDecimal("preco"));
        show.setCapacidade(rs.getInt("capacidade"));
        show.setIngressosVendidos(rs.getInt("ingressos_vendidos"));
        show.setCategoria(rs.getString("categoria"));
        show.setStatus(rs.getString("status"));
        show.setImagemUrl(rs.getString("imagem_url"));
        
        // Campos específicos do show
        show.setArtistaPrincipal(rs.getString("artista_principal"));
        
        String artistasConvidadosStr = rs.getString("artistas_convidados");
        if (artistasConvidadosStr != null && !artistasConvidadosStr.isEmpty()) {
            show.setArtistasConvidados(List.of(artistasConvidadosStr.split(",")));
        }
        
        show.setGeneroMusical(rs.getString("genero_musical"));
        show.setDuracaoMinutos(rs.getInt("duracao_minutos"));
        show.setTipoShow(rs.getString("tipo_show"));
        show.setTemAbertura(rs.getBoolean("tem_abertura"));
        show.setArtistaAbertura(rs.getString("artista_abertura"));
        show.setEquipamentoEspecial(rs.getString("equipamento_especial"));
        show.setTemVip(rs.getBoolean("tem_vip"));
        show.setPrecoVip(rs.getBigDecimal("preco_vip"));
        show.setRestricaoIdade(rs.getString("restricao_idade"));
        show.setPermiteEntradaInfantil(rs.getBoolean("permite_entrada_infantil"));
        
        return show;
    }
}