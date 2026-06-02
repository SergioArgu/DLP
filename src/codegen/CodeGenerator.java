package codegen;

import ast.ArrayType;
import ast.Statement;
import ast.Type;
import ast.VarDefinition;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    private PrintWriter out;
    private int contador = 0;

    public CodeGenerator(String outputFileName, String sourceFileName){
        try {
            this.out = new PrintWriter(outputFileName);
        } catch (IOException e) {
            System.err.println("Error oppening the file " + outputFileName + ".");
            System.exit(-1);
        }
        this.source(sourceFileName);//#source "input.txt"
    }

    //Instrucciones

    public void convertTo(Type from, Type to){
        if (from.suffix() == to.suffix()){
            return;
        }

        char f = from.suffix();
        char t = to.suffix();

        if (f == 'b' && t == 'i') {
            out.println("\tb2i");
        }
        else if (f == 'i' && t == 'f') {
            out.println("\ti2f");
        }
        else if (f == 'f' && t == 'i') {
            out.println("\tf2i");
        }
        else if (f == 'i' && t == 'b') {
            out.println("\ti2b");
        }
        else if (f == 'b' && t == 'f') {
            out.println("\tb2i");
            out.println("\ti2f");
        }
        else if (f == 'f' && t == 'b') {
            out.println("\tf2i");
            out.println("\ti2b");
        }
        else {
            throw new RuntimeException("Unsupported conversion from " + f + " to " + t);
        }
        out.flush();
    }

    public void pusha(int offset){
        out.println("\tpusha\t" + offset);
        out.flush();
    }

    public void pushb(int value) {
        out.println("\tpushb\t" + value);
        out.flush();
    }

    public void pushi(int value) {
        out.println("\tpushi\t" + value);
        out.flush();
    }

    public void pushf(double value) {
        out.println("\tpushf\t" + value);
        out.flush();
    }

    public void pushbp(){
        out.println("\tpush\tbp");
        out.flush();
    }

    public void jmp(String label){
        out.println("\tjmp\t" + label);
        out.flush();
    }

    public void jz(String label){
        out.println("\tjz\t" + label);
        out.flush();
    }

    public void jnz(String label){
        out.println("\tjnz\t" + label);
        out.flush();
    }

    public void store(Type type){
        out.println("\tstore" + type.suffix());
        out.flush();
    }

    public void source(String filename){
        out.println("\n#source\t\"" + filename + "\"\n");
        out.flush();
    }

    public void addi(){
        out.println("\taddi");
        out.flush();
    }

    public void load(Type type){
        out.println("\tload" + type.suffix());
        out.flush();
    }

    public void mainInvocation() {
        out.println("\n' Invocation to the main function");
        out.println("call main");
        out.println("halt\n");
        out.flush();
    }

    public void label(String id){
        out.println(" " + id + ":");
        out.flush();
    }

    //Generar labels únicos
    public String getLabel(){
        String label = "label"+contador;
        contador++;
        return label;
    }

    public void enter(int bytes) {
        out.println("\tenter\t" + bytes);
        out.flush();
    }

    public void ret(int retBytes, int localBytes, int paramBytes){
        out.println("\tret\t" + retBytes + ", " + localBytes + ", " + paramBytes);
        out.flush();
    }

    public void line(int line){
        out.println("\n#line\t" + line);
        out.flush();
    }

    public void out(Type type) {
        switch (type.suffix()) {
            case 'i':
                out.println("\touti");
                break;
            case 'f':
                out.println("\toutf");
                break;
            case 'b':
                out.println("\toutb");
                break;
            default:
                throw new RuntimeException("Tipo no soportado en log");
        }
        out.flush();
    }

    public void in(Type type) {

        switch (type.suffix()) {
            case 'i':
                out.println("\tini");
                break;
            case 'f':
                out.println("\tinf");
                break;
            case 'b':
                out.println("\tinb");
                break;
            default:
                throw new RuntimeException("Tipo no soportado en input");
        }
        out.flush();
    }

    public void pop(Type type) {
        switch (type.suffix()) {
            case 'i':
                out.println("\tpopi");
                break;
            case 'f':
                out.println("\tpopf");
                break;
            case 'b':
                out.println("\tpopb");
                break;
            default:
                throw new RuntimeException("Tipo no soportado en pop");
        }
        out.flush();
    }

    public void not() {
        out.println("\tnot");
        out.flush();
    }

    public void call(String id){
        out.println("\tcall\t" + id);
        out.flush();
    }

    public void arithmetic(String op, Type type) {
        char s = type.suffix();
        switch (op) {
            case "+":
                out.println("\tadd" + s);
                break;
            case "-":
                out.println("\tsub" + s);
                break;
            case "*":
                out.println("\tmul" + s);
                break;
            case "/":
                out.println("\tdiv" + s);
                break;
            case "%":
                out.println("\tmod" + s);
                break;
            default:
                throw new RuntimeException("Operador no válido: " + op);
        }
        out.flush();
    }

    public void comparison(String op, Type type) {
        char s = type.suffix();
        switch (op) {
            case "<":
                out.println("\tlt" + s);
                break;
            case ">":
                out.println("\tgt" + s);
                break;
            case "==":
                out.println("\teq" + s);
                break;
            case "!=":
                out.println("\tne" + s);
                break;
            case "<=":
                out.println("\tle" + s);
                break;
            case ">=":
                out.println("\tge" + s);
                break;
            default:
                throw new RuntimeException("Operador de comparación no válido: " + op);
        }
        out.flush();
    }


    public void logic(String op) {
        switch (op) {
            case "&&":
                out.println("\tand");
                break;
            case "||":
                out.println("\tor");
                break;
            default:
                throw new RuntimeException("Operador lógico no válido: " + op);
        }
        out.flush();
    }

    //Comentarios

    public void comentarioVariable(VarDefinition v) {
        out.println("\t' * " + v.getType().toString() + " " + v.getName() +
                    " (offset " + v.getOffset() + ")");
        out.flush();
    }

    public void comentarioTituloParametros() {
        out.println("\t' * Parameters");
        out.flush();
    }

    public void comentarioTituloVariablesLocales() {
        out.println("\t' * Local variables");
        out.flush();
    }

    public void comentarioParam(VarDefinition v) {
        out.println("\t' * " + v.getType().getClass().getSimpleName()
                + " " + v.getName()
                + " (offset " + v.getOffset() + ")");
        out.flush();
    }

    public void title(String string) {
        out.println("\t' * " + string);
        out.flush();
    }



}
