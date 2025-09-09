package repositories.impl;

import entities.Teatro;
import repositories.ITeatroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeatroRepository implements ITeatroRepository {
    private List<Teatro> teatros;
    private Long proximoId;

    public TeatroRepository() {
        this.teatros = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Teatro entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        teatros.add(entidade);
    }

    @Override
    public void remover(Long id) {
        teatros.removeIf(teatro -> teatro.getId().equals(id));
    }

    @Override
    public void alterar(Teatro entidade) {
        for (int i = 0; i < teatros.size(); i++) {
            if (teatros.get(i).getId().equals(entidade.getId())) {
                teatros.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Teatro> listar() {
        return new ArrayList<>(teatros);
    }

    @Override
    public Teatro buscarPorId(Long id) {
        return teatros.stream()
                .filter(teatro -> teatro.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Teatro> buscarPorGenero(String genero) {
        return teatros.stream()
                .filter(teatro -> genero.equals(teatro.getGenero()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teatro> buscarPorDiretor(String diretor) {
        return teatros.stream()
                .filter(teatro -> diretor.equals(teatro.getDiretor()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teatro> buscarPorElenco(String ator) {
        return teatros.stream()
                .filter(teatro -> ator.equals(teatro.getElenco()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teatro> buscarPorDuracao(Integer duracaoMinima) {
        return teatros.stream()
                .filter(teatro -> teatro.getDuracao() >= duracaoMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Teatro> buscarPorTipoTeatro(String tipoTeatro) {
        return teatros.stream()
                .filter(teatro -> tipoTeatro.equals(teatro.getTipoTeatro()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teatro> buscarPorClassificacaoIndicativa(String classificacao) {
        return teatros.stream()
                .filter(teatro -> classificacao.equals(teatro.getClassificacaoIndicativa()))
                .collect(Collectors.toList());
    }
}


