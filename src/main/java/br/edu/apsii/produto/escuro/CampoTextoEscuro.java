package br.edu.apsii.produto.escuro;

import br.edu.apsii.fabrica.Paletas;
import br.edu.apsii.produto.CampoTexto;
import br.edu.apsii.produto.EstilosComponentes;
import javax.swing.JTextField;

/** Produto concreto da familia escura. */
public final class CampoTextoEscuro implements CampoTexto {
    @Override
    public JTextField criar(int colunas) {
        return EstilosComponentes.campoTexto(colunas, Paletas.ESCURA);
    }
}
