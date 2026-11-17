package br.edu.apsii.fabrica;

import java.util.Objects;

/** Ponto de composicao: escolhe a familia, sem espalhar essa decisao pela interface. */
public final class SeletorFabrica {
    private SeletorFabrica() { }

    public static FabricaComponentes para(Tema tema) {
        Objects.requireNonNull(tema, "O tema e obrigatorio.");
        return switch (tema) {
            case CLARO -> new FabricaTemaClaro();
            case ESCURO -> new FabricaTemaEscuro();
        };
    }
}
