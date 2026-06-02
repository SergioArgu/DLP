package codegen;

import ast.*;

public class ValueCGVisitor extends AbstractCGVisitor<Object, Void>{
    //Expresiones

    private AddressCGVisitor address;

    public ValueCGVisitor(CodeGenerator cg){
        super(cg);
    }

    public void setAddress(AddressCGVisitor address) {
        this.address = address;
    }

    /* Plantilla de generación de código
    value[[NumberLiteral: expression -> REAL_CONSTANT]]() =
        <pushf> expression.valor
     */
    @Override
    public Void visit(NumberLiteral v, Object param) {
        cg().pushf(v.getValor());
        return null;
    }

    /* Plantilla de generación de código
    value[[CharLiteral: expression -> CHAR_CONSTANT]]() =
        <pushb> expression.valor
     */
    @Override
    public Void visit(CharLiteral v, Object param) {
        cg().pushb(v.getValor());
        return null;
    }

    /* Plantilla de generación de código
    value[[IntLiteral: expression -> INT_CONSTANT]]() =
        <pushi> expression.valor
     */
    @Override
    public Void visit(IntLiteral v, Object param) {
        cg().pushi(v.getValor());
        return null;
    }

    /* Plantilla de generación de código
    value[[Variable: expression --> ID]]() =
        address[[expression]]()
        <load> expression.type.suffix()
     */
    @Override
    public Void visit(Variable v, Object param) {
        v.accept(address, param);
        cg().load(v.getType());
        return null;
    }

    /* Plantilla de generación de código
    value[[FieldAcess: expression₁ --> expression₂ ID]]() =
        address[[expression₁]]()
        <load> expression₁.type.suffix()
     */
    @Override
    public Void visit(FieldAcess v, Object param) {
        v.accept(address,param);//Obtienes la direccion
        cg().load(v.getType());//Cargas el valor
        return null;
    }

    /* Plantilla de generación de código
    value[[ArrayAccess: expression₁ --> expression₂ expression₃]]() =
        address[[expression₁]]()
        <load> expression₁.type.suffix()
     */
    @Override
    public Void visit(ArrayAcess v, Object param) {
        v.accept(address, param);//Obtienes la direccion
        cg().load(v.getType());//Cargas el valor (solo cuando hay variables)
        return null;
    }

    /* Plantilla de generación de código
    value[[UnaryMinus: expression₁ --> expression₂]]() =
        value[[expression₂]]()
        <pushi> -1
        <mul> expression₁.type.suffix()
     */
    @Override
    public Void visit(UnaryMinus v, Object param) {
        v.getExpression().accept(this, param);
        cg().pushi(-1);
        cg().arithmetic("*", v.getType());
        return null;
    }

    /* Plantilla de generación de código
    value[[UnaryNot: expression₁ --> expression₂]]() =
        value[[expression₂]]()
        <not>
     */
    @Override
    public Void visit(UnaryNot v, Object param) {
        v.getExpression().accept(this, param);
        cg().not();
        return null;
    }

    /* Plantilla de generación de código
    value[[Arithmetic: expression₁ --> expression₂ (+|-|*|/) expression₃]]() =
        value[[expression₂]]()
        cg.convertTo(expression₂.type, expression₁.type);
        value[[expression₃]]()
        cg.convertTo(expression₃.type, expression₁.type);
        cg.arithmetic(expression₁.operator, expression₁.type);
     */
    @Override
    public Void visit(Arithmetic v, Object param) {
        v.getExpresionLeft().accept(this, param);
        cg().convertTo(v.getExpresionLeft().getType(), v.getType());
        v.getExpresionRight().accept(this, param);//Recorro expresion
        cg().convertTo(v.getExpresionRight().getType(), v.getType());//Convierto expresion
        cg().arithmetic(v.getOperator(), v.getType());//Opero
        return null;
    }

    /* Plantilla de generación de código
    value[[Comparison: expression₁ --> expression₂ (>|>=|<=|<|==|!=) expression₃]]() =
        value[[expression₂]]()
        cg.convertTo(expression₂.type, expression₁.type);
        value[[expression₃]]()
        cg.convertTo(expression₃.type, expression₁.type);
        cg.comparison(expression₁.operator, expression₁.type);
     */
    @Override
    public Void visit(Comparison v, Object param) {
        v.getExpresionLeft().accept(this, param);
        cg().convertTo(v.getExpresionLeft().getType(), v.getType());
        v.getExpresionRight().accept(this, param);//Recorro expresion
        cg().convertTo(v.getExpresionRight().getType(), v.getType());//Convierto expresion
        cg().comparison(v.getOperator(), v.getType());//Opero
        return null;
    }

    /* Plantilla de generación de código
    value[[Logic: expression₁ --> expression₂ ( && | || ) expression₃]]() =
        value[[expression₂]]()
        cg.convertTo(expression₂.type, expression₁.type);
        value[[expression₃]]()
        cg.convertTo(expression₃.type, expression₁.type);
        cg.logic(expression₁.operator, expression₁.type);
     */
    @Override
    public Void visit(Logic v, Object param) {
        v.getExpresionLeft().accept(this, param);
        cg().convertTo(v.getExpresionLeft().getType(), v.getType());
        v.getExpresionRight().accept(this, param);//Recorro expresion
        cg().convertTo(v.getExpresionRight().getType(), v.getType());//Convierto expresion
        cg().logic(v.getOperator());//Opero
        return null;
    }

    /* Plantilla de generación de código
    value[[Invocation: expression₁ --> expression₂ expression₃*]]() =
        for(int i = 0; i < expression₃.size(); i++){
            value[[expression₃.get(i)]]()
            cg.covertTo(expression₃.get(i).type, expression₂.type.getParams().get(i).type);
        }
        <call> expression₂.name
     */
    @Override
    public Void visit(Invocation v, Object param) {
        for (int i = 0; i<v.getArgumentos().size(); i++){
            v.getArgumentos().get(i).accept(this, param);
            cg().convertTo(v.getArgumentos().get(i).getType(), ((FunctionType)v.getName().getType()).getParams().get(i).getType());
        }
        cg().call(v.getName().getName());//Llamada a función
        return null;
    }

    /* Plantilla de generación de código
    value[[Cast: expression₁ --> expression₂ type]]() =
        value[[expression₂]]()
        cg.convertTo(expression₂.type, type);
     */
    @Override
    public Void visit(Cast v, Object param) {
        v.getExpression().accept(this, param);//Visito el valor y hace el push
        cg().convertTo(v.getExpression().getType(), v.getType());//Conversion
        return null;
    }


}
