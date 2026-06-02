package ast;

public class Comparison extends AbstractExpression{

    private String operator;
    private Expression expresionLeft;
    private Expression expresionRight;

    public Comparison(Expression expresionLeft, Expression expresionRight, String operator, int line, int column) {
        super(line, column);
        this.expresionLeft = expresionLeft;
        this.expresionRight = expresionRight;
        this.operator = operator;
    }

    public Expression getExpresionLeft() {
        return expresionLeft;
    }

    public Expression getExpresionRight() {
        return expresionRight;
    }

    public String getOperator() {
        return operator;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}