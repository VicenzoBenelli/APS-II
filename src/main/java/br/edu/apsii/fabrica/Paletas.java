package br.edu.apsii.fabrica;

import java.awt.Color;

/** Catalogo imutavel das paletas utilizadas pelas fabricas. */
public final class Paletas {
    public static final Paleta CLARA = new Paleta(
            new Color(0xF3F6FB), new Color(0xFFFFFF), new Color(0x172338),
            new Color(0x2563EB), Color.WHITE, new Color(0xCBD5E1));

    public static final Paleta ESCURA = new Paleta(
            new Color(0x111827), new Color(0x1F2937), new Color(0xF9FAFB),
            new Color(0x38BDF8), new Color(0x082F49), new Color(0x475569));

    private Paletas() { }
}
