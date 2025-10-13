package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import config.DatabaseConfig;
import entities.Workshop;
import repositories.IWorkshopRepository;

public class WorkshopRepository implements IWorkshopRepository {

    @Override
    public void salvar(Workshop workshop) {
        if (workshop == null) {
            throw new IllegalArgumentException("Workshop não pode ser nulo");
        }
        
        // Primeiro salva o evento base
        String sqlEvento = "INSERT INTO eventos (titulo, descricao, data_hora, data_inicio, data_fim, " +
                          "local, endereco, cidade, estado, cep, preco, capacidade, ingressos_vendidos, " +
                          "categoria, status, imagem_url, organizador_id, local_id, categoria_id) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS)) {
            
            stmtEvento.setString(1, workshop.getTitulo());
            stmtEvento.setString(2, workshop.getDescricao());
            
            Timestamp dataHora = workshop.getDataHora() != null ? 
                Timestamp.valueOf(workshop.getDataHora()) : null;
            stmtEvento.setTimestamp(3, dataHora);
            
            java.sql.Date dataInicio = workshop.getDataInicio() != null ? 
                java.sql.Date.valueOf(workshop.getDataInicio()) : null;
            stmtEvento.setDate(4, dataInicio);
            
            java.sql.Date dataFim = workshop.getDataFim() != null ? 
                java.sql.Date.valueOf(workshop.getDataFim()) : null;
            stmtEvento.setDate(5, dataFim);
            
            stmtEvento.setString(6, workshop.getLocal());
            stmtEvento.setString(7, workshop.getEndereco());
            stmtEvento.setString(8, workshop.getCidade());
            stmtEvento.setString(9, workshop.getEstado());
            stmtEvento.setString(10, workshop.getCep());
            stmtEvento.setBigDecimal(11, workshop.getPreco());
            stmtEvento.setInt(12, workshop.getCapacidade());
            stmtEvento.setInt(13, workshop.getIngressosVendidos() != null ? workshop.getIngressosVendidos() : 0);
            stmtEvento.setString(14, workshop.getCategoria());
            stmtEvento.setString(15, workshop.getStatus());
            stmtEvento.setString(16, workshop.getImagemUrl());
            stmtEvento.setObject(17, null); // organizador_id
            stmtEvento.setObject(18, null); // local_id
            stmtEvento.setObject(19, null); // categoria_id
            
            stmtEvento.executeUpdate();
            
            Long eventoId;
            try (ResultSet rs = stmtEvento.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoId = rs.getLong(1);
                    workshop.setId(eventoId);
                } else {
                    throw new RuntimeException("Não foi possível obter o ID do evento");
                }
            }
            
            // Agora salva os dados específicos do workshop
            String sqlWorkshop = "INSERT INTO eventos_workshop (evento_id, instrutor, nivel_dificuldade, " +
                                "duracao_horas, material_incluso, lista_materiais, equipamentos_necessarios, " +
                                "tem_certificado, tipo_certificado, prerequisitos, objetivos_aprendizagem, " +
                                "metodologia, numero_vagas, vagas_disponiveis, tipo_workshop, modalidade, " +
                                "permite_gravacao, permite_fotos) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmtWorkshop = conn.prepareStatement(sqlWorkshop)) {
                stmtWorkshop.setLong(1, eventoId);
                stmtWorkshop.setString(2, workshop.getInstrutor());
                stmtWorkshop.setString(3, workshop.getNivelDificuldade());
                stmtWorkshop.setObject(4, workshop.getDuracaoHoras());
                stmtWorkshop.setBoolean(5, workshop.getMaterialIncluso());
                stmtWorkshop.setString(6, workshop.getListaMateriais());
                stmtWorkshop.setString(7, workshop.getEquipamentosNecessarios());
                stmtWorkshop.setBoolean(8, workshop.getTemCertificado());
                stmtWorkshop.setString(9, workshop.getTipoCertificado());
                stmtWorkshop.setString(10, workshop.getPrerequisitos());
                stmtWorkshop.setString(11, workshop.getObjetivosAprendizagem());
                stmtWorkshop.setString(12, workshop.getMetodologia());
                stmtWorkshop.setObject(13, workshop.getNumeroVagas());
                stmtWorkshop.setObject(14, workshop.getVagasDisponiveis());
                stmtWorkshop.setString(15, workshop.getTipoWorkshop());
                stmtWorkshop.setString(16, workshop.getModalidade());
                stmtWorkshop.setBoolean(17, workshop.getPermiteGravacao());
                stmtWorkshop.setBoolean(18, workshop.getPermiteFotos());
                
                stmtWorkshop.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar workshop", e);
        }
    }

    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Remove primeiro os dados específicos do workshop
            String sqlWorkshop = "DELETE FROM eventos_workshop WHERE evento_id = ?";
            try (PreparedStatement stmtWorkshop = conn.prepareStatement(sqlWorkshop)) {
                stmtWorkshop.setLong(1, id);
                stmtWorkshop.executeUpdate();
            }
            
            // Depois remove o evento base
            String sqlEvento = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setLong(1, id);
                stmtEvento.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover workshop", e);
        }
    }

    @Override
    public void alterar(Workshop workshop) {
        if (workshop == null) {
            throw new IllegalArgumentException("Workshop não pode ser nulo");
        }
        if (workshop.getId() == null) {
            throw new IllegalArgumentException("ID do workshop é obrigatório para alteração");
        }
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Atualiza o evento base
            String sqlEvento = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, data_inicio = ?, " +
                             "data_fim = ?, local = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, " +
                             "preco = ?, capacidade = ?, ingressos_vendidos = ?, categoria = ?, status = ?, " +
                             "imagem_url = ? WHERE id = ?";
            
            try (PreparedStatement stmtEvento = conn.prepareStatement(sqlEvento)) {
                stmtEvento.setString(1, workshop.getTitulo());
                stmtEvento.setString(2, workshop.getDescricao());
                
                Timestamp dataHora = workshop.getDataHora() != null ? 
                    Timestamp.valueOf(workshop.getDataHora()) : null;
                stmtEvento.setTimestamp(3, dataHora);
                
                java.sql.Date dataInicio = workshop.getDataInicio() != null ? 
                    java.sql.Date.valueOf(workshop.getDataInicio()) : null;
                stmtEvento.setDate(4, dataInicio);
                
                java.sql.Date dataFim = workshop.getDataFim() != null ? 
                    java.sql.Date.valueOf(workshop.getDataFim()) : null;
                stmtEvento.setDate(5, dataFim);
                
                stmtEvento.setString(6, workshop.getLocal());
                stmtEvento.setString(7, workshop.getEndereco());
                stmtEvento.setString(8, workshop.getCidade());
                stmtEvento.setString(9, workshop.getEstado());
                stmtEvento.setString(10, workshop.getCep());
                stmtEvento.setBigDecimal(11, workshop.getPreco());
                stmtEvento.setInt(12, workshop.getCapacidade());
                stmtEvento.setInt(13, workshop.getIngressosVendidos() != null ? workshop.getIngressosVendidos() : 0);
                stmtEvento.setString(14, workshop.getCategoria());
                stmtEvento.setString(15, workshop.getStatus());
                stmtEvento.setString(16, workshop.getImagemUrl());
                stmtEvento.setLong(17, workshop.getId());
                
                stmtEvento.executeUpdate();
            }
            
            // Atualiza os dados específicos do workshop
            String sqlWorkshop = "UPDATE eventos_workshop SET instrutor = ?, nivel_dificuldade = ?, " +
                                "duracao_horas = ?, material_incluso = ?, lista_materiais = ?, " +
                                "equipamentos_necessarios = ?, tem_certificado = ?, tipo_certificado = ?, " +
                                "prerequisitos = ?, objetivos_aprendizagem = ?, metodologia = ?, " +
                                "numero_vagas = ?, vagas_disponiveis = ?, tipo_workshop = ?, modalidade = ?, " +
                                "permite_gravacao = ?, permite_fotos = ? WHERE evento_id = ?";
            
            try (PreparedStatement stmtWorkshop = conn.prepareStatement(sqlWorkshop)) {
                stmtWorkshop.setString(1, workshop.getInstrutor());
                stmtWorkshop.setString(2, workshop.getNivelDificuldade());
                stmtWorkshop.setObject(3, workshop.getDuracaoHoras());
                stmtWorkshop.setBoolean(4, workshop.getMaterialIncluso());
                stmtWorkshop.setString(5, workshop.getListaMateriais());
                stmtWorkshop.setString(6, workshop.getEquipamentosNecessarios());
                stmtWorkshop.setBoolean(7, workshop.getTemCertificado());
                stmtWorkshop.setString(8, workshop.getTipoCertificado());
                stmtWorkshop.setString(9, workshop.getPrerequisitos());
                stmtWorkshop.setString(10, workshop.getObjetivosAprendizagem());
                stmtWorkshop.setString(11, workshop.getMetodologia());
                stmtWorkshop.setObject(12, workshop.getNumeroVagas());
                stmtWorkshop.setObject(13, workshop.getVagasDisponiveis());
                stmtWorkshop.setString(14, workshop.getTipoWorkshop());
                stmtWorkshop.setString(15, workshop.getModalidade());
                stmtWorkshop.setBoolean(16, workshop.getPermiteGravacao());
                stmtWorkshop.setBoolean(17, workshop.getPermiteFotos());
                stmtWorkshop.setLong(18, workshop.getId());
                
                stmtWorkshop.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar workshop", e);
        }
    }

    @Override
    public List<Workshop> listar() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT e.*, ew.* FROM eventos e " +
                    "INNER JOIN eventos_workshop ew ON e.id = ew.evento_id " +
                    "ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Workshop workshop = criarWorkshopDoResultSet(rs);
                workshops.add(workshop);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar workshops", e);
        }
        
        return workshops;
    }

    @Override
    public Workshop buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT e.*, ew.* FROM eventos e " +
                    "INNER JOIN eventos_workshop ew ON e.id = ew.evento_id " +
                    "WHERE e.id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarWorkshopDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar workshop por ID", e);
        }
        
        return null;
    }

    @Override
    public List<Workshop> buscarPorInstrutor(String instrutor) {
        if (instrutor == null || instrutor.trim().isEmpty()) {
            throw new IllegalArgumentException("Instrutor não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ew.instrutor", instrutor);
    }

    @Override
    public List<Workshop> buscarPorNivel(String nivel) {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new IllegalArgumentException("Nível não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ew.nivel_dificuldade", nivel);
    }

    @Override
    public List<Workshop> buscarPorDuracao(Integer duracaoMinima) {
        if (duracaoMinima == null) {
            throw new IllegalArgumentException("Duração mínima não pode ser nula");
        }
        String sql = "SELECT e.*, ew.* FROM eventos e " +
                    "INNER JOIN eventos_workshop ew ON e.id = ew.evento_id " +
                    "WHERE ew.duracao_horas >= ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, duracaoMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Workshop> workshops = new ArrayList<>();
                while (rs.next()) {
                    workshops.add(criarWorkshopDoResultSet(rs));
                }
                return workshops;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar workshops por duração", e);
        }
    }

    @Override
    public List<Workshop> buscarPorMaterialIncluso(Boolean materialIncluso) {
        if (materialIncluso == null) {
            throw new IllegalArgumentException("Material incluso não pode ser nulo");
        }
        return buscarPorCampo("ew.material_incluso", materialIncluso);
    }

    @Override
    public List<Workshop> buscarPorCertificado(Boolean temCertificado) {
        if (temCertificado == null) {
            throw new IllegalArgumentException("Certificado não pode ser nulo");
        }
        return buscarPorCampo("ew.tem_certificado", temCertificado);
    }

    @Override
    public List<Workshop> buscarPorTipoWorkshop(String tipoWorkshop) {
        if (tipoWorkshop == null || tipoWorkshop.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de workshop não pode ser nulo ou vazio");
        }
        return buscarPorCampo("ew.tipo_workshop", tipoWorkshop);
    }
    
    private List<Workshop> buscarPorCampo(String campo, Object valor) {
        String sql = "SELECT e.*, ew.* FROM eventos e " +
                    "INNER JOIN eventos_workshop ew ON e.id = ew.evento_id " +
                    "WHERE " + campo + " = ? ORDER BY e.data_hora";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (valor instanceof String) {
                stmt.setString(1, (String) valor);
            } else if (valor instanceof Boolean) {
                stmt.setBoolean(1, (Boolean) valor);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Workshop> workshops = new ArrayList<>();
                while (rs.next()) {
                    workshops.add(criarWorkshopDoResultSet(rs));
                }
                return workshops;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar workshops por " + campo, e);
        }
    }
    
    private Workshop criarWorkshopDoResultSet(ResultSet rs) throws SQLException {
        Workshop workshop = new Workshop();
        
        // Campos do evento base
        workshop.setId(rs.getLong("id"));
        workshop.setTitulo(rs.getString("titulo"));
        workshop.setDescricao(rs.getString("descricao"));
        
        Timestamp dataHora = rs.getTimestamp("data_hora");
        if (dataHora != null) {
            workshop.setDataHora(dataHora.toLocalDateTime());
        }
        
        java.sql.Date dataInicio = rs.getDate("data_inicio");
        if (dataInicio != null) {
            workshop.setDataInicio(dataInicio.toLocalDate());
        }
        
        java.sql.Date dataFim = rs.getDate("data_fim");
        if (dataFim != null) {
            workshop.setDataFim(dataFim.toLocalDate());
        }
        
        workshop.setLocal(rs.getString("local"));
        workshop.setEndereco(rs.getString("endereco"));
        workshop.setCidade(rs.getString("cidade"));
        workshop.setEstado(rs.getString("estado"));
        workshop.setCep(rs.getString("cep"));
        workshop.setPreco(rs.getBigDecimal("preco"));
        workshop.setCapacidade(rs.getInt("capacidade"));
        workshop.setIngressosVendidos(rs.getInt("ingressos_vendidos"));
        workshop.setCategoria(rs.getString("categoria"));
        workshop.setStatus(rs.getString("status"));
        workshop.setImagemUrl(rs.getString("imagem_url"));
        
        // Campos específicos do workshop
        workshop.setInstrutor(rs.getString("instrutor"));
        workshop.setNivelDificuldade(rs.getString("nivel_dificuldade"));
        workshop.setDuracaoHoras(rs.getInt("duracao_horas"));
        workshop.setMaterialIncluso(rs.getBoolean("material_incluso"));
        workshop.setListaMateriais(rs.getString("lista_materiais"));
        workshop.setEquipamentosNecessarios(rs.getString("equipamentos_necessarios"));
        workshop.setTemCertificado(rs.getBoolean("tem_certificado"));
        workshop.setTipoCertificado(rs.getString("tipo_certificado"));
        workshop.setPrerequisitos(rs.getString("prerequisitos"));
        workshop.setObjetivosAprendizagem(rs.getString("objetivos_aprendizagem"));
        workshop.setMetodologia(rs.getString("metodologia"));
        workshop.setNumeroVagas(rs.getInt("numero_vagas"));
        workshop.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
        workshop.setTipoWorkshop(rs.getString("tipo_workshop"));
        workshop.setModalidade(rs.getString("modalidade"));
        workshop.setPermiteGravacao(rs.getBoolean("permite_gravacao"));
        workshop.setPermiteFotos(rs.getBoolean("permite_fotos"));
        
        return workshop;
    }
}