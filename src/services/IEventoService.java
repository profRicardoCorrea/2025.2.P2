package services;

import entities.Evento;
import java.util.List;
import java.time.LocalDate;

public interface IEventoService {
    void criarEvento(Evento evento);
    void atualizarEvento(Evento evento);
    void removerEvento(Long id);
    Evento buscarEventoPorId(Long id);
    List<Evento> listarTodosEventos();
    List<Evento> buscarEventosPorTipo(String tipoEvento);
    List<Evento> buscarEventosPorCategoria(String categoria);
    List<Evento> buscarEventosPorLocal(String local);
    List<Evento> buscarEventosPorOrganizador(String organizador);
    List<Evento> buscarEventosPorStatus(String status);
    List<Evento> buscarEventosGratuitos();
    List<Evento> buscarEventosPagos();
    List<Evento> buscarEventosPorFormato(String formato);
    List<Evento> buscarEventosPorData(LocalDate data);
    List<Evento> buscarEventosPorCidade(String cidade);
    List<Evento> buscarEventosPorEstado(String estado);
    List<Evento> buscarEventosAtivos();
    List<Evento> buscarEventosCancelados();
    List<Evento> buscarEventosEncerrados();
    boolean verificarDisponibilidadeEvento(Long eventoId);
    void cancelarEvento(Long eventoId);
    void encerrarEvento(Long eventoId);
    void ativarEvento(Long eventoId);
}


