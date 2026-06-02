package codegen;

import ast.*;

public class AddressCGVisitor extends AbstractCGVisitor<Object, Void>{
    //Lvalues

    private ValueCGVisitor value;

    public AddressCGVisitor(CodeGenerator cg){
        super(cg);
    }

    public void setValue(ValueCGVisitor value) {
        this.value = value;
    }

    /* Plantilla de generación de código
    address[[Variable: expression --> ID]]() =
        if (expression.definition.scope == 0){
            <pusha> expression.definition.offset
        } else{
            <pusha bp>
            <pusha> expression.definition.offset
            <addi>
        }
    */
    @Override
    public Void visit(Variable v, Object param) {
        if (v.getDefinition().getScope() == 0){//Variables globales
            cg().pusha( ( (VarDefinition)v.getDefinition() ).getOffset() );
        } else {//Variables locales y parámetros
            cg().pushbp();
            cg().pushi( ( (VarDefinition)v.getDefinition() ).getOffset() );
            cg().addi();
        }
        return null;
    }

    /* Plantilla de generación de código
    address[[FieldAccess: expression₁ --> expression₂ ID]]() =
        address[[expression₂]]()
        <pushi> expression₂.type.getField(ID).offset
        <addi>
     */
    @Override
    public Void visit(FieldAcess v, Object param) {
        v.getExpression().accept(this, param);
        RecordType type = (RecordType) v.getExpression().getType();
        cg().pushi(type.getField(v.getFieldName()).getOffset());//Consigo el offset del field correspondiente
        cg().addi();//Sumo la dirección de la exp y el offset
        return null;
    }

    /* Plantilla de generación de código
    address[[ArrayAccess: expression₁ --> expression₂ expression₃]]() =
        address[[expression₂]]()
        value[[expression₃]]()
        cg.convertTo(expression₃.type, IntType.getInstance());
        <pushi> expression₁.type.numberOfBytes()
        <muli>
        <addi>
     */
    @Override
    public Void visit(ArrayAcess v, Object param) {
        v.getArrayName().accept(this, param);//Miro ArrayName
        v.getIndex().accept(value, param);//Miro el valor del index
        cg().convertTo(v.getIndex().getType(), IntType.getInstance());//Convierto al tipo necesario. Ejemplo: a['c']  (Esto se permite)
        ArrayType arrayType = (ArrayType) v.getArrayName().getType();
        int size = arrayType.getType().numberOfBytes();

        //index * size
        cg().pushi(size);
        cg().arithmetic("*", IntType.getInstance());

        cg().addi();//base + offset
        return null;
    }
}
