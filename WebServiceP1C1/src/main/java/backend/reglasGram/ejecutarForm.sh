echo "STARTING JFLEX COMPILING"
java -jar jflex-full-1.8.2.jar lexerForm.flex
echo "STARTING CUP COMPILING"
java -jar java-cup-11b.jar -parser ParserForm -symbols symForm parserForm.cup
echo "PRESIONE ENTER PARA SALIR"
 read -p "$*"