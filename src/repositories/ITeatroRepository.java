package repositories;

import entities.Teatro;
import java.util.List;

public interface ITeatroRepository extends IRepository<Teatro> {
    List<Teatro> buscarPorGenero(String genero);
    List<Teatro> buscarPorDiretor(String diretor);
    List<Teatro> buscarPorElenco(String ator);
    List<Teatro> buscarPorDuracao(Integer duracaoMinima);
    List<Teatro> buscarPorTipoTeatro(String tipoTeatro);
    List<Teatro> buscarPorClassificacaoIndicativa(String classificacao);
}

