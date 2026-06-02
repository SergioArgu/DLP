package ast;

public class ArrayType extends AbstractType{

    private int size;
    private Type type;

    public ArrayType(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        if (other.equals(IntType.getInstance())){
            return type;
        }
        return new ErrorType("El índice de array tiene que ser un entero", locatable);
    }

    @Override
    public void mustPromote(Locatable locatable, Type other) {
        if (other instanceof ArrayType) {
            new ErrorType("ArrayType no promociona a ArrayType", locatable);
        }
    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
        new ErrorType("ArrayType no soporta la operación", locatable);
    }

    @Override
    public int numberOfBytes() {
        return getType().numberOfBytes()*getSize();
    }

    @Override
    public String toString() {
        return "ArrayType[of:" + type + ",size:" + size + "]";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
