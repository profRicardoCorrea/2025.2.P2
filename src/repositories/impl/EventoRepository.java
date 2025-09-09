package repositories.impl;

import entities.Evento;
import repositories.IEventoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class EventoRepository implements IEventoRepository {
    private List<Evento> eventos;
    private Long proximoId;

    public EventoRepository() {
        this.eventos = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Evento entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        eventos.add(entidade);
    }

    @Override
    public void remover(Long id) {
        eventos.removeIf(evento -> evento.getId().equals(id));
    }

    @Override
    public void alterar(Evento entidade) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId().equals(entidade.getId())) {
                eventos.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Evento> listar() {
        return new ArrayList<>(eventos);
    }

    @Override
    public Evento buscarPorId(Long id) {
        return eventos.stream()
                .filter(evento -> evento.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Evento> buscarPorTipo(String tipoEvento) {
        return eventos.stream()
                .filter(evento -> tipoEvento.equals(evento.getTipoEvento()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorCategoria(String categoria) {
        return eventos.stream()
                .filter(evento -> categoria.equals(evento.getCategoria()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorLocal(String local) {
        return eventos.stream()
                .filter(evento -> local.equals(evento.getLocal()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorOrganizador(String organizador) {
        return eventos.stream()
                .filter(evento -> organizador.equals(evento.getOrganizador()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorStatus(String status) {
        return eventos.stream()
                .filter(evento -> status.equals(evento.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorPreco(Boolean gratuito) {
        return eventos.stream()
                .filter(evento -> gratuito.equals(evento.getGratuito()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorFormato(String formato) {
        return eventos.stream()
                .filter(evento -> formato.equals(evento.getFormato()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorData(LocalDate data) {
        return eventos.stream()
                .filter(evento -> evento.getDataHora() != null && 
                                evento.getDataHora().toLocalDate().equals(data))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorCidade(String cidade) {
        return eventos.stream()
                .filter(evento -> cidade.equals(evento.getCidade()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorEstado(String estado) {
        return eventos.stream()
                .filter(evento -> estado.equals(evento.getEstado()))
                .collect(Collectors.toList());
    }
}

