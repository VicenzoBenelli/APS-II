package br.edu.apsii.fabrica;

import java.awt.Color;

/** Cores compartilhadas pelos componentes de uma mesma familia. */
public record Paleta(Color fundo, Color superficie, Color texto,
                     Color destaque, Color textoBotao, Color borda) {
}
