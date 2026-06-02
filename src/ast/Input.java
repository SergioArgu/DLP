package ast;

import java.util.List;

public class Input extends AbstractLocatable implements Statement{

    private List<Expression> arguments;

    public Input(List<Expression> arguments, int line, int column) {
        super(line, column);
        this.arguments = arguments;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Read";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}