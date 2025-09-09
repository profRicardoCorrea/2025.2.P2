package repositories;

import entities.Local;
import java.util.List;

public interface ILocalRepository extends IRepository<Local> {
    List<Local> buscarPorNome(String nome);
    List<Local> buscarPorCidade(String cidade);
    List<Local> buscarPorEstado(String estado);
    List<Local> buscarPorTipo(String tipo);
    List<Local> buscarPorCapacidade(Integer capacidadeMinima);
    List<Local> buscarPorEndereco(String endereco);
    List<Local> buscarPorCep(String cep);
}

