package br.edu.apsii.produto.escuro;

import br.edu.apsii.fabrica.Paletas;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.EstilosComponentes;
import javax.swing.JCheckBox;

/** Produto concreto da familia escura. */
public final class CaixaSelecaoEscura implements CaixaSelecao {
    @Override
    public JCheckBox criar(String texto) {
        return EstilosComponentes.caixaSelecao(texto, Paletas.ESCURA);
    }
}
