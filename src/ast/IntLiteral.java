package ast;

public class IntLiteral extends AbstractExpression{

    private int valor;

    public IntLiteral(int valor, int line, int column) {
        super(line, column);
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}