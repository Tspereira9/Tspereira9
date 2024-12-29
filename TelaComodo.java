package view;

import model.Comodo;
import model.Equipamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaComodo extends JFrame {
    
    private Comodo comodo;
    private JTextArea areaEquipamentos;
    
    // Construtor da classe TelaComodo
    public TelaComodo(Comodo comodo) {
        this.comodo = comodo;
        setTitle("Controle de Equipamentos - " + comodo.getNome());
        setLayout(new BorderLayout());
        
        // Área de equipamentos
        areaEquipamentos = new JTextArea(10, 30);
        areaEquipamentos.setEditable(false);
        atualizarEquipamentos();
        
        // Botões de controle para cada equipamento
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        
        for (Equipamento equipamento : comodo.getEquipamentos()) {
            JButton btnEquipamento = new JButton(equipamento.getNome());
            btnEquipamento.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (equipamento.isLigado()) {
                        equipamento.desligar();
                    } else {
                        equipamento.ligar();
                    }
                    atualizarEquipamentos();
                }
            });
            painelBotoes.add(btnEquipamento);
        }
        
        // Adiciona a área de equipamentos e o painel de botões à janela
        add(new JScrollPane(areaEquipamentos), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Configuração da janela
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Método para atualizar a área de equipamentos
    private void atualizarEquipamentos() {
        areaEquipamentos.setText(""); // Limpar a área de equipamentos
        for (Equipamento equipamento : comodo.getEquipamentos()) {
            areaEquipamentos.append(equipamento.getNome() + " - Status: " + (equipamento.isLigado() ? "Ligado" : "Desligado") + "\n");
        }
    }

    public static void main(String[] args) {
        // Exemplo de como utilizar a TelaComodo
        TelaComodo tela = new TelaComodo(new Comodo("Sala"));
    }
}
