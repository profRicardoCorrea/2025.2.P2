package repositories.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import config.DatabaseConfig;
import entities.Usuario;
import repositories.IUsuarioRepository;

public class UsuarioRepository implements IUsuarioRepository {
    
    @Override
    public void salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        String sql = "INSERT INTO usuarios (nome, email, senha, cpf, telefone, endereco, cidade, estado, cep, " +
                    "data_nascimento, genero, tipo_usuario, ativo, data_cadastro, ultimo_acesso, " +
                    "preferencias_culturais, recebe_notificacoes, foto_perfil) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getEndereco());
            stmt.setString(7, usuario.getCidade());
            stmt.setString(8, usuario.getEstado());
            stmt.setString(9, usuario.getCep());
            
            if (usuario.getDataNascimento() != null) {
                stmt.setTimestamp(10, Timestamp.valueOf(usuario.getDataNascimento().atStartOfDay()));
            } else {
                stmt.setNull(10, Types.TIMESTAMP);
            }
            
            stmt.setString(11, usuario.getGenero());
            stmt.setString(12, usuario.getTipoUsuario());
            stmt.setBoolean(13, usuario.getAtivo());
            
            Timestamp dataCadastro = usuario.getDataCadastro() != null ? 
                Timestamp.valueOf(usuario.getDataCadastro()) : Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(14, dataCadastro);
            
            Timestamp ultimoAcesso = usuario.getUltimoAcesso() != null ? 
                Timestamp.valueOf(usuario.getUltimoAcesso()) : Timestamp.valueOf(LocalDateTime.now());
            stmt.setTimestamp(15, ultimoAcesso);
            
            stmt.setString(16, usuario.getPreferenciasCulturais());
            stmt.setBoolean(17, usuario.getRecebeNotificacoes());
            stmt.setString(18, usuario.getFotoPerfil());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }
    
    @Override
    public void remover(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }
    
    @Override
    public void alterar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório para alteração");
        }
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, cpf = ?, telefone = ?, " +
                    "endereco = ?, cidade = ?, estado = ?, cep = ?, data_nascimento = ?, genero = ?, " +
                    "tipo_usuario = ?, ativo = ?, data_cadastro = ?, ultimo_acesso = ?, " +
                    "preferencias_culturais = ?, recebe_notificacoes = ?, foto_perfil = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getEndereco());
            stmt.setString(7, usuario.getCidade());
            stmt.setString(8, usuario.getEstado());
            stmt.setString(9, usuario.getCep());
            
            if (usuario.getDataNascimento() != null) {
                stmt.setTimestamp(10, Timestamp.valueOf(usuario.getDataNascimento().atStartOfDay()));
            } else {
                stmt.setNull(10, Types.TIMESTAMP);
            }
            
            stmt.setString(11, usuario.getGenero());
            stmt.setString(12, usuario.getTipoUsuario());
            stmt.setBoolean(13, usuario.getAtivo());
            
            if (usuario.getDataCadastro() != null) {
                stmt.setTimestamp(14, Timestamp.valueOf(usuario.getDataCadastro()));
            } else {
                stmt.setNull(14, Types.TIMESTAMP);
            }
            
            if (usuario.getUltimoAcesso() != null) {
                stmt.setTimestamp(15, Timestamp.valueOf(usuario.getUltimoAcesso()));
            } else {
                stmt.setNull(15, Types.TIMESTAMP);
            }
            
            stmt.setString(16, usuario.getPreferenciasCulturais());
            stmt.setBoolean(17, usuario.getRecebeNotificacoes());
            stmt.setString(18, usuario.getFotoPerfil());
            stmt.setLong(19, usuario.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar usuário", e);
        }
    }
    
    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = criarUsuarioDoResultSet(rs);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        
        return usuarios;
    }
    
    @Override
    public Usuario buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        
        return null;
    }
    
    @Override
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }
        
        return null;
    }
    
    @Override
    public Usuario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por CPF", e);
        }
        
        return null;
    }
    
    @Override
    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        return buscarPorCampo("nome", nome);
    }
    
    @Override
    public List<Usuario> buscarPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser nula ou vazia");
        }
        return buscarPorCampo("cidade", cidade);
    }
    
    @Override
    public List<Usuario> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser nulo ou vazio");
        }
        return buscarPorCampo("estado", estado);
    }
    
    @Override
    public List<Usuario> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
        return buscarPorCampo("tipo_usuario", tipo);
    }
    
    @Override
    public List<Usuario> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser nulo ou vazio");
        }
        String sql = "SELECT * FROM usuarios WHERE ativo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            boolean ativo = "ATIVO".equalsIgnoreCase(status) || "true".equalsIgnoreCase(status);
            stmt.setBoolean(1, ativo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Usuario> usuarios = new ArrayList<>();
                while (rs.next()) {
                    usuarios.add(criarUsuarioDoResultSet(rs));
                }
                return usuarios;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por status", e);
        }
    }
    
    @Override
    public List<Usuario> buscarPorDataNascimento(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        String sql = "SELECT * FROM usuarios WHERE DATE(data_nascimento) = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(data));
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Usuario> usuarios = new ArrayList<>();
                while (rs.next()) {
                    usuarios.add(criarUsuarioDoResultSet(rs));
                }
                return usuarios;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por data de nascimento", e);
        }
    }
    
    private List<Usuario> buscarPorCampo(String campo, String valor) {
        String sql = "SELECT * FROM usuarios WHERE " + campo + " = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, valor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Usuario> usuarios = new ArrayList<>();
                while (rs.next()) {
                    usuarios.add(criarUsuarioDoResultSet(rs));
                }
                return usuarios;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por " + campo, e);
        }
    }
    
    private Usuario criarUsuarioDoResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setTelefone(rs.getString("telefone"));
        usuario.setEndereco(rs.getString("endereco"));
        usuario.setCidade(rs.getString("cidade"));
        usuario.setEstado(rs.getString("estado"));
        usuario.setCep(rs.getString("cep"));
        
        Timestamp dataNascimento = rs.getTimestamp("data_nascimento");
        if (dataNascimento != null) {
            usuario.setDataNascimento(dataNascimento.toLocalDateTime().toLocalDate());
        }
        
        usuario.setGenero(rs.getString("genero"));
        usuario.setTipoUsuario(rs.getString("tipo_usuario"));
        usuario.setAtivo(rs.getBoolean("ativo"));
        
        Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
        if (dataCadastro != null) {
            usuario.setDataCadastro(dataCadastro.toLocalDateTime());
        }
        
        Timestamp ultimoAcesso = rs.getTimestamp("ultimo_acesso");
        if (ultimoAcesso != null) {
            usuario.setUltimoAcesso(ultimoAcesso.toLocalDateTime());
        }
        
        usuario.setPreferenciasCulturais(rs.getString("preferencias_culturais"));
        usuario.setRecebeNotificacoes(rs.getBoolean("recebe_notificacoes"));
        usuario.setFotoPerfil(rs.getString("foto_perfil"));
        
        return usuario;
    }
}


