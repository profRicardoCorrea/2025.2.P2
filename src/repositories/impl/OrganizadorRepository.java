package repositories.impl;

import entities.Organizador;
import repositories.IOrganizadorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizadorRepository implements IOrganizadorRepository {
    private List<Organizador> organizadores;
    private Long proximoId;

    public OrganizadorRepository() {
        this.organizadores = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Organizador entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        organizadores.add(entidade);
    }

    @Override
    public void remover(Long id) {
        organizadores.removeIf(organizador -> organizador.getId().equals(id));
    }

    @Override
    public void alterar(Organizador entidade) {
        for (int i = 0; i < organizadores.size(); i++) {
            if (organizadores.get(i).getId().equals(entidade.getId())) {
                organizadores.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Organizador> listar() {
        return new ArrayList<>(organizadores);
    }

    @Override
    public Organizador buscarPorId(Long id) {
        return organizadores.stream()
                .filter(organizador -> organizador.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Organizador> buscarPorNome(String nome) {
        return organizadores.stream()
                .filter(organizador -> nome.equals(organizador.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorEmail(String email) {
        return organizadores.stream()
                .filter(organizador -> email.equals(organizador.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorTelefone(String telefone) {
        return organizadores.stream()
                .filter(organizador -> telefone.equals(organizador.getTelefone()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorCidade(String cidade) {
        return organizadores.stream()
                .filter(organizador -> cidade.equals(organizador.getCidade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorEstado(String estado) {
        return organizadores.stream()
                .filter(organizador -> estado.equals(organizador.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorTipo(String tipo) {
        return organizadores.stream()
                .filter(organizador -> tipo.equals(organizador.getTipo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organizador> buscarPorStatus(String status) {
        return organizadores.stream()
                .filter(organizador -> status.equals(organizador.getStatus()))
                .collect(Collectors.toList());
    }
}

