package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Local {
    private Long id;
    private String nome;
    private String descricao;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String bairro;
    private String complemento;
    private String telefone;
    private String email;
    private String website;
    private String redesSociais;
    private String tipoLocal; // TEATRO, CASA_SHOW, GALERIA, MUSEU, AUDITORIO, ESPACO_ABERTO
    private Integer capacidade;
    private Boolean acessibilidade;
    private String recursosAcessibilidade;
    private Boolean estacionamento;
    private String tipoEstacionamento; // GRATUITO, PAGO, VALET
    private BigDecimal precoEstacionamento;
    private Boolean wifi;
    private Boolean arCondicionado;
    private Boolean bar;
    private Boolean restaurante;
    private String horarioFuncionamento;
    private String diasFuncionamento;
    private String coordenadasGPS;
    private String imagemUrl;
    private String plantaLocal;
    private String regras;
    private String restricoes;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private Long organizadorId;
    private String status; // ATIVO, INATIVO, EM_REFORMA, FECHADO_TEMPORARIAMENTE
    private List<String> fotos;
    private String videoUrl;
    private String mapaUrl;
    private String observacoes;
    private Boolean verificado;
    private String statusVerificacao;

    public Local() {
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.fotos = new ArrayList<>();
        this.verificado = false;
        this.statusVerificacao = "PENDENTE";
        this.status = "ATIVO";
        this.acessibilidade = false;
        this.estacionamento = false;
        this.wifi = false;
        this.arCondicionado = false;
        this.bar = false;
        this.restaurante = false;
    }

    public Local(String nome, String endereco, String cidade, String estado, String tipoLocal) {
        this();
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.tipoLocal = tipoLocal;
    }

    // Métodos específicos para locais
    public void adicionarFoto(String foto) {
        if (!fotos.contains(foto)) {
            fotos.add(foto);
        }
    }

    public void removerFoto(String foto) {
        fotos.remove(foto);
    }

    public boolean isTeatro() {
        return "TEATRO".equals(tipoLocal);
    }

    public boolean isCasaShow() {
        return "CASA_SHOW".equals(tipoLocal);
    }

    public boolean isGaleria() {
        return "GALERIA".equals(tipoLocal);
    }

    public boolean isMuseu() {
        return "MUSEU".equals(tipoLocal);
    }

    public boolean isAuditorio() {
        return "AUDITORIO".equals(tipoLocal);
    }

    public boolean isEspacoAberto() {
        return "ESPACO_ABERTO".equals(tipoLocal);
    }

    public boolean isAtivo() {
        return "ATIVO".equals(status);
    }

    public boolean isEmReforma() {
        return "EM_REFORMA".equals(status);
    }

    public boolean isFechadoTemporariamente() {
        return "FECHADO_TEMPORARIAMENTE".equals(status);
    }

    public boolean isInativo() {
        return "INATIVO".equals(status);
    }

    public String getEnderecoCompleto() {
        StringBuilder enderecoCompleto = new StringBuilder();
        if (endereco != null && !endereco.isEmpty()) {
            enderecoCompleto.append(endereco);
        }
        if (bairro != null && !bairro.isEmpty()) {
            if (enderecoCompleto.length() > 0) enderecoCompleto.append(", ");
            enderecoCompleto.append(bairro);
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

    public String getTipoLocalFormatado() {
        switch (tipoLocal) {
            case "TEATRO": return "Teatro";
            case "CASA_SHOW": return "Casa de Show";
            case "GALERIA": return "Galeria";
            case "MUSEU": return "Museu";
            case "AUDITORIO": return "Auditório";
            case "ESPACO_ABERTO": return "Espaço Aberto";
            default: return tipoLocal;
        }
    }

    public String getStatusFormatado() {
        switch (status) {
            case "ATIVO": return "Ativo";
            case "INATIVO": return "Inativo";
            case "EM_REFORMA": return "Em Reforma";
            case "FECHADO_TEMPORARIAMENTE": return "Fechado Temporariamente";
            default: return status;
        }
    }

    public String getTipoEstacionamentoFormatado() {
        if (!estacionamento) return "Não disponível";
        switch (tipoEstacionamento) {
            case "GRATUITO": return "Gratuito";
            case "PAGO": return "Pago";
            case "VALET": return "Valet";
            default: return tipoEstacionamento;
        }
    }

    public String getCapacidadeFormatada() {
        if (capacidade != null) {
            return String.format("%d pessoas", capacidade);
        }
        return "Capacidade não informada";
    }

    public boolean temRecursosBasicos() {
        return wifi || arCondicionado || bar || restaurante;
    }

    public boolean temAcessibilidade() {
        return acessibilidade && recursosAcessibilidade != null && !recursosAcessibilidade.isEmpty();
    }

    public boolean temEstacionamento() {
        return estacionamento && tipoEstacionamento != null;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { 
        this.nome = nome; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
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

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { 
        this.bairro = bairro; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { 
        this.complemento = complemento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email; 
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

    public String getTipoLocal() { return tipoLocal; }
    public void setTipoLocal(String tipoLocal) { 
        this.tipoLocal = tipoLocal; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { 
        this.capacidade = capacidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getAcessibilidade() { return acessibilidade; }
    public void setAcessibilidade(Boolean acessibilidade) { 
        this.acessibilidade = acessibilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRecursosAcessibilidade() { return recursosAcessibilidade; }
    public void setRecursosAcessibilidade(String recursosAcessibilidade) { 
        this.recursosAcessibilidade = recursosAcessibilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getEstacionamento() { return estacionamento; }
    public void setEstacionamento(Boolean estacionamento) { 
        this.estacionamento = estacionamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoEstacionamento() { return tipoEstacionamento; }
    public void setTipoEstacionamento(String tipoEstacionamento) { 
        this.tipoEstacionamento = tipoEstacionamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public BigDecimal getPrecoEstacionamento() { return precoEstacionamento; }
    public void setPrecoEstacionamento(BigDecimal precoEstacionamento) { 
        this.precoEstacionamento = precoEstacionamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getWifi() { return wifi; }
    public void setWifi(Boolean wifi) { 
        this.wifi = wifi; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getArCondicionado() { return arCondicionado; }
    public void setArCondicionado(Boolean arCondicionado) { 
        this.arCondicionado = arCondicionado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getBar() { return bar; }
    public void setBar(Boolean bar) { 
        this.bar = bar; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getRestaurante() { return restaurante; }
    public void setRestaurante(Boolean restaurante) { 
        this.restaurante = restaurante; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { 
        this.horarioFuncionamento = horarioFuncionamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getDiasFuncionamento() { return diasFuncionamento; }
    public void setDiasFuncionamento(String diasFuncionamento) { 
        this.diasFuncionamento = diasFuncionamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCoordenadasGPS() { return coordenadasGPS; }
    public void setCoordenadasGPS(String coordenadasGPS) { 
        this.coordenadasGPS = coordenadasGPS; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { 
        this.imagemUrl = imagemUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPlantaLocal() { return plantaLocal; }
    public void setPlantaLocal(String plantaLocal) { 
        this.plantaLocal = plantaLocal; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRegras() { return regras; }
    public void setRegras(String regras) { 
        this.regras = regras; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoes() { return restricoes; }
    public void setRestricoes(String restricoes) { 
        this.restricoes = restricoes; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { 
        this.ativo = ativo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    public Long getOrganizadorId() { return organizadorId; }
    public void setOrganizadorId(Long organizadorId) { 
        this.organizadorId = organizadorId; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getFotos() { return new ArrayList<>(fotos); }
    public void setFotos(List<String> fotos) { 
        this.fotos = new ArrayList<>(fotos); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { 
        this.videoUrl = videoUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getMapaUrl() { return mapaUrl; }
    public void setMapaUrl(String mapaUrl) { 
        this.mapaUrl = mapaUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { 
        this.observacoes = observacoes; 
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

    @Override
    public String toString() {
        return String.format("Local: %s - %s - %s", 
            nome, getTipoLocalFormatado(), cidade != null ? cidade : "Cidade não informada");
    }
}
