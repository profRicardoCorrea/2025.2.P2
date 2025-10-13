package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

import config.DatabaseConfig;
import entities.Ingresso;
import repositories.IIngressoRepository;

public class IngressoRepository implements IIngressoRepository {
    
    @Override
    public void salvar(Ingresso ingresso) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso não pode ser nulo");
        }
        String sql = "INSERT INTO ingressos (codigo, evento_id, usuario_id, tipo_ingresso, preco, " +
                    "status, data_compra, data_utilizacao, data_cancelamento, forma_pagamento, " +
                    "observacoes, transferivel, nome_beneficiario, cpf_beneficiario, telefone_beneficiario, " +
                    "email_beneficiario, local_retirada, horario_retirada, assento, setor, fila, porta_entrada) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, ingresso.getCodigo());
            stmt.setLong(2, ingresso.getEventoId());
            stmt.setLong(3, ingresso.getUsuarioId());
            stmt.setString(4, ingresso.getTipoIngresso());
            stmt.setBigDecimal(5, ingresso.getPreco());
            stmt.setString(6, ingresso.getStatus());
            
            Timestamp dataCompra = ingresso.getDataCompra() != null ? 
                Timestamp.valueOf(ingresso.getDataCompra()) : Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(7, dataCompra);
            
            Timestamp dataUtilizacao = ingresso.getDataUtilizacao() != null ? 
                Timestamp.valueOf(ingresso.getDataUtilizacao()) : null;
            stmt.setTimestamp(8, dataUtilizacao);
            
            Timestamp dataCancelamento = ingresso.getDataCancelamento() != null ? 
                Timestamp.valueOf(ingresso.getDataCancelamento()) : null;
            stmt.setTimestamp(9, dataCancelamento);
            
            stmt.setString(10, ingresso.getFormaPagamento());
            stmt.setString(11, ingresso.getObservacoes());
            stmt.setBoolean(12, ingresso.getTransferivel());
            stmt.setString(13, ingresso.getNomeBeneficiario());
            stmt.setString(14, ingresso.getCpfBeneficiario());
            stmt.setString(15, ingresso.getTelefoneBeneficiario());
            stmt.setString(16, ingresso.getEmailBeneficiario());
            stmt.setString(17, ingresso.getLocalRetirada());
            stmt.setString(18, ingresso.getHorarioRetirada());
            stmt.setString(19, ingresso.getAssento());
            stmt.setString(20, ingresso.getSetor());
            stmt.setString(21, ingresso.getFila());
            stmt.setString(22, ingresso.getPortaEntrada());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ingresso.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ingresso", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM ingressos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover ingresso", e);
        }
    }
    
    @Override
    public void alterar(Ingresso ingresso) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso não pode ser nulo");
        }
        if (ingresso.getId() == null) {
            throw new IllegalArgumentException("ID do ingresso é obrigatório para alteração");
        }
        String sql = "UPDATE ingressos SET codigo = ?, evento_id = ?, usuario_id = ?, tipo_ingresso = ?, " +
                    "preco = ?, status = ?, data_compra = ?, data_utilizacao = ?, data_cancelamento = ?, " +
                    "forma_pagamento = ?, observacoes = ?, transferivel = ?, nome_beneficiario = ?, " +
                    "cpf_beneficiario = ?, telefone_beneficiario = ?, email_beneficiario = ?, " +
                    "local_retirada = ?, horario_retirada = ?, assento = ?, setor = ?, fila = ?, " +
                    "porta_entrada = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ingresso.getCodigo());
            stmt.setLong(2, ingresso.getEventoId());
            stmt.setLong(3, ingresso.getUsuarioId());
            stmt.setString(4, ingresso.getTipoIngresso());
            stmt.setBigDecimal(5, ingresso.getPreco());
            stmt.setString(6, ingresso.getStatus());
            
            Timestamp dataCompra = ingresso.getDataCompra() != null ? 
                Timestamp.valueOf(ingresso.getDataCompra()) : null;
            stmt.setTimestamp(7, dataCompra);
            
            Timestamp dataUtilizacao = ingresso.getDataUtilizacao() != null ? 
                Timestamp.valueOf(ingresso.getDataUtilizacao()) : null;
            stmt.setTimestamp(8, dataUtilizacao);
            
            Timestamp dataCancelamento = ingresso.getDataCancelamento() != null ? 
                Timestamp.valueOf(ingresso.getDataCancelamento()) : null;
            stmt.setTimestamp(9, dataCancelamento);
            
            stmt.setString(10, ingresso.getFormaPagamento());
            stmt.setString(11, ingresso.getObservacoes());
            stmt.setBoolean(12, ingresso.getTransferivel());
            stmt.setString(13, ingresso.getNomeBeneficiario());
            stmt.setString(14, ingresso.getCpfBeneficiario());
            stmt.setString(15, ingresso.getTelefoneBeneficiario());
            stmt.setString(16, ingresso.getEmailBeneficiario());
            stmt.setString(17, ingresso.getLocalRetirada());
            stmt.setString(18, ingresso.getHorarioRetirada());
            stmt.setString(19, ingresso.getAssento());
            stmt.setString(20, ingresso.getSetor());
            stmt.setString(21, ingresso.getFila());
            stmt.setString(22, ingresso.getPortaEntrada());
            stmt.setLong(23, ingresso.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar ingresso", e);
        }
    }
    
    @Override
    public List<Ingresso> listar() {
        List<Ingresso> ingressos = new ArrayList<>();
        String sql = "SELECT * FROM ingressos ORDER BY data_compra DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ingresso ingresso = criarIngressoDoResultSet(rs);
                ingressos.add(ingresso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ingressos", e);
        }
        
        return ingressos;
    }
    
    @Override
    public Ingresso buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM ingressos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarIngressoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ingresso por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Ingresso> buscarPorEvento(Long eventoId) {
        if (eventoId == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo");
        }
        return buscarPorCampo("evento_id", eventoId);
    }
    
    @Override
    public List<Ingresso> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        return buscarPorCampo("usuario_id", usuarioId);
    }
    
    @Override
    public List<Ingresso> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        return buscarPorCampo("status", status);
    }
    
    @Override
    public List<Ingresso> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_ingresso", tipo);
    }
    
    @Override
    public List<Ingresso> buscarPorDataCompra(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        String sql = "SELECT * FROM ingressos WHERE DATE(data_compra) = ? ORDER BY data_compra DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(data));
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Ingresso> ingressos = new ArrayList<>();
                while (rs.next()) {
                    ingressos.add(criarIngressoDoResultSet(rs));
                }
                return ingressos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ingressos por data de compra", e);
        }
    }
    
    @Override
    public List<Ingresso> buscarPorDataEvento(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        String sql = "SELECT i.* FROM ingressos i " +
                    "INNER JOIN eventos e ON i.evento_id = e.id " +
                    "WHERE DATE(e.data_hora) = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(data));
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Ingresso> ingressos = new ArrayList<>();
                while (rs.next()) {
                    ingressos.add(criarIngressoDoResultSet(rs));
                }
                return ingressos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ingressos por data do evento", e);
        }
    }
    
    @Override
    public List<Ingresso> buscarPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        if (precoMinimo == null && precoMaximo == null) {
            throw new IllegalArgumentException("Pelo menos um preço deve ser informado");
        }
        
        StringBuilder sql = new StringBuilder("SELECT * FROM ingressos WHERE ");
        List<Object> params = new ArrayList<>();
        
        if (precoMinimo != null && precoMaximo != null) {
            sql.append("preco BETWEEN ? AND ?");
            params.add(precoMinimo);
            params.add(precoMaximo);
        } else if (precoMinimo != null) {
            sql.append("preco >= ?");
            params.add(precoMinimo);
        } else {
            sql.append("preco <= ?");
            params.add(precoMaximo);
        }
        
        sql.append(" ORDER BY preco");
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Ingresso> ingressos = new ArrayList<>();
                while (rs.next()) {
                    ingressos.add(criarIngressoDoResultSet(rs));
                }
                return ingressos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ingressos por preço", e);
        }
    }
    
    private List<Ingresso> buscarPorCampo(String campo, Object valor) {
        String sql = "SELECT * FROM ingressos WHERE " + campo + " = ? ORDER BY data_compra DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (valor instanceof Long) {
                stmt.setLong(1, (Long) valor);
            } else if (valor instanceof String) {
                stmt.setString(1, (String) valor);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Ingresso> ingressos = new ArrayList<>();
                while (rs.next()) {
                    ingressos.add(criarIngressoDoResultSet(rs));
                }
                return ingressos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ingressos por " + campo, e);
        }
    }
    
    private Ingresso criarIngressoDoResultSet(ResultSet rs) throws SQLException {
        Ingresso ingresso = new Ingresso();
        ingresso.setId(rs.getLong("id"));
        ingresso.setCodigo(rs.getString("codigo"));
        ingresso.setEventoId(rs.getLong("evento_id"));
        ingresso.setUsuarioId(rs.getLong("usuario_id"));
        ingresso.setTipoIngresso(rs.getString("tipo_ingresso"));
        ingresso.setPreco(rs.getBigDecimal("preco"));
        ingresso.setStatus(rs.getString("status"));
        
        Timestamp dataCompra = rs.getTimestamp("data_compra");
        if (dataCompra != null) {
            ingresso.setDataCompra(dataCompra.toLocalDateTime());
        }
        
        Timestamp dataUtilizacao = rs.getTimestamp("data_utilizacao");
        if (dataUtilizacao != null) {
            ingresso.setDataUtilizacao(dataUtilizacao.toLocalDateTime());
        }
        
        Timestamp dataCancelamento = rs.getTimestamp("data_cancelamento");
        if (dataCancelamento != null) {
            ingresso.setDataCancelamento(dataCancelamento.toLocalDateTime());
        }
        
        ingresso.setFormaPagamento(rs.getString("forma_pagamento"));
        ingresso.setObservacoes(rs.getString("observacoes"));
        ingresso.setTransferivel(rs.getBoolean("transferivel"));
        ingresso.setNomeBeneficiario(rs.getString("nome_beneficiario"));
        ingresso.setCpfBeneficiario(rs.getString("cpf_beneficiario"));
        ingresso.setTelefoneBeneficiario(rs.getString("telefone_beneficiario"));
        ingresso.setEmailBeneficiario(rs.getString("email_beneficiario"));
        ingresso.setLocalRetirada(rs.getString("local_retirada"));
        ingresso.setHorarioRetirada(rs.getString("horario_retirada"));
        ingresso.setAssento(rs.getString("assento"));
        ingresso.setSetor(rs.getString("setor"));
        ingresso.setFila(rs.getString("fila"));
        ingresso.setPortaEntrada(rs.getString("porta_entrada"));
        
        return ingresso;
    }
}