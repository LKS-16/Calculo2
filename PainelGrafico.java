package grafico;

import javax.swing.*;
import java.awt.*;

public class PainelGrafico extends JPanel {
    private final Funcao funcao;
    private final double xMin, xMax;
    private final int passos;

    public PainelGrafico(Funcao funcao, double xMin, double xMax, int passos) {
        this.funcao = funcao;
        this.xMin = xMin;
        this.xMax = xMax;
        this.passos = passos;
    }

    // Método chamado automaticamente para desenhar o painel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharEixos(g);      // eixo X e Y
        desenharFuncao(g);     // curva da função
        desenharIntegral(g);   // retângulos da integral
    }

    // Desenha os eixos X e Y no centro do painel
    private void desenharEixos(Graphics g) {
        int largura = getWidth();
        int altura = getHeight();
        g.setColor(Color.GRAY);
        g.drawLine(0, altura / 2, largura, altura / 2);      // eixo X
        g.drawLine(largura / 2, 0, largura / 2, altura);     // eixo Y
    }

    // Desenha a curva da função conectando pontos consecutivos
    private void desenharFuncao(Graphics g) {
        g.setColor(Color.BLUE);
        int largura = getWidth();
        int altura = getHeight();
        double escalaX = largura / (xMax - xMin);
        double escalaY = altura / (xMax - xMin); // proporção 1:1

        for (int i = 0; i < largura - 1; i++) {
            double x1 = xMin + i / escalaX;
            double x2 = xMin + (i + 1) / escalaX;
            double y1 = funcao.calcular(x1);
            double y2 = funcao.calcular(x2);

            int px1 = i;
            int py1 = altura / 2 - (int)(y1 * escalaY);
            int px2 = i + 1;
            int py2 = altura / 2 - (int)(y2 * escalaY);

            g.drawLine(px1, py1, px2, py2); // linha entre os pontos
        }
    }

    // Desenha barras verticais representando a soma de Riemann (área da integral)
    private void desenharIntegral(Graphics g) {
        g.setColor(new Color(0, 255, 0, 80)); // verde semi-transparente
        int largura = getWidth();
        int altura = getHeight();
        double escalaX = largura / (xMax - xMin);
        double escalaY = altura / (xMax - xMin);

        double dx = (xMax - xMin) / passos;
        for (int i = 0; i < passos; i++) {
            double x = xMin + i * dx;
            double y = funcao.calcular(x);

            int xPixel = (int)((x - xMin) * escalaX);
            int yPixel = (int)(y * escalaY);
            int alturaBarra = (int)(yPixel);

            // desenha o retângulo representando a área sob a curva
            g.fillRect(xPixel, altura / 2 - alturaBarra, (int)(dx * escalaX), alturaBarra);
        }
    }
}
