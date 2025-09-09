package repositories.impl;

import entities.Local;
import repositories.ILocalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocalRepository implements ILocalRepository {
    private List<Local> locais;
    private Long proximoId;

    public LocalRepository() {
        this.locais = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Local entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        locais.add(entidade);
    }

    @Override
    public void remover(Long id) {
        locais.removeIf(local -> local.getId().equals(id));
    }

    @Override
    public void alterar(Local entidade) {
        for (int i = 0; i < locais.size(); i++) {
            if (locais.get(i).getId().equals(entidade.getId())) {
                locais.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Local> listar() {
        return new ArrayList<>(locais);
    }

    @Override
    public Local buscarPorId(Long id) {
        return locais.stream()
                .filter(local -> local.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Local> buscarPorNome(String nome) {
        return locais.stream()
                .filter(local -> nome.equals(local.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorCidade(String cidade) {
        return locais.stream()
                .filter(local -> cidade.equals(local.getCidade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorEstado(String estado) {
        return locais.stream()
                .filter(local -> estado.equals(local.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorTipo(String tipo) {
        return locais.stream()
                .filter(local -> tipo.equals(local.getTipo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorCapacidade(Integer capacidadeMinima) {
        return locais.stream()
                .filter(local -> local.getCapacidade() >= capacidadeMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorEndereco(String endereco) {
        return locais.stream()
                .filter(local -> endereco.equals(local.getEndereco()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> buscarPorCep(String cep) {
        return locais.stream()
                .filter(local -> cep.equals(local.getCep()))
                .collect(Collectors.toList());
    }
}

