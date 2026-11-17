package br.edu.apsii.produto.claro;

import br.edu.apsii.fabrica.Paletas;
import br.edu.apsii.produto.CampoTexto;
import br.edu.apsii.produto.EstilosComponentes;
import javax.swing.JTextField;

/** Produto concreto da familia clara. */
public final class CampoTextoClaro implements CampoTexto {
    @Override
    public JTextField criar(int colunas) {
        return EstilosComponentes.campoTexto(colunas, Paletas.CLARA);
    }
}
