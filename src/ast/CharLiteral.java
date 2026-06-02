package ast;

public class CharLiteral extends AbstractExpression{

    private char valor;

    public CharLiteral(char valor, int line, int column) {
        super(line, column);
        this.valor = valor;
    }

    public char getValor() {
        return valor;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}