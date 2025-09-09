package entities;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

public abstract class AbstractEvent {
    protected Long id;
    protected String titulo;
    protected String descricao;
    protected LocalDateTime dataHora;
    protected LocalDate dataInicio;
    protected LocalDate dataFim;
    protected String local;
    protected String endereco;
    protected String cidade;
    protected String estado;
    protected String cep;
    protected BigDecimal preco;
    protected Integer capacidade;
    protected Integer ingressosVendidos;
    protected String categoria;
    protected String status; // ATIVO, CANCELADO, ENCERRADO
    protected String imagemUrl;
    protected LocalDateTime dataCriacao;
    protected LocalDateTime dataAtualizacao;

    // Construtor padrão
    public AbstractEvent() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.status = "ATIVO";
        this.ingressosVendidos = 0;
    }

    // Construtor com parâmetros principais
    public AbstractEvent(String titulo, String descricao, LocalDateTime dataHora, 
                        String local, String endereco, String cidade, String estado, 
                        BigDecimal preco, Integer capacidade, String categoria) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.preco = preco;
        this.capacidade = capacidade;
        this.categoria = categoria;
    }

    // Métodos abstratos que devem ser implementados pelas subclasses
    public abstract String getTipoEvento();
    public abstract String getDetalhesEspecificos();

    // Métodos para verificar disponibilidade
    public boolean temIngressosDisponiveis() {
        return ingressosVendidos < capacidade;
    }

    public Integer getIngressosDisponiveis() {
        return capacidade - ingressosVendidos;
    }

    public boolean estaAtivo() {
        return "ATIVO".equals(status);
    }

    public boolean estaEncerrado() {
        return "ENCERRADO".equals(status);
    }

    public boolean estaCancelado() {
        return "CANCELADO".equals(status);
    }

    // Métodos para venda de ingressos
    public boolean venderIngresso() {
        if (temIngressosDisponiveis() && estaAtivo()) {
            ingressosVendidos++;
            dataAtualizacao = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public boolean cancelarIngresso() {
        if (ingressosVendidos > 0) {
            ingressosVendidos--;
            dataAtualizacao = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { 
        this.dataHora = dataHora; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { 
        this.dataInicio = dataInicio; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { 
        this.dataFim = dataFim; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getLocal() { return local; }
    public void setLocal(String local) { 
        this.local = local; 
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

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { 
        this.preco = preco; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { 
        this.capacidade = capacidade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getIngressosVendidos() { return ingressosVendidos; }
    public void setIngressosVendidos(Integer ingressosVendidos) { 
        this.ingressosVendidos = ingressosVendidos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { 
        this.imagemUrl = imagemUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    @Override
    public String toString() {
        return String.format("Evento: %s - %s - %s - %s", 
            titulo, categoria, local, dataHora != null ? dataHora.toString() : "Data não definida");
    }
}
