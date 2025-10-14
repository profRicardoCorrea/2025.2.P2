package services.impl;

import entities.Local;
import repositories.ILocalRepository;
import services.ILocalService;
import java.util.List;
import java.time.LocalDate;

public class LocalService implements ILocalService {
    
    private final ILocalRepository localRepository;
    
    public LocalService(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }
    
    @Override
    public void criarLocal(Local local) {
        validarLocal(local);
        localRepository.salvar(local);
    }
    
    @Override
    public void atualizarLocal(Local local) {
        if (local.getId() == null) {
            throw new IllegalArgumentException("ID do local é obrigatório para atualização");
        }
        validarLocal(local);
        localRepository.alterar(local);
    }
    
    @Override
    public void removerLocal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do local é obrigatório");
        }
        Local local = localRepository.buscarPorId(id);
        if (local == null) {
            throw new IllegalArgumentException("Local não encontrado");
        }
        localRepository.remover(id);
    }
    
    @Override
    public Local buscarLocalPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do local é obrigatório");
        }
        return localRepository.buscarPorId(id);
    }
    
    @Override
    public List<Local> listarTodosLocais() {
        return localRepository.listar();
    }
    
    @Override
    public List<Local> buscarLocaisPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do local é obrigatório");
        }
        return localRepository.buscarPorNome(nome);
    }
    
    @Override
    public List<Local> buscarLocaisPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        return localRepository.buscarPorCidade(cidade);
    }
    
    @Override
    public List<Local> buscarLocaisPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório");
        }
        return localRepository.buscarPorEstado(estado);
    }
    
    @Override
    public List<Local> buscarLocaisPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do local é obrigatório");
        }
        return localRepository.buscarPorTipo(tipo);
    }
    
    @Override
    public List<Local> buscarLocaisPorCapacidade(Integer capacidadeMinima) {
        if (capacidadeMinima == null || capacidadeMinima <= 0) {
            throw new IllegalArgumentException("Capacidade mínima deve ser maior que zero");
        }
        return localRepository.buscarPorCapacidade(capacidadeMinima);
    }
    
    @Override
    public List<Local> buscarLocaisPorEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }
        return localRepository.buscarPorEndereco(endereco);
    }
    
    @Override
    public List<Local> buscarLocaisPorCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        return localRepository.buscarPorCep(cep);
    }
    
    @Override
    public List<Local> buscarLocaisDisponiveis() {
        // Implementar lógica para buscar locais disponíveis
        return localRepository.listar().stream()
                .filter(local -> "DISPONIVEL".equals(local.getStatus()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public List<Local> buscarLocaisPorFaixaCapacidade(Integer capacidadeMinima, Integer capacidadeMaxima) {
        if (capacidadeMinima == null || capacidadeMinima <= 0) {
            throw new IllegalArgumentException("Capacidade mínima deve ser maior que zero");
        }
        if (capacidadeMaxima == null || capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima deve ser maior que zero");
        }
        if (capacidadeMinima > capacidadeMaxima) {
            throw new IllegalArgumentException("Capacidade mínima não pode ser maior que a máxima");
        }
        
        return localRepository.listar().stream()
                .filter(local -> local.getCapacidade() >= capacidadeMinima && 
                               local.getCapacidade() <= capacidadeMaxima)
                .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public boolean verificarDisponibilidadeLocal(Long localId, LocalDate data) {
        if (localId == null) {
            throw new IllegalArgumentException("ID do local é obrigatório");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data é obrigatória");
        }
        
        Local local = localRepository.buscarPorId(localId);
        if (local == null) {
            return false;
        }
        
        // Implementar lógica de verificação de disponibilidade por data
        return "DISPONIVEL".equals(local.getStatus());
    }
    
    @Override
    public void marcarLocalComoIndisponivel(Long localId) {
        Local local = buscarLocalPorId(localId);
        if (local == null) {
            throw new IllegalArgumentException("Local não encontrado");
        }
        local.setStatus("INDISPONIVEL");
        localRepository.alterar(local);
    }
    
    @Override
    public void marcarLocalComoDisponivel(Long localId) {
        Local local = buscarLocalPorId(localId);
        if (local == null) {
            throw new IllegalArgumentException("Local não encontrado");
        }
        local.setStatus("DISPONIVEL");
        localRepository.alterar(local);
    }
    
    private void validarLocal(Local local) {
        if (local == null) {
            throw new IllegalArgumentException("Local não pode ser nulo");
        }
        if (local.getNome() == null || local.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do local é obrigatório");
        }
        if (local.getEndereco() == null || local.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do local é obrigatório");
        }
        if (local.getCidade() == null || local.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade do local é obrigatória");
        }
        if (local.getEstado() == null || local.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado do local é obrigatório");
        }
        if (local.getCep() == null || local.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP do local é obrigatório");
        }
        if (local.getCapacidade() == null || local.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade do local deve ser maior que zero");
        }
        if (local.getTipoLocal() == null || local.getTipoLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do local é obrigatório");
        }
    }
}


