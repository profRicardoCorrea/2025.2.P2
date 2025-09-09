package repositories.impl;

import entities.Workshop;
import repositories.IWorkshopRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkshopRepository implements IWorkshopRepository {
    private List<Workshop> workshops;
    private Long proximoId;

    public WorkshopRepository() {
        this.workshops = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Workshop entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        workshops.add(entidade);
    }

    @Override
    public void remover(Long id) {
        workshops.removeIf(workshop -> workshop.getId().equals(id));
    }

    @Override
    public void alterar(Workshop entidade) {
        for (int i = 0; i < workshops.size(); i++) {
            if (workshops.get(i).getId().equals(entidade.getId())) {
                workshops.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Workshop> listar() {
        return new ArrayList<>(workshops);
    }

    @Override
    public Workshop buscarPorId(Long id) {
        return workshops.stream()
                .filter(workshop -> workshop.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Workshop> buscarPorInstrutor(String instrutor) {
        return workshops.stream()
                .filter(workshop -> instrutor.equals(workshop.getInstrutor()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workshop> buscarPorNivel(String nivel) {
        return workshops.stream()
                .filter(workshop -> nivel.equals(workshop.getNivel()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workshop> buscarPorDuracao(Integer duracaoMinima) {
        return workshops.stream()
                .filter(workshop -> workshop.getDuracao() >= duracaoMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workshop> buscarPorMaterialIncluso(Boolean materialIncluso) {
        return workshops.stream()
                .filter(workshop -> materialIncluso.equals(workshop.getMaterialIncluso()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workshop> buscarPorCertificado(Boolean temCertificado) {
        return workshops.stream()
                .filter(workshop -> temCertificado.equals(workshop.getTemCertificado()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workshop> buscarPorTipoWorkshop(String tipoWorkshop) {
        return workshops.stream()
                .filter(workshop -> tipoWorkshop.equals(workshop.getTipoWorkshop()))
                .collect(Collectors.toList());
    }
}


