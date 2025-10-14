package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Evento extends AbstractEvent {
    private String organizador;
    private String tipoEvento; // FESTIVAL, FEIRA, CONFERENCIA, WORKSHOP, APRESENTACAO
    private String tema;
    private String publicoAlvo;
    private String formato; // PRESENCIAL, ONLINE, HIBRIDO
    private Boolean gratuito;
    private String formaPagamento;
    private String politicaCancelamento;
    private String politicaReembolso;
    private String termosUso;
    private String politicaPrivacidade;
    private String hashtags;
    private String streamingUrl;
    private String plataformaOnline;
    private String linkInscricao;
    private String linkMaterial;
    private String certificado;
    private Boolean temCertificado;
    private String tipoCertificado;
    private String patrocinadores;
    private String apoiadores;
    private String parceiros;
    private String contatoEmergencia;
    private String telefoneEmergencia;
    private String emailEmergencia;
    private String observacoesEspeciais;
    private String checklist;
    private String cronograma;
    private String mapaEvento;
    private String instrucoesParticipacao;
    private String regrasComportamento;
    private String restricoesIdade;
    private String restricoesVestimenta;
    private String restricoesAlimentacao;
    private String restricoesFotografia;
    private String restricoesGravacao;
    private String restricoesAcessibilidade;
    private String restricoesMobilidade;
    private String restricoesVisuais;
    private String restricoesAuditivas;
    private String restricoesMotoras;
    private String restricoesCognitivas;
    private String restricoesPsicologicas;
    private String restricoesMedicas;
    private String restricoesReligiosas;
    private String restricoesCulturais;
    private String restricoesSociais;
    private String restricoesEconomicas;
    private String restricoesGeograficas;
    private String restricoesTemporais;
    private String restricoesTecnicas;
    private String restricoesLegais;
    private String restricoesFiscais;
    private String restricoesBancarias;
    private String restricoesSeguranca;
    private String restricoesSaude;
    private String restricoesAmbientais;
    private String restricoesSustentabilidade;
    private String restricoesResponsabilidade;
    private String restricoesTransparencia;
    private String restricoesGovernanca;
    private String restricoesCompliance;
    private String restricoesAuditoria;
    private String restricoesControle;
    private String restricoesMonitoramento;
    private String restricoesAvaliacao;
    private String restricoesFeedback;
    private String restricoesMelhoria;
    private String restricoesInovacao;
    private String restricoesCriatividade;
    private String restricoesQualidade;
    private String restricoesEficiencia;
    private String restricoesEficacia;
    private String restricoesProdutividade;
    private String restricoesRentabilidade;
    private String restricoesLucratividade;

    public Evento() {
        super();
        this.tipoEvento = "APRESENTACAO";
        this.formato = "PRESENCIAL";
        this.gratuito = false;
        this.temCertificado = false;
    }

    public Evento(String titulo, String descricao, LocalDateTime dataHora, 
                  String local, String endereco, String cidade, String estado, 
                  BigDecimal preco, Integer capacidade, String categoria,
                  String organizador, String tipoEvento) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.organizador = organizador;
        this.tipoEvento = tipoEvento;
        this.formato = "PRESENCIAL";
        this.gratuito = false;
        this.temCertificado = false;
    }

     
    public String getTipoEvento() {
        return this.tipoEvento;
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Organizador: %s, Tipo: %s, Formato: %s, %s", 
            organizador, tipoEvento, formato, gratuito ? "Gratuito" : "Pago");
    }

    // Métodos específicos para eventos
    public boolean isFestival() {
        return "FESTIVAL".equals(tipoEvento);
    }

    public boolean isFeira() {
        return "FEIRA".equals(tipoEvento);
    }

    public boolean isConferencia() {
        return "CONFERENCIA".equals(tipoEvento);
    }

    public boolean isWorkshop() {
        return "WORKSHOP".equals(tipoEvento);
    }

    public boolean isApresentacao() {
        return "APRESENTACAO".equals(tipoEvento);
    }

    public boolean isPresencial() {
        return "PRESENCIAL".equals(formato);
    }

    public boolean isOnline() {
        return "ONLINE".equals(formato);
    }

    public boolean isHibrido() {
        return "HIBRIDO".equals(formato);
    }

    public boolean isGratuito() {
        return gratuito != null && gratuito;
    }

    public boolean temCertificado() {
        return temCertificado != null && temCertificado;
    }

    public String getFormatoFormatado() {
        switch (formato) {
            case "PRESENCIAL": return "Presencial";
            case "ONLINE": return "Online";
            case "HIBRIDO": return "Híbrido";
            default: return formato;
        }
    }

    public String getTipoEventoFormatado() {
        switch (tipoEvento) {
            case "FESTIVAL": return "Festival";
            case "FEIRA": return "Feira";
            case "CONFERENCIA": return "Conferência";
            case "WORKSHOP": return "Workshop";
            case "APRESENTACAO": return "Apresentação";
            default: return tipoEvento;
        }
    }

    public String getPrecoFormatado() {
        if (isGratuito()) {
            return "Gratuito";
        }
        if (preco != null) {
            return String.format("R$ %.2f", preco);
        }
        return "Preço não informado";
    }

    public boolean temStreaming() {
        return streamingUrl != null && !streamingUrl.isEmpty();
    }

    public boolean temInscricaoOnline() {
        return linkInscricao != null && !linkInscricao.isEmpty();
    }

    public boolean temMaterialOnline() {
        return linkMaterial != null && !linkMaterial.isEmpty();
    }

    public boolean temPatrocinadores() {
        return patrocinadores != null && !patrocinadores.isEmpty();
    }

    public boolean temApoiadores() {
        return apoiadores != null && !apoiadores.isEmpty();
    }

    public boolean temParceiros() {
        return parceiros != null && !parceiros.isEmpty();
    }

    public boolean temContatoEmergencia() {
        return contatoEmergencia != null && !contatoEmergencia.isEmpty();
    }

    public boolean temObservacoesEspeciais() {
        return observacoesEspeciais != null && !observacoesEspeciais.isEmpty();
    }

    public boolean temChecklist() {
        return checklist != null && !checklist.isEmpty();
    }

    public boolean temCronograma() {
        return cronograma != null && !cronograma.isEmpty();
    }

    public boolean temMapaEvento() {
        return mapaEvento != null && !mapaEvento.isEmpty();
    }

    public boolean temInstrucoesParticipacao() {
        return instrucoesParticipacao != null && !instrucoesParticipacao.isEmpty();
    }

    public boolean temRegrasComportamento() {
        return regrasComportamento != null && !regrasComportamento.isEmpty();
    }

    public boolean temRestricoesIdade() {
        return restricoesIdade != null && !restricoesIdade.isEmpty();
    }

    public boolean temRestricoesVestimenta() {
        return restricoesVestimenta != null && !restricoesVestimenta.isEmpty();
    }

    public boolean temRestricoesAlimentacao() {
        return restricoesAlimentacao != null && !restricoesAlimentacao.isEmpty();
    }

    public boolean temRestricoesFotografia() {
        return restricoesFotografia != null && !restricoesFotografia.isEmpty();
    }

    public boolean temRestricoesGravacao() {
        return restricoesGravacao != null && !restricoesGravacao.isEmpty();
    }

    public boolean temRestricoesAcessibilidade() {
        return restricoesAcessibilidade != null && !restricoesAcessibilidade.isEmpty();
    }

    public boolean temRestricoesMobilidade() {
        return restricoesMobilidade != null && !restricoesMobilidade.isEmpty();
    }

    public boolean temRestricoesVisuais() {
        return restricoesVisuais != null && !restricoesVisuais.isEmpty();
    }

    public boolean temRestricoesAuditivas() {
        return restricoesAuditivas != null && !restricoesAuditivas.isEmpty();
    }

    public boolean temRestricoesMotoras() {
        return restricoesMotoras != null && !restricoesMotoras.isEmpty();
    }

    public boolean temRestricoesCognitivas() {
        return restricoesCognitivas != null && !restricoesCognitivas.isEmpty();
    }

    public boolean temRestricoesPsicologicas() {
        return restricoesPsicologicas != null && !restricoesPsicologicas.isEmpty();
    }

    public boolean temRestricoesMedicas() {
        return restricoesMedicas != null && !restricoesMedicas.isEmpty();
    }

    public boolean temRestricoesReligiosas() {
        return restricoesReligiosas != null && !restricoesReligiosas.isEmpty();
    }

    public boolean temRestricoesCulturais() {
        return restricoesCulturais != null && !restricoesCulturais.isEmpty();
    }

    public boolean temRestricoesSociais() {
        return restricoesSociais != null && !restricoesSociais.isEmpty();
    }

    public boolean temRestricoesEconomicas() {
        return restricoesEconomicas != null && !restricoesEconomicas.isEmpty();
    }

    public boolean temRestricoesGeograficas() {
        return restricoesGeograficas != null && !restricoesGeograficas.isEmpty();
    }

    public boolean temRestricoesTemporais() {
        return restricoesTemporais != null && !restricoesTemporais.isEmpty();
    }

    public boolean temRestricoesTecnicas() {
        return restricoesTecnicas != null && !restricoesTecnicas.isEmpty();
    }

    public boolean temRestricoesLegais() {
        return restricoesLegais != null && !restricoesLegais.isEmpty();
    }

    public boolean temRestricoesFiscais() {
        return restricoesFiscais != null && !restricoesFiscais.isEmpty();
    }

    public boolean temRestricoesBancarias() {
        return restricoesBancarias != null && !restricoesBancarias.isEmpty();
    }

    public boolean temRestricoesSeguranca() {
        return restricoesSeguranca != null && !restricoesSeguranca.isEmpty();
    }

    public boolean temRestricoesSaude() {
        return restricoesSaude != null && !restricoesSaude.isEmpty();
    }

    public boolean temRestricoesAmbientais() {
        return restricoesAmbientais != null && !restricoesAmbientais.isEmpty();
    }

    public boolean temRestricoesSustentabilidade() {
        return restricoesSustentabilidade != null && !restricoesSustentabilidade.isEmpty();
    }

    public boolean temRestricoesResponsabilidade() {
        return restricoesResponsabilidade != null && !restricoesResponsabilidade.isEmpty();
    }

    public boolean temRestricoesTransparencia() {
        return restricoesTransparencia != null && !restricoesTransparencia.isEmpty();
    }

    public boolean temRestricoesGovernanca() {
        return restricoesGovernanca != null && !restricoesGovernanca.isEmpty();
    }

    public boolean temRestricoesCompliance() {
        return restricoesCompliance != null && !restricoesCompliance.isEmpty();
    }

    public boolean temRestricoesAuditoria() {
        return restricoesAuditoria != null && !restricoesAuditoria.isEmpty();
    }

    public boolean temRestricoesControle() {
        return restricoesControle != null && !restricoesControle.isEmpty();
    }

    public boolean temRestricoesMonitoramento() {
        return restricoesMonitoramento != null && !restricoesMonitoramento.isEmpty();
    }

    public boolean temRestricoesAvaliacao() {
        return restricoesAvaliacao != null && !restricoesAvaliacao.isEmpty();
    }

    public boolean temRestricoesFeedback() {
        return restricoesFeedback != null && !restricoesFeedback.isEmpty();
    }

    public boolean temRestricoesMelhoria() {
        return restricoesMelhoria != null && !restricoesMelhoria.isEmpty();
    }

    public boolean temRestricoesInovacao() {
        return restricoesInovacao != null && !restricoesInovacao.isEmpty();
    }

    public boolean temRestricoesCriatividade() {
        return restricoesCriatividade != null && !restricoesCriatividade.isEmpty();
    }

    public boolean temRestricoesQualidade() {
        return restricoesQualidade != null && !restricoesQualidade.isEmpty();
    }

    public boolean temRestricoesEficiencia() {
        return restricoesEficiencia != null && !restricoesEficiencia.isEmpty();
    }

    public boolean temRestricoesEficacia() {
        return restricoesEficacia != null && !restricoesEficacia.isEmpty();
    }

    public boolean temRestricoesProdutividade() {
        return restricoesProdutividade != null && !restricoesProdutividade.isEmpty();
    }

    public boolean temRestricoesRentabilidade() {
        return restricoesRentabilidade != null && !restricoesRentabilidade.isEmpty();
    }

    public boolean temRestricoesLucratividade() {
        return restricoesLucratividade != null && !restricoesLucratividade.isEmpty();
    }

    // Getters e Setters específicos
    public String getOrganizador() { return organizador; }
    public void setOrganizador(String organizador) { 
        this.organizador = organizador; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { 
        this.tipoEvento = tipoEvento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTema() { return tema; }
    public void setTema(String tema) { 
        this.tema = tema; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(String publicoAlvo) { 
        this.publicoAlvo = publicoAlvo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { 
        this.formato = formato; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getGratuito() { return gratuito; }
    public void setGratuito(Boolean gratuito) { 
        this.gratuito = gratuito; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { 
        this.formaPagamento = formaPagamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPoliticaCancelamento() { return politicaCancelamento; }
    public void setPoliticaCancelamento(String politicaCancelamento) { 
        this.politicaCancelamento = politicaCancelamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPoliticaReembolso() { return politicaReembolso; }
    public void setPoliticaReembolso(String politicaReembolso) { 
        this.politicaReembolso = politicaReembolso; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTermosUso() { return termosUso; }
    public void setTermosUso(String termosUso) { 
        this.termosUso = termosUso; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPoliticaPrivacidade() { return politicaPrivacidade; }
    public void setPoliticaPrivacidade(String politicaPrivacidade) { 
        this.politicaPrivacidade = politicaPrivacidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getHashtags() { return hashtags; }
    public void setHashtags(String hashtags) { 
        this.hashtags = hashtags; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getStreamingUrl() { return streamingUrl; }
    public void setStreamingUrl(String streamingUrl) { 
        this.streamingUrl = streamingUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPlataformaOnline() { return plataformaOnline; }
    public void setPlataformaOnline(String plataformaOnline) { 
        this.plataformaOnline = plataformaOnline; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getLinkInscricao() { return linkInscricao; }
    public void setLinkInscricao(String linkInscricao) { 
        this.linkInscricao = linkInscricao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getLinkMaterial() { return linkMaterial; }
    public void setLinkMaterial(String linkMaterial) { 
        this.linkMaterial = linkMaterial; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCertificado() { return certificado; }
    public void setCertificado(String certificado) { 
        this.certificado = certificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemCertificado() { return temCertificado; }
    public void setTemCertificado(Boolean temCertificado) { 
        this.temCertificado = temCertificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoCertificado() { return tipoCertificado; }
    public void setTipoCertificado(String tipoCertificado) { 
        this.tipoCertificado = tipoCertificado; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPatrocinadores() { return patrocinadores; }
    public void setPatrocinadores(String patrocinadores) { 
        this.patrocinadores = patrocinadores; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getApoiadores() { return apoiadores; }
    public void setApoiadores(String apoiadores) { 
        this.apoiadores = apoiadores; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getParceiros() { return parceiros; }
    public void setParceiros(String parceiros) { 
        this.parceiros = parceiros; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getContatoEmergencia() { return contatoEmergencia; }
    public void setContatoEmergencia(String contatoEmergencia) { 
        this.contatoEmergencia = contatoEmergencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTelefoneEmergencia() { return telefoneEmergencia; }
    public void setTelefoneEmergencia(String telefoneEmergencia) { 
        this.telefoneEmergencia = telefoneEmergencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmailEmergencia() { return emailEmergencia; }
    public void setEmailEmergencia(String emailEmergencia) { 
        this.emailEmergencia = emailEmergencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getObservacoesEspeciais() { return observacoesEspeciais; }
    public void setObservacoesEspeciais(String observacoesEspeciais) { 
        this.observacoesEspeciais = observacoesEspeciais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getChecklist() { return checklist; }
    public void setChecklist(String checklist) { 
        this.checklist = checklist; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCronograma() { return cronograma; }
    public void setCronograma(String cronograma) { 
        this.cronograma = cronograma; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getMapaEvento() { return mapaEvento; }
    public void setMapaEvento(String mapaEvento) { 
        this.mapaEvento = mapaEvento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getInstrucoesParticipacao() { return instrucoesParticipacao; }
    public void setInstrucoesParticipacao(String instrucoesParticipacao) { 
        this.instrucoesParticipacao = instrucoesParticipacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRegrasComportamento() { return regrasComportamento; }
    public void setRegrasComportamento(String regrasComportamento) { 
        this.regrasComportamento = regrasComportamento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesIdade() { return restricoesIdade; }
    public void setRestricoesIdade(String restricoesIdade) { 
        this.restricoesIdade = restricoesIdade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesVestimenta() { return restricoesVestimenta; }
    public void setRestricoesVestimenta(String restricoesVestimenta) { 
        this.restricoesVestimenta = restricoesVestimenta; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAlimentacao() { return restricoesAlimentacao; }
    public void setRestricoesAlimentacao(String restricoesAlimentacao) { 
        this.restricoesAlimentacao = restricoesAlimentacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesFotografia() { return restricoesFotografia; }
    public void setRestricoesFotografia(String restricoesFotografia) { 
        this.restricoesFotografia = restricoesFotografia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesGravacao() { return restricoesGravacao; }
    public void setRestricoesGravacao(String restricoesGravacao) { 
        this.restricoesGravacao = restricoesGravacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAcessibilidade() { return restricoesAcessibilidade; }
    public void setRestricoesAcessibilidade(String restricoesAcessibilidade) { 
        this.restricoesAcessibilidade = restricoesAcessibilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesMobilidade() { return restricoesMobilidade; }
    public void setRestricoesMobilidade(String restricoesMobilidade) { 
        this.restricoesMobilidade = restricoesMobilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesVisuais() { return restricoesVisuais; }
    public void setRestricoesVisuais(String restricoesVisuais) { 
        this.restricoesVisuais = restricoesVisuais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAuditivas() { return restricoesAuditivas; }
    public void setRestricoesAuditivas(String restricoesAuditivas) { 
        this.restricoesAuditivas = restricoesAuditivas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesMotoras() { return restricoesMotoras; }
    public void setRestricoesMotoras(String restricoesMotoras) { 
        this.restricoesMotoras = restricoesMotoras; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesCognitivas() { return restricoesCognitivas; }
    public void setRestricoesCognitivas(String restricoesCognitivas) { 
        this.restricoesCognitivas = restricoesCognitivas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesPsicologicas() { return restricoesPsicologicas; }
    public void setRestricoesPsicologicas(String restricoesPsicologicas) { 
        this.restricoesPsicologicas = restricoesPsicologicas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesMedicas() { return restricoesMedicas; }
    public void setRestricoesMedicas(String restricoesMedicas) { 
        this.restricoesMedicas = restricoesMedicas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesReligiosas() { return restricoesReligiosas; }
    public void setRestricoesReligiosas(String restricoesReligiosas) { 
        this.restricoesReligiosas = restricoesReligiosas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesCulturais() { return restricoesCulturais; }
    public void setRestricoesCulturais(String restricoesCulturais) { 
        this.restricoesCulturais = restricoesCulturais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesSociais() { return restricoesSociais; }
    public void setRestricoesSociais(String restricoesSociais) { 
        this.restricoesSociais = restricoesSociais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesEconomicas() { return restricoesEconomicas; }
    public void setRestricoesEconomicas(String restricoesEconomicas) { 
        this.restricoesEconomicas = restricoesEconomicas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesGeograficas() { return restricoesGeograficas; }
    public void setRestricoesGeograficas(String restricoesGeograficas) { 
        this.restricoesGeograficas = restricoesGeograficas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesTemporais() { return restricoesTemporais; }
    public void setRestricoesTemporais(String restricoesTemporais) { 
        this.restricoesTemporais = restricoesTemporais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesTecnicas() { return restricoesTecnicas; }
    public void setRestricoesTecnicas(String restricoesTecnicas) { 
        this.restricoesTecnicas = restricoesTecnicas; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesLegais() { return restricoesLegais; }
    public void setRestricoesLegais(String restricoesLegais) { 
        this.restricoesLegais = restricoesLegais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesFiscais() { return restricoesFiscais; }
    public void setRestricoesFiscais(String restricoesFiscais) { 
        this.restricoesFiscais = restricoesFiscais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesBancarias() { return restricoesBancarias; }
    public void setRestricoesBancarias(String restricoesBancarias) { 
        this.restricoesBancarias = restricoesBancarias; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesSeguranca() { return restricoesSeguranca; }
    public void setRestricoesSeguranca(String restricoesSeguranca) { 
        this.restricoesSeguranca = restricoesSeguranca; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesSaude() { return restricoesSaude; }
    public void setRestricoesSaude(String restricoesSaude) { 
        this.restricoesSaude = restricoesSaude; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAmbientais() { return restricoesAmbientais; }
    public void setRestricoesAmbientais(String restricoesAmbientais) { 
        this.restricoesAmbientais = restricoesAmbientais; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesSustentabilidade() { return restricoesSustentabilidade; }
    public void setRestricoesSustentabilidade(String restricoesSustentabilidade) { 
        this.restricoesSustentabilidade = restricoesSustentabilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesResponsabilidade() { return restricoesResponsabilidade; }
    public void setRestricoesResponsabilidade(String restricoesResponsabilidade) { 
        this.restricoesResponsabilidade = restricoesResponsabilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesTransparencia() { return restricoesTransparencia; }
    public void setRestricoesTransparencia(String restricoesTransparencia) { 
        this.restricoesTransparencia = restricoesTransparencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesGovernanca() { return restricoesGovernanca; }
    public void setRestricoesGovernanca(String restricoesGovernanca) { 
        this.restricoesGovernanca = restricoesGovernanca; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesCompliance() { return restricoesCompliance; }
    public void setRestricoesCompliance(String restricoesCompliance) { 
        this.restricoesCompliance = restricoesCompliance; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAuditoria() { return restricoesAuditoria; }
    public void setRestricoesAuditoria(String restricoesAuditoria) { 
        this.restricoesAuditoria = restricoesAuditoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesControle() { return restricoesControle; }
    public void setRestricoesControle(String restricoesControle) { 
        this.restricoesControle = restricoesControle; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesMonitoramento() { return restricoesMonitoramento; }
    public void setRestricoesMonitoramento(String restricoesMonitoramento) { 
        this.restricoesMonitoramento = restricoesMonitoramento; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesAvaliacao() { return restricoesAvaliacao; }
    public void setRestricoesAvaliacao(String restricoesAvaliacao) { 
        this.restricoesAvaliacao = restricoesAvaliacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesFeedback() { return restricoesFeedback; }
    public void setRestricoesFeedback(String restricoesFeedback) { 
        this.restricoesFeedback = restricoesFeedback; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesMelhoria() { return restricoesMelhoria; }
    public void setRestricoesMelhoria(String restricoesMelhoria) { 
        this.restricoesMelhoria = restricoesMelhoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesInovacao() { return restricoesInovacao; }
    public void setRestricoesInovacao(String restricoesInovacao) { 
        this.restricoesInovacao = restricoesInovacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesCriatividade() { return restricoesCriatividade; }
    public void setRestricoesCriatividade(String restricoesCriatividade) { 
        this.restricoesCriatividade = restricoesCriatividade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesQualidade() { return restricoesQualidade; }
    public void setRestricoesQualidade(String restricoesQualidade) { 
        this.restricoesQualidade = restricoesQualidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesEficiencia() { return restricoesEficiencia; }
    public void setRestricoesEficiencia(String restricoesEficiencia) { 
        this.restricoesEficiencia = restricoesEficiencia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesEficacia() { return restricoesEficacia; }
    public void setRestricoesEficacia(String restricoesEficacia) { 
        this.restricoesEficacia = restricoesEficacia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesProdutividade() { return restricoesProdutividade; }
    public void setRestricoesProdutividade(String restricoesProdutividade) { 
        this.restricoesProdutividade = restricoesProdutividade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesRentabilidade() { return restricoesRentabilidade; }
    public void setRestricoesRentabilidade(String restricoesRentabilidade) { 
        this.restricoesRentabilidade = restricoesRentabilidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricoesLucratividade() { return restricoesLucratividade; }
    public void setRestricoesLucratividade(String restricoesLucratividade) { 
        this.restricoesLucratividade = restricoesLucratividade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Evento: %s - %s - %s - %s", 
            titulo, organizador, getTipoEventoFormatado(), local);
    }
}
