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
string = ({letra}|{number}|[_]|[-]|[$])+
stringSpace =({letra}|{number})+({string}|{whiteSpace})*
cont_consulta = ( "\"SELECT" | "\"" + {whiteSpace} + "SELECT" ) {consulta} ( "]\"" | "]" + {whiteSpace} + "\"" )
consulta       = ( [^\]] |"]"+ [^ \f\t\r\n\]\"] | "]"+ ({whiteSpace}) [^\]\"] )*
consulta_N = ("CONSULTA")[-]({entero})
CONT_ID = ([$]|[_]|[-])({letra}|{number}|[$]|[-]|[_])+
cont_opciones = (({stringSpace}|{string})("|")({whiteSpace})?)+({stringSpace}|{string})

cont_url = (({stringSpace}|".."|[:])?[/\\])*({stringSpace}("."){letra})

ini_solicitudes = {ini_solicitud}[eE][sS]
ini_solicitud = [iI][nN][iI][_][sS][oO][lL][iI][cC][iI][tT][uU][dD]
fin_solicitudes = {fin_solicitud}[eE][sS]
fin_solicitud = [fF][iI][nN][_][sS][oO][lL][iI][cC][iI][tT][uU][dD]
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
  return new java_cup.runtime.Symbol(symIndigo.EOF);
%eofval}

%%
/*reglas lexicas*/

/*palabras clave*/
<YYINITIAL> "CREAR_USUARIO"				{return new Symbol(CREAR_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1 )); }
<YYINITIAL> "MODIFICAR_USUARIO"			{return new Symbol(MODIFICAR_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "ELIMINAR_USUARIO"			{return new Symbol(ELIMINAR_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "LOGIN_USUARIO"				{return new Symbol(LOGIN_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "NUEVO_FORMULARIO"			{return new Symbol(NUEVO_FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "ELIMINAR_FORMULARIO"		{return new Symbol(ELIMINAR_FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "MODIFICAR_FORMULARIO"		{return new Symbol(MODIFICAR_FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "AGREGAR_COMPONENTE"		{return new Symbol(AGREGAR_COMPONENTE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "ELIMINAR_COMPONENTE"		{return new Symbol(ELIMINAR_COMPONENTE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "MODIFICAR_COMPONENTE"		{return new Symbol(MODIFICAR_COMPONENTE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "CONSULTAR_DATOS"			{return new Symbol(CONSULTAR_DATOS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "CREDENCIALES_USUARIO"		{return new Symbol(CREDENCIALES_USUARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "PARAMETROS_FORMULARIO"		{return new Symbol(PARAMETROS_FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "PARAMETROS_COMPONENTE"		{return new Symbol(PARAMETROS_COMPONENTE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "CONSULTAS"					{return new Symbol(CONSULTAS,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {consulta_N}				{return new Symbol(CONSULTA_N,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "ID"						{return new Symbol(ID,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "TITULO"					{return new Symbol(TITULO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "TEXTO_VISIBLE"				{return new Symbol(TEXTO_VISIBLE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "OPCIONES"					{return new Symbol(OPCIONES,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "FORMULARIO"				{return new Symbol(FORMULARIO,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> "URL"						{return new Symbol(URL,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

<YYINITIAL> {ini_solicitud}				{return new Symbol(INI_SOLICITUD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {ini_solicitudes}			{return new Symbol(INI_SOLICITUDES,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {fin_solicitud}				{return new Symbol(FIN_SOLICITUD,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
<YYINITIAL> {fin_solicitudes}			{return new Symbol(FIN_SOLICITUDES,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}



<YYINITIAL>{
	/*cadenas*/
	{CONT_ID}							{return new Symbol(CONT_ID,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	{string}							{return new Symbol(STRING,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	{stringSpace}						{return new Symbol(STRING_SPACE,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}	
	{cont_consulta}						{return new Symbol(CONT_CONSULTA,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	{cont_opciones}						{return new Symbol(CONT_OPCIONES,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}
	{cont_url}							{return new Symbol(CONT_URL,yyline+1,yycolumn+1, new Token(yytext(),yyline+1,yycolumn+1));}

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