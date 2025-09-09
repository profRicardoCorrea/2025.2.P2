package services;

import entities.Ingresso;
import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;

public interface IIngressoService {
    void criarIngresso(Ingresso ingresso);
    void atualizarIngresso(Ingresso ingresso);
    void removerIngresso(Long id);
    Ingresso buscarIngressoPorId(Long id);
    List<Ingresso> listarTodosIngressos();
    List<Ingresso> buscarIngressosPorEvento(Long eventoId);
    List<Ingresso> buscarIngressosPorUsuario(Long usuarioId);
    List<Ingresso> buscarIngressosPorStatus(String status);
    List<Ingresso> buscarIngressosPorTipo(String tipo);
    List<Ingresso> buscarIngressosPorDataCompra(LocalDate data);
    List<Ingresso> buscarIngressosPorDataEvento(LocalDate data);
    List<Ingresso> buscarIngressosPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo);
    List<Ingresso> buscarIngressosVendidos();
    List<Ingresso> buscarIngressosCancelados();
    List<Ingresso> buscarIngressosUtilizados();
    void cancelarIngresso(Long id);
    void confirmarIngresso(Long id);
    void utilizarIngresso(Long id);
    void reembolsarIngresso(Long id);
    boolean verificarDisponibilidadeIngresso(Long eventoId);
    void validarCompraIngresso(Ingresso ingresso);
    BigDecimal calcularValorTotalIngressosEvento(Long eventoId);
    int contarIngressosVendidosEvento(Long eventoId);
}


