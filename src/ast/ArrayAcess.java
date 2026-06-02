package ast;

public class ArrayAcess extends AbstractExpression{

    private Expression arrayName;
    private Expression index;

    public ArrayAcess(Expression arrayName, Expression index, int line, int column) {
        super(line, column);
        this.arrayName = arrayName;
        this.index = index;
    }

    public Expression getArrayName() {
        return arrayName;
    }

    public Expression getIndex() {
        return index;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}