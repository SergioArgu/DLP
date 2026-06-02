package ast;

public class NumberLiteral extends AbstractExpression{

    private double valor;

    public NumberLiteral(double valor, int line, int column) {
        super(line, column);
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}
