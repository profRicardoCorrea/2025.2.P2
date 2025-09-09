package services;

import entities.Danca;
import java.util.List;

public interface IDancaService {
    void criarDanca(Danca danca);
    void atualizarDanca(Danca danca);
    void removerDanca(Long id);
    Danca buscarDancaPorId(Long id);
    List<Danca> listarTodasDancas();
    List<Danca> buscarDancasPorEstilo(String estilo);
    List<Danca> buscarDancasPorNivel(String nivel);
    List<Danca> buscarDancasPorInstrutor(String instrutor);
    List<Danca> buscarDancasPorDuracao(Integer duracaoMinima);
    List<Danca> buscarDancasPorTipo(String tipoDanca);
    List<Danca> buscarDancasPorMusica(String generoMusical);
    List<Danca> buscarDancasAtivas();
    List<Danca> buscarDancasPorFaixaDuracao(Integer duracaoMinima, Integer duracaoMaxima);
    void validarDanca(Danca danca);
    void ativarDanca(Long id);
    void desativarDanca(Long id);
    boolean verificarDisponibilidadeDanca(Long dancaId);
    void adicionarParticipante(Long dancaId, Long usuarioId);
    void removerParticipante(Long dancaId, Long usuarioId);
    int contarParticipantesDanca(Long dancaId);
}


