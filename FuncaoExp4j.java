package grafico;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FuncaoExp4j implements Funcao {
    private final Expression expressao;

    // Construtor: recebe uma string como "x^2 + 3*x" e transforma em expressão
    public FuncaoExp4j(String formula) {
        this.expressao = new ExpressionBuilder(formula)
                .variable("x") // a variável da expressão é "x"
                .build();
    }

    // Avalia a expressão substituindo x por um valor real
    @Override
    public double calcular(double x) {
        return expressao.setVariable("x", x).evaluate();
    }
}
