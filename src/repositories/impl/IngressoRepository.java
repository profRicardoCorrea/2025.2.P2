package repositories.impl;

import entities.Ingresso;
import repositories.IIngressoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.math.BigDecimal;

public class IngressoRepository implements IIngressoRepository {
    private List<Ingresso> ingressos;
    private Long proximoId;

    public IngressoRepository() {
        this.ingressos = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Ingresso entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        ingressos.add(entidade);
    }

    @Override
    public void remover(Long id) {
        ingressos.removeIf(ingresso -> ingresso.getId().equals(id));
    }

    @Override
    public void alterar(Ingresso entidade) {
        for (int i = 0; i < ingressos.size(); i++) {
            if (ingressos.get(i).getId().equals(entidade.getId())) {
                ingressos.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Ingresso> listar() {
        return new ArrayList<>(ingressos);
    }

    @Override
    public Ingresso buscarPorId(Long id) {
        return ingressos.stream()
                .filter(ingresso -> ingresso.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ingresso> buscarPorEvento(Long eventoId) {
        return ingressos.stream()
                .filter(ingresso -> eventoId.equals(ingresso.getEventoId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorUsuario(Long usuarioId) {
        return ingressos.stream()
                .filter(ingresso -> usuarioId.equals(ingresso.getUsuarioId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorStatus(String status) {
        return ingressos.stream()
                .filter(ingresso -> status.equals(ingresso.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorTipo(String tipo) {
        return ingressos.stream()
                .filter(ingresso -> tipo.equals(ingresso.getTipo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorDataCompra(LocalDate data) {
        return ingressos.stream()
                .filter(ingresso -> ingresso.getDataCompra() != null && 
                                  ingresso.getDataCompra().equals(data))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorDataEvento(LocalDate data) {
        return ingressos.stream()
                .filter(ingresso -> ingresso.getDataEvento() != null && 
                                  ingresso.getDataEvento().equals(data))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingresso> buscarPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return ingressos.stream()
                .filter(ingresso -> ingresso.getPreco() != null &&
                                  ingresso.getPreco().compareTo(precoMinimo) >= 0 &&
                                  ingresso.getPreco().compareTo(precoMaximo) <= 0)
                .collect(Collectors.toList());
    }
}


