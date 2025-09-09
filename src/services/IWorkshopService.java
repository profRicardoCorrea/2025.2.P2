package services;

import entities.Workshop;
import java.util.List;

public interface IWorkshopService {
    void criarWorkshop(Workshop workshop);
    void atualizarWorkshop(Workshop workshop);
    void removerWorkshop(Long id);
    Workshop buscarWorkshopPorId(Long id);
    List<Workshop> listarTodosWorkshops();
    List<Workshop> buscarWorkshopsPorInstrutor(String instrutor);
    List<Workshop> buscarWorkshopsPorNivel(String nivel);
    List<Workshop> buscarWorkshopsPorDuracao(Integer duracaoMinima);
    List<Workshop> buscarWorkshopsPorMaterialIncluso(Boolean materialIncluso);
    List<Workshop> buscarWorkshopsPorCertificado(Boolean temCertificado);
    List<Workshop> buscarWorkshopsPorTipo(String tipoWorkshop);
    List<Workshop> buscarWorkshopsAtivos();
    List<Workshop> buscarWorkshopsPorFaixaDuracao(Integer duracaoMinima, Integer duracaoMaxima);
    void validarWorkshop(Workshop workshop);
    void ativarWorkshop(Long id);
    void desativarWorkshop(Long id);
    boolean verificarDisponibilidadeWorkshop(Long workshopId);
    void adicionarParticipante(Long workshopId, Long usuarioId);
    void removerParticipante(Long workshopId, Long usuarioId);
    int contarParticipantesWorkshop(Long workshopId);
}


