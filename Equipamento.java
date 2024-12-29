package model;

import javax.swing.JOptionPane;

public class Equipamento {
    private String nome;
    private boolean status; // Indica se o equipamento está ligado ou desligado
    private Sensor sensor;  // Sensor associado ao equipamento, como luminosidade ou temperatura.

    // Construtor da classe Equipamento
    public Equipamento(String nome, Sensor sensor) {
        this.nome = nome;
        this.status = false;  // Por padrão, o equipamento começa desligado.
        this.sensor = sensor;
    }

    // Método para obter o nome do equipamento
    public String getNome() {
        return nome;
    }

    // Método para verificar se o equipamento está ligado
    public boolean isLigado() {
        return status;
    }

    // Método para ligar o equipamento
    public void ligar() {
        this.status = true;
        atualizarSensor(true);  // Atualiza o sensor com base no estado do equipamento.
    }

    // Método para desligar o equipamento
    public void desligar() {
        this.status = false;
        atualizarSensor(false);  // Atualiza o sensor com base no estado do equipamento.
    }

    // Método privado para atualizar o sensor com base no estado do equipamento
    private void atualizarSensor(boolean ligado) {
        if (sensor != null) {
            if (sensor.getTipo().equals("Luminosidade")) {
                if (ligado) {
                    sensor.setValor(100.0);  // Defina um valor representando o ambiente iluminado.
                } else {
                    sensor.setValor(0.0);  // Quando desligado, não há luz.
                }
            } else if (sensor.getTipo().equals("Temperatura")) {
                if (ligado) {
                    // Se o chuveiro estiver ligado, não sobrescrever a temperatura ajustada
                    if (sensor.getValor() == 0.0) { // Apenas se a temperatura não foi ajustada
                        sensor.setValor(23.0);  // Temperatura padrão ao ligar
                    }
                } else {
                    sensor.setValor(0.0);  // Temperatura ao desligar o chuveiro
                }
            }
        }
    }

    // Método para ajustar a temperatura do equipamento
    public void ajustarTemperatura(int ajuste) {
        // Verifica se o chuveiro está desligado antes de ajustar a temperatura
        if (sensor != null && sensor.getTipo().equals("Temperatura")) {
            if (nome.equals("Chuveiro") && status) {
                // Não pode ajustar a temperatura com o chuveiro ligado
                JOptionPane.showMessageDialog(null, "Não é possível alterar a temperatura com o chuveiro ligado.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nome.equals("Chuveiro")) {
                // Ajuste de temperatura para o chuveiro
                double novaTemperatura = 23.0;
                if (ajuste == 9) {
                    novaTemperatura = 27.0;  // Modo quente
                } else if (ajuste == -5) {
                    novaTemperatura = 18.0;  // Modo frio
                }

                // Exibe a mensagem de temperatura ajustada para o chuveiro
                if (novaTemperatura == 18) {
                    JOptionPane.showMessageDialog(null, "Temperatura ajustada para Frio (18°C)");
                } else if (novaTemperatura == 27) {
                    JOptionPane.showMessageDialog(null, "Temperatura ajustada para Quente (27°C)");
                }

                sensor.setValor(novaTemperatura);  // Atualiza a temperatura do sensor para o chuveiro
            } else if (nome.equals("Ar Condicionado")) {
                // Ajuste de temperatura para o ar condicionado
                double novaTemperatura = sensor.getValor() + ajuste;

                // Garantir que as temperaturas fiquem dentro dos valores desejados para o ar condicionado
                if (novaTemperatura < 9) {
                    novaTemperatura = 9; // Temperatura mínima para o ar condicionado
                } else if (novaTemperatura > 30) {
                    novaTemperatura = 30; // Temperatura máxima para o ar condicionado
                }

                // Exibir a mensagem de temperatura ajustada para o ar condicionado
                JOptionPane.showMessageDialog(null, "Temperatura ajustada para " + novaTemperatura + "°C");

                sensor.setValor(novaTemperatura);  // Atualiza a temperatura do sensor para o ar condicionado
            }
        }
    }
}
