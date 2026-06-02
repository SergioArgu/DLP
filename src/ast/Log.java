package ast;

import java.util.List;

public class Log extends AbstractLocatable implements Statement{

    private List<Expression> arguments;
    //private Expression argument;

    public Log(List<Expression> arguments, int line, int column) {
        super(line, column);
        this.arguments = arguments;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Write";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
