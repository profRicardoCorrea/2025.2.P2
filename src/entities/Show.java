package entities;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Show extends AbstractEvent {
    private String artistaPrincipal;
    private List<String> artistasConvidados;
    private String generoMusical;
    private Integer duracaoMinutos;
    private String tipoShow; // AO_VIVO, DJ, BANDA, SOLO
    private Boolean temAbertura;
    private String artistaAbertura;
    private String equipamentoEspecial;
    private Boolean temVip;
    private BigDecimal precoVip;
    private String restricaoIdade;
    private Boolean permiteEntradaInfantil;

    public Show() {
        super();
        this.artistasConvidados = new ArrayList<>();
        this.tipoShow = "AO_VIVO";
        this.temAbertura = false;
        this.temVip = false;
        this.permiteEntradaInfantil = false;
    }

    public Show(String titulo, String descricao, LocalDateTime dataHora, 
                String local, String endereco, String cidade, String estado, 
                BigDecimal preco, Integer capacidade, String categoria,
                String artistaPrincipal, String generoMusical) {
        super(titulo, descricao, dataHora, local, endereco, cidade, estado, preco, capacidade, categoria);
        this.artistaPrincipal = artistaPrincipal;
        this.generoMusical = generoMusical;
        this.artistasConvidados = new ArrayList<>();
        this.tipoShow = "AO_VIVO";
        this.temAbertura = false;
        this.temVip = false;
        this.permiteEntradaInfantil = false;
    }

    @Override
    public String getTipoEvento() {
        return "SHOW";
    }

    @Override
    public String getDetalhesEspecificos() {
        return String.format("Artista: %s, Gênero: %s, Duração: %d min, Tipo: %s", 
            artistaPrincipal, generoMusical, duracaoMinutos, tipoShow);
    }

    // Métodos específicos para shows
    public void adicionarArtistaConvidado(String artista) {
        if (!artistasConvidados.contains(artista)) {
            artistasConvidados.add(artista);
        }
    }

    public void removerArtistaConvidado(String artista) {
        artistasConvidados.remove(artista);
    }

    public boolean isShowAoVivo() {
        return "AO_VIVO".equals(tipoShow);
    }

    public boolean isShowDJ() {
        return "DJ".equals(tipoShow);
    }

    public boolean isShowBanda() {
        return "BANDA".equals(tipoShow);
    }

    public boolean isShowSolo() {
        return "SOLO".equals(tipoShow);
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

    // Getters e Setters específicos
    public String getArtistaPrincipal() { return artistaPrincipal; }
    public void setArtistaPrincipal(String artistaPrincipal) { 
        this.artistaPrincipal = artistaPrincipal; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<String> getArtistasConvidados() { return new ArrayList<>(artistasConvidados); }
    public void setArtistasConvidados(List<String> artistasConvidados) { 
        this.artistasConvidados = new ArrayList<>(artistasConvidados); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getGeneroMusical() { return generoMusical; }
    public void setGeneroMusical(String generoMusical) { 
        this.generoMusical = generoMusical; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { 
        this.duracaoMinutos = duracaoMinutos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoShow() { return tipoShow; }
    public void setTipoShow(String tipoShow) { 
        this.tipoShow = tipoShow; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemAbertura() { return temAbertura; }
    public void setTemAbertura(Boolean temAbertura) { 
        this.temAbertura = temAbertura; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getArtistaAbertura() { return artistaAbertura; }
    public void setArtistaAbertura(String artistaAbertura) { 
        this.artistaAbertura = artistaAbertura; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEquipamentoEspecial() { return equipamentoEspecial; }
    public void setEquipamentoEspecial(String equipamentoEspecial) { 
        this.equipamentoEspecial = equipamentoEspecial; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getTemVip() { return temVip; }
    public void setTemVip(Boolean temVip) { 
        this.temVip = temVip; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public BigDecimal getPrecoVip() { return precoVip; }
    public void setPrecoVip(BigDecimal precoVip) { 
        this.precoVip = precoVip; 
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
        return String.format("Show: %s - %s - %s - %s", 
            titulo, artistaPrincipal, generoMusical, local);
    }
}
