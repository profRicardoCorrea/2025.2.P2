package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Danca extends AbstractEvent {
    private String coreografo;
    private String companhia;
    private String estiloDanca; // CLASSICA, CONTEMPORANEA, JAZZ, HIP_HOP, SALSA, TANGO
    private Integer duracaoMinutos;
    private List<String> bailarinos;
    private String musica;
    private String compositor;
    private String cenografia;
    private String figurino;
    private String iluminacao;
    private Boolean temSolista;
    private String bailarinoSolista;
    private String nivelTecnico; // INICIANTE, INTERMEDIARIO, AVANCADO, PROFISSIONAL
    private Boolean temInteracaoPublico;
    private String tipoApresentacao; // SOLO, DUETO, GRUPO, COREOGRAFIA_COLETIVA
    private String restricaoIdade;
    private Boolean permiteEntradaInfantil;

    public Danca() {
        super();
        this.bailarinos = new ArrayList<>();
        this.estiloDanca = "CONTEMPORANEA";
        this.temSolista = false;
        this.nivelTecnico = "INTERMEDIARIO";
        this.temInteracaoPublico = false;
        this.tipoApresentacao = "GRUPO";
        this.permiteEntradaInfantil = false;
    }

    public Danca(String titulo, String descricao, LocalDateTime dataHora, 
                 String local, String endereco, String cidade, String estado, 
                 BigDecimal preco, Integer capacidade, String categoria,
                 String coreografo, String companhia, String estiloDanca) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.coreografo = coreografo;
        this.companhia = companhia;
        this.estiloDanca = estiloDanca;
        this.bailarinos = new ArrayList<>();
        this.temSolista = false;
        this.nivelTecnico = "INTERMEDIARIO";
        this.temInteracaoPublico = false;
        this.tipoApresentacao = "GRUPO";
        this.permiteEntradaInfantil = false;
    }

    @Override
    public String getTipoEvento() {
        return "DANÇA";
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Coreógrafo: %s, Companhia: %s, Estilo: %s, Duração: %d min", 
            coreografo, companhia, estiloDanca, duracaoMinutos);
    }

    // Métodos específicos para dança
    public void adicionarBailarino(String bailarino) {
        if (!bailarinos.contains(bailarino)) {
            bailarinos.add(bailarino);
        }
    }

    public void removerBailarino(String bailarino) {
        bailarinos.remove(bailarino);
    }

    public boolean isDancaClassica() {
        return "CLASSICA".equals(estiloDanca);
    }

    public boolean isDancaContemporanea() {
        return "CONTEMPORANEA".equals(estiloDanca);
    }

    public boolean isDancaJazz() {
        return "JAZZ".equals(estiloDanca);
    }

    public boolean isDancaHipHop() {
        return "HIP_HOP".equals(estiloDanca);
    }

    public boolean isDancaSalsa() {
        return "SALSA".equals(estiloDanca);
    }

    public boolean isDancaTango() {
        return "TANGO".equals(estiloDanca);
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

    public boolean isApresentacaoSolo() {
        return "SOLO".equals(tipoApresentacao);
    }

    public boolean isApresentacaoDueto() {
        return "DUETO".equals(tipoApresentacao);
    }

    public boolean isApresentacaoGrupo() {
        return "GRUPO".equals(tipoApresentacao);
    }

    public boolean isApresentacaoColetiva() {
        return "COREOGRAFIA_COLETIVA".equals(tipoApresentacao);
    }

    // Getters e Setters específicos
    public String getCoreografo() { return coreografo; }
    public void setCoreografo(String coreografo) { 
        this.coreografo = coreografo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCompanhia() { return companhia; }
    public void setCompanhia(String companhia) { 
        this.companhia = companhia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEstiloDanca() { return estiloDanca; }
    public void setEstiloDanca(String estiloDanca) { 
        this.estiloDanca = estiloDanca; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { 
        this.duracaoMinutos = duracaoMinutos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getBailarinos() { return new ArrayList<>(bailarinos); }
    public void setBailarinos(List<String> bailarinos) { 
        this.bailarinos = new ArrayList<>(bailarinos); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getMusica() { return musica; }
    public void setMusica(String musica) { 
        this.musica = musica; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCompositor() { return compositor; }
    public void setCompositor(String compositor) { 
        this.compositor = compositor; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCenografia() { return cenografia; }
    public void setCenografia(String cenografia) { 
        this.cenografia = cenografia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getFigurino() { return figurino; }
    public void setFigurino(String figurino) { 
        this.figurino = figurino; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getIluminacao() { return iluminacao; }
    public void setIluminacao(String iluminacao) { 
        this.iluminacao = iluminacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemSolista() { return temSolista; }
    public void setTemSolista(Boolean temSolista) { 
        this.temSolista = temSolista; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getBailarinoSolista() { return bailarinoSolista; }
    public void setBailarinoSolista(String bailarinoSolista) { 
        this.bailarinoSolista = bailarinoSolista; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getNivelTecnico() { return nivelTecnico; }
    public void setNivelTecnico(String nivelTecnico) { 
        this.nivelTecnico = nivelTecnico; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemInteracaoPublico() { return temInteracaoPublico; }
    public void setTemInteracaoPublico(Boolean temInteracaoPublico) { 
        this.temInteracaoPublico = temInteracaoPublico; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoApresentacao() { return tipoApresentacao; }
    public void setTipoApresentacao(String tipoApresentacao) { 
        this.tipoApresentacao = tipoApresentacao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getRestricaoIdade() { return restricaoIdade; }
    public void setRestricaoIdade(String restricaoIdade) { 
        this.restricaoIdade = restricaoIdade; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getPermiteEntradaInfantil() { return permiteEntradaInfantil; }
    public void setPermiteEntradaInfantil(Boolean permiteEntradaInfantil) { 
        this.permiteEntradaInfantil = permiteEntradaInfantil; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Dança: %s - %s - %s - %s", 
            titulo, coreografo, estiloDanca, local);
    }
}
