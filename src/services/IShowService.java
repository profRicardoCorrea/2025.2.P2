package services;

import entities.Show;
import java.util.List;

public interface IShowService {
    void criarShow(Show show);
    void atualizarShow(Show show);
    void removerShow(Long id);
    Show buscarShowPorId(Long id);
    List<Show> listarTodosShows();
    List<Show> buscarShowsPorArtista(String artista);
    List<Show> buscarShowsPorGenero(String genero);
    List<Show> buscarShowsPorTipo(String tipoShow);
    List<Show> buscarShowsPorDuracao(Integer duracaoMinima);
    List<Show> buscarShowsPorFaixaEtaria(String faixaEtaria);
    List<Show> buscarShowsPorTipoIngresso(String tipoIngresso);
    List<Show> buscarShowsAtivos();
    List<Show> buscarShowsPorFaixaDuracao(Integer duracaoMinima, Integer duracaoMaxima);
    void validarShow(Show show);
    void ativarShow(Long id);
    void desativarShow(Long id);
    boolean verificarDisponibilidadeShow(Long showId);
    void adicionarParticipante(Long showId, Long usuarioId);
    void removerParticipante(Long showId, Long usuarioId);
    int contarParticipantesShow(Long showId);
}


