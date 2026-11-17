package br.edu.apsii.ui;

import br.edu.apsii.fabrica.FabricaComponentes;
import br.edu.apsii.fabrica.Paleta;
import br.edu.apsii.fabrica.SeletorFabrica;
import br.edu.apsii.fabrica.Tema;
import br.edu.apsii.produto.Botao;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.CampoTexto;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/** Cliente do Abstract Factory: utiliza as interfaces, nunca os produtos concretos. */
public final class JanelaPrincipal extends JFrame {
    private JTextField campoNome;
    private JCheckBox caixaNotificacoes;
    private final JPanel raiz = new JPanel(new BorderLayout(0, 24));
    private final JPanel cabecalho = new JPanel();
    private final JPanel trocaTema = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
    private final JPanel rodape = new JPanel(new BorderLayout(0, 8));
    private final JComboBox<Tema> seletor = new JComboBox<>(Tema.values());
    private final JLabel familia = new JLabel();
    private FabricaComponentes fabricaAtual;
    private JPanel formularioAtual;

    public JanelaPrincipal() {
        super("APS-II | Demonstracao de Abstract Factory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(580, 510));
        setSize(720, 570);
        setLocationRelativeTo(null);
    }

    public void iniciar() {
        if (fabricaAtual == null) {
            montarEstrutura();
            aplicarTema(SeletorFabrica.para(Tema.CLARO));
        }
        setVisible(true);
    }

    private void montarEstrutura() {
        Paleta paleta = SeletorFabrica.para(Tema.CLARO).paleta();
        raiz.setBackground(paleta.fundo());
        raiz.setBorder(BorderFactory.createEmptyBorder(30, 38, 26, 38));

        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        cabecalho.setBackground(paleta.fundo());
        JLabel titulo = etiqueta("Personalizador de Interface", paleta, 24, Font.BOLD);
        JLabel subtitulo = etiqueta("Abstract Factory: uma familia visual por vez", paleta, 14, Font.PLAIN);
        cabecalho.add(titulo);
        cabecalho.add(Box.createVerticalStrut(8));
        cabecalho.add(subtitulo);
        cabecalho.add(Box.createVerticalStrut(24));

        trocaTema.setBackground(paleta.fundo());
        trocaTema.add(etiqueta("Tema:", paleta, 14, Font.BOLD));
        seletor.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        seletor.setBackground(paleta.superficie());
        seletor.setForeground(paleta.texto());
        seletor.addActionListener(evento -> {
            Tema novoTema = (Tema) seletor.getSelectedItem();
            if (novoTema != null && novoTema != fabricaAtual.tema()) {
                aplicarTema(SeletorFabrica.para(novoTema));
            }
        });
        trocaTema.add(seletor);
        cabecalho.add(trocaTema);
        raiz.add(cabecalho, BorderLayout.NORTH);

        familia.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        familia.setHorizontalAlignment(SwingConstants.CENTER);
        rodape.add(familia, BorderLayout.NORTH);
        JLabel legenda = etiqueta("Troque o tema: os tres componentes mudam juntos.", paleta, 12, Font.PLAIN);
        legenda.setHorizontalAlignment(SwingConstants.CENTER);
        rodape.add(legenda, BorderLayout.SOUTH);
        raiz.add(rodape, BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    private void aplicarTema(FabricaComponentes fabrica) {
        String nomeAnterior = campoNome == null ? "" : campoNome.getText();
        boolean selecaoAnterior = caixaNotificacoes != null && caixaNotificacoes.isSelected();
        Paleta paleta = fabrica.paleta();

        // A familia inteira continua sendo criada pela fabrica abstrata.
        Botao produtoBotao = fabrica.criarBotao();
        CampoTexto produtoCampo = fabrica.criarCampoTexto();
        CaixaSelecao produtoSelecao = fabrica.criarCaixaSelecao();

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(paleta.superficie());
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(paleta.borda()),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        GridBagConstraints pos = new GridBagConstraints();
        pos.gridx = 0;
        pos.gridy = 0;
        pos.weightx = 1;
        pos.fill = GridBagConstraints.HORIZONTAL;
        pos.anchor = GridBagConstraints.WEST;
        pos.insets = new Insets(0, 0, 10, 0);
        formulario.add(etiqueta("Seu nome", paleta, 14, Font.BOLD), pos);

        campoNome = produtoCampo.criar(26);
        campoNome.setText(nomeAnterior);
        pos.gridy++;
        pos.insets = new Insets(0, 0, 20, 0);
        formulario.add(campoNome, pos);

        caixaNotificacoes = produtoSelecao.criar("Desejo receber notificações");
        caixaNotificacoes.setSelected(selecaoAnterior);
        pos.gridy++;
        pos.insets = new Insets(0, 0, 24, 0);
        formulario.add(caixaNotificacoes, pos);

        JButton confirmar = produtoBotao.criar("Confirmar preferências");
        confirmar.addActionListener(evento -> confirmarPreferencias(fabrica));
        pos.gridy++;
        pos.insets = new Insets(0, 0, 0, 0);
        formulario.add(confirmar, pos);
        familia.setText("Família: " + produtoBotao.getClass().getSimpleName()
                + " + " + produtoCampo.getClass().getSimpleName()
                + " + " + produtoSelecao.getClass().getSimpleName());
        // Mantem o seletor instalado enquanto seu popup processa a selecao.
        if (formularioAtual != null) {
            raiz.remove(formularioAtual);
        }
        formularioAtual = formulario;
        fabricaAtual = fabrica;
        raiz.add(formulario, BorderLayout.CENTER);
        for (JPanel painel : new JPanel[] {raiz, cabecalho, trocaTema, rodape}) {
            painel.setBackground(paleta.fundo());
            for (java.awt.Component componente : painel.getComponents()) {
                if (componente instanceof JLabel rotulo) {
                    rotulo.setForeground(paleta.texto());
                }
            }
        }
        seletor.setBackground(paleta.superficie());
        seletor.setForeground(paleta.texto());
        raiz.revalidate();
        raiz.repaint();
    }

    private void confirmarPreferencias(FabricaComponentes fabrica) {
        String nome = campoNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe seu nome antes de confirmar.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocusInWindow();
            return;
        }
        String notificacoes = caixaNotificacoes.isSelected() ? "ativadas" : "desativadas";
        JOptionPane.showMessageDialog(this,
                "Olá, " + nome + "!\nTema " + fabrica.tema()
                        + " aplicado.\nNotificações " + notificacoes + ".",
                "Preferências confirmadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static JLabel etiqueta(String texto, Paleta paleta, int tamanho, int estilo) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(paleta.texto());
        etiqueta.setFont(new Font(Font.SANS_SERIF, estilo, tamanho));
        return etiqueta;
    }
}
