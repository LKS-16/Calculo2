package grafico;

import javax.swing.*;
import java.awt.*;

public class Principal {
    public static void main(String[] args) {
        // Inicializa a interface gráfica em uma thread segura
        SwingUtilities.invokeLater(() -> {
            // Cria a janela principal
            JFrame janela = new JFrame("Área sob ou entre funções");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(1200, 650);
            janela.setLayout(new BorderLayout());

            //Painel de Entrada de Dados 
            JPanel painelEntrada = new JPanel();

            // Campos de texto para inserir as funções f(x) e g(x)
            JTextField campoFuncao1 = new TextField("sin(x)", 15);  // f(x) erro simulado
            JTextField campoFuncao2 = new JTextField("cos(x)", 15);  // g(x)
            campoFuncao2.setEnabled(false);  // Começa desativado

            // Campos para os limites de integração
            JTextField campoA = new JTextField("0", 5);
            JTextField campoB = new JTextField("pi", 5);

            // Botão que atualiza o gráfico
            JButton botaoAtualizar = new JButton("Atualizar");

            // Checkbox para definir se a área será entre duas funções
            JCheckBox checkDuasFuncoes = new JCheckBox("Área entre duas funções?");
            checkDuasFuncoes.addActionListener(e -> {
                campoFuncao2.setEnabled(checkDuasFuncoes.isSelected());
            });

            // Adiciona os componentes ao painel de entrada
            painelEntrada.add(new JLabel("f(x) ="));
            painelEntrada.add(campoFuncao1);
            painelEntrada.add(checkDuasFuncoes);
            painelEntrada.add(new JLabel("g(x) ="));
            painelEntrada.add(campoFuncao2);
            painelEntrada.add(new JLabel("a ="));
            painelEntrada.add(campoA);
            painelEntrada.add(new JLabel("b ="));
            painelEntrada.add(campoB);
            painelEntrada.add(botaoAtualizar);

            // Painel onde o gráfico será exibido 
            JPanel painelGraficoHolder = new JPanel(new BorderLayout());

            // Cria funções iniciais padrão
            Funcao f1 = new FuncaoExp4j(campoFuncao1.getText());
            Funcao f2 = new FuncaoExp4j(campoFuncao2.getText());

            // Lê os limites a e b (com suporte a "pi")
            double a = parseDoubleSeguro(campoA.getText(), 0);
            double b = parseDoubleSeguro(campoB.getText(), Math.PI);

            // Cria o painel gráfico inicial com apenas f(x)
            PainelGrafico painelGrafico = new PainelGrafico(f1, f2, false, -5, 5, 200, a, b);
            painelGraficoHolder.add(painelGrafico, BorderLayout.CENTER);

            // Painel de Legenda com funções disponíveis
            JPanel painelLegenda = new JPanel();
            painelLegenda.setLayout(new BoxLayout(painelLegenda, BoxLayout.Y_AXIS));
            painelLegenda.setBorder(BorderFactory.createTitledBorder("Funções disponíveis"));

            // Lista de funções que o usuário pode usar
            String[] funcoes = {
                    "sqrt(x)  → raiz quadrada",
                    "pi       → constante π",
                    "sin(x)   → seno",
                    "cos(x)   → cosseno",
                    "tan(x)   → tangente",
                    "sec(x)   → secante",
                    "csc(x)   → cosecante",
                    "cot(x)   → cotangente",
                    "log(x)   → logaritmo natural",
                    "ln(x)    → logaritmo natural",
                    "exp(x)   → exponencial",
                    "^        → potência (x^2, etc.)"
            };

            // Adiciona os textos de legenda ao painel
            for (String linha : funcoes) {
                painelLegenda.add(new JLabel(linha));
            }

            // Ação do botão "Atualizar"
            botaoAtualizar.addActionListener(e -> {
                try {
                    // Cria novas funções com base na entrada
                    Funcao novaF1 = new FuncaoExp4j(campoFuncao1.getText());
                    Funcao novaF2 = new FuncaoExp4j(campoFuncao2.getText());

                    // Lê os novos valores de a e b
                    double novoA = parseDoubleSeguro(campoA.getText(), 0);
                    double novoB = parseDoubleSeguro(campoB.getText(), Math.PI);

                    // Verifica se o modo de duas funções está ativado
                    boolean usarDuas = checkDuasFuncoes.isSelected();

                    // Cria um novo gráfico com os dados atualizados
                    PainelGrafico novoGrafico = new PainelGrafico(novaF1, novaF2, usarDuas, -5, 5, 200, novoA, novoB);
                    painelGraficoHolder.removeAll();
                    painelGraficoHolder.add(novoGrafico);
                    painelGraficoHolder.revalidate();
                    painelGraficoHolder.repaint();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(janela, "Erro: " + ex.getMessage());
                }
            });

            // Adiciona os painéis à janela principal
            janela.add(painelEntrada, BorderLayout.NORTH);
            janela.add(painelGraficoHolder, BorderLayout.CENTER);
            janela.add(painelLegenda, BorderLayout.EAST);

            // Exibe a janela
            janela.setVisible(true);
        });
    }

    // Função auxiliar que interpreta "pi" e números normalmente
    private static double parseDoubleSeguro(String texto, double padrao) {
        try {
            if (texto.equalsIgnoreCase("pi")) return Math.PI;
            return Double.parseDouble(texto);
        } catch (Exception e) {
            return padrao;
        }
    }
}
