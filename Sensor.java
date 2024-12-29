package model;

public class Sensor {
    private String tipo;
    private double valor;

    // Construtor da classe Sensor
    public Sensor(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    // Método para obter o tipo do sensor
    public String getTipo() {
        return tipo;
    }

    // Método para obter o valor do sensor
    public double getValor() {
        return valor;
    }

    // Método para definir o valor do sensor
    public void setValor(double valor) {
        this.valor = valor;
    }
}
