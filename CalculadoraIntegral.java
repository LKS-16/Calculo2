package grafico;

public class CalculadoraIntegral {
    // Método que calcula a integral definida de f(x), de a até b, usando soma de Riemann
    public static double integrar(Funcao f, double a, double b, int passos) {
        double soma = 0.0;
        double dx = (b - a) / passos; // largura de cada retângulo

        // Soma das áreas dos retângulos
        for (int i = 0; i < passos; i++) {
            double x = a + i * dx;
            soma += f.calcular(x) * dx;
        }

        return soma; // valor aproximado da integral
    }
}
