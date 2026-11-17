package br.edu.apsii.produto.claro;

import br.edu.apsii.fabrica.Paletas;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.EstilosComponentes;
import javax.swing.JCheckBox;

/** Produto concreto da familia clara. */
public final class CaixaSelecaoClara implements CaixaSelecao {
    @Override
    public JCheckBox criar(String texto) {
        return EstilosComponentes.caixaSelecao(texto, Paletas.CLARA);
    }
}
