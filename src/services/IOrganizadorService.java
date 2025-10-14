package services;

import entities.Organizador;
import java.util.List;

public interface IOrganizadorService {
    void criarOrganizador(Organizador organizador);
    void atualizarOrganizador(Organizador organizador);
    void removerOrganizador(Long id);
    Organizador buscarOrganizadorPorId(Long id);
    List<Organizador> listarTodosOrganizadores();
    List<Organizador> buscarOrganizadoresPorNome(String nome);
    List<Organizador> buscarOrganizadoresPorEmail(String email);
    List<Organizador> buscarOrganizadoresPorTelefone(String telefone);
    List<Organizador> buscarOrganizadoresPorCidade(String cidade);
    List<Organizador> buscarOrganizadoresPorEstado(String estado);
    List<Organizador> buscarOrganizadoresPorTipo(String tipo);
    List<Organizador> buscarOrganizadoresPorStatus(String status);
    List<Organizador> buscarOrganizadoresAtivos();
    Organizador buscarOrganizadorPorEmailExato(String email);
    boolean verificarSeEmailJaExiste(String email);
    boolean verificarSeTelefoneJaExiste(String telefone);
    void ativarOrganizador(Long id);
    void desativarOrganizador(Long id);
    void validarDadosOrganizador(Organizador organizador);
}


