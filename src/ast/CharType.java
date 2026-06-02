package ast;

import java.util.List;

public class CharType extends AbstractType{

    private static final CharType CHAR_TYPE = new CharType();

    //Constructor privado porque es un singleton y retorno siempre la misma instancia
    private CharType() {}

    public static CharType getInstance() {
        return CHAR_TYPE;
    }

    @Override
    public Type arithmetic(Locatable locatable, Type other) {
        if (other == NumberType.getInstance()){
            return other;
        } else if (other == this || other == IntType.getInstance()){
            return IntType.getInstance();
        }
        return new ErrorType("Tipo " + this + "no soporta operaciones aritméticas con tipo " + other, locatable);
    }

    @Override
    public Type arithmetic(Locatable locatable) {
        return this;
    }

    @Override
    public void mustPromote(Locatable locatable, Type other){
        if (other == IntType.getInstance()){
            return;
        }
        if(other != this){
            new ErrorType("Tipo " + other.getClass().getSimpleName() + " no promociona a tipo char", locatable);
        }
    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
    }

    @Override
    public void mustBeLogical(Locatable locatable) {
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        return new ErrorType("Tipo char no soporta operaciones de indexing", locatable);
    }

    @Override
    public Type dot(Locatable locatable, String str) {
        return new ErrorType("Tipo char no soporta operaciones de acceso a campo", locatable);
    }

    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        return new ErrorType("Tipo char no soporta la invocación", locatable);
    }

    @Override
    public Type Comparison(Locatable loc, Type other) {
        return this;
    }

    @Override
    public int numberOfBytes() {
        return 1;
    }

    @Override
    public char suffix() {
        return 'b';
    }

    @Override
    public String toString() {
        return "CharType";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
