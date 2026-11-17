package br.edu.apsii.fabrica;

/** Familias de componentes visuais disponiveis. */
public enum Tema {
    CLARO("Claro"),
    ESCURO("Escuro");

    private final String descricao;

    Tema(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
