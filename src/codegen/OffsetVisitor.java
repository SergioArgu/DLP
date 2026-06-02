package codegen;

import ast.*;
import semantic.AbstractVisitor;

public class OffsetVisitor extends AbstractVisitor<Boolean, Void> {

    //char --> 1 byte
    //int --> 2 bytes
    //number --> 4 bytes

    private int globalBytesSum = 0;
    private int localBytesSum = 0;
    private int paramBytesSum = 0;

    @Override
    public Void visit(VarDefinition v, Boolean isLocal) {
        super.visit(v, isLocal);
        if (v.getScope() == 0){//Variables globales
            v.setOffset(globalBytesSum);
            globalBytesSum+=v.getType().numberOfBytes();
        } else{
            if (isLocal){//Variables locales
                localBytesSum+=v.getType().numberOfBytes();
                v.setOffset(-localBytesSum);
            } else{
                v.setOffset(4+paramBytesSum);
                paramBytesSum+=v.getType().numberOfBytes();
            }
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition v, Boolean isLocal) {
        localBytesSum = 0;
        paramBytesSum = 0;
        //Recorrer
        //PARÁMETROS (desde FunctionType)
        FunctionType ft = (FunctionType) v.getType();
        if (ft.getParams() != null) {
            for (int i = ft.getParams().size() - 1; i >= 0; i--) {//Se recorre en reverse
                VarDefinition paramDef = ft.getParams().get(i);
                paramDef.accept(this, false);//false para params
            }
        }
        //STATEMENTS (VarDefinition y statements)
        if (v.getStatements() != null) {
            for (Statement st : v.getStatements()) {
                st.accept(this, true);//true para variables locales
            }
        }
        v.setLocalBytes(localBytesSum);//Guardo cuanto ocupan las variables locales para usarlo en generación de código
        return null;
    }

    @Override
    public Void visit(RecordType v, Boolean param) {
        int bytesFieldSum = 0;
        for (RecordField field:v.getLista()){
            field.setOffset(bytesFieldSum);
            bytesFieldSum+=field.getType().numberOfBytes();
            field.getType().accept(this, null);
        }
        return null;
    }


}
