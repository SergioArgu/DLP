package ast;

import errorhandler.ErrorHandler;

public class ErrorType extends AbstractType{

    String message;
    Locatable locatable;

    public ErrorType(String message, Locatable locatable) {
        this.message = message;
        this.locatable = locatable;
        ErrorHandler.getInstance().addError(this);
    }

    @Override
    public String toString() {
        return "ErrorType{" +
                "message='" + message + '\'' +
                ", locatable=" + "Linea:" +  locatable.getLine() + "Columna: " + locatable.getColumn() +
                '}';
    }

    @Override
    public int numberOfBytes() {
        throw new UnsupportedOperationException("Una VarDefinition no debe poder ser de tipo ErrorType (No tiene valor de numberOfBytes)");
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
