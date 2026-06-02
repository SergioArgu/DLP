package ast;


public class VarDefinition extends AbstractDefinition implements Statement{

    private int offset;

    public VarDefinition(String name, Type type, int line, int column) {
        super(line, column, type, name);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
