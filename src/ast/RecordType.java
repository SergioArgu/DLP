package ast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecordType extends AbstractType{

    private List<RecordField> lista;

    public RecordType(List<RecordField> lista) {
        this.lista = lista;
    }

    public List<RecordField> getLista() {
        return lista;
    }

    @Override
    public Type dot(Locatable locatable, String str) {
        for (RecordField field : lista) {
            if (field.getName().equals(str)) {
                return field.getType();
            }
        }
        return new ErrorType("El campo " + str + " no está definido en el RecordField", locatable);
    }

    @Override
    public Type Comparison(Locatable locatable, Type other) {
        return new ErrorType("RecordType no soporta operaciones de comparación", locatable);
    }

    @Override
    public void mustPromote(Locatable locatable, Type other) {
        new ErrorType("Tipo " + other.getClass().getSimpleName() + " no promociona a RecordType", locatable);
    }

    @Override
    public int numberOfBytes() {
        int bytes = 0;
        for (int i= 0; i<lista.size(); i++){
            bytes+=lista.get(i).getType().numberOfBytes();
        }
        return bytes;
    }

    public RecordField getField(String id){
        for (RecordField field:getLista()){
            if (field.getName().equals(id)){
                return field;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "RecordType[fields:" + lista + "]";
    }

    public <PT, RT> RT accept(Visitor<PT,RT> v, PT param){
        return v.visit(this, param);
    }




}

