package br.edu.apsii.fabrica;

import br.edu.apsii.produto.Botao;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.CampoTexto;
import br.edu.apsii.produto.escuro.BotaoEscuro;
import br.edu.apsii.produto.escuro.CaixaSelecaoEscura;
import br.edu.apsii.produto.escuro.CampoTextoEscuro;

/** ConcreteFactory: cria exclusivamente produtos da familia escura. */
public final class FabricaTemaEscuro implements FabricaComponentes {
    @Override
    public Tema tema() {
        return Tema.ESCURO;
    }

    @Override
    public Paleta paleta() {
        return Paletas.ESCURA;
    }

    @Override
    public Botao criarBotao() {
        return new BotaoEscuro();
    }

    @Override
    public CampoTexto criarCampoTexto() {
        return new CampoTextoEscuro();
    }

    @Override
    public CaixaSelecao criarCaixaSelecao() {
        return new CaixaSelecaoEscura();
    }
}
