package services;

import java.util.List;

import entities.JogoFisico;

public interface IJogoFisicoService {
    void cadastrarJogo(JogoFisico jogo);
    void removerJogo(Long id);
    void atualizarJogo(JogoFisico jogo);
    List<JogoFisico> listarJogos();
    List<JogoFisico> buscarPorMidia(String midia);
    JogoFisico buscarPorId(Long id);
    void sincronizarRepositorios();
}
