package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import config.DatabaseConfig;
import entities.Local;
import repositories.ILocalRepository;

public class LocalRepository implements ILocalRepository {
    
    @Override
    public void salvar(Local local) {
        if (local == null) {
            throw new IllegalArgumentException("Local não pode ser nulo");
        }
        String sql = "INSERT INTO locais (nome, descricao, endereco, cidade, estado, cep, bairro, " +
                    "complemento, telefone, email, website, redes_sociais, tipo_local, capacidade, " +
                    "acessibilidade, recursos_acessibilidade, estacionamento, tipo_estacionamento, " +
                    "preco_estacionamento, wifi, ar_condicionado, bar, restaurante, horario_funcionamento, " +
                    "dias_funcionamento, coordenadas_gps, imagem_url, planta_local, regras, restricoes, " +
                    "ativo, organizador_id, status, fotos, video_url, mapa_url, observacoes, verificado, " +
                    "status_verificacao) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getDescricao());
            stmt.setString(3, local.getEndereco());
            stmt.setString(4, local.getCidade());
            stmt.setString(5, local.getEstado());
            stmt.setString(6, local.getCep());
            stmt.setString(7, local.getBairro());
            stmt.setString(8, local.getComplemento());
            stmt.setString(9, local.getTelefone());
            stmt.setString(10, local.getEmail());
            stmt.setString(11, local.getWebsite());
            stmt.setString(12, local.getRedesSociais());
            stmt.setString(13, local.getTipoLocal());
            stmt.setObject(14, local.getCapacidade());
            stmt.setBoolean(15, local.getAcessibilidade());
            stmt.setString(16, local.getRecursosAcessibilidade());
            stmt.setBoolean(17, local.getEstacionamento());
            stmt.setString(18, local.getTipoEstacionamento());
            stmt.setObject(19, local.getPrecoEstacionamento());
            stmt.setBoolean(20, local.getWifi());
            stmt.setBoolean(21, local.getArCondicionado());
            stmt.setBoolean(22, local.getBar());
            stmt.setBoolean(23, local.getRestaurante());
            stmt.setString(24, local.getHorarioFuncionamento());
            stmt.setString(25, local.getDiasFuncionamento());
            stmt.setString(26, local.getCoordenadasGPS());
            stmt.setString(27, local.getImagemUrl());
            stmt.setString(28, local.getPlantaLocal());
            stmt.setString(29, local.getRegras());
            stmt.setString(30, local.getRestricoes());
            stmt.setBoolean(31, local.getAtivo());
            stmt.setObject(32, local.getOrganizadorId());
            stmt.setString(33, local.getStatus());
            stmt.setString(34, String.join(",", local.getFotos()));
            stmt.setString(35, local.getVideoUrl());
            stmt.setString(36, local.getMapaUrl());
            stmt.setString(37, local.getObservacoes());
            stmt.setBoolean(38, local.getVerificado());
            stmt.setString(39, local.getStatusVerificacao());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    local.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar local", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM locais WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover local", e);
        }
    }
    
    @Override
    public void alterar(Local local) {
        if (local == null) {
            throw new IllegalArgumentException("Local não pode ser nulo");
        }
        if (local.getId() == null) {
            throw new IllegalArgumentException("ID do local é obrigatório para alteração");
        }
        String sql = "UPDATE locais SET nome = ?, descricao = ?, endereco = ?, cidade = ?, estado = ?, " +
                    "cep = ?, bairro = ?, complemento = ?, telefone = ?, email = ?, website = ?, " +
                    "redes_sociais = ?, tipo_local = ?, capacidade = ?, acessibilidade = ?, " +
                    "recursos_acessibilidade = ?, estacionamento = ?, tipo_estacionamento = ?, " +
                    "preco_estacionamento = ?, wifi = ?, ar_condicionado = ?, bar = ?, restaurante = ?, " +
                    "horario_funcionamento = ?, dias_funcionamento = ?, coordenadas_gps = ?, " +
                    "imagem_url = ?, planta_local = ?, regras = ?, restricoes = ?, ativo = ?, " +
                    "organizador_id = ?, status = ?, fotos = ?, video_url = ?, mapa_url = ?, " +
                    "observacoes = ?, verificado = ?, status_verificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getDescricao());
            stmt.setString(3, local.getEndereco());
            stmt.setString(4, local.getCidade());
            stmt.setString(5, local.getEstado());
            stmt.setString(6, local.getCep());
            stmt.setString(7, local.getBairro());
            stmt.setString(8, local.getComplemento());
            stmt.setString(9, local.getTelefone());
            stmt.setString(10, local.getEmail());
            stmt.setString(11, local.getWebsite());
            stmt.setString(12, local.getRedesSociais());
            stmt.setString(13, local.getTipoLocal());
            stmt.setObject(14, local.getCapacidade());
            stmt.setBoolean(15, local.getAcessibilidade());
            stmt.setString(16, local.getRecursosAcessibilidade());
            stmt.setBoolean(17, local.getEstacionamento());
            stmt.setString(18, local.getTipoEstacionamento());
            stmt.setObject(19, local.getPrecoEstacionamento());
            stmt.setBoolean(20, local.getWifi());
            stmt.setBoolean(21, local.getArCondicionado());
            stmt.setBoolean(22, local.getBar());
            stmt.setBoolean(23, local.getRestaurante());
            stmt.setString(24, local.getHorarioFuncionamento());
            stmt.setString(25, local.getDiasFuncionamento());
            stmt.setString(26, local.getCoordenadasGPS());
            stmt.setString(27, local.getImagemUrl());
            stmt.setString(28, local.getPlantaLocal());
            stmt.setString(29, local.getRegras());
            stmt.setString(30, local.getRestricoes());
            stmt.setBoolean(31, local.getAtivo());
            stmt.setObject(32, local.getOrganizadorId());
            stmt.setString(33, local.getStatus());
            stmt.setString(34, String.join(",", local.getFotos()));
            stmt.setString(35, local.getVideoUrl());
            stmt.setString(36, local.getMapaUrl());
            stmt.setString(37, local.getObservacoes());
            stmt.setBoolean(38, local.getVerificado());
            stmt.setString(39, local.getStatusVerificacao());
            stmt.setLong(40, local.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar local", e);
        }
    }
    
    @Override
    public List<Local> listar() {
        List<Local> locais = new ArrayList<>();
        String sql = "SELECT * FROM locais ORDER BY nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Local local = criarLocalDoResultSet(rs);
                locais.add(local);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar locais", e);
        }
        
        return locais;
    }
    
    @Override
    public Local buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM locais WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarLocalDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar local por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Local> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        return buscarPorCampo("nome", nome);
    }
    
    @Override
    public List<Local> buscarPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser nula ou vazia");
        }
        return buscarPorCampo("cidade", cidade);
    }
    
    @Override
    public List<Local> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser nulo ou vazio");
        }
        return buscarPorCampo("estado", estado);
    }
    
    @Override
    public List<Local> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_local", tipo);
    }
    
    @Override
    public List<Local> buscarPorCapacidade(Integer capacidadeMinima) {
        if (capacidadeMinima == null) {
            throw new IllegalArgumentException("Capacidade mínima não pode ser nula");
        }
        String sql = "SELECT * FROM locais WHERE capacidade >= ? ORDER BY capacidade";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capacidadeMinima);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Local> locais = new ArrayList<>();
                while (rs.next()) {
                    locais.add(criarLocalDoResultSet(rs));
                }
                return locais;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar locais por capacidade", e);
        }
    }
    
    @Override
    public List<Local> buscarPorEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
        }
        String sql = "SELECT * FROM locais WHERE endereco LIKE ? ORDER BY nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + endereco + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Local> locais = new ArrayList<>();
                while (rs.next()) {
                    locais.add(criarLocalDoResultSet(rs));
                }
                return locais;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar locais por endereço", e);
        }
    }
    
    @Override
    public List<Local> buscarPorCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser nulo ou vazio");
        }
        return buscarPorCampo("cep", cep);
    }
    
    private List<Local> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT * FROM locais WHERE " + campo + " = ? ORDER BY nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Local> locais = new ArrayList<>();
                while (rs.next()) {
                    locais.add(criarLocalDoResultSet(rs));
                }
                return locais;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar locais por " + campo, e);
        }
    }
    
    private Local criarLocalDoResultSet(ResultSet rs) throws SQLException {
        Local local = new Local();
        local.setId(rs.getLong("id"));
        local.setNome(rs.getString("nome"));
        local.setDescricao(rs.getString("descricao"));
        local.setEndereco(rs.getString("endereco"));
        local.setCidade(rs.getString("cidade"));
        local.setEstado(rs.getString("estado"));
        local.setCep(rs.getString("cep"));
        local.setBairro(rs.getString("bairro"));
        local.setComplemento(rs.getString("complemento"));
        local.setTelefone(rs.getString("telefone"));
        local.setEmail(rs.getString("email"));
        local.setWebsite(rs.getString("website"));
        local.setRedesSociais(rs.getString("redes_sociais"));
        local.setTipoLocal(rs.getString("tipo_local"));
        local.setCapacidade(rs.getInt("capacidade"));
        local.setAcessibilidade(rs.getBoolean("acessibilidade"));
        local.setRecursosAcessibilidade(rs.getString("recursos_acessibilidade"));
        local.setEstacionamento(rs.getBoolean("estacionamento"));
        local.setTipoEstacionamento(rs.getString("tipo_estacionamento"));
        local.setPrecoEstacionamento(rs.getBigDecimal("preco_estacionamento"));
        local.setWifi(rs.getBoolean("wifi"));
        local.setArCondicionado(rs.getBoolean("ar_condicionado"));
        local.setBar(rs.getBoolean("bar"));
        local.setRestaurante(rs.getBoolean("restaurante"));
        local.setHorarioFuncionamento(rs.getString("horario_funcionamento"));
        local.setDiasFuncionamento(rs.getString("dias_funcionamento"));
        local.setCoordenadasGPS(rs.getString("coordenadas_gps"));
        local.setImagemUrl(rs.getString("imagem_url"));
        local.setPlantaLocal(rs.getString("planta_local"));
        local.setRegras(rs.getString("regras"));
        local.setRestricoes(rs.getString("restricoes"));
        local.setAtivo(rs.getBoolean("ativo"));
        
        Long organizadorId = rs.getLong("organizador_id");
        if (!rs.wasNull()) {
            local.setOrganizadorId(organizadorId);
        }
        
        local.setStatus(rs.getString("status"));
        
        String fotosStr = rs.getString("fotos");
        if (fotosStr != null && !fotosStr.isEmpty()) {
            local.setFotos(List.of(fotosStr.split(",")));
        }
        
        local.setVideoUrl(rs.getString("video_url"));
        local.setMapaUrl(rs.getString("mapa_url"));
        local.setObservacoes(rs.getString("observacoes"));
        local.setVerificado(rs.getBoolean("verificado"));
        local.setStatusVerificacao(rs.getString("status_verificacao"));
        
        return local;
    }
}