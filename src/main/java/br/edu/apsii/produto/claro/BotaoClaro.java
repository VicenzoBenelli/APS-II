package br.edu.apsii.produto.claro;

import br.edu.apsii.fabrica.Paletas;
import br.edu.apsii.produto.Botao;
import br.edu.apsii.produto.EstilosComponentes;
import javax.swing.JButton;

/** Produto concreto da familia clara. */
public final class BotaoClaro implements Botao {
    @Override
    public JButton criar(String texto) {
        return EstilosComponentes.botao(texto, Paletas.CLARA);
    }
}
