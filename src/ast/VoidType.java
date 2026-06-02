package ast;

import java.util.List;

public class VoidType extends AbstractType{

    private static final VoidType VOID_TYPE = new VoidType();

    //Constructor privado porque es un singleton y retorno siempre la misma instancia
    private VoidType() {}

    public static VoidType getInstance() {
        return VOID_TYPE;
    }

    @Override
    public void mustPromote(Locatable locatable, Type other) {
        if (other == IntType.getInstance()){
            new ErrorType("Tipo int no promociona a tipo void", locatable);
            return;
        }
        new ErrorType("Tipo " + other.getClass().getSimpleName() + " no promociona a VoidType", locatable);

    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
        new ErrorType("VoidType no soporta la operación", locatable);
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        return new ErrorType("Tipo void no soporta operaciones de indexing", locatable);
    }

    @Override
    public Type dot(Locatable locatable, String str) {
        return new ErrorType("Tipo void no soporta operaciones de acceso a campo", locatable);
    }

    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        return new ErrorType("Tipo void no soporta la invocación", locatable);
    }

    @Override
    public int numberOfBytes() {
        throw new UnsupportedOperationException("Una VarDefinition no debe poder ser de tipo void (No tiene valor de numberOfBytes)");
    }

    @Override
    public String toString() {
        return "void";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}

