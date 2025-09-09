package repositories.impl;

import entities.Usuario;
import repositories.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class UsuarioRepository implements IUsuarioRepository {
    private List<Usuario> usuarios;
    private Long proximoId;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Usuario entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        usuarios.add(entidade);
    }

    @Override
    public void remover(Long id) {
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

    @Override
    public void alterar(Usuario entidade) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(entidade.getId())) {
                usuarios.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(usuario -> email.equals(usuario.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorCpf(String cpf) {
        return usuarios.stream()
                .filter(usuario -> cpf.equals(usuario.getCpf()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return usuarios.stream()
                .filter(usuario -> nome.equals(usuario.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorCidade(String cidade) {
        return usuarios.stream()
                .filter(usuario -> cidade.equals(usuario.getCidade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorEstado(String estado) {
        return usuarios.stream()
                .filter(usuario -> estado.equals(usuario.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorTipo(String tipo) {
        return usuarios.stream()
                .filter(usuario -> tipo.equals(usuario.getTipo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorStatus(String status) {
        return usuarios.stream()
                .filter(usuario -> status.equals(usuario.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorDataNascimento(LocalDate data) {
        return usuarios.stream()
                .filter(usuario -> usuario.getDataNascimento() != null && 
                                 usuario.getDataNascimento().equals(data))
                .collect(Collectors.toList());
    }
}


