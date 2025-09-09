package repositories;

import entities.Evento;
import java.util.List;

public interface IEventoRepository extends IRepository<Evento> {
    List<Evento> buscarPorTipo(String tipoEvento);
    List<Evento> buscarPorCategoria(String categoria);
    List<Evento> buscarPorLocal(String local);
    List<Evento> buscarPorOrganizador(String organizador);
    List<Evento> buscarPorStatus(String status);
    List<Evento> buscarPorPreco(Boolean gratuito);
    List<Evento> buscarPorFormato(String formato);
    List<Evento> buscarPorData(java.time.LocalDate data);
    List<Evento> buscarPorCidade(String cidade);
    List<Evento> buscarPorEstado(String estado);
}

