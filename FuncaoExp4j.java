package grafico;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

public class FuncaoExp4j implements Funcao {

    private final Expression expressao;

    public FuncaoExp4j(String expressaoTexto) {
        // Define as funções personalizadas que não existem nativamente no exp4j
        Function cot = new Function("cot", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.tan(args[0]);
            }
        };

        Function sec = new Function("sec", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.cos(args[0]);
            }
        };

        Function csc = new Function("csc", 1) {
            @Override
            public double apply(double... args) {
                return 1.0 / Math.sin(args[0]);
            }
        };

        // Cria a expressão matemática usando exp4j
        this.expressao = new ExpressionBuilder(expressaoTexto)
                .variables("x", "pi") // Suporta variáveis x e pi
                .function(cot)
                .function(sec)
                .function(csc)
                .build()
                .setVariable("pi", Math.PI); // Define pi como constante π
    }

    @Override
    public double calcular(double x) {
        // Substitui o valor de x e avalia a expressão
        return expressao.setVariable("x", x).evaluate();
    }
}
