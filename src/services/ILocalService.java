package services;

import entities.Local;
import java.util.List;
import java.time.LocalDate;

public interface ILocalService {
    void criarLocal(Local local);
    void atualizarLocal(Local local);
    void removerLocal(Long id);
    Local buscarLocalPorId(Long id);
    List<Local> listarTodosLocais();
    List<Local> buscarLocaisPorNome(String nome);
    List<Local> buscarLocaisPorCidade(String cidade);
    List<Local> buscarLocaisPorEstado(String estado);
    List<Local> buscarLocaisPorTipo(String tipo);
    List<Local> buscarLocaisPorCapacidade(Integer capacidadeMinima);
    List<Local> buscarLocaisPorEndereco(String endereco);
    List<Local> buscarLocaisPorCep(String cep);
    List<Local> buscarLocaisDisponiveis();
    List<Local> buscarLocaisPorFaixaCapacidade(Integer capacidadeMinima, Integer capacidadeMaxima);
    boolean verificarDisponibilidadeLocal(Long localId, LocalDate data);
    void marcarLocalComoIndisponivel(Long localId);
    void marcarLocalComoDisponivel(Long localId);
}


