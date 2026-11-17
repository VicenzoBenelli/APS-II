package br.edu.apsii.fabrica;

import br.edu.apsii.produto.claro.BotaoClaro;
import br.edu.apsii.produto.claro.CaixaSelecaoClara;
import br.edu.apsii.produto.claro.CampoTextoClaro;
import br.edu.apsii.produto.escuro.BotaoEscuro;
import br.edu.apsii.produto.escuro.CaixaSelecaoEscura;
import br.edu.apsii.produto.escuro.CampoTextoEscuro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FabricaComponentesTest {
    @Test
    void fabricaClaraCriaSomenteProdutosClaros() {
        FabricaComponentes fabrica = new FabricaTemaClaro();

        assertEquals(Tema.CLARO, fabrica.tema());
        assertInstanceOf(BotaoClaro.class, fabrica.criarBotao());
        assertInstanceOf(CampoTextoClaro.class, fabrica.criarCampoTexto());
        assertInstanceOf(CaixaSelecaoClara.class, fabrica.criarCaixaSelecao());
    }

    @Test
    void fabricaEscuraCriaSomenteProdutosEscuros() {
        FabricaComponentes fabrica = new FabricaTemaEscuro();

        assertEquals(Tema.ESCURO, fabrica.tema());
        assertInstanceOf(BotaoEscuro.class, fabrica.criarBotao());
        assertInstanceOf(CampoTextoEscuro.class, fabrica.criarCampoTexto());
        assertInstanceOf(CaixaSelecaoEscura.class, fabrica.criarCaixaSelecao());
    }

    @Test
    void componentesReaisUsamCoresDaFamilia() {
        for (Tema tema : Tema.values()) {
            FabricaComponentes fabrica = SeletorFabrica.para(tema);
            Paleta cores = fabrica.paleta();
            assertEquals(cores.destaque(), fabrica.criarBotao().criar("OK").getBackground());
            assertEquals(cores.superficie(), fabrica.criarCampoTexto().criar(20).getBackground());
            assertEquals(cores.superficie(), fabrica.criarCaixaSelecao().criar("Avisar").getBackground());
        }
    }

    @Test
    void familiasApresentamPaletasDiferentes() {
        FabricaComponentes clara = SeletorFabrica.para(Tema.CLARO);
        FabricaComponentes escura = SeletorFabrica.para(Tema.ESCURO);

        assertNotEquals(clara.paleta().fundo(), escura.paleta().fundo());
        assertNotEquals(clara.criarBotao().criar("OK").getBackground(),
                escura.criarBotao().criar("OK").getBackground());
    }

    @Test
    void seletorRejeitaTemaNulo() {
        assertThrows(NullPointerException.class, () -> SeletorFabrica.para(null));
    }

    @Test
    void campoTextoRejeitaColunasInvalidas() {
        FabricaComponentes fabrica = new FabricaTemaClaro();
        assertThrows(IllegalArgumentException.class, () -> fabrica.criarCampoTexto().criar(0));
    }
}
