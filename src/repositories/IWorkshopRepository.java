package repositories;

import entities.Workshop;
import java.util.List;

public interface IWorkshopRepository extends IRepository<Workshop> {
    List<Workshop> buscarPorInstrutor(String instrutor);
    List<Workshop> buscarPorNivel(String nivel);
    List<Workshop> buscarPorDuracao(Integer duracaoMinima);
    List<Workshop> buscarPorMaterialIncluso(Boolean materialIncluso);
    List<Workshop> buscarPorCertificado(Boolean temCertificado);
    List<Workshop> buscarPorTipoWorkshop(String tipoWorkshop);
}

