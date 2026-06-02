package ast;

public class Arithmetic extends AbstractExpression{

    private String operator;
    private Expression expresionLeft;
    private Expression expresionRight;

    public Arithmetic(Expression expresionLeft, Expression expresionRight, String operator, int line, int column) {
        super(line, column);
        this.expresionLeft = expresionLeft;
        this.expresionRight = expresionRight;
        this.operator = operator;
    }

    public Expression getExpresionRight() {
        return expresionRight;
    }

    public Expression getExpresionLeft() {
        return expresionLeft;
    }

    public String getOperator() {
        return operator;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}
