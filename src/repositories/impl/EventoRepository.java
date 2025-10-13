package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Evento;
import repositories.IEventoRepository;

public class EventoRepository implements IEventoRepository {
    
    @Override
    public void salvar(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo");
        }
        String sql = "INSERT INTO eventos (titulo, descricao, data_hora, local, cidade, estado, " +
                    "capacidade, categoria, organizador, tipo_evento, formato, gratuito, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(evento.getDataHora()));
            stmt.setString(4, evento.getLocal());
            stmt.setString(5, evento.getCidade());
            stmt.setString(6, evento.getEstado());
            stmt.setInt(7, evento.getCapacidade());
            stmt.setString(8, evento.getCategoria());
            stmt.setString(9, evento.getOrganizador());
            stmt.setString(10, evento.getTipoEvento());
            stmt.setString(11, evento.getFormato());
            stmt.setBoolean(12, evento.getGratuito());
            stmt.setString(13, evento.getStatus());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar evento", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM eventos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover evento", e);
        }
    }
    
    @Override
    public void alterar(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo");
        }
        if (evento.getId() == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório para alteração");
        }
        String sql = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, local = ?, " +
                    "cidade = ?, estado = ?, capacidade = ?, categoria = ?, organizador = ?, " +
                    "tipo_evento = ?, formato = ?, gratuito = ?, status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(evento.getDataHora()));
            stmt.setString(4, evento.getLocal());
            stmt.setString(5, evento.getCidade());
            stmt.setString(6, evento.getEstado());
            stmt.setInt(7, evento.getCapacidade());
            stmt.setString(8, evento.getCategoria());
            stmt.setString(9, evento.getOrganizador());
            stmt.setString(10, evento.getTipoEvento());
            stmt.setString(11, evento.getFormato());
            stmt.setBoolean(12, evento.getGratuito());
            stmt.setString(13, evento.getStatus());
            stmt.setLong(14, evento.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar evento", e);
        }
    }
    
    @Override
    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Evento evento = criarEventoDoResultSet(rs);
                eventos.add(evento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar eventos", e);
        }
        
        return eventos;
    }
    
    @Override
    public Evento buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM eventos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarEventoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar evento por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Evento> buscarPorTipo(String tipoEvento) {
        if (tipoEvento == null || tipoEvento.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do evento não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_evento", tipoEvento);
    }
    
    @Override
    public List<Evento> buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser nula ou vazia");
        }
        return buscarPorCampo("categoria", categoria);
    }
    
    @Override
    public List<Evento> buscarPorLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local não pode ser nulo ou vazio");
        }
        return buscarPorCampo("local", local);
    }
    
    @Override
    public List<Evento> buscarPorOrganizador(String organizador) {
        if (organizador == null || organizador.trim().isEmpty()) {
            throw new IllegalArgumentException("Organizador não pode ser nulo ou vazio");
        }
        return buscarPorCampo("organizador", organizador);
    }
    
    @Override
    public List<Evento> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        return buscarPorCampo("status", status);
    }
    
    @Override
    public List<Evento> buscarPorPreco(Boolean gratuito) {
        if (gratuito == null) {
            throw new IllegalArgumentException("Parâmetro gratuito não pode ser nulo");
        }
        String sql = "SELECT * FROM eventos WHERE gratuito = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, gratuito);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Evento> eventos = new ArrayList<>();
                while (rs.next()) {
                    eventos.add(criarEventoDoResultSet(rs));
                }
                return eventos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar eventos por preço", e);
        }
    }
    
    @Override
    public List<Evento> buscarPorFormato(String formato) {
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("Formato não pode ser nulo ou vazio");
        }
        return buscarPorCampo("formato", formato);
    }
    
    @Override
    public List<Evento> buscarPorData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        String sql = "SELECT * FROM eventos WHERE DATE(data_hora) = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(data));
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Evento> eventos = new ArrayList<>();
                while (rs.next()) {
                    eventos.add(criarEventoDoResultSet(rs));
                }
                return eventos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar eventos por data", e);
        }
    }
    
    @Override
    public List<Evento> buscarPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser nula ou vazia");
        }
        return buscarPorCampo("cidade", cidade);
    }
    
    @Override
    public List<Evento> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser nulo ou vazio");
        }
        return buscarPorCampo("estado", estado);
    }
    
    private List<Evento> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT * FROM eventos WHERE " + campo + " = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Evento> eventos = new ArrayList<>();
                while (rs.next()) {
                    eventos.add(criarEventoDoResultSet(rs));
                }
                return eventos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar eventos por " + campo, e);
        }
    }
    
    private Evento criarEventoDoResultSet(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setId(rs.getLong("id"));
        evento.setTitulo(rs.getString("titulo"));
        evento.setDescricao(rs.getString("descricao"));
        
        Timestamp timestamp = rs.getTimestamp("data_hora");
        if (timestamp != null) {
            evento.setDataHora(timestamp.toLocalDateTime());
        }
        
        evento.setLocal(rs.getString("local"));
        evento.setCidade(rs.getString("cidade"));
        evento.setEstado(rs.getString("estado"));
        evento.setCapacidade(rs.getInt("capacidade"));
        evento.setCategoria(rs.getString("categoria"));
        evento.setOrganizador(rs.getString("organizador"));
        evento.setTipoEvento(rs.getString("tipo_evento"));
        evento.setFormato(rs.getString("formato"));
        evento.setGratuito(rs.getBoolean("gratuito"));
        evento.setStatus(rs.getString("status"));
        
        return evento;
    }
}

