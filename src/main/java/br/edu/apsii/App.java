package br.edu.apsii;

import br.edu.apsii.ui.JanelaPrincipal;
import javax.swing.SwingUtilities;

/** Ponto de entrada do sistema. */
public final class App {
    private App() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().iniciar());
    }
}
