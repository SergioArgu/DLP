grammar TSmm;	
@header{
import ast.*;
}
program returns [Programa ast] locals[List<Definition> defs = new ArrayList<>()]:
        (vd=varDefinition {$defs.addAll($vd.ast);} |fd=functionDefinition {$defs.add($fd.ast);} )* (md=mainFunctionDefinition {$defs.add($md.ast);})  EOF {$ast = new Programa($defs);}
       ;

expression returns [Expression ast] locals[List<Expression> arguments = new ArrayList<>()]:
        INT_CONSTANT {$ast = new IntLiteral(LexerHelper.lexemeToInt($INT_CONSTANT.text), $INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine() + 1);}//Enteros
        | REAL_CONSTANT {$ast = new NumberLiteral(LexerHelper.lexemeToReal($REAL_CONSTANT.text), $REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine() + 1);}//Números reales
        | CHAR_CONSTANT {$ast = new CharLiteral(LexerHelper.lexemeToChar($CHAR_CONSTANT.text), $CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine() + 1);}//Caracteres
        | ID {$ast = new Variable($ID.text, $ID.getLine(), $ID.getCharPositionInLine() + 1);}//Variables
        | '(' e1=expression ')' {$ast = $e1.ast;}//Paréntesis
        | e1=expression '[' e2=expression ']' {$ast = new ArrayAcess($e1.ast, $e2.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//ArrayAcess vector[9][(4.3 as int)]
        | e1=expression '.' ID {$ast = new FieldAcess($ID.text, $e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//FieldAcess p.age
        | '(' e1=expression 'as' t=simpleType ')' {$ast = new Cast($t.ast, $e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//Cast (n as char)
        | '-' e1=expression {$ast = new UnaryMinus($e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//UnaryMinus
        | '!' e1=expression {$ast = new UnaryNot($e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//UnaryNot
        | e1=expression OP=('*' | '/' | '%') e2=expression {$ast = new Arithmetic($e1.ast, $e2.ast, $OP.text, $e1.ast.getLine(), $e1.ast.getColumn());}//Arithmetic
        | e1=expression OP=('+' | '-') e2=expression {$ast = new Arithmetic($e1.ast, $e2.ast, $OP.text, $e1.ast.getLine(), $e1.ast.getColumn());}//Arithmetic
        | e1=expression OP=('>' | '>=' | '<' | '<=' | '!=' | '==') e2=expression {$ast = new Comparison($e1.ast, $e2.ast, $OP.text, $e1.ast.getLine(), $e1.ast.getColumn());}//Comparison
        | e1=expression OP=('&&' | '||') e2=expression {$ast = new Logic($e1.ast, $e2.ast, $OP.text, $e1.ast.getLine(), $e1.ast.getColumn());}//Logic
        | ID1=ID '(' (e1=expression {$arguments.add($e1.ast);} (',' e2=expression {$arguments.add($e2.ast);} )*)? ')' {$ast = new Invocation($arguments, new Variable($ID1.text, $ID1.getLine(), $ID1.getCharPositionInLine() + 1), $ID1.getLine(), $ID1.getCharPositionInLine() + 1);}//Invocacion Statement --> log f(i, i); Expression --> f(i, i)   EL LOG SE COGE LUEGO EN EL STATEMENT PERO NO SIEMPRE LLEVA LOG ES UN EJEMPLO
        ;

simpleType returns [Type ast]:
         'int' {$ast = IntType.getInstance();}//Int
        | 'number' {$ast = NumberType.getInstance();}//Number
        | 'char' {$ast = CharType.getInstance();}//Char
        ;

body returns [List<Statement> ast = new ArrayList<Statement>()]:
    s=statement {$ast.add($s.ast);}//Una sola statement
    | '{' (s=statement {$ast.add($s.ast);})* '}'//Varias statements
    ;

statement returns [Statement ast] locals[List<Statement> else_ = new ArrayList<>(), List<Expression> arguments = new ArrayList<>()]:
        'log' e1=expression { $arguments.add($e1.ast); } (',' e2=expression { $arguments.add($e2.ast); })* ';'
        {$ast = new Log($arguments, $e1.ast.getLine(), $e1.ast.getColumn());}//Log
        | 'input' e1=expression { $arguments.add($e1.ast); } (',' e2=expression { $arguments.add($e2.ast); })* ';' {$ast = new Input($arguments, $e1.ast.getLine(), $e1.ast.getColumn());}//Input
        | e1=expression '=' e2=expression ';'{$ast = new Assignment($e1.ast, $e2.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//Asignación
        | 'if' '(' e1=expression ')' b1=body ('else' b2=body {$else_=$b2.ast ;})? {$ast = new If($else_, $b1.ast, $e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//If-Else
        | 'while' '(' e1=expression ')' b1=body {$ast = new While($b1.ast, $e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//While
        | 'return' e1=expression ';' {$ast = new Return($e1.ast, $e1.ast.getLine(), $e1.ast.getColumn());}//Return
        | ID1=ID '(' (e1=expression {$arguments.add($e1.ast);} (',' e2=expression {$arguments.add($e2.ast);} )*)? ')' ';' {$ast = new Invocation($arguments, new Variable($ID1.text, $ID1.getLine(), $ID1.getCharPositionInLine() + 1), $ID1.getLine(), $ID1.getCharPositionInLine() + 1);}//Procedimiento(Invocacion as a statement) Ejemplo: p(); o f(1, 2.2);  Parecido a la invocación como expression pero con un ; al final
        ;

functionType returns [Type ast]:
            s=simpleType {$ast = $s.ast;}//Tipos simples
            | 'void' {$ast = VoidType.getInstance();}//Void
            ;

type returns [Type ast] locals[List<RecordField> fields = new ArrayList<>(), List<String> vars = new ArrayList<>()]:
    s=simpleType {$ast = $s.ast;}//Tipos simples
    | '[' I1=INT_CONSTANT ']' t=type {$ast = new ArrayType($t.ast, LexerHelper.lexemeToInt($I1.text));}//ArrayType (parte derecha a los dos puntos) [10][5]int; Debe ser type por si te ponen otro [5] pero solo puede llevar tipos simples en realidad
    | '[' ({ $vars = new ArrayList<>(); } 'let' ID1=ID { $vars.add($ID1.text); } (',' ID2=ID { $vars.add($ID2.text); })* ':' st=type ';'
    {
        for (String v : $vars) {
            $fields.add(new RecordField(v, $st.ast, $ID1.getLine(), $ID1.getCharPositionInLine() + 1));
        }
    } )* ']' {$ast = new RecordType($fields);}//RecordType ( [recordField*] )(parte derecha a los dos puntos)
    ;

varDefinition returns [List<VarDefinition> ast] locals[List<String> variables = new ArrayList<>()]:
            'let' ID1=ID { $variables.add($ID1.text); } (',' ID2=ID { $variables.add($ID2.text); })* ':' t=type ';'
             {
                    $ast = new ArrayList<>();
                    for (String v : $variables) {
                        $ast.add(new VarDefinition(v, $t.ast, $ID1.getLine(), $ID1.getCharPositionInLine() + 1));
                    }
             }
            ;

functionDefinition returns [FunctionDefinition ast]
                    locals[List<Statement> stmt = new ArrayList<>(), List<VarDefinition> params = new ArrayList<>()]:
                'function' ID1=ID '(' (p1=paramDef {$params.add($p1.ast);} (',' p2=paramDef {$params.add($p2.ast);})*)? ')' ':' ft=functionType '{' (vd=varDefinition {$stmt.addAll($vd.ast);})* (st=statement {$stmt.add($st.ast);})* '}' {$ast = new FunctionDefinition($stmt, new FunctionType($ft.ast, $params), $ID1.text, $ID1.getLine(), $ID1.getCharPositionInLine() + 1);}
                ;

//Parametros de la función
paramDef returns [VarDefinition ast]:
        ID1=ID ':' t=simpleType {$ast = new VarDefinition($ID1.text, $t.ast, $ID1.getLine(), $ID1.getCharPositionInLine() + 1);}
        ;
//Siempre debe ir al final si existe
mainFunctionDefinition returns[FunctionDefinition ast]
                locals[List<Statement> stmt = new ArrayList<>(), List<VarDefinition> params = new ArrayList<>()]:
                'function' m='main' '(' ')' ':' 'void' '{' (vd=varDefinition {$stmt.addAll($vd.ast);})* (st=statement {$stmt.add($st.ast);})* '}' {$ast = new FunctionDefinition($stmt, new FunctionType(VoidType.getInstance(), $params), "main",$m.getLine(), $m.getCharPositionInLine() + 1);}// $vd.ast.get(0).getLine(), $vd.ast.get(0).getColumn());}
                ;

//---------------
fragment
COMMENTS: '//' ~[\n]*//Comentario de una línea
        | '/*'.*?'*/'//Comentario multilínea
        ;

WHITES: ([ \n\t\r]|COMMENTS) + -> skip
    ;

INT_CONSTANT: [1-9] [0-9]*
            | '0'
            ;

fragment//Base del real
MANTISSA: INT_CONSTANT
        | INT_CONSTANT* '.' INT_CONSTANT*
        ;

fragment
EXPONENTE: [eE][-+]?[0-9]+
        ;

REAL_CONSTANT: MANTISSA EXPONENTE*
            ;

CHAR_CONSTANT: '\''.'\''//Cualquier caracter
            | '\'' ('\\n'|'\\t') '\''//Los \n y \t
            | '\'''\\'INT_CONSTANT'\''//Los /126 (ASCII)
            ;

ID: [a-zA-Z_][a-zA-Z_0-9]*//Ejemplos: var1  _var_1  VAR_1_AB_2
    ;