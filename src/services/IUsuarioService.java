package services;

import entities.Usuario;
import java.util.List;
import java.time.LocalDate;

public interface IUsuarioService {
    void criarUsuario(Usuario usuario);
    void atualizarUsuario(Usuario usuario);
    void removerUsuario(Long id);
    Usuario buscarUsuarioPorId(Long id);
    List<Usuario> listarTodosUsuarios();
    List<Usuario> buscarUsuariosPorNome(String nome);
    List<Usuario> buscarUsuariosPorCidade(String cidade);
    List<Usuario> buscarUsuariosPorEstado(String estado);
    List<Usuario> buscarUsuariosPorTipo(String tipo);
    List<Usuario> buscarUsuariosPorStatus(String status);
    List<Usuario> buscarUsuariosPorDataNascimento(LocalDate data);
    List<Usuario> buscarUsuariosAtivos();
    Usuario buscarUsuarioPorEmail(String email);
    Usuario buscarUsuarioPorCpf(String cpf);
    boolean verificarSeEmailJaExiste(String email);
    boolean verificarSeCpfJaExiste(String cpf);
    void ativarUsuario(Long id);
    void desativarUsuario(Long id);
    void validarDadosUsuario(Usuario usuario);
    boolean autenticarUsuario(String email, String senha);
    void alterarSenha(Long usuarioId, String novaSenha);
    void resetarSenha(String email);
}


