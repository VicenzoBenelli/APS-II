package br.edu.apsii.fabrica;

import br.edu.apsii.produto.Botao;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.CampoTexto;
import br.edu.apsii.produto.claro.BotaoClaro;
import br.edu.apsii.produto.claro.CaixaSelecaoClara;
import br.edu.apsii.produto.claro.CampoTextoClaro;

/** ConcreteFactory: cria exclusivamente produtos da familia clara. */
public final class FabricaTemaClaro implements FabricaComponentes {
    @Override
    public Tema tema() {
        return Tema.CLARO;
    }

    @Override
    public Paleta paleta() {
        return Paletas.CLARA;
    }

    @Override
    public Botao criarBotao() {
        return new BotaoClaro();
    }

    @Override
    public CampoTexto criarCampoTexto() {
        return new CampoTextoClaro();
    }

    @Override
    public CaixaSelecao criarCaixaSelecao() {
        return new CaixaSelecaoClara();
    }
}
