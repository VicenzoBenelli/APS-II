package br.edu.apsii.ui;

import br.edu.apsii.fabrica.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicReference;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.event.MouseEvent;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class JanelaPrincipalTest {
    @Test
    void alternaDezVezesComCliquesReaisSemExcecaoNaEdt() throws Exception {
        assumeFalse(GraphicsEnvironment.isHeadless(), "Teste de mouse requer ambiente grafico");
        AtomicReference<Throwable> erroEdt = new AtomicReference<>();
        Thread.UncaughtExceptionHandler anterior = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> erroEdt.compareAndSet(null, e));
        JanelaPrincipal[] janela = new JanelaPrincipal[1];
        try {
            SwingUtilities.invokeAndWait(() -> {
                janela[0] = new JanelaPrincipal();
                janela[0].iniciar();
                buscar(janela[0].getContentPane(), JTextField.class).setText("Maria");
                buscar(janela[0].getContentPane(), JCheckBox.class).setSelected(true);
            });
            Robot robot = new Robot();
            robot.setAutoDelay(80);
            robot.waitForIdle();
            for (int i = 0; i < 10; i++) {
                Tema tema = i % 2 == 0 ? Tema.ESCURO : Tema.CLARO;
                Point[] ponto = new Point[1];
                SwingUtilities.invokeAndWait(() -> {
                    JComboBox<?> combo = buscar(janela[0].getContentPane(), JComboBox.class);
                    ponto[0] = combo.getLocationOnScreen();
                    ponto[0].translate(combo.getWidth() / 2, combo.getHeight() / 2);
                });
                clicar(robot, ponto[0]);
                SwingUtilities.invokeAndWait(() -> {
                    JComboBox<?> combo = buscar(janela[0].getContentPane(), JComboBox.class);
                    assertTrue(combo.isPopupVisible());
                    JList<?> lista = ((BasicComboPopup) combo.getUI().getAccessibleChild(combo, 0)).getList();
                    Rectangle celula = lista.getCellBounds(tema.ordinal(), tema.ordinal());
                    ponto[0] = lista.getLocationOnScreen();
                    ponto[0].translate(celula.x + celula.width / 2, celula.y + celula.height / 2);
                });
                clicar(robot, ponto[0]);
                SwingUtilities.invokeAndWait(() -> {
                    Container raiz = janela[0].getContentPane();
                    assertEquals(tema, buscar(raiz, JComboBox.class).getSelectedItem());
                    assertEquals(SeletorFabrica.para(tema).paleta().fundo(), raiz.getBackground());
                    assertEquals("Maria", buscar(raiz, JTextField.class).getText());
                    assertTrue(buscar(raiz, JCheckBox.class).isSelected());
                });
                assertNull(erroEdt.get(), "Excecao durante clique na EDT");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> { if (janela[0] != null) janela[0].dispose(); });
            Thread.setDefaultUncaughtExceptionHandler(anterior);
        }
    }

    private static void clicar(Robot robot, Point ponto) {
        robot.mouseMove(ponto.x, ponto.y);
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
    }

    @Test
    void alternaDezVezesPreservandoFormularioEConfirmacao() throws Exception {
        assumeFalse(GraphicsEnvironment.isHeadless(), "Teste de janela requer ambiente grafico");
        AtomicReference<Throwable> erroEdt = new AtomicReference<>();
        Thread.UncaughtExceptionHandler anterior = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> erroEdt.compareAndSet(null, e));
        try {
            SwingUtilities.invokeAndWait(() -> {
                JanelaPrincipal janela = new JanelaPrincipal();
                try {
                    janela.iniciar();
                    janela.iniciar(); // Nao deve duplicar a estrutura nem os listeners.
                    Container raiz = janela.getContentPane();
                    JComboBox<?> seletor = buscar(raiz, JComboBox.class);
                    buscar(raiz, JTextField.class).setText("  Ana Silva  ");
                    for (int i = 0; i < 10; i++) {
                        boolean marcada = i < 5;
                        buscar(raiz, JCheckBox.class).setSelected(marcada);
                        Tema tema = i % 2 == 0 ? Tema.ESCURO : Tema.CLARO;
                        // Popup aberto reproduz a selecao feita pelo usuario.
                        seletor.showPopup();
                        BasicComboPopup popup = (BasicComboPopup) seletor.getUI().getAccessibleChild(seletor, 0);
                        JList<?> lista = popup.getList();
                        lista.setSelectedIndex(tema.ordinal());
                        Rectangle celula = lista.getCellBounds(tema.ordinal(), tema.ordinal());
                        lista.dispatchEvent(new MouseEvent(lista, MouseEvent.MOUSE_RELEASED,
                                System.currentTimeMillis(), 0, celula.x + 5, celula.y + 5, 1, false,
                                MouseEvent.BUTTON1));
                        seletor.hidePopup();
                        assertSame(raiz, janela.getContentPane());
                        assertSame(seletor, buscar(raiz, JComboBox.class));
                        assertEquals(1, seletor.getActionListeners().length);
                        assertEquals("  Ana Silva  ", buscar(raiz, JTextField.class).getText());
                        assertEquals(marcada, buscar(raiz, JCheckBox.class).isSelected());
                        Paleta paleta = SeletorFabrica.para(tema).paleta();
                        assertEquals(paleta.fundo(), raiz.getBackground());
                        assertEquals(paleta.superficie(), buscar(raiz, JTextField.class).getBackground());
                        assertEquals(paleta.texto(), buscar(raiz, JTextField.class).getForeground());
                        assertEquals(paleta.superficie(), buscar(raiz, JCheckBox.class).getBackground());
                        JButton botao = buscar(raiz, JButton.class);
                        assertEquals(paleta.destaque(), botao.getBackground());
                        assertEquals(1, botao.getActionListeners().length);
                        JTextField campoAtual = buscar(raiz, JTextField.class);
                        seletor.setSelectedItem(tema);
                        assertSame(campoAtual, buscar(raiz, JTextField.class));
                        assertSame(botao, buscar(raiz, JButton.class));
                        if (i < 2) registrar(janela, tema.toString());
                        confirmar(janela, "Olá, Ana Silva!\nTema " + tema + " aplicado.\nNotificações "
                                + (marcada ? "ativadas" : "desativadas") + ".");
                    }
                    buscar(raiz, JTextField.class).setText("   ");
                    confirmar(janela, "Informe seu nome antes de confirmar.");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    janela.dispose();
                }
            });
            SwingUtilities.invokeAndWait(() -> { });
            assertNull(erroEdt.get(), "Excecao nao tratada na EDT");
        } finally {
            Thread.setDefaultUncaughtExceptionHandler(anterior);
        }
    }

    private static void confirmar(JFrame janela, String esperado) {
        AtomicReference<Object> mensagem = new AtomicReference<>();
        Timer fechar = new Timer(50, e -> {
            for (Window w : janela.getOwnedWindows()) {
                if (w instanceof JDialog && w.isVisible()) {
                    mensagem.set(buscar((Container) w, JOptionPane.class).getMessage());
                    w.dispose();
                }
            }
        });
        // Limita a espera mesmo se uma regressao impedir a abertura do dialogo.
        Timer limite = new Timer(3000, e -> {
            for (Window w : janela.getOwnedWindows()) w.dispose();
        });
        limite.setRepeats(false);
        fechar.start();
        limite.start();
        try {
            buscar(janela.getContentPane(), JButton.class).doClick();
            assertEquals(esperado, mensagem.get());
        } finally {
            fechar.stop();
            limite.stop();
        }
    }

    private static void registrar(JFrame janela, String tema) throws Exception {
        janela.validate();
        BufferedImage imagem = new BufferedImage(janela.getWidth(), janela.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagem.createGraphics();
        try { janela.paint(g); } finally { g.dispose(); }
        Path pasta = Path.of("target", "evidencias");
        Files.createDirectories(pasta);
        ImageIO.write(imagem, "png", pasta.resolve("tema-" + tema + ".png").toFile());
    }

    private static <T extends Component> T buscar(Container raiz, Class<T> tipo) {
        T resultado = encontrar(raiz, tipo);
        if (resultado == null) throw new AssertionError("Componente ausente: " + tipo);
        return resultado;
    }

    private static <T extends Component> T encontrar(Container raiz, Class<T> tipo) {
        for (Component c : raiz.getComponents()) if (tipo.isInstance(c)) return tipo.cast(c);
        for (Component c : raiz.getComponents()) {
            if (c instanceof Container filho && !(c instanceof JComboBox)) {
                T resultado = encontrar(filho, tipo);
                if (resultado != null) return resultado;
            }
        }
        return null;
    }
}
