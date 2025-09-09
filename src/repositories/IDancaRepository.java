package repositories;

import entities.Danca;
import java.util.List;

public interface IDancaRepository extends IRepository<Danca> {
    List<Danca> buscarPorEstilo(String estilo);
    List<Danca> buscarPorNivel(String nivel);
    List<Danca> buscarPorInstrutor(String instrutor);
    List<Danca> buscarPorDuracao(Integer duracaoMinima);
    List<Danca> buscarPorTipoDanca(String tipoDanca);
    List<Danca> buscarPorMusica(String generoMusical);
}

