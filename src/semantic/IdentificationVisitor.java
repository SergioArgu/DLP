package semantic;

import ast.*;
import symboltable.SymbolTable;

import java.util.HashSet;
import java.util.Set;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {

    //Para identificar ámbitos y detectar dos errores:
    //- Variable no definida
    //- Mismo nombre de una variable en el mismo ámbito

    SymbolTable symbolTable = new SymbolTable();

    @Override
    public Void visit(Variable v, Void param) {
        super.visit(v, param);
        Definition def = symbolTable.find(v.getName());
        if (def == null) {
            ErrorType error = new ErrorType("Variable no definida anteriormente '" + v.getName() + "'", v);
            v.setDefinition(new VarDefinition(v.getName(), error, v.getLine(), v.getColumn()));
        } else {
            v.setDefinition(def);//Enlazo la variable con su definición
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition v, Void param) {
        boolean respuestaInsert = symbolTable.insert(v);
        if (!respuestaInsert) {
            new ErrorType("(Nombre de FUNCIÓN DUPLICADO '" + v.getName() + "' ). En otra Definición hay el mismo nombre en el mismo ámbito", v);
        } else {
            symbolTable.set();
            //Recorrer
            super.visit(v, param);
            //---
            symbolTable.reset();
        }
        return null;
    }

    @Override
    public Void visit(VarDefinition v, Void param) {
        super.visit(v, param);
        boolean respuestaInsert = symbolTable.insert(v);
        if (!respuestaInsert) {
            new ErrorType("(Nombre de VARIABLE DUPLICADA '" + v.getName() + "' ). En otra Definición hay el mismo nombre en el mismo ámbito", v);
        }
        return null;
    }

    @Override
    public Void visit(RecordType v, Void param) {
        Set<String> nombres = new HashSet<>();
        for (RecordField field : v.getLista()) {
            if (!nombres.add(field.getName())) {
                new ErrorType("Campo duplicado '" + field.getName() + "'", new AbstractLocatable(field.getLine(), field.getColumn()));
            }
        }
        super.visit(v, param);
        return null;
    }

}
