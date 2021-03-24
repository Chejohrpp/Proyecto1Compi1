echo "STARTING JFLEX COMPILING"
java -jar jflex-full-1.8.2.jar lexerUser.flex
echo "STARTING CUP COMPILING"
java -jar java-cup-11b.jar -parser ParserUser -symbols symUser parserUser.cup
echo "PRESIONE ENTER PARA SALIR"
 read -p "$*"