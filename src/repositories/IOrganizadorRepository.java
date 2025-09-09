package repositories;

import entities.Organizador;
import java.util.List;

public interface IOrganizadorRepository extends IRepository<Organizador> {
    List<Organizador> buscarPorNome(String nome);
    List<Organizador> buscarPorEmail(String email);
    List<Organizador> buscarPorTelefone(String telefone);
    List<Organizador> buscarPorCidade(String cidade);
    List<Organizador> buscarPorEstado(String estado);
    List<Organizador> buscarPorTipo(String tipo);
    List<Organizador> buscarPorStatus(String status);
}

