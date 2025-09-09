package entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Organizador {
    private Long id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String website;
    private String redesSociais;
    private String descricao;
    private String categoria; // PRODUCAO, CASA_SHOW, TEATRO, GALERIA, ORGANIZACAO_EVENTOS
    private String tipoOrganizacao; // PESSOA_FISICA, PESSOA_JURIDICA
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String logoUrl;
    private String certificacoes;
    private String experiencia;
    private Integer anosExperiencia;
    private List<String> especialidades;
    private String contatoResponsavel;
    private String telefoneResponsavel;
    private String emailResponsavel;
    private Boolean verificado;
    private String statusVerificacao; // PENDENTE, EM_ANALISE, APROVADO, REJEITADO
    private String observacoesVerificacao;

    public Organizador() {
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.especialidades = new ArrayList<>();
        this.verificado = false;
        this.statusVerificacao = "PENDENTE";
        this.tipoOrganizacao = "PESSOA_JURIDICA";
    }

    public Organizador(String nome, String email, String telefone, String categoria) {
        this();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.categoria = categoria;
    }

    // Métodos específicos para organizadores
    public void adicionarEspecialidade(String especialidade) {
        if (!especialidades.contains(especialidade)) {
            especialidades.add(especialidade);
        }
    }

    public void removerEspecialidade(String especialidade) {
        especialidades.remove(especialidade);
    }

    public boolean isPessoaFisica() {
        return "PESSOA_FISICA".equals(tipoOrganizacao);
    }

    public boolean isPessoaJuridica() {
        return "PESSOA_JURIDICA".equals(tipoOrganizacao);
    }

    public boolean isProducao() {
        return "PRODUCAO".equals(categoria);
    }

    public boolean isCasaShow() {
        return "CASA_SHOW".equals(categoria);
    }

    public boolean isTeatro() {
        return "TEATRO".equals(categoria);
    }

    public boolean isGaleria() {
        return "GALERIA".equals(categoria);
    }

    public boolean isOrganizacaoEventos() {
        return "ORGANIZACAO_EVENTOS".equals(categoria);
    }

    public boolean isVerificado() {
        return verificado && "APROVADO".equals(statusVerificacao);
    }

    public boolean isPendenteVerificacao() {
        return "PENDENTE".equals(statusVerificacao);
    }

    public boolean isEmAnalise() {
        return "EM_ANALISE".equals(statusVerificacao);
    }

    public boolean isAprovado() {
        return "APROVADO".equals(statusVerificacao);
    }

    public boolean isRejeitado() {
        return "REJEITADO".equals(statusVerificacao);
    }

    public void aprovarVerificacao() {
        this.statusVerificacao = "APROVADO";
        this.verificado = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void rejeitarVerificacao(String observacoes) {
        this.statusVerificacao = "REJEITADO";
        this.verificado = false;
        this.observacoesVerificacao = observacoes;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void colocarEmAnalise() {
        this.statusVerificacao = "EM_ANALISE";
        this.dataAtualizacao = LocalDateTime.now();
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

    public String getCategoriaFormatada() {
        switch (categoria) {
            case "PRODUCAO": return "Produção";
            case "CASA_SHOW": return "Casa de Show";
            case "TEATRO": return "Teatro";
            case "GALERIA": return "Galeria";
            case "ORGANIZACAO_EVENTOS": return "Organização de Eventos";
            default: return categoria;
        }
    }

    public String getStatusVerificacaoFormatado() {
        switch (statusVerificacao) {
            case "PENDENTE": return "Pendente";
            case "EM_ANALISE": return "Em Análise";
            case "APROVADO": return "Aprovado";
            case "REJEITADO": return "Rejeitado";
            default: return statusVerificacao;
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { 
        this.nome = nome; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { 
        this.razaoSocial = razaoSocial; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { 
        this.cnpj = cnpj; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { 
        this.endereco = endereco; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { 
        this.cidade = cidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { 
        this.estado = estado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCep() { return cep; }
    public void setCep(String cep) { 
        this.cep = cep; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { 
        this.website = website; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRedesSociais() { return redesSociais; }
    public void setRedesSociais(String redesSociais) { 
        this.redesSociais = redesSociais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoOrganizacao() { return tipoOrganizacao; }
    public void setTipoOrganizacao(String tipoOrganizacao) { 
        this.tipoOrganizacao = tipoOrganizacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { 
        this.ativo = ativo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { 
        this.logoUrl = logoUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCertificacoes() { return certificacoes; }
    public void setCertificacoes(String certificacoes) { 
        this.certificacoes = certificacoes; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { 
        this.experiencia = experiencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(Integer anosExperiencia) { 
        this.anosExperiencia = anosExperiencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getEspecialidades() { return new ArrayList<>(especialidades); }
    public void setEspecialidades(List<String> especialidades) { 
        this.especialidades = new ArrayList<>(especialidades); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getContatoResponsavel() { return contatoResponsavel; }
    public void setContatoResponsavel(String contatoResponsavel) { 
        this.contatoResponsavel = contatoResponsavel; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTelefoneResponsavel() { return telefoneResponsavel; }
    public void setTelefoneResponsavel(String telefoneResponsavel) { 
        this.telefoneResponsavel = telefoneResponsavel; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmailResponsavel() { return emailResponsavel; }
    public void setEmailResponsavel(String emailResponsavel) { 
        this.emailResponsavel = emailResponsavel; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getVerificado() { return verificado; }
    public void setVerificado(Boolean verificado) { 
        this.verificado = verificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getStatusVerificacao() { return statusVerificacao; }
    public void setStatusVerificacao(String statusVerificacao) { 
        this.statusVerificacao = statusVerificacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getObservacoesVerificacao() { return observacoesVerificacao; }
    public void setObservacoesVerificacao(String observacoesVerificacao) { 
        this.observacoesVerificacao = observacoesVerificacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Organizador: %s - %s - %s", 
            nome, getCategoriaFormatada(), cidade != null ? cidade : "Cidade não informada");
    }
}
