package backend.reglasGram;
import backend.objetos.*;
import java_cup.runtime.*;
import static backend.reglasGram.symForm.*;
import java.util.ArrayList;
import java.util.List;

%%
/*configuracion*/
%class LexerForm
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

lineTerminator = \r|\n|\r\n
whiteSpace     = {lineTerminator} | [ \t\f]


%eofval{
  return new java_cup.runtime.Symbol(symForm.EOF);
%eofval}

%%

/*reglas lexicas*/

<YYINITIAL> "db.formularios"				{return new Symbol(DB_FORMULARIOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "FORMULARIO"					{return new Symbol(FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "ID_FORMULARIO"					{return new Symbol(ID_FORM,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "NOMBRE"						{return new Symbol(NOMBRE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "TITULO"						{return new Symbol(TITULO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "TEMA"							{return new Symbol(TEMA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "USUARIO_CREACION"				{return new Symbol(USER_CREACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "FECHA_CREACION"				{return new Symbol(FECHA_CREACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "ESTRUCTURA"					{return new Symbol(ESTRUCTURA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "DATOS_RECOPILADOS"				{return new Symbol(DATOS_RECOPILADOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "ID"						{return new Symbol(ID_COMP,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "NOMBRE_CAMPO"				{return new Symbol(NOMBRE_CAMPO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "CLASE"						{return new Symbol(CLASE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "INDICE"					{return new Symbol(INDICE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "TEXTO_VISIBLE"				{return new Symbol(TEXTO_VISIBLE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "ALINEACION"				{return new Symbol(ALINEACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "OPCIONES"					{return new Symbol(OPCIONES,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "REQUERIDO"					{return new Symbol(REQUERIDO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "FILAS"						{return new Symbol(FILAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "COLUMNAS"					{return new Symbol(COLUMNAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "URL"						{return new Symbol(URL,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "REGISTRO"					{return new Symbol(REGISTRO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }

<YYINITIAL> {http}						{}
<YYINITIAL> {https}						{}
<YYINITIAL> {path_relativo}				{}




<YYINITIAL>{
	/*cadenas*/
	{allStrings}						{return new Symbol(STRING,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/*simbolos*/
	"\""								{return new Symbol(COMILLA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"{"									{return new Symbol(LLAI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"}"									{return new Symbol(LLAD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	":"									{return new Symbol(DOS_PUNTOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"["									{return new Symbol(CORI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"]"									{return new Symbol(CORD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	","									{return new Symbol(COMA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"("									{return new Symbol(PAI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	")"									{return new Symbol(PAD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/* espacios en blanco */
	{whiteSpace}                   		{ /* ignorar */ }
}

[^]                              	{return new Symbol(ERROR,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}