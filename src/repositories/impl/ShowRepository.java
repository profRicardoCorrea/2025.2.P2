package repositories.impl;

import entities.Show;
import repositories.IShowRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowRepository implements IShowRepository {
    private List<Show> shows;
    private Long proximoId;

    public ShowRepository() {
        this.shows = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Show entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        shows.add(entidade);
    }

    @Override
    public void remover(Long id) {
        shows.removeIf(show -> show.getId().equals(id));
    }

    @Override
    public void alterar(Show entidade) {
        for (int i = 0; i < shows.size(); i++) {
            if (shows.get(i).getId().equals(entidade.getId())) {
                shows.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Show> listar() {
        return new ArrayList<>(shows);
    }

    @Override
    public Show buscarPorId(Long id) {
        return shows.stream()
                .filter(show -> show.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Show> buscarPorArtista(String artista) {
        return shows.stream()
                .filter(show -> artista.equals(show.getArtista()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> buscarPorGeneroMusical(String genero) {
        return shows.stream()
                .filter(show -> genero.equals(show.getGeneroMusical()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> buscarPorTipoShow(String tipoShow) {
        return shows.stream()
                .filter(show -> tipoShow.equals(show.getTipoShow()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> buscarPorDuracao(Integer duracaoMinima) {
        return shows.stream()
                .filter(show -> show.getDuracao() >= duracaoMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> buscarPorFaixaEtaria(String faixaEtaria) {
        return shows.stream()
                .filter(show -> faixaEtaria.equals(show.getFaixaEtaria()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> buscarPorTipoIngresso(String tipoIngresso) {
        return shows.stream()
                .filter(show -> tipoIngresso.equals(show.getTipoIngresso()))
                .collect(Collectors.toList());
    }
}


