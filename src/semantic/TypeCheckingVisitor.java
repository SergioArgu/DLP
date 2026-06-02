package semantic;

import ast.*;

import java.util.ArrayList;
import java.util.List;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Void> {

    @Override
    public Void visit(Variable v, Type param) {
        super.visit(v, param);
        v.setType(v.getDefinition().getType());
        return null;
    }

    @Override
    public Void visit(Arithmetic v, Type param) {
        super.visit(v, param);
        v.setType(v.getExpresionLeft().getType().arithmetic(v, v.getExpresionRight().getType()));
        return null;
    }

    @Override
    public Void visit(ArrayAcess v, Type param) {
        super.visit(v, param);
        v.setType(v.getArrayName().getType().squareBrackets(v, v.getIndex().getType()));
        return null;
    }

    @Override
    public Void visit(IntLiteral v, Type param) {
        super.visit(v, param);
        v.setType(IntType.getInstance());
        return null;
    }

    @Override
    public Void visit(CharLiteral v, Type param) {
        super.visit(v, param);
        v.setType(CharType.getInstance());
        return null;
    }

    @Override
    public Void visit(NumberLiteral v, Type param) {
        super.visit(v, param);
        v.setType(NumberType.getInstance());
        return null;
    }

    @Override
    public Void visit(FieldAcess v, Type param) {
        super.visit(v, param);
        v.setType(v.getExpression().getType().dot(v, v.getFieldName()));
        return null;
    }

    @Override
    public Void visit(Cast v, Type param) {
        super.visit(v, param);
        Type source = v.getExpression().getType();
        Type target = v.getType();
        //source.mustPromote(v, target);
        v.setType(target);
        return null;
    }

    @Override
    public Void visit(UnaryMinus v, Type param) {
        super.visit(v, param);
        v.setType(v.getExpression().getType().arithmetic(v));
        return null;
    }

    @Override
    public Void visit(UnaryNot v, Type param) {
        super.visit(v, param);
        v.getExpression().getType().mustBeLogical(v);
        v.setType(v.getExpression().getType());

        return null;
    }

    @Override
    public Void visit(Invocation v, Type param) {
        super.visit(v, param);
        if (v.getName().getType() instanceof ErrorType) {
            return null;
        }
        List<Type> tipos = new ArrayList<>();
        for (Expression e : v.getArgumentos()) {
            tipos.add(e.getType());
        }
        v.setType(v.getName().getType().parentesis(v, tipos));
        return null;
    }

    @Override
    public Void visit(Comparison v, Type param) {
        super.visit(v, param);
        Type left = v.getExpresionLeft().getType();
        Type right = v.getExpresionRight().getType();
        v.setType(left.Comparison(v, right));
        return null;
    }

    @Override
    public Void visit(Logic v, Type param) {
        super.visit(v, param);
        Type left = v.getExpresionLeft().getType();
        Type right = v.getExpresionRight().getType();
        v.setType(left.logic(v, right));
        return null;
    }

    @Override
    public Void visit(RecordField v, Type param) {
        super.visit(v, param);
        return null;
    }

    @Override
    public Void visit(Assignment v, Type param) {
        super.visit(v, param);
        if (v.getExpressionLeft().getType() instanceof ErrorType || v.getExpressionRight().getType() instanceof ErrorType){
            return null;
        }
        v.getExpressionLeft().getType().mustPromote(v, v.getExpressionRight().getType());
        //v.setType(v.getExpressionLeft().getType());
        return null;
    }

    @Override
    public Void visit(While v, Type param) {
        super.visit(v, param);
        v.getExpresion().getType().mustBeLogical(v);
        return null;
    }

    @Override
    public Void visit(If v, Type param) {
        super.visit(v, param);
        v.getExpresion().getType().mustBeLogical(v);
        return null;
    }

    @Override
    public Void visit(Input v, Type param) {
        super.visit(v, param);
        for (Expression e : v.getArguments()) {
            if (e.getType() != null && !(e.getType() instanceof ErrorType)) {
                e.getType().mustBeBuildIn(v);
            }
        }
        return null;
    }

    @Override
    public Void visit(Return v, Type param) {
        super.visit(v, param);

        FunctionType functionType = (FunctionType) param;
        Type expected = functionType.getType();
        Type actual = v.getExpresion().getType();
        //actual.mustPromote(v, expected);
        expected.mustPromote(v, actual);
        return null;
    }

    @Override
    public Void visit(Log v, Type param) {
        super.visit(v, param);
        for (Expression e : v.getArguments()) {
            if (e.getType() != null && !(e.getType() instanceof ErrorType)) {
                e.getType().mustBeBuildIn(v);
            }
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition v, Type param) {
        super.visit(v, v.getType());
        return null;
    }

    @Override
    public Void visit(VarDefinition v, Type param) {
        super.visit(v, param);
        return null;
    }

    @Override
    public Void visit(Programa v, Type param) {
        super.visit(v, param);
        return null;
    }
}
