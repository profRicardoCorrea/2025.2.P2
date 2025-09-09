package services;

import entities.Teatro;
import java.util.List;

public interface ITeatroService {
    void criarTeatro(Teatro teatro);
    void atualizarTeatro(Teatro teatro);
    void removerTeatro(Long id);
    Teatro buscarTeatroPorId(Long id);
    List<Teatro> listarTodosTeatros();
    List<Teatro> buscarTeatrosPorGenero(String genero);
    List<Teatro> buscarTeatrosPorDiretor(String diretor);
    List<Teatro> buscarTeatrosPorElenco(String ator);
    List<Teatro> buscarTeatrosPorDuracao(Integer duracaoMinima);
    List<Teatro> buscarTeatrosPorTipo(String tipoTeatro);
    List<Teatro> buscarTeatrosPorClassificacao(String classificacao);
    List<Teatro> buscarTeatrosAtivos();
    List<Teatro> buscarTeatrosPorFaixaDuracao(Integer duracaoMinima, Integer duracaoMaxima);
    void validarTeatro(Teatro teatro);
    void ativarTeatro(Long id);
    void desativarTeatro(Long id);
    boolean verificarDisponibilidadeTeatro(Long teatroId);
    void adicionarParticipante(Long teatroId, Long usuarioId);
    void removerParticipante(Long teatroId, Long usuarioId);
    int contarParticipantesTeatro(Long teatroId);
}


