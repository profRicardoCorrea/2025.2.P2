package repositories.impl;

import entities.Avaliacao;
import repositories.IAvaliacaoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class AvaliacaoRepository implements IAvaliacaoRepository {
    private List<Avaliacao> avaliacoes;
    private Long proximoId;

    public AvaliacaoRepository() {
        this.avaliacoes = new ArrayList<>();
        this.proximoId = 1L;
    }

    @Override
    public void salvar(Avaliacao entidade) {
        if (entidade.getId() == null) {
            entidade.setId(proximoId++);
        }
        avaliacoes.add(entidade);
    }

    @Override
    public void remover(Long id) {
        avaliacoes.removeIf(avaliacao -> avaliacao.getId().equals(id));
    }

    @Override
    public void alterar(Avaliacao entidade) {
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getId().equals(entidade.getId())) {
                avaliacoes.set(i, entidade);
                break;
            }
        }
    }

    @Override
    public List<Avaliacao> listar() {
        return new ArrayList<>(avaliacoes);
    }

    @Override
    public Avaliacao buscarPorId(Long id) {
        return avaliacoes.stream()
                .filter(avaliacao -> avaliacao.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Avaliacao> buscarPorEvento(Long eventoId) {
        return avaliacoes.stream()
                .filter(avaliacao -> eventoId.equals(avaliacao.getEventoId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> buscarPorUsuario(Long usuarioId) {
        return avaliacoes.stream()
                .filter(avaliacao -> usuarioId.equals(avaliacao.getUsuarioId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> buscarPorNota(Integer nota) {
        return avaliacoes.stream()
                .filter(avaliacao -> nota.equals(avaliacao.getNota()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> buscarPorNotaMinima(Integer notaMinima) {
        return avaliacoes.stream()
                .filter(avaliacao -> avaliacao.getNota() >= notaMinima)
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> buscarPorData(LocalDate data) {
        return avaliacoes.stream()
                .filter(avaliacao -> avaliacao.getDataAvaliacao() != null && 
                                   avaliacao.getDataAvaliacao().equals(data))
                .collect(Collectors.toList());
    }

    @Override
    public List<Avaliacao> buscarPorStatus(String status) {
        return avaliacoes.stream()
                .filter(avaliacao -> status.equals(avaliacao.getStatus()))
                .collect(Collectors.toList());
    }
}


