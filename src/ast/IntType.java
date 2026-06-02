package ast;

import java.util.List;

public class IntType extends AbstractType {

    private static final IntType INT_TYPE = new IntType();

    //Constructor privado porque es un singleton y retorno siempre la misma instancia
    private IntType() {
    }

    public static IntType getInstance() {
        return INT_TYPE;
    }

    @Override
    public Type arithmetic(Locatable locatable, Type other) {
        if (other == NumberType.getInstance()){
            return other;
        } else if (other == this || other == CharType.getInstance()){
            return this;
        }
        return new ErrorType("Tipo " + this + "no soporta operaciones aritméticas con tipo " + other, locatable);
    }

    @Override
    public Type arithmetic(Locatable locatable) {
        return this;
    }


    @Override
    public void mustPromote(Locatable locatable, Type other) {
        if (other == CharType.getInstance() || other == IntType.getInstance()){
            return; // char/int -> int OK
        }
        else {
            new ErrorType("Tipo " + other.getClass().getSimpleName() + " no promociona a int", locatable);
        }
    }


    @Override
    public void mustBeBuildIn(Locatable locatable) {
    }

    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        return new ErrorType("Tipo int no soporta la invocación", locatable);
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        return new ErrorType("Tipo int no soporta operaciones de indexing", locatable);
    }

    @Override
    public Type logic(Locatable locatable, Type other) {
        if (other instanceof NumberType){
            return new ErrorType("Tipo int no soporta operaciones lógicas con tipo " + other.getClass().getSimpleName(), locatable);
        }
        return this;
    }

    @Override
    public Type Comparison(Locatable locatable, Type other) {
        return this;
    }

    @Override
    public void mustBeLogical(Locatable locatable) {
    }

    @Override
    public Type dot(Locatable locatable, String str) {
        return new ErrorType("Tipo int no soporta operaciones de acceso a campo", locatable);
    }

    @Override
    public int numberOfBytes() {
        return 2;
    }

    @Override
    public char suffix() {
        return 'i';
    }

    @Override
    public String toString() {
        return "IntType";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}

