package ast;

import java.util.List;

public abstract class AbstractType implements Type{

    @Override
    public void mustBeLogical(Locatable locatable) {
        new ErrorType("No soporta operaciones lógicas", locatable);
    }

    //Operaciones aritméticas (IntType, CharType, NumberType)
    @Override
    public Type arithmetic(Locatable locatable, Type other) {
        return new ErrorType("Operación aritmética no soportada", locatable);
        //return null;
    }

    //Operaciones aritméticas unarias
    @Override
    public Type arithmetic(Locatable locatable) {
        return new ErrorType("Operación aritmética unaria no soportada", locatable);
        //return null;
    }

    //operaciones de comparación
    @Override
    public Type Comparison(Locatable locatable, Type other) {
        return new ErrorType("Operación de comparación no soportada", locatable);
        //return null;
    }

    //Operaciones lógicas
    @Override
    public Type logic(Locatable locatable, Type other) {
        return new ErrorType("Operación lógica no soportada", locatable);
        //return null;
    }

    //Tipos que no cuadran en asignaciones, returns de funciones...
    @Override
    public void mustPromote(Locatable locatable, Type other) {
        new ErrorType("No promociona", locatable);
    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
        new ErrorType("Tipo incorrecto", locatable);
    }

    @Override
    public Type squareBrackets(Locatable locatable, Type other) {
        return new ErrorType("No soporta indexing", locatable);
        //return null;
    }

    //RecordFields cuando se usa un campo que no existe
    @Override
    public Type dot(Locatable locatable, String str) {
        return new ErrorType("No soporta acceso a campo", locatable);
        //return null;

    }

    //Tipo x soporta invocacion o no
    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        for (Type t:others){
            if (t instanceof ErrorType){
                return null;
            }
        }
        return new ErrorType("No soporta paréntesis", locatable);
        //return null;
    }


    @Override
    public char suffix() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " has no suffix");
    }
}
