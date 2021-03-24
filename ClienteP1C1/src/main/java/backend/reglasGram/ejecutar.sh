echo "STARTING JFLEX COMPILING"
java -jar jflex-full-1.8.2.jar lexerIndigo.flex
echo "STARTING CUP COMPILING"
java -jar java-cup-11b.jar -parser ParserIndigo -symbols symIndigo parserIndigo.cup
echo "PRESIONE ENTER PARA SALIR"
 read -p "$*"