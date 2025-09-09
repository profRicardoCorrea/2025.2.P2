package repositories;

import entities.Avaliacao;
import java.util.List;

public interface IAvaliacaoRepository extends IRepository<Avaliacao> {
    List<Avaliacao> buscarPorEvento(Long eventoId);
    List<Avaliacao> buscarPorUsuario(Long usuarioId);
    List<Avaliacao> buscarPorNota(Integer nota);
    List<Avaliacao> buscarPorNotaMinima(Integer notaMinima);
    List<Avaliacao> buscarPorData(java.time.LocalDate data);
    List<Avaliacao> buscarPorStatus(String status);
}

