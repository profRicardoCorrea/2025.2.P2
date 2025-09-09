package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Teatro extends AbstractEvent {
    private String diretor;
    private String autor;
    private String companhia;
    private String generoTeatral; // DRAMA, COMEDIA, TRAGEDIA, MUSICAL, INFANTIL
    private Integer duracaoMinutos;
    private Integer numeroAtos;
    private List<String> elenco;
    private String sinopse;
    private String cenografia;
    private String figurino;
    private String iluminacao;
    private String sonoplastia;
    private Boolean temIntervalo;
    private Integer duracaoIntervalo;
    private String classificacaoIndicativa;
    private Boolean permiteEntradaInfantil;
    private String tipoTeatro; // PALCO_ITALIANO, ARENA, TEATRO_DE_RUA

    public Teatro() {
        super();
        this.elenco = new ArrayList<>();
        this.generoTeatral = "DRAMA";
        this.temIntervalo = false;
        this.permiteEntradaInfantil = false;
        this.tipoTeatro = "PALCO_ITALIANO";
    }

    public Teatro(String titulo, String descricao, LocalDateTime dataHora, 
                  String local, String endereco, String cidade, String estado, 
                  BigDecimal preco, Integer capacidade, String categoria,
                  String diretor, String autor, String companhia, String generoTeatral) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.diretor = diretor;
        this.autor = autor;
        this.companhia = companhia;
        this.generoTeatral = generoTeatral;
        this.elenco = new ArrayList<>();
        this.temIntervalo = false;
        this.permiteEntradaInfantil = false;
        this.tipoTeatro = "PALCO_ITALIANO";
    }

    @Override
    public String getTipoEvento() {
        return "TEATRO";
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Diretor: %s, Autor: %s, Companhia: %s, Gênero: %s, Duração: %d min", 
            diretor, autor, companhia, generoTeatral, duracaoMinutos);
    }

    // Métodos específicos para teatro
    public void adicionarAtor(String ator) {
        if (!elenco.contains(ator)) {
            elenco.add(ator);
        }
    }

    public void removerAtor(String ator) {
        elenco.remove(ator);
    }

    public boolean isTeatroInfantil() {
        return "INFANTIL".equals(generoTeatral);
    }

    public boolean isTeatroMusical() {
        return "MUSICAL".equals(generoTeatral);
    }

    public boolean isTeatroComedia() {
        return "COMEDIA".equals(generoTeatral);
    }

    public boolean isTeatroDrama() {
        return "DRAMA".equals(generoTeatral);
    }

    public boolean isTeatroTragedia() {
        return "TRAGEDIA".equals(generoTeatral);
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

    public String getDuracaoComIntervalo() {
        if (duracaoMinutos != null && temIntervalo && duracaoIntervalo != null) {
            return String.format("%s + %dmin de intervalo", getDuracaoFormatada(), duracaoIntervalo);
        }
        return getDuracaoFormatada();
    }

    // Getters e Setters específicos
    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { 
        this.diretor = diretor; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { 
        this.autor = autor; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCompanhia() { return companhia; }
    public void setCompanhia(String companhia) { 
        this.companhia = companhia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getGeneroTeatral() { return generoTeatral; }
    public void setGeneroTeatral(String generoTeatral) { 
        this.generoTeatral = generoTeatral; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { 
        this.duracaoMinutos = duracaoMinutos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getNumeroAtos() { return numeroAtos; }
    public void setNumeroAtos(Integer numeroAtos) { 
        this.numeroAtos = numeroAtos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getElenco() { return new ArrayList<>(elenco); }
    public void setElenco(List<String> elenco) { 
        this.elenco = new ArrayList<>(elenco); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { 
        this.sinopse = sinopse; 
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

    public String getSonoplastia() { return sonoplastia; }
    public void setSonoplastia(String sonoplastia) { 
        this.sonoplastia = sonoplastia; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemIntervalo() { return temIntervalo; }
    public void setTemIntervalo(Boolean temIntervalo) { 
        this.temIntervalo = temIntervalo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getDuracaoIntervalo() { return duracaoIntervalo; }
    public void setDuracaoIntervalo(Integer duracaoIntervalo) { 
        this.duracaoIntervalo = duracaoIntervalo; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getClassificacaoIndicativa() { return classificacaoIndicativa; }
    public void setClassificacaoIndicativa(String classificacaoIndicativa) { 
        this.classificacaoIndicativa = classificacaoIndicativa; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getPermiteEntradaInfantil() { return permiteEntradaInfantil; }
    public void setPermiteEntradaInfantil(Boolean permiteEntradaInfantil) { 
        this.permiteEntradaInfantil = permiteEntradaInfantil; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoTeatro() { return tipoTeatro; }
    public void setTipoTeatro(String tipoTeatro) { 
        this.tipoTeatro = tipoTeatro; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Teatro: %s - %s - %s - %s", 
            titulo, diretor, generoTeatral, local);
    }
}
