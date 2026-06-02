package ast;


public class Assignment extends AbstractExpression implements Statement {

    private Expression expressionLeft;
    private Expression expressionRight;

    public Assignment(Expression expressionLeft, Expression expressionRight, int line, int column) {
        super(line, column);
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
    }

    public Expression getExpressionLeft() {
        return expressionLeft;
    }

    public Expression getExpressionRight() {
        return expressionRight;
    }

    @Override
    public String toString() {
        return "Assignment";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }


}