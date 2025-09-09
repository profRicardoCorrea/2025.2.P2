package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Workshop extends AbstractEvent {
    private String instrutor;
    private String organizador;
    private String areaCultural; // MUSICA, TEATRO, DANCA, ARTES_VISUAIS, LITERATURA
    private Integer duracaoMinutos;
    private Integer numeroSessoes;
    private Integer vagasDisponiveis;
    private String nivel; // INICIANTE, INTERMEDIARIO, AVANCADO
    private String materialNecessario;
    private String preRequisitos;
    private Boolean certificado;
    private String tipoCertificado;
    private Boolean materialIncluso;
    private BigDecimal precoMaterial;
    private String localizacaoEspecifica;
    private Boolean temCoffeeBreak;
    private String horarioCoffeeBreak;
    private Boolean permiteAuditoria;
    private BigDecimal precoAuditoria;
    private String metodologia;
    private String objetivoAprendizado;

    public Workshop() {
        super();
        this.areaCultural = "ARTES_VISUAIS";
        this.nivel = "INICIANTE";
        this.certificado = false;
        this.materialIncluso = false;
        this.temCoffeeBreak = false;
        this.permiteAuditoria = false;
    }

    public Workshop(String titulo, String descricao, LocalDateTime dataHora, 
                    String local, String endereco, String cidade, String estado, 
                    BigDecimal preco, Integer capacidade, String categoria,
                    String instrutor, String organizador, String areaCultural) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.instrutor = instrutor;
        this.organizador = organizador;
        this.areaCultural = areaCultural;
        this.nivel = "INICIANTE";
        this.certificado = false;
        this.materialIncluso = false;
        this.temCoffeeBreak = false;
        this.permiteAuditoria = false;
    }

    @Override
    public String getTipoEvento() {
        return "WORKSHOP";
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Instrutor: %s, Área: %s, Nível: %s, Duração: %d min, Sessões: %d", 
            instrutor, areaCultural, nivel, duracaoMinutos, numeroSessoes);
    }

    // Métodos específicos para workshops
    public boolean isWorkshopMusica() {
        return "MUSICA".equals(areaCultural);
    }

    public boolean isWorkshopTeatro() {
        return "TEATRO".equals(areaCultural);
    }

    public boolean isWorkshopDanca() {
        return "DANCA".equals(areaCultural);
    }

    public boolean isWorkshopArtesVisuais() {
        return "ARTES_VISUAIS".equals(areaCultural);
    }

    public boolean isWorkshopLiteratura() {
        return "LITERATURA".equals(areaCultural);
    }

    public boolean isNivelIniciante() {
        return "INICIANTE".equals(nivel);
    }

    public boolean isNivelIntermediario() {
        return "INTERMEDIARIO".equals(nivel);
    }

    public boolean isNivelAvancado() {
        return "AVANCADO".equals(nivel);
    }

    public String getDuracaoFormatada() {
        if (duracaoMinutos != null) {
            int horas = duracaoMinutos / 60;
            int minutos = duracaoMinutos % 60;
            if (horas > 0) {
                return String.format("%dh %dmin", horas, minutos);
            } else {
                return String.format("%dmin", minutos);
            }
        }
        return "Duração não definida";
    }

    public String getDuracaoTotal() {
        if (duracaoMinutos != null && numeroSessoes != null) {
            int duracaoTotal = duracaoMinutos * numeroSessoes;
            int horas = duracaoTotal / 60;
            int minutos = duracaoTotal % 60;
            if (horas > 0) {
                return String.format("%dh %dmin (%d sessões)", horas, minutos, numeroSessoes);
            } else {
                return String.format("%dmin (%d sessões)", minutos, numeroSessoes);
            }
        }
        return getDuracaoFormatada();
    }

    public BigDecimal getPrecoTotal() {
        BigDecimal precoTotal = preco != null ? preco : BigDecimal.ZERO;
        if (materialIncluso && precoMaterial != null) {
            precoTotal = precoTotal.add(precoMaterial);
        }
        return precoTotal;
    }

    // Getters e Setters específicos
    public String getInstrutor() { return instrutor; }
    public void setInstrutor(String instrutor) { 
        this.instrutor = instrutor; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getOrganizador() { return organizador; }
    public void setOrganizador(String organizador) { 
        this.organizador = organizador; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getAreaCultural() { return areaCultural; }
    public void setAreaCultural(String areaCultural) { 
        this.areaCultural = areaCultural; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { 
        this.duracaoMinutos = duracaoMinutos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getNumeroSessoes() { return numeroSessoes; }
    public void setNumeroSessoes(Integer numeroSessoes) { 
        this.numeroSessoes = numeroSessoes; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(Integer vagasDisponiveis) { 
        this.vagasDisponiveis = vagasDisponiveis; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { 
        this.nivel = nivel; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getMaterialNecessario() { return materialNecessario; }
    public void setMaterialNecessario(String materialNecessario) { 
        this.materialNecessario = materialNecessario; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPreRequisitos() { return preRequisitos; }
    public void setPreRequisitos(String preRequisitos) { 
        this.preRequisitos = preRequisitos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getCertificado() { return certificado; }
    public void setCertificado(Boolean certificado) { 
        this.certificado = certificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoCertificado() { return tipoCertificado; }
    public void setTipoCertificado(String tipoCertificado) { 
        this.tipoCertificado = tipoCertificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getMaterialIncluso() { return materialIncluso; }
    public void setMaterialIncluso(Boolean materialIncluso) { 
        this.materialIncluso = materialIncluso; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public BigDecimal getPrecoMaterial() { return precoMaterial; }
    public void setPrecoMaterial(BigDecimal precoMaterial) { 
        this.precoMaterial = precoMaterial; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getLocalizacaoEspecifica() { return localizacaoEspecifica; }
    public void setLocalizacaoEspecifica(String localizacaoEspecifica) { 
        this.localizacaoEspecifica = localizacaoEspecifica; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemCoffeeBreak() { return temCoffeeBreak; }
    public void setTemCoffeeBreak(Boolean temCoffeeBreak) { 
        this.temCoffeeBreak = temCoffeeBreak; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getHorarioCoffeeBreak() { return horarioCoffeeBreak; }
    public void setHorarioCoffeeBreak(String horarioCoffeeBreak) { 
        this.horarioCoffeeBreak = horarioCoffeeBreak; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getPermiteAuditoria() { return permiteAuditoria; }
    public void setPermiteAuditoria(Boolean permiteAuditoria) { 
        this.permiteAuditoria = permiteAuditoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public BigDecimal getPrecoAuditoria() { return precoAuditoria; }
    public void setPrecoAuditoria(BigDecimal precoAuditoria) { 
        this.precoAuditoria = precoAuditoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getMetodologia() { return metodologia; }
    public void setMetodologia(String metodologia) { 
        this.metodologia = metodologia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getObjetivoAprendizado() { return objetivoAprendizado; }
    public void setObjetivoAprendizado(String objetivoAprendizado) { 
        this.objetivoAprendizado = objetivoAprendizado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Workshop: %s - %s - %s - %s", 
            titulo, instrutor, areaCultural, local);
    }
}
