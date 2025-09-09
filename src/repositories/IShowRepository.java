package repositories;

import entities.Show;
import java.util.List;

public interface IShowRepository extends IRepository<Show> {
    List<Show> buscarPorArtista(String artista);
    List<Show> buscarPorGeneroMusical(String genero);
    List<Show> buscarPorTipoShow(String tipoShow);
    List<Show> buscarPorDuracao(Integer duracaoMinima);
    List<Show> buscarPorFaixaEtaria(String faixaEtaria);
    List<Show> buscarPorTipoIngresso(String tipoIngresso);
}

