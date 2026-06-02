package ast;

import java.util.List;

public class Return extends AbstractLocatable implements Statement{

    private Expression expresion;

    public Return(Expression expresion, int line, int column) {
        super(line, column);
        this.expresion = expresion;
    }

    public Expression getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "Return";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
