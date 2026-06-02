package ast;

public abstract class AbstractExpression extends AbstractLocatable implements Expression {

    private Boolean lvalue = false;
    private Definition definition;
    private Type type;

    public AbstractExpression(int line, int column) {
        super(line, column);
    }

    public boolean getLValue() {
        return lvalue;
    }//Comprobaciones

    public void setLvalue(Boolean lvalue) {//Calcular atributos
        this.lvalue = lvalue;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
