package ast;

public interface Visitor<PT, RT> {

    //PT --> parámetro
    //RT --> return
    //Esquema a seguir: recorrer - calcular atributos - comprobaciones

    //Expresiones
    RT visit (Variable v, PT param);
    RT visit (Arithmetic v, PT param);
    RT visit (ArrayAcess v, PT param);
    RT visit (IntLiteral v, PT param);
    RT visit (CharLiteral v, PT param);
    RT visit (NumberLiteral v, PT param);
    RT visit (FieldAcess v, PT param);
    RT visit (Cast v, PT param);
    RT visit (UnaryMinus v, PT param);
    RT visit (UnaryNot v, PT param);
    RT visit (Invocation v, PT param);
    RT visit (Comparison v, PT param);
    RT visit (Logic v, PT param);

    //RecordField
    RT visit(RecordField v, PT param);

    //Statements
    RT visit(Assignment v, PT param);
    RT visit(While v, PT param);
    RT visit(If v, PT param);
    RT visit(Input v, PT param);
    RT visit(Return v, PT param);
    RT visit(Log v, PT param);

    //Definiciones
    RT visit(FunctionDefinition v, PT param);
    RT visit(VarDefinition v, PT param);

    //Program
    RT visit(Programa v, PT param);

    //Types
    RT visit(RecordType v, PT param);
    RT visit(NumberType numberType, PT param);
    RT visit(VoidType voidType, PT param);
    RT visit(IntType intType, PT param);
    RT visit(FunctionType functionType, PT param);
    RT visit(ErrorType errorType, PT param);
    RT visit(CharType charType, PT param);
    RT visit(ArrayType arrayType, PT param);
}
