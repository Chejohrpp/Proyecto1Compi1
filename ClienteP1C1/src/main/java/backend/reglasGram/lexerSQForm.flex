/*codigo de usuario*/
package com.proyecto1.reglas.codigo;
import java_cup.runtime.*;
import static com.reglas.codigo.symSQForm.*;

%%
/*configuracion*/
%class LexerSqForm
%public
%unicode
%line
%column
%cup
decimal=[1-9][0-9]*[.]{number}| [0][.]{number}
number=[0-9]+
id_form = ([$]|[_]|[-])({letra}|{number}|[_]|[-])*
nombre = ({letra}|{number}|[_]|[-])+
string = ({letra}|{number})+({letra}|{number}|{whiteSpace})*
lineTerminator = \r|\n|\r\n
whiteSpace     = {lineTerminator} | [ \t\f]


%%
/*reglas*/	

/*palabras clave*/
<YYINITIAL> "SELECT"
<YYINITIAL> "TO"
<YYINITIAL> "FORM"
<YYINITIAL> "WHERE"
<YYINITIAL> "NOT"
<YYINITIAL> "AND"
<YYINITIAL> "OR"



<YYINITIAL>{
	
	/*cadenas*/
	{id_form}
	{nombre}
	{string}
	({number} | {decimal})

	/*SIMBOLOS*/
	"-"
	">"
	"<"
	"="
	"["
	"]"
	","
	"\""

}

