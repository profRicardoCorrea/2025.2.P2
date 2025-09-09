package repositories.impl;

import entities.Exposicao;
import repositories.IExposicaoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExposicaoRepository implements IExposicaoRepository {
    private List<Exposicao> exposicoes;
    private Long proximoId;

    public ExposicaoRepository() {
        this.exposicoes = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Exposicao entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        exposicoes.add(entidade);
    }

    @Override
    public void remover(Long id) {
        exposicoes.removeIf(exposicao -> exposicao.getId().equals(id));
    }

    @Override
    public void alterar(Exposicao entidade) {
        for (int i = 0; i < exposicoes.size(); i++) {
            if (exposicoes.get(i).getId().equals(entidade.getId())) {
                exposicoes.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Exposicao> listar() {
        return new ArrayList<>(exposicoes);
    }

    @Override
    public Exposicao buscarPorId(Long id) {
        return exposicoes.stream()
                .filter(exposicao -> exposicao.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Exposicao> buscarPorArtista(String artista) {
        return exposicoes.stream()
                .filter(exposicao -> artista.equals(exposicao.getArtista()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exposicao> buscarPorTipoArte(String tipoArte) {
        return exposicoes.stream()
                .filter(exposicao -> tipoArte.equals(exposicao.getTipoArte()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exposicao> buscarPorTema(String tema) {
        return exposicoes.stream()
                .filter(exposicao -> tema.equals(exposicao.getTema()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exposicao> buscarPorCurador(String curador) {
        return exposicoes.stream()
                .filter(exposicao -> curador.equals(exposicao.getCurador()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exposicao> buscarPorTipoExposicao(String tipoExposicao) {
        return exposicoes.stream()
                .filter(exposicao -> tipoExposicao.equals(exposicao.getTipoExposicao()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exposicao> buscarPorFaixaEtaria(String faixaEtaria) {
        return exposicoes.stream()
                .filter(exposicao -> faixaEtaria.equals(exposicao.getFaixaEtaria()))
                .collect(Collectors.toList());
    }
}


