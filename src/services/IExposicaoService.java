package services;

import entities.Exposicao;
import java.util.List;

public interface IExposicaoService {
    void criarExposicao(Exposicao exposicao);
    void atualizarExposicao(Exposicao exposicao);
    void removerExposicao(Long id);
    Exposicao buscarExposicaoPorId(Long id);
    List<Exposicao> listarTodasExposicoes();
    List<Exposicao> buscarExposicoesPorArtista(String artista);
    List<Exposicao> buscarExposicoesPorTipoArte(String tipoArte);
    List<Exposicao> buscarExposicoesPorTema(String tema);
    List<Exposicao> buscarExposicoesPorCurador(String curador);
    List<Exposicao> buscarExposicoesPorTipo(String tipoExposicao);
    List<Exposicao> buscarExposicoesPorFaixaEtaria(String faixaEtaria);
    List<Exposicao> buscarExposicoesAtivas();
    void validarExposicao(Exposicao exposicao);
    void ativarExposicao(Long id);
    void desativarExposicao(Long id);
    boolean verificarDisponibilidadeExposicao(Long exposicaoId);
    void adicionarParticipante(Long exposicaoId, Long usuarioId);
    void removerParticipante(Long exposicaoId, Long usuarioId);
    int contarParticipantesExposicao(Long exposicaoId);
}


