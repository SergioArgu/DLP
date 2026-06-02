package ast;

public interface Expression extends Locatable{
    public Type getType();

    public void setType(Type type);

}
