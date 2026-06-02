package ast;

import java.util.List;

public class If extends AbstractLocatable implements Statement{

    private List<Statement> else_;
    private List<Statement> then;
    private Expression expresion;

    public If(List<Statement> else_, List<Statement> then, Expression expresion, int line, int column) {
        super(line, column);
        this.else_ = else_;
        this.then = then;
        this.expresion = expresion;
    }

    public List<Statement> getElse_() {
        return else_;
    }

    public List<Statement> getThen() {
        return then;
    }

    public Expression getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return "If";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
