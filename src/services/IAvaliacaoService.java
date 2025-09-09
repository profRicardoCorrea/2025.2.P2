package services;

import entities.Avaliacao;
import java.util.List;
import java.time.LocalDate;

public interface IAvaliacaoService {
    void criarAvaliacao(Avaliacao avaliacao);
    void atualizarAvaliacao(Avaliacao avaliacao);
    void removerAvaliacao(Long id);
    Avaliacao buscarAvaliacaoPorId(Long id);
    List<Avaliacao> listarTodasAvaliacoes();
    List<Avaliacao> buscarAvaliacoesPorEvento(Long eventoId);
    List<Avaliacao> buscarAvaliacoesPorUsuario(Long usuarioId);
    List<Avaliacao> buscarAvaliacoesPorNota(Integer nota);
    List<Avaliacao> buscarAvaliacoesPorNotaMinima(Integer notaMinima);
    List<Avaliacao> buscarAvaliacoesPorData(LocalDate data);
    List<Avaliacao> buscarAvaliacoesPorStatus(String status);
    List<Avaliacao> buscarAvaliacoesAprovadas();
    List<Avaliacao> buscarAvaliacoesPendentes();
    List<Avaliacao> buscarAvaliacoesRejeitadas();
    double calcularMediaAvaliacoesEvento(Long eventoId);
    double calcularMediaAvaliacoesUsuario(Long usuarioId);
    void aprovarAvaliacao(Long id);
    void rejeitarAvaliacao(Long id);
    void validarAvaliacao(Avaliacao avaliacao);
    boolean verificarSeUsuarioJaAvaliouEvento(Long usuarioId, Long eventoId);
}


