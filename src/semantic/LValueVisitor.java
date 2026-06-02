package semantic;

import ast.*;

public class LValueVisitor extends AbstractVisitor<Void, Void> {

    //Solo las expressiones tienen LValue
    //Sirve para detectar que puede ser LValue y que no

    @Override
    public Void visit(Variable v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(Arithmetic v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(ArrayAcess v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(IntLiteral v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(CharLiteral v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(NumberLiteral v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(FieldAcess v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(true);
        return null;
    }

    @Override
    public Void visit(Cast v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(UnaryMinus v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(UnaryNot v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(Invocation v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(Comparison v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(Logic v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        return null;
    }

    @Override
    public Void visit(Assignment v, Void param) {
        super.visit(v, param);
        //Calcular atributos
        v.setLvalue(false);
        //Comprobaciones
        if (!((AbstractExpression) v.getExpressionLeft()).getLValue()) {
            new ErrorType("La parte izquierda de la asignación no es LValue", v.getExpressionLeft());
        }
        return null;
    }

    @Override
    public Void visit(Input v, Void param) {
        //Recorrer
        for (Expression e : v.getArguments()) {
            e.accept(this, null);
            //Comprobaciones
            if (!((AbstractExpression) e).getLValue()) {
                new ErrorType("La expression de input no es LValue", e);
            }
        }
        return null;
    }

}
