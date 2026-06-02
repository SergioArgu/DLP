package codegen;

import ast.*;

import java.util.List;

public class ExecuteCGVisitor extends AbstractCGVisitor<Object, Void>{
    //Statements, program, definitions (VarDefinition y FunctionDefinition)

    private AddressCGVisitor address;
    private ValueCGVisitor value;

    public ExecuteCGVisitor(CodeGenerator cg){
        super(cg);
        address = new AddressCGVisitor(cg);
        value = new ValueCGVisitor(cg);
        value.setAddress(address);
        address.setValue(value);

    }

    /* Plantilla de generación de código
    execute[[Programa: programa --> definition*]]() =
        for (Definition def:definition*){
            if (def instanceof VarDefinition){
                execute[[def]]()
            }
        }
        <call main>
        <halt>
        for (Definition def:definition*){
            if (def instanceof FunctionDefinition){
                execute[[def]]()
            }
        }
     */
    @Override
    public Void visit(Programa v, Object param) {
        for (Definition def:v.getDefinitions()){
            if (def instanceof VarDefinition){
                def.accept(this, param);
            }
        }
        cg().mainInvocation();
        for (Definition def:v.getDefinitions()){
            if (def instanceof FunctionDefinition){
                def.accept(this, param);
            }
        }
        return null;
    }

    /* Plantilla de generación de código
    execute[[VarDefinition: definition --> ID type]]() =

     */
    @Override
    public Void visit(VarDefinition v, Object param) {
        if (v.getScope() == 0) {
            cg().comentarioVariable(v);
        }
        return null;
    }

    /* Plantilla de generación de código
    execute[[FuncDefinition: definition --> ID type statement*]]() =
        id<:>
        for (int i = 0; i < type.returnType.getParams().size(); i++){
            execute[[type.returnType.getParams().get(i)]]()
        }
        <enter> definition.localBytes
        for (int i = 0; i < statement*.size(); i++){
            execute[[statement*.get(i)]](definition)
        }
        if (type.returnType == VoidType.getInstance()){
            <ret> 0, definition.localBytes, type.returnType.getParams().map(p->p.type.numberOfBytes().sum())
        }
     */
    @Override
    public Void visit(FunctionDefinition v, Object param) {
        //Linea
        cg().line(v.getLine());

        //Label (Nombre función)
        cg().label(v.getName());

        //Visito los parámetros de la función
        cg().comentarioTituloParametros();
        List<VarDefinition> paramsFunction = ( (FunctionType)v.getType() ).getParams();
        for (VarDefinition p:paramsFunction){
            p.accept(this, param);
            cg().comentarioParam(p);
        }

        //Comentarios variables locales y reservar espacio
        cg().comentarioTituloVariablesLocales();
        for (Statement stmt:v.getStatements()){
            if (stmt instanceof VarDefinition){
                cg().comentarioVariable( (VarDefinition)stmt );
            }
        }
        cg().enter(v.getLocalBytes());//Reservar espacio para las variables locales

        //Visito el cuerpo de la función
        for (Statement stmt:v.getStatements()){
            stmt.accept(this, v);
        }
        if (((FunctionType) v.getType()).getType() == VoidType.getInstance()){
            FunctionType ft = ((FunctionType) v.getType());
            int bytesArgus = 0;
            for (int i = 0; i < ft.getParams().size(); i++){
                bytesArgus += ft.getParams().get(i).getType().numberOfBytes();
            }
            cg().ret(0, v.getLocalBytes(), bytesArgus);
        }
        return null;
    }

    /* Plantilla de generación de código
    execute[[Invocation: statement --> expression₁ expresion₂*]]
        value[[(Expression) statement]]()
        if(expression₁.type.returnType != VoidType.getInstance()){
            <pop> expression₁.type.returnType.suffix()
        }
     */
    @Override
    public Void visit(Invocation v, Object param) {
        //Linea y titulo
        cg().line(v.getLine());

        v.accept(value, param);
        if ( ((FunctionType)v.getName().getType()).getType() != VoidType.getInstance() ){
            cg().pop( ((FunctionType)v.getName().getType()).getType() ) ;
        }
        return null;
    }

    /* Plantilla de generación de código
    execute[[Return: statement --> expression]](FunctionDefinition fd)
        value[[expression]]()
        cg.convertTo(expression.type, fd.type.returnType);
        <ret> fd.type.returnType.numberOfBytes(), fd.localBytes, fd.type.returnType.getParams().map(p->p.type.numberOfBytes().sum())
     */
    @Override
    public Void visit(Return v, Object param) {
        //Linea y titulo
        cg().line(v.getLine());
        cg().title(v.toString());


        FunctionDefinition fd = (FunctionDefinition) param;
        FunctionType ft = (FunctionType) fd.getType();

        v.getExpresion().accept(value, param);
        cg().convertTo(v.getExpresion().getType(), ft.getType());

        //ret a,b,c   (a = bytes devueltos, b = bytes variables locales, c = bytes argumentos)
        int bytesArgus = 0;
        for (int i = 0; i < ft.getParams().size(); i++){
            bytesArgus += ft.getParams().get(i).getType().numberOfBytes();
        }
        cg().ret(ft.getType().numberOfBytes(), fd.getLocalBytes(), bytesArgus);
        return null;
    }

    /* Plantilla de generación de código
    execute[[While: statement₁ --> expression statement₂*]]() =
        String cond = cg.getLabel();
        String end = cg.getLabel();
        cond <:>
        value[[expression]]()
        cg.convertTo(expression.type, IntType.getInstance())
        <jz> end
        for (Statement st:statement₂*){
            execute[[st]]()
        }
        <jmp> cond
        end <:>
     */
    @Override
    public Void visit(While v, Object param) {
        //Linea y titulo
        cg().line(v.getLine());
        cg().title(v.toString());

        cg().line(v.getLine());
        //Generar labels
        String cond = cg().getLabel();
        String end = cg().getLabel();

        //Pongo el label
        cg().label(cond);

        //Pongo en la pila el valor de la condición
        v.getExpresion().accept(value, param);

        //Convierto el tipo
        cg().convertTo(v.getExpresion().getType(), IntType.getInstance());

        //Si la condicion es 0 (Falso) salto a end
        cg().jz(end);

        //Evaluo el body
        cg().title(v.toString() + " body");
        for (Statement st:v.getBody()){
            st.accept(this, param);
        }

        //Salto a condición otra vez. Este salto es incondicional (Se hace siempre)
        cg().jmp(cond);

        //Pongo el label
        cg().label(end);
        return null;
    }

    /* Plantilla de generación de código
    execute[[If: statement₁ --> expression statement₂* statement₃*]]() =
        String end = cg.getLabel();
        String else = cg.getLabel();
        value[[expression]]()
        cg.convertTo(expression.type, IntType.getInstance());
        <jz> else
        for(Statement st:statement₂*){
            execute[[st]]()
        }
        <jmp> end
        else <:>
        for(Statement st:statement₃*){
            execute[[st]]()
        }
        end <:>
     */
    @Override
    public Void visit(If v, Object param) {
        //Linea y titulo
        cg().line(v.getLine());
        cg().title(v.toString());

        cg().line(v.getLine());
        //Generar labels
        String else_ = cg().getLabel();
        String end = cg().getLabel();

        //Evalúo la condición
        v.getExpresion().accept(value, param);

        //Convierto el tipo
        cg().convertTo(v.getExpresion().getType(), IntType.getInstance());

        //Si la condicion es 0 (Falso) salto a else_
        cg().jz(else_);

        //Evaluo el body
        cg().title("if body");
        for (Statement st:v.getThen()){
            st.accept(this, param);
        }

        //Salto a end
        cg().jmp(end);

        //Pongo el label
        cg().label(else_);

        //Evaluo el else
        cg().title("else body");
        for (Statement st:v.getElse_()){
            st.accept(this, param);
        }

        //Pongo el label
        cg().label(end);
        return null;
    }

    /* Plantilla de generación de código
    execute[[Assignment: statement --> expression₁ expression₂]]() =
        address[[expression₁]]()
        value[[expression₂]]()
        cg.convertTo(expression₂.type, expression₁.type);
        <store> expression₁.type.suffix()
     */
    @Override
    public Void visit(Assignment v, Object param) {
        //Linea y titulo
        cg().line(v.getLine());
        cg().title(v.toString());

        v.getExpressionLeft().accept(address, param);
        v.getExpressionRight().accept(value, param);
        cg().convertTo(v.getExpressionRight().getType(), v.getExpressionLeft().getType());
        cg().store(v.getExpressionLeft().getType());
        return null;
    }

    /* Plantilla de generación de código
    execute[[Input: statement --> expression*]]() =
        for(int i = 0; i < expression*.size(); i++){
            value[[expression*.get(i)]]()
            <in> expression*.get(i).type.suffix()
            <store> expression*.get(i).type.suffix()
        }
     */
    @Override
    public Void visit(Input v, Object param) {
        for (Expression exp:v.getArguments()){
            //Linea y titulo
            cg().line(v.getLine());
            cg().title(v.toString());
            exp.accept(address, param);
            cg().in(exp.getType());
            cg().store(exp.getType());
        }
        return null;
    }

    /* Plantilla de generación de código
    execute[[Log: statement --> expression*]]() =
        for (int i = 0; i < expression*.size(); i++){
            value[[expression*.get(i)]]()
            <out> expression*.get(i).type.suffix()
        }
     */
    @Override
    public Void visit(Log v, Object param) {
        for (Expression exp:v.getArguments()){
            //Linea y titulo
            cg().line(v.getLine());
            cg().title(v.toString());
            exp.accept(value, param);
            cg().out(exp.getType());
        }
        return null;
    }

}
