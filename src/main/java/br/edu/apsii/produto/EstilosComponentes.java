package br.edu.apsii.produto;

import br.edu.apsii.fabrica.Paleta;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/** Regras comuns de desenho, sem decidir qual familia deve ser criada. */
public final class EstilosComponentes {
    private EstilosComponentes() { }

    public static JButton botao(String rotulo, Paleta paleta) {
        JButton botao = new JButton(Objects.requireNonNull(rotulo));
        botao.setBackground(paleta.destaque());
        botao.setForeground(paleta.textoBotao());
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        botao.setPreferredSize(new Dimension(220, 44));
        botao.setBorder(new EmptyBorder(12, 18, 12, 18));
        return botao;
    }

    public static JTextField campoTexto(int colunas, Paleta paleta) {
        if (colunas < 1) {
            throw new IllegalArgumentException("O campo deve ter ao menos uma coluna.");
        }
        JTextField campo = new JTextField(colunas);
        campo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        campo.setBackground(paleta.superficie());
        campo.setForeground(paleta.texto());
        campo.setCaretColor(paleta.texto());
        campo.setSelectionColor(paleta.destaque());
        campo.setBorder(new LineBorder(paleta.borda(), 1, true));
        campo.setPreferredSize(new Dimension(340, 42));
        return campo;
    }

    public static JCheckBox caixaSelecao(String rotulo, Paleta paleta) {
        JCheckBox caixa = new JCheckBox(Objects.requireNonNull(rotulo));
        caixa.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        caixa.setBackground(paleta.superficie());
        caixa.setForeground(paleta.texto());
        caixa.setFocusPainted(false);
        return caixa;
    }
}
