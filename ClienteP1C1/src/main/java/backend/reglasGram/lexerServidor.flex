/*codigo de usuario*/
package backend.reglasGram;
import backend.objetos.*;
import java_cup.runtime.*;
import static backend.reglasGram.symServidor.*;
import java.util.ArrayList;
import java.util.List;

%%
/*configuracion*/
%class LexerServidor
%public
%unicode
%line
%column
%cup
decimal=[1-9][0-9]*[.]{entero}| [0][.]{entero}
entero=[0-9]+
number = ({entero}|{decimal})
letra = [a-zA-Z]
string = ({letra}|{number}|[_]|[-]|[$]|[\.])+
stringSpace =({letra}|{number})+({string}|{whiteSpace})*
CONT_ID = ([$]|[_]|[-])({letra}|{number}|[$]|[-]|[_])+
cont_opciones = (({stringSpace}|{string})("|")({whiteSpace})?)+({stringSpace}|{string})

cont_url =({path_relativo}|{http}|{https})?([\\\/])?(({stringSpace})[\\\/])+({stringSpace})?
http = ("http:/")
https = ("https:/")
path_relativo = (({letra})(":"))

allStrings = ({string}|{stringSpace}|{CONT_ID}|{cont_opciones}|{cont_url})

ini_respuestas = {ini_respuesta}[sS]
ini_respuesta = [iI][nN][iI][_][rR][eE][sS][pP][uU][eE][sS][tT][aA]
fin_respuestas = {fin_respuesta}[sS]
fin_respuesta = [fF][iI][nN][_][rR][eE][sS][pP][uU][eE][sS][tT][aA]
lineTerminator = \r|\n|\r\n
whiteSpace     = {lineTerminator} | [ \t\f]

%{
     List<LosErrores> errores = new ArrayList<LosErrores>();

     private void estructuraError(String charError, int linea, int columna){
     	String elError = "Error: No se reconoce el caracter: " + charError + " -> {Linea: "+ linea +", Columna: "+ columna +" }" ;
     	errores.add(new LosErrores(elError));
     }
     public List getErroresLexicos(){
     	return errores;
     }
%}

%eofval{
  return new java_cup.runtime.Symbol(symServidor.EOF);
%eofval}

%%
/*reglas lexicas*/

/*palabras clave*/
<YYINITIAL> "USUARIO"				{return new Symbol(USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "RESPUESTA_SERVIDOR"	{return new Symbol(RESPUESTA_SERVIDOR,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "RESPUESTA"				{return new Symbol(RESPUESTA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "LOGIN_USUARIO"			{return new Symbol(LOGIN_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "BLOQUE"				{return new Symbol(BLOQUE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

<YYINITIAL> {ini_respuesta}				{return new Symbol(INI_RESPUESTA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {ini_respuestas}			{return new Symbol(INI_RESPUESTAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {fin_respuesta}				{return new Symbol(FIN_RESPUESTA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {fin_respuestas}			{return new Symbol(FIN_RESPUESTAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

<YYINITIAL> {http}						{}
<YYINITIAL> {https}						{}
<YYINITIAL> {path_relativo}				{}

<YYINITIAL>{
	/*cadenas*/
	{allStrings}						{return new Symbol(STRING,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/*simbolos*/
	"\""								{return new Symbol(COMILLAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"{"									{return new Symbol(LLAI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"}"									{return new Symbol(LLAD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	":"									{return new Symbol(DOS_PUNTOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"["									{return new Symbol(CORI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"]"									{return new Symbol(CORD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	","									{return new Symbol(COMA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
 	"<"									{return new Symbol(MENQ,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	">"									{return new Symbol(MAQ,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"!"									{return new Symbol(ADMIRACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/* espacios en blanco */
	{whiteSpace}                   		{ /* ignorar */ }
}

/* error */
    [^]                              	{estructuraError(yytext(),yyline+1,yycolumn+1);}