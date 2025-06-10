package grafico;

public class CalculadoraIntegral {

    // Método que calcula a integral definida de uma função f entre os limites a e b
    public static double calcularArea(Funcao f, double a, double b, int n) {
        double dx = (b - a) / n;
        double soma = 0.0;

        // Regra dos trapézios:
        // A área aproximada é (dx/2) * [f(a) + 2*f(x1) + 2*f(x2) + ... + f(b)]
        for (int i = 0; i <= n; i++) {
            double x = a + i * dx;
            double peso = (i == 0 || i == n) ? 1 : 2; // extremos têm peso 1, demais têm peso 2
            soma += peso * f.calcular(x);
        }

        return (dx / 2.0) * soma;
    }

    // Método que calcula a área entre duas funções f1 e f2 entre os limites a e b
    public static double calcularAreaEntreFuncoes(Funcao f1, Funcao f2, double a, double b, int n) {
        double dx = (b - a) / n;
        double soma = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * dx;
            double y1 = f1.calcular(x);
            double y2 = f2.calcular(x);

            // A diferença entre as funções define a altura do trapézio
            double diff = y1 - y2;
            double peso = (i == 0 || i == n) ? 1 : 2;
            soma += peso * diff;
        }

        return Math.abs((dx / 2.0) * soma);
    }
}
