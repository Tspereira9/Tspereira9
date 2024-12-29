package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    private Casa casa;
    private JComboBox<Comodo> comboComodos;
    private JTextArea areaSensores;
    private JPanel painelEquipamentos;

    // Construtor da classe TelaPrincipal
    public TelaPrincipal(Casa casa) {
        this.casa = casa;
        setTitle("Automação Residencial");
        setLayout(new BorderLayout());

        // Criando os componentes da interface gráfica
        comboComodos = new JComboBox<>();
        areaSensores = new JTextArea(10, 30);
        areaSensores.setEditable(false);
        painelEquipamentos = new JPanel();

        // Adiciona os Cômodos no JComboBox
        carregarComodos();

        // Painel de controle de Cômodos
        JPanel painelControle = new JPanel();
        painelControle.setLayout(new FlowLayout(FlowLayout.LEFT));
        painelControle.add(new JLabel("Selecione o Cômodo:"));
        painelControle.add(comboComodos);

        // Painel de sensores
        JPanel painelSensores = new JPanel();
        painelSensores.setLayout(new BorderLayout());
        painelSensores.add(new JScrollPane(areaSensores), BorderLayout.CENTER);

        // Organizando a tela
        add(painelControle, BorderLayout.NORTH);
        add(painelSensores, BorderLayout.CENTER);
        add(painelEquipamentos, BorderLayout.SOUTH);

        // Configurações da janela
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Adicionar listener para mudança no JComboBox
        comboComodos.addActionListener(e -> {
            Comodo comodoSelecionado = (Comodo) comboComodos.getSelectedItem();
            if (comodoSelecionado != null) {
                atualizarEquipamentos(comodoSelecionado);
                atualizarSensores(comodoSelecionado);
            }
        });
    }

    // Carrega os Cômodos no JComboBox
    private void carregarComodos() {
        if (casa.getComodos() != null && !casa.getComodos().isEmpty()) {
            for (Comodo comodo : casa.getComodos()) {
                comboComodos.addItem(comodo);
            }
        } else {
            comboComodos.addItem(new Comodo("Sem Cômodos")); // Caso não haja cômodos
        }
    }

    // Atualiza os sensores do cômodo selecionado
    private void atualizarSensores(Comodo comodo) {
        areaSensores.setText(""); // Limpar área de sensores
        if (comodo != null) {
            for (Sensor sensor : comodo.getSensores()) {
                areaSensores.append("Sensor de " + sensor.getTipo() + ": " + sensor.getValor() + "\n");
            }
        }
    }

    // Atualiza os botões de ligar/desligar de acordo com os equipamentos do cômodo
    private void atualizarEquipamentos(Comodo comodo) {
        painelEquipamentos.removeAll();
        painelEquipamentos.setLayout(new GridLayout(comodo.getEquipamentos().size(), 1, 10, 10));

        for (Equipamento equipamento : comodo.getEquipamentos()) {
            JButton btnEquipamento = new JButton(equipamento.getNome() + " - " + (equipamento.isLigado() ? "Desligar" : "Ligar"));

            btnEquipamento.setBackground(equipamento.isLigado() ? Color.RED : Color.GREEN);
            btnEquipamento.setForeground(Color.WHITE);
            btnEquipamento.setFocusPainted(false);

            btnEquipamento.addActionListener(e -> {
                if (equipamento.isLigado()) {
                    equipamento.desligar();
                } else {
                    equipamento.ligar();
                }
                btnEquipamento.setText(equipamento.getNome() + " - " + (equipamento.isLigado() ? "Desligar" : "Ligar"));
                btnEquipamento.setBackground(equipamento.isLigado() ? Color.RED : Color.GREEN);
                atualizarSensores(comodo);
            });

            painelEquipamentos.add(btnEquipamento);

            // Adicionando controle específico para o chuveiro no banheiro
            if (equipamento.getNome().equals("Chuveiro")) {
                JButton btnTemperatura = new JButton("Alterar Temperatura");
                btnTemperatura.addActionListener(e -> {
                    if (equipamento.isLigado()) {
                        JOptionPane.showMessageDialog(null, "Não é possível alterar a temperatura com o chuveiro ligado.",
                                "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String[] opcoes = {"Quente", "Frio"};
                        int escolha = JOptionPane.showOptionDialog(null, "Escolha a temperatura do chuveiro:", "Chuveiro",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
                        if (escolha == 0) {
                            equipamento.ajustarTemperatura(9);  // Modo quente (ajusta para 27°C)
                        } else if (escolha == 1) {
                            equipamento.ajustarTemperatura(-5); // Modo frio (ajusta para 18°C)
                        }
                        atualizarSensores(comodo);
                    }
                });
                painelEquipamentos.add(btnTemperatura);

            } else if (equipamento.getNome().equals("Ar Condicionado")) {
                JButton btnAumentar = new JButton("+");
                JButton btnDiminuir = new JButton("-");

                btnAumentar.addActionListener(e -> {
                    equipamento.ajustarTemperatura(1); // Aumenta a temperatura (esquentar)
                    atualizarSensores(comodo);
                });

                btnDiminuir.addActionListener(e -> {
                    equipamento.ajustarTemperatura(-1); // Diminui a temperatura (esfriar)
                    atualizarSensores(comodo);
                });

                painelEquipamentos.add(btnAumentar);
                painelEquipamentos.add(btnDiminuir);
            }
        }

        painelEquipamentos.revalidate();
        painelEquipamentos.repaint();
    }

    public static void main(String[] args) {
        // Criando a casa
        Casa casa = new Casa("Minha Casa");

        // Criando cômodos
        Comodo sala = new Comodo("Sala");
        Comodo quarto = new Comodo("Quarto");
        Comodo banheiro = new Comodo("Banheiro");

        // Criando sensores
        Sensor luminosidadeSala = new Sensor("Luminosidade", 50.0);
        Sensor temperaturaSala = new Sensor("Temperatura", 22.0);
        Sensor luminosidadeQuarto = new Sensor("Luminosidade", 60.0);
        Sensor temperaturaBanheiro = new Sensor("Temperatura", 25.0);
        Sensor temperaturaQuarto = new Sensor("Temperatura", 23.0);

        // Criando equipamentos
        Equipamento lampadaSala = new Equipamento("Lâmpada", luminosidadeSala);
        Equipamento arCondicionadoSala = new Equipamento("Ar Condicionado", temperaturaSala);
        Equipamento lampadaQuarto = new Equipamento("Lâmpada", luminosidadeQuarto);
        Equipamento arCondicionadoQuarto = new Equipamento("Ar Condicionado", temperaturaQuarto);
        Equipamento chuveiro = new Equipamento("Chuveiro", temperaturaBanheiro);

        // Adicionando sensores e equipamentos aos cômodos
        sala.adicionarEquipamento(lampadaSala);
        sala.adicionarEquipamento(arCondicionadoSala);
        sala.adicionarSensor(luminosidadeSala);
        sala.adicionarSensor(temperaturaSala);

        quarto.adicionarEquipamento(lampadaQuarto);
        quarto.adicionarEquipamento(arCondicionadoQuarto);
        quarto.adicionarSensor(luminosidadeQuarto);
        quarto.adicionarSensor(temperaturaQuarto);

        banheiro.adicionarEquipamento(chuveiro);
        banheiro.adicionarSensor(temperaturaBanheiro);

        // Adicionando os cômodos à casa
        casa.adicionarComodo(sala);
        casa.adicionarComodo(quarto);
        casa.adicionarComodo(banheiro);

        // Inicializando a interface gráfica
        new TelaPrincipal(casa);
    }
}