package grafico;

import javax.swing.*;
import java.awt.*;

public class PainelGrafico extends JPanel {
    private final Funcao f1, f2;       // As duas funções que podem ser desenhadas
    private final boolean duasFuncoes; // Define se vai desenhar uma ou duas funções
    private final double xMin, xMax;   // Limites do eixo X no gráfico
    private final double a, b;         // Limites de integração (onde sombrear)
    private final int passos;          // Número de divisões para precisão do gráfico

    public PainelGrafico(Funcao f1, Funcao f2, boolean duasFuncoes,
                         double xMin, double xMax, int passos, double a, double b) {
        this.f1 = f1;
        this.f2 = f2;
        this.duasFuncoes = duasFuncoes;
        this.xMin = xMin;
        this.xMax = xMax;
        this.passos = passos;
        this.a = a;
        this.b = b;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha os eixos coordenados
        desenharEixos(g);

        // Se for com duas funções, desenha entre elas
        if (duasFuncoes) {
            desenharAreaEntreFuncoes(g);
            desenharCurva(g, f1, Color.BLUE);
            desenharCurva(g, f2, Color.RED);
        } else {
            // Apenas uma função: área abaixo dela
            desenharAreaAbaixoFuncao(g, f1);
            desenharCurva(g, f1, Color.BLUE);
        }
    }

    // Desenha os eixos X e Y com numeração
    private void desenharEixos(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        double escalaX = w / (xMax - xMin);
        double escalaY = h / (xMax - xMin); // mesma escala para X e Y por simplicidade

        // Coordenadas da origem no painel
        int y0 = h / 2;
        int x0 = (int) (-xMin * escalaX);

        g.setColor(Color.GRAY);
        g.drawLine(0, y0, w, y0); // Eixo X
        g.drawLine(x0, 0, x0, h); // Eixo Y

        g.setColor(Color.DARK_GRAY);

        // Números no eixo X
        for (int i = (int) xMin; i <= xMax; i++) {
            int x = (int) ((i - xMin) * escalaX);
            g.drawLine(x, y0 - 3, x, y0 + 3);
            g.drawString(String.valueOf(i), x - 5, y0 + 15);
        }

        // Números no eixo Y
        for (int i = -5; i <= 5; i++) {
            int y = (int) (h / 2 - i * escalaY);
            g.drawLine(x0 - 3, y, x0 + 3, y);
            if (i != 0)
                g.drawString(String.valueOf(i), x0 + 5, y + 5);
        }
    }

    // Desenha a curva de uma função usando linhas conectadas
    private void desenharCurva(Graphics g, Funcao f, Color cor) {
        int w = getWidth();
        int h = getHeight();
        double escalaX = w / (xMax - xMin);
        double escalaY = h / (xMax - xMin);
        double dx = (xMax - xMin) / passos;

        g.setColor(cor);

        for (int i = 0; i < passos - 1; i++) {
            double x1 = xMin + i * dx;
            double x2 = xMin + (i + 1) * dx;
            double y1 = f.calcular(x1);
            double y2 = f.calcular(x2);

            int px1 = (int) ((x1 - xMin) * escalaX);
            int px2 = (int) ((x2 - xMin) * escalaX);
            int py1 = (int) (h / 2 - y1 * escalaY);
            int py2 = (int) (h / 2 - y2 * escalaY);

            g.drawLine(px1, py1, px2, py2);
        }
    }

    // Preenche a área sob uma função f(x), entre a e b
    private void desenharAreaAbaixoFuncao(Graphics g, Funcao f) {
        int w = getWidth();
        int h = getHeight();
        double escalaX = w / (xMax - xMin);
        double escalaY = h / (xMax - xMin);
        double dx = (xMax - xMin) / passos;

        g.setColor(new Color(0, 100, 255, 80)); // azul translúcido

        for (int i = 0; i < passos; i++) {
            double x0 = xMin + i * dx;
            double x1 = x0 + dx;

            if (x1 < a || x0 > b) continue; // fora da região de integração

            double xi = Math.max(x0, a);
            double xf = Math.min(x1, b);
            double xm = (xi + xf) / 2;

            double y = f.calcular(xm);
            int px = (int) ((xi - xMin) * escalaX);
            int largura = (int) ((xf - xi) * escalaX);
            int alturaPx = (int) (y * escalaY);

            int py = h / 2 - alturaPx;
            g.fillRect(px, Math.min(h / 2, py), largura, Math.abs(alturaPx));
        }
    }

    // Preenche a área entre duas funções f(x) e g(x), entre a e b
    private void desenharAreaEntreFuncoes(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        double escalaX = w / (xMax - xMin);
        double escalaY = h / (xMax - xMin);
        double dx = (xMax - xMin) / passos;

        g.setColor(new Color(255, 100, 0, 80)); // laranja translúcido

        for (int i = 0; i < passos; i++) {
            double x0 = xMin + i * dx;
            double x1 = x0 + dx;

            if (x1 < a || x0 > b) continue;

            double xi = Math.max(x0, a);
            double xf = Math.min(x1, b);
            double xm = (xi + xf) / 2;

            double y1 = f1.calcular(xm);
            double y2 = f2.calcular(xm);

            int px = (int) ((xi - xMin) * escalaX);
            int largura = (int) ((xf - xi) * escalaX);
            int py1 = (int) (h / 2 - y1 * escalaY);
            int py2 = (int) (h / 2 - y2 * escalaY);

            int yTop = Math.min(py1, py2);
            int altura = Math.abs(py1 - py2);
            g.fillRect(px, yTop, largura, altura);
        }
    }
}
