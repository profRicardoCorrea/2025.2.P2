package entities;

import java.time.LocalDateTime;

public class Avaliacao {
    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private Integer nota; // 1 a 5
    private String comentario;
    private String categoriaAvaliacao; // GERAL, PRODUCAO, ARTISTAS, LOCAL, PREÇO
    private LocalDateTime dataAvaliacao;
    private Boolean anonima;
    private Boolean moderada;
    private String status; // PENDENTE, APROVADA, REJEITADA
    private String respostaOrganizador;
    private LocalDateTime dataResposta;
    private Boolean util;
    private Integer numeroVotosUtil;

    public Avaliacao() {
        this.dataAvaliacao = LocalDateTime.now();
        this.anonima = false;
        this.moderada = false;
        this.status = "PENDENTE";
        this.util = false;
        this.numeroVotosUtil = 0;
    }

    public Avaliacao(Long eventoId, Long usuarioId, Integer nota, String comentario) {
        this();
        this.eventoId = eventoId;
        this.usuarioId = usuarioId;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Métodos específicos para avaliações
    public boolean isNotaValida() {
        return nota != null && nota >= 1 && nota <= 5;
    }

    public String getNotaFormatada() {
        if (isNotaValida()) {
            StringBuilder estrelas = new StringBuilder();
            for (int i = 1; i <= 5; i++) {
                if (i <= nota) {
                    estrelas.append("★");
                } else {
                    estrelas.append("☆");
                }
            }
            return estrelas.toString();
        }
        return "Nota inválida";
    }

    public String getNotaTexto() {
        if (nota == null) return "Não avaliado";
        switch (nota) {
            case 1: return "Péssimo";
            case 2: return "Ruim";
            case 3: return "Regular";
            case 4: return "Bom";
            case 5: return "Excelente";
            default: return "Nota inválida";
        }
    }

    public boolean isPendente() {
        return "PENDENTE".equals(status);
    }

    public boolean isAprovada() {
        return "APROVADA".equals(status);
    }

    public boolean isRejeitada() {
        return "REJEITADA".equals(status);
    }

    public boolean podeSerModerada() {
        return isPendente();
    }

    public void aprovar() {
        if (podeSerModerada()) {
            this.status = "APROVADA";
        }
    }

    public void rejeitar() {
        if (podeSerModerada()) {
            this.status = "REJEITADA";
        }
    }

    public void adicionarResposta(String resposta) {
        this.respostaOrganizador = resposta;
        this.dataResposta = LocalDateTime.now();
    }

    public void marcarComoUtil() {
        this.util = true;
        this.numeroVotosUtil++;
    }

    public void desmarcarComoUtil() {
        if (this.util) {
            this.util = false;
            this.numeroVotosUtil = Math.max(0, this.numeroVotosUtil - 1);
        }
    }

    public boolean temResposta() {
        return respostaOrganizador != null && !respostaOrganizador.isEmpty();
    }

    public String getComentarioExibicao() {
        if (anonima) {
            return "Usuário Anônimo: " + comentario;
        }
        return comentario;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { 
        this.nota = nota; 
        this.dataAvaliacao = LocalDateTime.now();
    }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { 
        this.comentario = comentario; 
        this.dataAvaliacao = LocalDateTime.now();
    }

    public String getCategoriaAvaliacao() { return categoriaAvaliacao; }
    public void setCategoriaAvaliacao(String categoriaAvaliacao) { 
        this.categoriaAvaliacao = categoriaAvaliacao; 
        this.dataAvaliacao = LocalDateTime.now();
    }

    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }

    public Boolean getAnonima() { return anonima; }
    public void setAnonima(Boolean anonima) { this.anonima = anonima; }

    public Boolean getModerada() { return moderada; }
    public void setModerada(Boolean moderada) { this.moderada = moderada; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRespostaOrganizador() { return respostaOrganizador; }
    public void setRespostaOrganizador(String respostaOrganizador) { this.respostaOrganizador = respostaOrganizador; }

    public LocalDateTime getDataResposta() { return dataResposta; }
    public void setDataResposta(LocalDateTime dataResposta) { this.dataResposta = dataResposta; }

    public Boolean getUtil() { return util; }
    public void setUtil(Boolean util) { this.util = util; }

    public Integer getNumeroVotosUtil() { return numeroVotosUtil; }
    public void setNumeroVotosUtil(Integer numeroVotosUtil) { this.numeroVotosUtil = numeroVotosUtil; }

    @Override
    public String toString() {
        return String.format("Avaliação: %s - %s - %s", 
            getNotaFormatada(), comentario != null ? comentario.substring(0, Math.min(comentario.length(), 30)) + "..." : "Sem comentário", status);
    }
}
