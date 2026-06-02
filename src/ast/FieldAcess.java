package ast;

public class FieldAcess extends AbstractExpression{

    private String fieldName;
    private Expression expression;

    public FieldAcess(String fieldName, Expression expression, int line, int column) {
        super(line, column);
        this.fieldName = fieldName;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getFieldName() {
        return fieldName;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }
}