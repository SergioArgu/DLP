package errorhandler;

import ast.ErrorType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {//Singleton

    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler();
    private List<ErrorType> listaErrores;

    private ErrorHandler(){
        listaErrores = new ArrayList<>(); // Initialize the list
    }

    public boolean anyError(){
        if (!listaErrores.isEmpty()){
            return true;
        }
        return false;
    }

    public void addError(ErrorType error){
        listaErrores.add(error);
    }

    public void showErrors(PrintStream out){
        if (listaErrores.isEmpty()) {
            out.println("No errors.");
            return;
        }

        for (ErrorType error:listaErrores) {
            out.println(error);
        }
    }

    public static ErrorHandler getInstance() {
        return ERROR_HANDLER;
    }


}
