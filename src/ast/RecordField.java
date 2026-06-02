package ast;

import java.util.List;

public class RecordField extends AbstractLocatable {//Es lo que hay dentro del cuerpo del record
//Igual que varDefinition pero cambia comportamiento por lo que se usa una clase distinta
    private String name;
    private Type type;

    private int offset;

    public RecordField(String name, Type type, int line, int column) {
        super(line, column);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Field[" +
                "name:" + name +
                ",type:" + type +
                ",offset:" + offset +
                ']';
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }


}
