package repositories;

import entities.Ingresso;
import java.util.List;

public interface IIngressoRepository extends IRepository<Ingresso> {
    List<Ingresso> buscarPorEvento(Long eventoId);
    List<Ingresso> buscarPorUsuario(Long usuarioId);
    List<Ingresso> buscarPorStatus(String status);
    List<Ingresso> buscarPorTipo(String tipo);
    List<Ingresso> buscarPorDataCompra(java.time.LocalDate data);
    List<Ingresso> buscarPorDataEvento(java.time.LocalDate data);
    List<Ingresso> buscarPorPreco(java.math.BigDecimal precoMinimo, java.math.BigDecimal precoMaximo);
}

