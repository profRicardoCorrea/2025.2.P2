package repositories;

import entities.Usuario;
import java.util.List;

public interface IUsuarioRepository extends IRepository<Usuario> {
    Usuario buscarPorEmail(String email);
    Usuario buscarPorCpf(String cpf);
    List<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarPorCidade(String cidade);
    List<Usuario> buscarPorEstado(String estado);
    List<Usuario> buscarPorTipo(String tipo);
    List<Usuario> buscarPorStatus(String status);
    List<Usuario> buscarPorDataNascimento(java.time.LocalDate data);
}

