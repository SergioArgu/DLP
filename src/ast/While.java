package ast;

import java.util.List;

public class While extends AbstractLocatable implements Statement{

    private List<Statement> body;
    private Expression expresion;

    public While(List<Statement> body, Expression expresion, int line, int column) {
        super(line, column);
        this.body = body;
        this.expresion = expresion;
    }

    public List<Statement> getBody() {
        return body;
    }

    public Expression getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "While";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
