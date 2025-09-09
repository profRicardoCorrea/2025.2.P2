package repositories.impl;

import entities.Danca;
import repositories.IDancaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DancaRepository implements IDancaRepository {
    private List<Danca> dancas;
    private Long proximoId;

    public DancaRepository() {
        this.dancas = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Danca entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        dancas.add(entidade);
    }

    @Override
    public void remover(Long id) {
        dancas.removeIf(danca -> danca.getId().equals(id));
    }

    @Override
    public void alterar(Danca entidade) {
        for (int i = 0; i < dancas.size(); i++) {
            if (dancas.get(i).getId().equals(entidade.getId())) {
                dancas.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Danca> listar() {
        return new ArrayList<>(dancas);
    }

    @Override
    public Danca buscarPorId(Long id) {
        return dancas.stream()
                .filter(danca -> danca.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Danca> buscarPorEstilo(String estilo) {
        return dancas.stream()
                .filter(danca -> estilo.equals(danca.getEstilo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Danca> buscarPorNivel(String nivel) {
        return dancas.stream()
                .filter(danca -> nivel.equals(danca.getNivel()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Danca> buscarPorInstrutor(String instrutor) {
        return dancas.stream()
                .filter(danca -> instrutor.equals(danca.getInstrutor()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Danca> buscarPorDuracao(Integer duracaoMinima) {
        return dancas.stream()
                .filter(danca -> danca.getDuracao() >= duracaoMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Danca> buscarPorTipoDanca(String tipoDanca) {
        return dancas.stream()
                .filter(danca -> tipoDanca.equals(danca.getTipoDanca()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Danca> buscarPorMusica(String generoMusical) {
        return dancas.stream()
                .filter(danca -> generoMusical.equals(danca.getGeneroMusical()))
                .collect(Collectors.toList());
    }
}


