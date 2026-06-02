package semantic;

import ast.*;

public class AbstractVisitor<PT,RT> implements Visitor<PT,RT> {

    //Visitor abstracto solo para recorrer

    @Override
    public RT visit(Variable v, PT param) {
        return null;
    }

    @Override
    public RT visit(Arithmetic v, PT param) {
        //Recorrer
        v.getExpresionLeft().accept(this, param);
        v.getExpresionRight().accept(this, param);
        return null;
    }

    @Override
    public RT visit(ArrayAcess v, PT param) {
        //Recorrer
        v.getArrayName().accept(this, param);
        v.getIndex().accept(this, param);
        return null;
    }

    @Override
    public RT visit(IntLiteral v, PT param) {
        return null;
    }

    @Override
    public RT visit(CharLiteral v, PT param) {
        return null;
    }

    @Override
    public RT visit(NumberLiteral v, PT param) {
        return null;
    }

    @Override
    public RT visit(FieldAcess v, PT param) {
        //Recorrer
        v.getExpression().accept(this, param);
        return null;
    }

    @Override
    public RT visit(Cast v, PT param) {
        //Recorrer
        v.getExpression().accept(this, param);
        return null;
    }

    @Override
    public RT visit(UnaryMinus v, PT param) {
        //Recorrer
        v.getExpression().accept(this, param);
        return null;
    }

    @Override
    public RT visit(UnaryNot v, PT param) {
        //Recorrer
        v.getExpression().accept(this, param);
        return null;
    }

    @Override
    public RT visit(Invocation v, PT param) {
        //Recorrer
        for (var arg : v.getArgumentos()) {
            arg.accept(this, param);
        }
        v.getName().accept(this, param);
        return null;
    }

    @Override
    public RT visit(Comparison v, PT param) {
        //Recorrer
        v.getExpresionLeft().accept(this, param);
        v.getExpresionRight().accept(this, param);
        return null;
    }

    @Override
    public RT visit(Logic v, PT param) {
        //Recorrer
        v.getExpresionLeft().accept(this, param);
        v.getExpresionRight().accept(this, param);
        return null;
    }

    @Override
    public RT visit(RecordField v, PT param) {
        return null;
    }

    @Override
    public RT visit(Assignment v, PT param) {
        //Recorrer
        v.getExpressionLeft().accept(this, param);
        v.getExpressionRight().accept(this, param);
        return null;
    }

    @Override
    public RT visit(While v, PT param) {
        //Recorrer
        v.getExpresion().accept(this, param);
        for (Statement st : v.getBody()) {
            st.accept(this, param);
        }
        return null;
    }

    @Override
    public RT visit(If v, PT param) {
        //Recorrer
        v.getExpresion().accept(this, param);
        for (Statement st : v.getThen()) {
            st.accept(this, param);
        }
        if (v.getElse_() != null) {
            for (Statement st : v.getElse_()) {
                st.accept(this, param);
            }
        }
        return null;
    }

    @Override
    public RT visit(Input v, PT param) {
        //Recorrer
        for (Expression e : v.getArguments()) {
            e.accept(this, param);
        }
        return null;
    }

    @Override
    public RT visit(Return v, PT param) {
        //Recorrer
        if (v.getExpresion() != null) {
            v.getExpresion().accept(this, param);
        }
        return null;
    }

    @Override
    public RT visit(Log v, PT param) {
        //Recorrer
        for (Expression e : v.getArguments()) {
            e.accept(this, param);
        }
        return null;
    }

    @Override
    public RT visit(FunctionDefinition v, PT param) {
        //Recorrer
        //PARÁMETROS (desde FunctionType)
        FunctionType ft = (FunctionType) v.getType();
        if (ft.getParams() != null) {
            for (VarDefinition paramDef : ft.getParams()) {
                paramDef.accept(this, param);
            }
        }
        //STATEMENTS (VarDefinition y statements)
        if (v.getStatements() != null) {
            for (Statement st : v.getStatements()) {
                st.accept(this, param);
            }
        }
        return null;
    }

    @Override
    public RT visit(VarDefinition v, PT param) {
        if (v.getType() != null) {
            v.getType().accept(this, param);
        }
        return null;
    }

    @Override
    public RT visit(Programa v, PT param) {
        //Recorrer
        if (v.getDefinitions() != null) {
            for (Definition def : v.getDefinitions()) {
                def.accept(this, param);
            }
        }
        return null;
    }

    @Override
    public RT visit(RecordType v, PT param) {
        return null;
    }

    @Override
    public RT visit(NumberType numberType, PT param) {
        return null;
    }

    @Override
    public RT visit(VoidType voidType, PT param) {
        return null;
    }

    @Override
    public RT visit(IntType intType, PT param) {
        return null;
    }

    @Override
    public RT visit(FunctionType functionType, PT param) {
        return null;
    }

    @Override
    public RT visit(ErrorType errorType, PT param) {
        return null;
    }

    @Override
    public RT visit(CharType charType, PT param) {
        return null;
    }

    @Override
    public RT visit(ArrayType arrayType, PT param) {
        return null;
    }


}
