package ast;

import java.util.List;

public interface Type {

    public void mustBeLogical(Locatable locatable);

    public Type arithmetic(Locatable locatable, Type type);

    public Type arithmetic(Locatable locatable);

    public Type Comparison(Locatable locatable, Type type);

    public Type logic(Locatable locatable, Type type);

    public void mustPromote(Locatable locatable, Type type);

    public void mustBeBuildIn(Locatable locatable);

    public Type squareBrackets(Locatable locatable, Type type);

    public Type dot(Locatable locatable, String str);

    public Type parentesis(Locatable locatable, List<Type> types);

    public int numberOfBytes();

    public char suffix();

    public <PT,RT> RT accept(Visitor<PT,RT> v, PT param);

}
