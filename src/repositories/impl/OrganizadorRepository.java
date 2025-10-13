package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import config.DatabaseConfig;
import entities.Organizador;
import repositories.IOrganizadorRepository;

public class OrganizadorRepository implements IOrganizadorRepository {
    
    @Override
    public void salvar(Organizador organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não pode ser nulo");
        }
        String sql = "INSERT INTO organizadores (nome, razao_social, cnpj, email, telefone, endereco, " +
                    "cidade, estado, cep, website, redes_sociais, descricao, categoria, tipo_organizacao, " +
                    "ativo, logo_url, certificacoes, experiencia, anos_experiencia, especialidades, " +
                    "contato_responsavel, telefone_responsavel, email_responsavel, verificado, " +
                    "status_verificacao, observacoes_verificacao) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, organizador.getNome());
            stmt.setString(2, organizador.getRazaoSocial());
            stmt.setString(3, organizador.getCnpj());
            stmt.setString(4, organizador.getEmail());
            stmt.setString(5, organizador.getTelefone());
            stmt.setString(6, organizador.getEndereco());
            stmt.setString(7, organizador.getCidade());
            stmt.setString(8, organizador.getEstado());
            stmt.setString(9, organizador.getCep());
            stmt.setString(10, organizador.getWebsite());
            stmt.setString(11, organizador.getRedesSociais());
            stmt.setString(12, organizador.getDescricao());
            stmt.setString(13, organizador.getCategoria());
            stmt.setString(14, organizador.getTipoOrganizacao());
            stmt.setBoolean(15, organizador.getAtivo());
            stmt.setString(16, organizador.getLogoUrl());
            stmt.setString(17, organizador.getCertificacoes());
            stmt.setString(18, organizador.getExperiencia());
            stmt.setObject(19, organizador.getAnosExperiencia());
            stmt.setString(20, String.join(",", organizador.getEspecialidades()));
            stmt.setString(21, organizador.getContatoResponsavel());
            stmt.setString(22, organizador.getTelefoneResponsavel());
            stmt.setString(23, organizador.getEmailResponsavel());
            stmt.setBoolean(24, organizador.getVerificado());
            stmt.setString(25, organizador.getStatusVerificacao());
            stmt.setString(26, organizador.getObservacoesVerificacao());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    organizador.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar organizador", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM organizadores WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover organizador", e);
        }
    }
    
    @Override
    public void alterar(Organizador organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não pode ser nulo");
        }
        if (organizador.getId() == null) {
            throw new IllegalArgumentException("ID do organizador é obrigatório para alteração");
        }
        String sql = "UPDATE organizadores SET nome = ?, razao_social = ?, cnpj = ?, email = ?, " +
                    "telefone = ?, endereco = ?, cidade = ?, estado = ?, cep = ?, website = ?, " +
                    "redes_sociais = ?, descricao = ?, categoria = ?, tipo_organizacao = ?, ativo = ?, " +
                    "logo_url = ?, certificacoes = ?, experiencia = ?, anos_experiencia = ?, " +
                    "especialidades = ?, contato_responsavel = ?, telefone_responsavel = ?, " +
                    "email_responsavel = ?, verificado = ?, status_verificacao = ?, " +
                    "observacoes_verificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, organizador.getNome());
            stmt.setString(2, organizador.getRazaoSocial());
            stmt.setString(3, organizador.getCnpj());
            stmt.setString(4, organizador.getEmail());
            stmt.setString(5, organizador.getTelefone());
            stmt.setString(6, organizador.getEndereco());
            stmt.setString(7, organizador.getCidade());
            stmt.setString(8, organizador.getEstado());
            stmt.setString(9, organizador.getCep());
            stmt.setString(10, organizador.getWebsite());
            stmt.setString(11, organizador.getRedesSociais());
            stmt.setString(12, organizador.getDescricao());
            stmt.setString(13, organizador.getCategoria());
            stmt.setString(14, organizador.getTipoOrganizacao());
            stmt.setBoolean(15, organizador.getAtivo());
            stmt.setString(16, organizador.getLogoUrl());
            stmt.setString(17, organizador.getCertificacoes());
            stmt.setString(18, organizador.getExperiencia());
            stmt.setObject(19, organizador.getAnosExperiencia());
            stmt.setString(20, String.join(",", organizador.getEspecialidades()));
            stmt.setString(21, organizador.getContatoResponsavel());
            stmt.setString(22, organizador.getTelefoneResponsavel());
            stmt.setString(23, organizador.getEmailResponsavel());
            stmt.setBoolean(24, organizador.getVerificado());
            stmt.setString(25, organizador.getStatusVerificacao());
            stmt.setString(26, organizador.getObservacoesVerificacao());
            stmt.setLong(27, organizador.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar organizador", e);
        }
    }
    
    @Override
    public List<Organizador> listar() {
        List<Organizador> organizadores = new ArrayList<>();
        String sql = "SELECT * FROM organizadores ORDER BY nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Organizador organizador = criarOrganizadorDoResultSet(rs);
                organizadores.add(organizador);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar organizadores", e);
        }
        
        return organizadores;
    }
    
    @Override
    public Organizador buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM organizadores WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarOrganizadorDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar organizador por ID", e);
        }
        
        return null;
    }
    
    @Override
    public List<Organizador> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        return buscarPorCampo("nome", nome);
    }
    
    @Override
    public List<Organizador> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        return buscarPorCampo("email", email);
    }
    
    @Override
    public List<Organizador> buscarPorTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
        }
        return buscarPorCampo("telefone", telefone);
    }
    
    @Override
    public List<Organizador> buscarPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser nula ou vazia");
        }
        return buscarPorCampo("cidade", cidade);
    }
    
    @Override
    public List<Organizador> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser nulo ou vazio");
        }
        return buscarPorCampo("estado", estado);
    }
    
    @Override
    public List<Organizador> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_organizacao", tipo);
    }
    
    @Override
    public List<Organizador> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        return buscarPorCampo("status_verificacao", status);
    }
    
    private List<Organizador> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT * FROM organizadores WHERE " + campo + " = ? ORDER BY nome";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Organizador> organizadores = new ArrayList<>();
                while (rs.next()) {
                    organizadores.add(criarOrganizadorDoResultSet(rs));
                }
                return organizadores;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar organizadores por " + campo, e);
        }
    }
    
    private Organizador criarOrganizadorDoResultSet(ResultSet rs) throws SQLException {
        Organizador organizador = new Organizador();
        organizador.setId(rs.getLong("id"));
        organizador.setNome(rs.getString("nome"));
        organizador.setRazaoSocial(rs.getString("razao_social"));
        organizador.setCnpj(rs.getString("cnpj"));
        organizador.setEmail(rs.getString("email"));
        organizador.setTelefone(rs.getString("telefone"));
        organizador.setEndereco(rs.getString("endereco"));
        organizador.setCidade(rs.getString("cidade"));
        organizador.setEstado(rs.getString("estado"));
        organizador.setCep(rs.getString("cep"));
        organizador.setWebsite(rs.getString("website"));
        organizador.setRedesSociais(rs.getString("redes_sociais"));
        organizador.setDescricao(rs.getString("descricao"));
        organizador.setCategoria(rs.getString("categoria"));
        organizador.setTipoOrganizacao(rs.getString("tipo_organizacao"));
        organizador.setAtivo(rs.getBoolean("ativo"));
        organizador.setLogoUrl(rs.getString("logo_url"));
        organizador.setCertificacoes(rs.getString("certificacoes"));
        organizador.setExperiencia(rs.getString("experiencia"));
        organizador.setAnosExperiencia(rs.getInt("anos_experiencia"));
        
        String especialidadesStr = rs.getString("especialidades");
        if (especialidadesStr != null && !especialidadesStr.isEmpty()) {
            organizador.setEspecialidades(List.of(especialidadesStr.split(",")));
        }
        
        organizador.setContatoResponsavel(rs.getString("contato_responsavel"));
        organizador.setTelefoneResponsavel(rs.getString("telefone_responsavel"));
        organizador.setEmailResponsavel(rs.getString("email_responsavel"));
        organizador.setVerificado(rs.getBoolean("verificado"));
        organizador.setStatusVerificacao(rs.getString("status_verificacao"));
        organizador.setObservacoesVerificacao(rs.getString("observacoes_verificacao"));
        
        return organizador;
    }
}