package repositories;

import entities.Exposicao;
import java.util.List;

public interface IExposicaoRepository extends IRepository<Exposicao> {
    List<Exposicao> buscarPorArtista(String artista);
    List<Exposicao> buscarPorTipoArte(String tipoArte);
    List<Exposicao> buscarPorTema(String tema);
    List<Exposicao> buscarPorCurador(String curador);
    List<Exposicao> buscarPorTipoExposicao(String tipoExposicao);
    List<Exposicao> buscarPorFaixaEtaria(String faixaEtaria);
}

