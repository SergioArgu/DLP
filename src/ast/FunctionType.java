package ast;

import java.util.List;

public class FunctionType extends AbstractType{

    private Type type;//Tipo de retorno
    private List<VarDefinition> params;//Parametros

    public FunctionType(Type type, List<VarDefinition> params) {
        this.type = type;
        this.params = params;
    }

    public List<VarDefinition> getParams() {
        return params;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void mustPromote(Locatable locatable, Type other) {
        if (other instanceof FunctionType) {
            new ErrorType("FunctionType no promociona a FunctionType", locatable);
        }
    }

    //Tipo x soporta invocacion o no
    @Override
    public Type parentesis(Locatable locatable, List<Type> others) {
        if (others.size() != getParams().size()) {
            return new ErrorType("Número de parámetros incorrecto", locatable);
        }

        for (int i = 0; i < others.size(); i++) {
            Type tipoParametro = getParams().get(i).getType();
            Type tipoArgumento = others.get(i);

            tipoParametro.mustPromote(locatable, tipoArgumento);
        }

        return this.getType();
    }

    @Override
    public void mustBeBuildIn(Locatable locatable) {
        this.getType().mustBeBuildIn(locatable);
    }

    @Override
    public int numberOfBytes() {
        throw new UnsupportedOperationException("Una VarDefinition no debe poder ser de tipo FunctionType (No tiene valor de numberOfBytes)");
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }


}

