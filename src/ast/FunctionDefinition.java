package ast;

import java.util.List;

public class FunctionDefinition extends AbstractDefinition{

    private List<Statement> statements;//VarDefinitions y statements
    private int localBytes;//Bytes que ocupan las variables locales de la función


    public FunctionDefinition(List<Statement> statements, FunctionType functionType, String name, int line, int column) {
        super(line, column, functionType, name);
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public int getLocalBytes() {
        return localBytes;
    }

    public void setLocalBytes(int localBytes) {
        this.localBytes = localBytes;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }

}
