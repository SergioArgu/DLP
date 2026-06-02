package ast;

import java.util.List;

public class NumberType extends AbstractType{

    private static final NumberType NUMBER_TYPE = new NumberType();

    //Constructor privado porque es un singleton y retorno siempre la misma instancia
    private NumberType() {}

    public static NumberType getInstance() {
        return NUMBER_TYPE;
    }

    @Override
    public Type arithmetic(Locatable locatable, Type other) {
        if (other == IntType.getInstance() || other == this || other == CharType.getInstance()){
            return this;
        }
        return new ErrorType("Tipo " + this + "no soporta operaciones aritméticas con tipo " + other, locatable);
    }

    @Override
    public Type logic(Locatable locatable, Type other) {
        return new ErrorType("Number no soporta operaciones lógicas", locatable);
    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
    }

    @Override
    public void mustPromote(Locatable locatable, Type other) {
        if (other == IntType.getInstance()){
            return;// int -> number OK
        }
        if (!(other == NumberType.getInstance())){
            new ErrorType("Tipo " + other.getClass().getSimpleName() + " no promociona a NumberType", locatable);
        }
    }

    @Override
    public void mustBeLogical(Locatable locatable) {
        new ErrorType("Tipo number no puede ser usado en una condición/Operación unaria", locatable);
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        return new ErrorType("Tipo number no soporta operaciones de indexing", locatable);
    }

    @Override
    public Type dot(Locatable locatable, String str) {
        return new ErrorType("Tipo number no soporta operaciones de acceso a campo", locatable);
    }

    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        return new ErrorType("Tipo number no soporta la invocación", locatable);
    }

    @Override
    public Type Comparison(Locatable loc, Type other) {
        return this;
    }

    @Override
    public int numberOfBytes() {
        return 4;
    }

    @Override
    public char suffix() {
        return 'f';
    }

    @Override
    public String toString() {
        return "NumberType";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}

