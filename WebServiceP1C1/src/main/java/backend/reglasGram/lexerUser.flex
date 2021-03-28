package backend.reglasGram;
import backend.objetos.*;
import java_cup.runtime.*;
import static backend.reglasGram.symUser.*;
import java.util.ArrayList;
import java.util.List;

%%
/*configuracion*/
%class LexerUser
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
lineTerminator = \r|\n|\r\n
whiteSpace     = {lineTerminator} | [ \t\f]



%eofval{
  return new java_cup.runtime.Symbol(symUser.EOF);
%eofval}

%%

/*reglas lexicas*/

<YYINITIAL> "db.users"				{return new Symbol(DB_USERS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "USUARIO"				{return new Symbol(USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "USER"					{return new Symbol(USER,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "PASS"					{return new Symbol(PASS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "FECHA_CREACION"		{return new Symbol(FECHA_CREACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "FECHA_MODIFICACION"	{return new Symbol(FECHA_MODIFICACION,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }

<YYINITIAL>{
	/*cadenas*/
	{string}							{return new Symbol(STRING,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/*simbolos*/
	"\""								{return new Symbol(COMILLA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"{"									{return new Symbol(LLAI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"}"									{return new Symbol(LLAD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	":"									{return new Symbol(DOS_PUNTOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"["									{return new Symbol(CORI,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	"]"									{return new Symbol(CORD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	","									{return new Symbol(COMA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

	/* espacios en blanco */
	{whiteSpace}                   		{ /* ignorar */ }
}

/* error */
    [^]                              	{return new Symbol(ERROR,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}