package model;

import java.util.ArrayList;
import java.util.List;

public class Comodo {
    private String nome;
    private List<Equipamento> equipamentos;
    private List<Sensor> sensores;

    // Construtor da classe Comodo
    public Comodo(String nome) {
        this.nome = nome;
        this.equipamentos = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    // Método para obter o nome do cômodo
    public String getNome() {
        return nome;
    }

    // Método para definir o nome do cômodo
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método para obter a lista de equipamentos do cômodo
    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    // Método para adicionar um equipamento ao cômodo
    public void adicionarEquipamento(Equipamento equipamento) {
        this.equipamentos.add(equipamento);
    }

    // Método para obter a lista de sensores do cômodo
    public List<Sensor> getSensores() {
        return sensores;
    }

    // Método para adicionar um sensor ao cômodo
    public void adicionarSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    // Sobrescrevendo o método toString() para exibir o nome do cômodo
    @Override
    public String toString() {
        return nome; // Retorna apenas o nome do cômodo para exibição no JComboBox
    }
}
