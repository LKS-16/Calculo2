package grafico;
import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Cria a janela principal
            JFrame janela = new JFrame("Gráfico de Integral");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(800, 600);
            janela.setLayout(new BoxLayout(janela.getContentPane(), BoxLayout.Y_AXIS)); // layout vertical

            // Painel superior com campo de texto e botão
            JPanel painelEntrada = new JPanel();
            JTextField campoFuncao = new JTextField("x^2", 20); // Função inicial
            JButton botaoAtualizar = new JButton("Atualizar");

            painelEntrada.add(new JLabel("f(x) = "));
            painelEntrada.add(campoFuncao);
            painelEntrada.add(botaoAtualizar);

            // Painel que segura o gráfico
            JPanel painelGraficoHolder = new JPanel();
            painelGraficoHolder.setLayout(new BorderLayout());

            // Cria a função com base no texto inicial e desenha o gráfico
            Funcao funcao = new FuncaoExp4j(campoFuncao.getText());
            PainelGrafico painelGrafico = new PainelGrafico(funcao, -5, 5, 100);
            painelGraficoHolder.add(painelGrafico, BorderLayout.CENTER);

            // Ação do botão: atualizar o gráfico com a nova função
            botaoAtualizar.addActionListener(e -> {
                try {
                    Funcao novaFuncao = new FuncaoExp4j(campoFuncao.getText());
                    PainelGrafico novoPainel = new PainelGrafico(novaFuncao, -5, 5, 100);
                    painelGraficoHolder.removeAll(); // remove gráfico antigo
                    painelGraficoHolder.add(novoPainel);
                    painelGraficoHolder.revalidate(); // atualiza o layout
                    painelGraficoHolder.repaint();    // redesenha
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(janela, "Função inválida: " + ex.getMessage());
                }
            });

            // Adiciona os painéis à janela
            janela.add(painelEntrada);
            janela.add(painelGraficoHolder);

            janela.setVisible(true);
        });
    }
}
