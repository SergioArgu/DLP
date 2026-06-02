package codegen;

import ast.*;

public abstract class AbstractCGVisitor<PT,RT> implements Visitor<PT,RT>{

    private CodeGenerator cg;

    public AbstractCGVisitor(CodeGenerator cg){
        this.cg = cg;
    }

    protected CodeGenerator cg() {
        return cg;
    }

    @Override
    public RT visit(Variable v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Variable");
    }

    @Override
    public RT visit(Arithmetic v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Arithmetic");
    }

    @Override
    public RT visit(ArrayAcess v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in ArrayAcess");
    }

    @Override
    public RT visit(IntLiteral v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in IntLiteral");
    }

    @Override
    public RT visit(CharLiteral v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in CharLiteral");
    }

    @Override
    public RT visit(NumberLiteral v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in NumberLiteral");
    }

    @Override
    public RT visit(FieldAcess v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in FieldAcess");
    }

    @Override
    public RT visit(Cast v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Cast");
    }

    @Override
    public RT visit(UnaryMinus v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in UnaryMinus");
    }

    @Override
    public RT visit(UnaryNot v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in UnaryNot");
    }

    @Override
    public RT visit(Invocation v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Invocation");
    }

    @Override
    public RT visit(Comparison v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Comparison");
    }

    @Override
    public RT visit(Logic v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Logic");
    }

    @Override
    public RT visit(RecordField v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in RecordField");
    }

    @Override
    public RT visit(Assignment v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Assignment");
    }

    @Override
    public RT visit(While v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in While");
    }

    @Override
    public RT visit(If v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in If");
    }

    @Override
    public RT visit(Input v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Input");
    }

    @Override
    public RT visit(Return v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Return");
    }

    @Override
    public RT visit(Log v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Log");
    }

    @Override
    public RT visit(FunctionDefinition v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in FunctionDefinition");
    }

    @Override
    public RT visit(VarDefinition v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in VarDefinition");
    }

    @Override
    public RT visit(Programa v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in Programa");
    }

    @Override
    public RT visit(RecordType v, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in RecordType");
    }

    @Override
    public RT visit(NumberType numberType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in NumberType");
    }

    @Override
    public RT visit(VoidType voidType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in VoidType");
    }

    @Override
    public RT visit(IntType intType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in IntType");
    }

    @Override
    public RT visit(FunctionType functionType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in FunctionType");
    }

    @Override
    public RT visit(ErrorType errorType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in ErrorType");
    }

    @Override
    public RT visit(CharType charType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in CharType");
    }

    @Override
    public RT visit(ArrayType arrayType, PT param) {
        throw new UnsupportedOperationException("Unsupported Operation Exception in ArrayType");
    }
}
