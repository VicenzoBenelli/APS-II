package br.edu.apsii.fabrica;

import br.edu.apsii.produto.Botao;
import br.edu.apsii.produto.CaixaSelecao;
import br.edu.apsii.produto.CampoTexto;

/** ABSTRACT FACTORY: contrato para criar uma familia completa de produtos. */
public interface FabricaComponentes {
    Tema tema();
    Paleta paleta();
    Botao criarBotao();
    CampoTexto criarCampoTexto();
    CaixaSelecao criarCaixaSelecao();
}
