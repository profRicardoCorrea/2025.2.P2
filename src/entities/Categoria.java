package entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Categoria {
    private Long id;
    private String nome;
    private String descricao;
    private String cor;
    private String icone;
    private String slug;
    private Boolean ativa;
    private Integer ordem;
    private Long categoriaPaiId;
    private List<Categoria> subcategorias;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String metadados;
    private Integer numeroEventos;
    private String tags;
    private String imagemUrl;
    private Boolean destaque;
    private String tipoCategoria; // PRINCIPAL, SECUNDARIA, ESPECIAL

    public Categoria() {
        this.ativa = true;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.subcategorias = new ArrayList<>();
        this.destaque = false;
        this.tipoCategoria = "SECUNDARIA";
        this.numeroEventos = 0;
    }

    public Categoria(String nome, String descricao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.slug = gerarSlug(nome);
    }

    // Métodos específicos para categorias
    private String gerarSlug(String nome) {
        if (nome == null) return "";
        return nome.toLowerCase()
                  .replaceAll("[^a-z0-9\\s-]", "")
                  .replaceAll("\\s+", "-")
                  .replaceAll("-+", "-")
                  .trim();
    }

    public void adicionarSubcategoria(Categoria subcategoria) {
        if (!subcategorias.contains(subcategoria)) {
            subcategorias.add(subcategoria);
            subcategoria.setCategoriaPaiId(this.id);
        }
    }

    public void removerSubcategoria(Categoria subcategoria) {
        if (subcategorias.remove(subcategoria)) {
            subcategoria.setCategoriaPaiId(null);
        }
    }

    public boolean isCategoriaPrincipal() {
        return "PRINCIPAL".equals(tipoCategoria);
    }

    public boolean isCategoriaSecundaria() {
        return "SECUNDARIA".equals(tipoCategoria);
    }

    public boolean isCategoriaEspecial() {
        return "ESPECIAL".equals(tipoCategoria);
    }

    public boolean isCategoriaRaiz() {
        return categoriaPaiId == null;
    }

    public boolean isSubcategoria() {
        return categoriaPaiId != null;
    }

    public boolean temSubcategorias() {
        return !subcategorias.isEmpty();
    }

    public int getNivel() {
        if (isCategoriaRaiz()) {
            return 0;
        }
        return 1; // Simplificado - em um sistema real seria calculado recursivamente
    }

    public String getCaminhoCompleto() {
        if (isCategoriaRaiz()) {
            return nome;
        }
        return "..." + " > " + nome;
    }

    public void incrementarNumeroEventos() {
        this.numeroEventos++;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void decrementarNumeroEventos() {
        if (this.numeroEventos > 0) {
            this.numeroEventos--;
            this.dataAtualizacao = LocalDateTime.now();
        }
    }

    public String getCorFormatada() {
        if (cor != null && !cor.isEmpty()) {
            if (cor.startsWith("#")) {
                return cor;
            } else {
                return "#" + cor;
            }
        }
        return "#000000"; // Cor padrão preta
    }

    public String getTipoFormatado() {
        switch (tipoCategoria) {
            case "PRINCIPAL": return "Principal";
            case "SECUNDARIA": return "Secundária";
            case "ESPECIAL": return "Especial";
            default: return tipoCategoria;
        }
    }

    public boolean podeSerEditada() {
        return numeroEventos == 0 && !temSubcategorias();
    }

    public boolean podeSerExcluida() {
        return podeSerEditada();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { 
        this.nome = nome; 
        this.slug = gerarSlug(nome);
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getCor() { return cor; }
    public void setCor(String cor) { 
        this.cor = cor; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getIcone() { return icone; }
    public void setIcone(String icone) { 
        this.icone = icone; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { 
        this.ativa = ativa; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { 
        this.ordem = ordem; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Long getCategoriaPaiId() { return categoriaPaiId; }
    public void setCategoriaPaiId(Long categoriaPaiId) { 
        this.categoriaPaiId = categoriaPaiId; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<Categoria> getSubcategorias() { return new ArrayList<>(subcategorias); }
    public void setSubcategorias(List<Categoria> subcategorias) { 
        this.subcategorias = new ArrayList<>(subcategorias); 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    public String getMetadados() { return metadados; }
    public void setMetadados(String metadados) { 
        this.metadados = metadados; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Integer getNumeroEventos() { return numeroEventos; }
    public void setNumeroEventos(Integer numeroEventos) { 
        this.numeroEventos = numeroEventos; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTags() { return tags; }
    public void setTags(String tags) { 
        this.tags = tags; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { 
        this.imagemUrl = imagemUrl; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Boolean getDestaque() { return destaque; }
    public void setDestaque(Boolean destaque) { 
        this.destaque = destaque; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getTipoCategoria() { return tipoCategoria; }
    public void setTipoCategoria(String tipoCategoria) { 
        this.tipoCategoria = tipoCategoria; 
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("Categoria: %s - %s - %s", 
            nome, getTipoFormatado(), ativa ? "Ativa" : "Inativa");
    }
}
