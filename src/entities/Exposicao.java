package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Exposicao extends AbstractEvent {
    private String curador;
    private String tipoExposicao; // PERMANENTE, TEMPORARIA, ITINERANTE
    private List<String> artistas;
    private String periodoHistorico;
    private String tecnicaArtistica;
    private Integer numeroObras;
    private String catalogoUrl;
    private Boolean temVisitaGuiada;
    private String horarioVisita;

    public Exposicao() {
        super();
        this.artistas = new ArrayList<>();
        this.tipoExposicao = "TEMPORARIA";
        this.temVisitaGuiada = false;
    }

    public Exposicao(String titulo, String descricao, LocalDateTime dataHora, 
                     String local, String endereco, String cidade, String estado, 
                     BigDecimal preco, Integer capacidade, String categoria,
                     String curador, String tipoExposicao) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.curador = curador;
        this.tipoExposicao = tipoExposicao;
        this.artistas = new ArrayList<>();
        this.temVisitaGuiada = false;
    }

    @Override
    public String getTipoEvento() {
        return "EXPOSIÇÃO";
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Curador: %s, Tipo: %s, Artistas: %s, Obras: %d", 
            curador, tipoExposicao, String.join(", ", artistas), numeroObras);
    }

    // Métodos específicos para exposições
    public void adicionarArtista(String artista) {
        if (!artistas.contains(artista)) {
            artistas.add(artista);
        }
    }

    public void removerArtista(String artista) {
        artistas.remove(artista);
    }

    public boolean isExposicaoPermanente() {
        return "PERMANENTE".equals(tipoExposicao);
    }

    public boolean isExposicaoTemporaria() {
        return "TEMPORARIA".equals(tipoExposicao);
    }

    public boolean isExposicaoItinerante() {
        return "ITINERANTE".equals(tipoExposicao);
    }

    // Getters e Setters específicos
    public String getCurador() { return curador; }
    public void setCurador(String curador) { 
        this.curador = curador; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoExposicao() { return tipoExposicao; }
    public void setTipoExposicao(String tipoExposicao) { 
        this.tipoExposicao = tipoExposicao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getArtistas() { return new ArrayList<>(artistas); }
    public void setArtistas(List<String> artistas) { 
        this.artistas = new ArrayList<>(artistas); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPeriodoHistorico() { return periodoHistorico; }
    public void setPeriodoHistorico(String periodoHistorico) { 
        this.periodoHistorico = periodoHistorico; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTecnicaArtistica() { return tecnicaArtistica; }
    public void setTecnicaArtistica(String tecnicaArtistica) { 
        this.tecnicaArtistica = tecnicaArtistica; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getNumeroObras() { return numeroObras; }
    public void setNumeroObras(Integer numeroObras) { 
        this.numeroObras = numeroObras; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCatalogoUrl() { return catalogoUrl; }
    public void setCatalogoUrl(String catalogoUrl) { 
        this.catalogoUrl = catalogoUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemVisitaGuiada() { return temVisitaGuiada; }
    public void setTemVisitaGuiada(Boolean temVisitaGuiada) { 
        this.temVisitaGuiada = temVisitaGuiada; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getHorarioVisita() { return horarioVisita; }
    public void setHorarioVisita(String horarioVisita) { 
        this.horarioVisita = horarioVisita; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Exposição: %s - Curador: %s - %s - %s", 
            titulo, curador, tipoExposicao, local);
    }
}
