package ast;

public interface ASTNode {

    public <PT,RT> RT accept(Visitor<PT,RT> v, PT param);//Para todos los nodos
}
