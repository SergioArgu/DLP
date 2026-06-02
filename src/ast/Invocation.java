package ast;

import java.util.List;

public class Invocation extends AbstractExpression implements Statement{

    private List<Expression> argumentos;
    private Variable name;

    public Invocation(List<Expression> argumentos, Variable name, int line, int column) {
        super(line, column);
        this.argumentos = argumentos;
        this.name = name;
    }

    public List<Expression> getArgumentos() {
        return argumentos;
    }

    public Variable getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Invocation";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}