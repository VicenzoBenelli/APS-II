package br.edu.apsii.produto;

import javax.swing.JCheckBox;

/** Produto abstrato: caixa de selecao de uma familia visual. */
public interface CaixaSelecao {
    JCheckBox criar(String texto);
}
