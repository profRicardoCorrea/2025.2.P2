package services.impl;

import entities.Organizador;
import repositories.IOrganizadorRepository;
import services.IOrganizadorService;
import java.util.List;

public class OrganizadorService implements IOrganizadorService {
    
    private final IOrganizadorRepository organizadorRepository;
    
    public OrganizadorService(IOrganizadorRepository organizadorRepository) {
        this.organizadorRepository = organizadorRepository;
    }
    
    @Override
    public void criarOrganizador(Organizador organizador) {
        validarDadosOrganizador(organizador);
        if (verificarSeEmailJaExiste(organizador.getEmail())) {
            throw new IllegalArgumentException("Já existe um organizador com este email");
        }
        if (verificarSeTelefoneJaExiste(organizador.getTelefone())) {
            throw new IllegalArgumentException("Já existe um organizador com este telefone");
        }
        organizadorRepository.salvar(organizador);
    }
    
    @Override
    public void atualizarOrganizador(Organizador organizador) {
        if (organizador.getId() == null) {
            throw new IllegalArgumentException("ID do organizador é obrigatório para atualização");
        }
        validarDadosOrganizador(organizador);
        organizadorRepository.alterar(organizador);
    }
    
    @Override
    public void removerOrganizador(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do organizador é obrigatório");
        }
        Organizador organizador = organizadorRepository.buscarPorId(id);
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não encontrado");
        }
        organizadorRepository.remover(id);
    }
    
    @Override
    public Organizador buscarOrganizadorPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorId(id);
    }
    
    @Override
    public List<Organizador> listarTodosOrganizadores() {
        return organizadorRepository.listar();
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorNome(nome);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorEmail(email);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorTelefone(telefone);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        return organizadorRepository.buscarPorCidade(cidade);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório");
        }
        return organizadorRepository.buscarPorEstado(estado);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorTipo(tipo);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status do organizador é obrigatório");
        }
        return organizadorRepository.buscarPorStatus(status);
    }
    
    @Override
    public List<Organizador> buscarOrganizadoresAtivos() {
        return organizadorRepository.buscarPorStatus("ATIVO");
    }
    
    @Override
    public Organizador buscarOrganizadorPorEmailExato(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do organizador é obrigatório");
        }
        List<Organizador> organizadores = organizadorRepository.buscarPorEmail(email);
        return organizadores.stream()
                .filter(org -> email.equals(org.getEmail()))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean verificarSeEmailJaExiste(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return buscarOrganizadorPorEmailExato(email) != null;
    }
    
    @Override
    public boolean verificarSeTelefoneJaExiste(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return false;
        }
        List<Organizador> organizadores = organizadorRepository.buscarPorTelefone(telefone);
        return organizadores.stream()
                .anyMatch(org -> telefone.equals(org.getTelefone()));
    }
    
    @Override
    public void ativarOrganizador(Long id) {
        Organizador organizador = buscarOrganizadorPorId(id);
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não encontrado");
        }
        organizador.setStatus("ATIVO");
        organizadorRepository.alterar(organizador);
    }
    
    @Override
    public void desativarOrganizador(Long id) {
        Organizador organizador = buscarOrganizadorPorId(id);
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não encontrado");
        }
        organizador.setStatus("INATIVO");
        organizadorRepository.alterar(organizador);
    }
    
    @Override
    public void validarDadosOrganizador(Organizador organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não pode ser nulo");
        }
        if (organizador.getNome() == null || organizador.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do organizador é obrigatório");
        }
        if (organizador.getEmail() == null || organizador.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do organizador é obrigatório");
        }
        if (organizador.getTelefone() == null || organizador.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do organizador é obrigatório");
        }
        if (organizador.getCidade() == null || organizador.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade do organizador é obrigatória");
        }
        if (organizador.getEstado() == null || organizador.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado do organizador é obrigatório");
        }
        if (organizador.getTipo() == null || organizador.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do organizador é obrigatório");
        }
        
        // Validação básica de email
        if (!organizador.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
}


