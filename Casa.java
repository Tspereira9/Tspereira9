package model;

import java.util.ArrayList;
import java.util.List;

public class Casa {
    private String nome;
    private List<Comodo> comodos;

    // Construtor da classe Casa
    public Casa(String nome) {
        this.nome = nome;
        this.comodos = new ArrayList<>();
    }

    // Método para adicionar um cômodo à casa
    public void adicionarComodo(Comodo comodo) {
        this.comodos.add(comodo);
    }

    // Método para obter a lista de cômodos da casa
    public List<Comodo> getComodos() {
        return comodos;
    }
}

