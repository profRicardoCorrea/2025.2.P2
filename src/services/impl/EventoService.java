package services.impl;

import entities.Evento;
import repositories.IEventoRepository;
import services.IEventoService;
import java.util.List;
import java.time.LocalDate;

public class EventoService implements IEventoService {
    
    private final IEventoRepository eventoRepository;
    
    public EventoService(IEventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
    
    @Override
    public void criarEvento(Evento evento) {
        validarEvento(evento);
        eventoRepository.salvar(evento);
    }
    
    @Override
    public void atualizarEvento(Evento evento) {
        if (evento.getId() == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório para atualização");
        }
        validarEvento(evento);
        eventoRepository.alterar(evento);
    }
    
    @Override
    public void removerEvento(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        Evento evento = eventoRepository.buscarPorId(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        eventoRepository.remover(id);
    }
    
    @Override
    public Evento buscarEventoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        return eventoRepository.buscarPorId(id);
    }
    
    @Override
    public List<Evento> listarTodosEventos() {
        return eventoRepository.listar();
    }
    
    @Override
    public List<Evento> buscarEventosPorTipo(String tipoEvento) {
        if (tipoEvento == null || tipoEvento.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do evento é obrigatório");
        }
        return eventoRepository.buscarPorTipo(tipoEvento);
    }
    
    @Override
    public List<Evento> buscarEventosPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
        return eventoRepository.buscarPorCategoria(categoria);
    }
    
    @Override
    public List<Evento> buscarEventosPorLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local é obrigatório");
        }
        return eventoRepository.buscarPorLocal(local);
    }
    
    @Override
    public List<Evento> buscarEventosPorOrganizador(String organizador) {
        if (organizador == null || organizador.trim().isEmpty()) {
            throw new IllegalArgumentException("Organizador é obrigatório");
        }
        return eventoRepository.buscarPorOrganizador(organizador);
    }
    
    @Override
    public List<Evento> buscarEventosPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status é obrigatório");
        }
        return eventoRepository.buscarPorStatus(status);
    }
    
    @Override
    public List<Evento> buscarEventosGratuitos() {
        return eventoRepository.buscarPorPreco(true);
    }
    
    @Override
    public List<Evento> buscarEventosPagos() {
        return eventoRepository.buscarPorPreco(false);
    }
    
    @Override
    public List<Evento> buscarEventosPorFormato(String formato) {
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("Formato é obrigatório");
        }
        return eventoRepository.buscarPorFormato(formato);
    }
    
    @Override
    public List<Evento> buscarEventosPorData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data é obrigatória");
        }
        return eventoRepository.buscarPorData(data);
    }
    
    @Override
    public List<Evento> buscarEventosPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        return eventoRepository.buscarPorCidade(cidade);
    }
    
    @Override
    public List<Evento> buscarEventosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório");
        }
        return eventoRepository.buscarPorEstado(estado);
    }
    
    @Override
    public List<Evento> buscarEventosAtivos() {
        return eventoRepository.buscarPorStatus("ATIVO");
    }
    
    @Override
    public List<Evento> buscarEventosCancelados() {
        return eventoRepository.buscarPorStatus("CANCELADO");
    }
    
    @Override
    public List<Evento> buscarEventosEncerrados() {
        return eventoRepository.buscarPorStatus("ENCERRADO");
    }
    
    @Override
    public boolean verificarDisponibilidadeEvento(Long eventoId) {
        Evento evento = buscarEventoPorId(eventoId);
        if (evento == null) {
            return false;
        }
        return evento.temIngressosDisponiveis() && evento.estaAtivo();
    }
    
    @Override
    public void cancelarEvento(Long id) {
        Evento evento = buscarEventoPorId(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        evento.setStatus("CANCELADO");
        eventoRepository.alterar(evento);
    }
    
    @Override
    public void encerrarEvento(Long id) {
        Evento evento = buscarEventoPorId(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        evento.setStatus("ENCERRADO");
        eventoRepository.alterar(evento);
    }
    
    @Override
    public void ativarEvento(Long id) {
        Evento evento = buscarEventoPorId(id);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        evento.setStatus("ATIVO");
        eventoRepository.alterar(evento);
    }
    
    private void validarEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo");
        }
        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do evento é obrigatório");
        }
        if (evento.getDescricao() == null || evento.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do evento é obrigatória");
        }
        if (evento.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora do evento são obrigatórias");
        }
        if (evento.getLocal() == null || evento.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Local do evento é obrigatório");
        }
        if (evento.getCidade() == null || evento.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade do evento é obrigatória");
        }
        if (evento.getEstado() == null || evento.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado do evento é obrigatório");
        }
        if (evento.getCapacidade() == null || evento.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade do evento deve ser maior que zero");
        }
        if (evento.getCategoria() == null || evento.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria do evento é obrigatória");
        }
    }
}


