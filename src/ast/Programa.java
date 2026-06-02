package ast;

import java.util.List;

public class Programa implements ASTNode{

    private List<Definition> definitions;

    public Programa(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }



}
