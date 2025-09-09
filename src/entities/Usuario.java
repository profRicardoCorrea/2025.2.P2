package entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private LocalDateTime dataNascimento;
    private String genero;
    private String tipoUsuario; // ADMIN, ORGANIZADOR, USUARIO_COMUM
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimoAcesso;
    private String preferenciasCulturais;
    private List<String> interesses;
    private Boolean recebeNotificacoes;
    private String fotoPerfil;

    public Usuario() {
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
        this.ultimoAcesso = LocalDateTime.now();
        this.interesses = new ArrayList<>();
        this.recebeNotificacoes = true;
        this.tipoUsuario = "USUARIO_COMUM";
    }

    public Usuario(String nome, String email, String senha, String cpf) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

    // Métodos específicos para usuários
    public void adicionarInteresse(String interesse) {
        if (!interesses.contains(interesse)) {
            interesses.add(interesse);
        }
    }

    public void removerInteresse(String interesse) {
        interesses.remove(interesse);
    }

    public boolean isAdmin() {
        return "ADMIN".equals(tipoUsuario);
    }

    public boolean isOrganizador() {
        return "ORGANIZADOR".equals(tipoUsuario);
    }

    public boolean isUsuarioComum() {
        return "USUARIO_COMUM".equals(tipoUsuario);
    }

    public void atualizarUltimoAcesso() {
        this.ultimoAcesso = LocalDateTime.now();
    }

    public boolean podeOrganizarEventos() {
        return isAdmin() || isOrganizador();
    }

    public String getNomeCompleto() {
        return nome != null ? nome : "Nome não informado";
    }

    public String getEnderecoCompleto() {
        StringBuilder enderecoCompleto = new StringBuilder();
        if (endereco != null && !endereco.isEmpty()) {
            enderecoCompleto.append(endereco);
        }
        if (cidade != null && !cidade.isEmpty()) {
            if (enderecoCompleto.length() > 0) enderecoCompleto.append(", ");
            enderecoCompleto.append(cidade);
        }
        if (estado != null && !estado.isEmpty()) {
            if (enderecoCompleto.length() > 0) enderecoCompleto.append(" - ");
            enderecoCompleto.append(estado);
        }
        if (cep != null && !cep.isEmpty()) {
            if (enderecoCompleto.length() > 0) enderecoCompleto.append(" - CEP: ");
            enderecoCompleto.append(cep);
        }
        return enderecoCompleto.length() > 0 ? enderecoCompleto.toString() : "Endereço não informado";
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public LocalDateTime getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDateTime dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }

    public String getPreferenciasCulturais() { return preferenciasCulturais; }
    public void setPreferenciasCulturais(String preferenciasCulturais) { this.preferenciasCulturais = preferenciasCulturais; }

    public List<String> getInteresses() { return new ArrayList<>(interesses); }
    public void setInteresses(List<String> interesses) { this.interesses = new ArrayList<>(interesses); }

    public Boolean getRecebeNotificacoes() { return recebeNotificacoes; }
    public void setRecebeNotificacoes(Boolean recebeNotificacoes) { this.recebeNotificacoes = recebeNotificacoes; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    @Override
    public String toString() {
        return String.format("Usuário: %s - %s - %s", 
            nome, email, tipoUsuario);
    }
}
