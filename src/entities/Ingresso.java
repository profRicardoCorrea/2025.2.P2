package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

public class Ingresso {
    private String codigo;
    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private String tipoIngresso; // PADRAO, VIP, MEIA_ENTRADA, GRATUITO
    private BigDecimal preco;
    private String status; // RESERVADO, PAGO, CANCELADO, UTILIZADO
    private LocalDateTime dataCompra;
    private LocalDateTime dataUtilizacao;
    private LocalDateTime dataCancelamento;
    private String formaPagamento;
    private String observacoes;
    private Boolean transferivel;
    private String nomeBeneficiario;
    private String cpfBeneficiario;
    private String telefoneBeneficiario;
    private String emailBeneficiario;
    private String localRetirada;
    private String horarioRetirada;
    private String assento;
    private String setor;
    private String fila;
    private String portaEntrada;

    public Ingresso() {
        this.codigo = gerarCodigoUnico();
        this.status = "RESERVADO";
        this.dataCompra = LocalDateTime.now();
        this.transferivel = false;
    }

    public Ingresso(Long eventoId, Long usuarioId, String tipoIngresso, BigDecimal preco) {
        this();
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
        this.tipoIngresso = tipoIngresso;
        this.preco = preco;
    }

    // Métodos específicos para ingressos
    private String gerarCodigoUnico() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public boolean isReservado() {
        return "RESERVADO".equals(status);
    }

    public boolean isPago() {
        return "PAGO".equals(status);
    }

    public boolean isCancelado() {
        return "CANCELADO".equals(status);
    }

    public boolean isUtilizado() {
        return "UTILIZADO".equals(status);
    }

    public boolean isVip() {
        return "VIP".equals(tipoIngresso);
    }

    public boolean isMeiaEntrada() {
        return "MEIA_ENTRADA".equals(tipoIngresso);
    }

    public boolean isGratuito() {
        return "GRATUITO".equals(tipoIngresso);
    }

    public boolean podeSerUtilizado() {
        return isPago() && !isUtilizado() && !isCancelado();
    }

    public boolean podeSerCancelado() {
        return isReservado() || isPago();
    }

    public void confirmarPagamento() {
        if (isReservado()) {
            this.status = "PAGO";
        }
    }

    public void cancelarIngresso() {
        if (podeSerCancelado()) {
            this.status = "CANCELADO";
            this.dataCancelamento = LocalDateTime.now();
        }
    }

    public void utilizarIngresso() {
        if (podeSerUtilizado()) {
            this.status = "UTILIZADO";
            this.dataUtilizacao = LocalDateTime.now();
        }
    }

    public String getLocalizacaoAssento() {
        if (setor != null && fila != null && assento != null) {
            return String.format("Setor: %s, Fila: %s, Assento: %s", setor, fila, assento);
        } else if (setor != null && assento != null) {
            return String.format("Setor: %s, Assento: %s", setor, assento);
        } else if (assento != null) {
            return String.format("Assento: %s", assento);
        }
        return "Assento não definido";
    }

    public String getStatusFormatado() {
        switch (status) {
            case "RESERVADO": return "Reservado";
            case "PAGO": return "Pago";
            case "CANCELADO": return "Cancelado";
            case "UTILIZADO": return "Utilizado";
            default: return status;
        }
    }

    public String getTipoFormatado() {
        switch (tipoIngresso) {
            case "PADRAO": return "Padrão";
            case "VIP": return "VIP";
            case "MEIA_ENTRADA": return "Meia Entrada";
            case "GRATUITO": return "Gratuito";
            default: return tipoIngresso;
        }
    }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoIngresso() { return tipoIngresso; }
    public void setTipoIngresso(String tipoIngresso) { this.tipoIngresso = tipoIngresso; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDateTime dataCompra) { this.dataCompra = dataCompra; }

    public LocalDateTime getDataUtilizacao() { return dataUtilizacao; }
    public void setDataUtilizacao(LocalDateTime dataUtilizacao) { this.dataUtilizacao = dataUtilizacao; }

    public LocalDateTime getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDateTime dataCancelamento) { this.dataCancelamento = dataCancelamento; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Boolean getTransferivel() { return transferivel; }
    public void setTransferivel(Boolean transferivel) { this.transferivel = transferivel; }

    public String getNomeBeneficiario() { return nomeBeneficiario; }
    public void setNomeBeneficiario(String nomeBeneficiario) { this.nomeBeneficiario = nomeBeneficiario; }

    public String getCpfBeneficiario() { return cpfBeneficiario; }
    public void setCpfBeneficiario(String cpfBeneficiario) { this.cpfBeneficiario = cpfBeneficiario; }

    public String getTelefoneBeneficiario() { return telefoneBeneficiario; }
    public void setTelefoneBeneficiario(String telefoneBeneficiario) { this.telefoneBeneficiario = telefoneBeneficiario; }

    public String getEmailBeneficiario() { return emailBeneficiario; }
    public void setEmailBeneficiario(String emailBeneficiario) { this.emailBeneficiario = emailBeneficiario; }

    public String getLocalRetirada() { return localRetirada; }
    public void setLocalRetirada(String localRetirada) { this.localRetirada = localRetirada; }

    public String getHorarioRetirada() { return horarioRetirada; }
    public void setHorarioRetirada(String horarioRetirada) { this.horarioRetirada = horarioRetirada; }

    public String getAssento() { return assento; }
    public void setAssento(String assento) { this.assento = assento; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }

    public String getPortaEntrada() { return portaEntrada; }
    public void setPortaEntrada(String portaEntrada) { this.portaEntrada = portaEntrada; }

    @Override
    public String toString() {
        return String.format("Ingresso: %s - %s - %s - %s", 
            codigo, tipoIngresso, status, nomeBeneficiario != null ? nomeBeneficiario : "Não definido");
    }
}
