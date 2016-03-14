Trabalho Prático de LFA - Algoritmo CYK
Guilherme Alves, Henrique Jensen e Lucas Pedroso

O objetivo do trabalho prático foi desenvolver um aplicativo que, usando o algoritmo CYK, 
verifique se uma GLC (Gramatica Livre de Contexto) na FNC (Forma Normal de Chomsky é capaz 
de derivar uma palavra p. 

O aplicativo foi desenvolvido Java. Recebe como entrada um arquivo texto com a GLC G na FNC 
e uma palavra p que tentará ser derivada a partir de S. Ele proverá como saída um arquivo 
texto com a matriz triangular produzida pelo algoritmo CYK.

Para compilar e executar o algoritmo, respectivamente, primeiramente deve-se entrar na pasta
do projeto TrabalhoLFA/src e executar os seguintes comandos:

	javac Main.java
	java Main arquivoDeEntrada.txt palavra arquivoDeSaida.txt

Exemplo Prático:

Como teste, foi disponibilizado o arquivo glc.txt (dentro da pasta src) para ser utilizado 
como o arquivo de entrada. Para executá-lo, os seguintes parâmetros podem ser passados para o teste:

	java Main glc.txt aaabbb saida.txt

No arquivo saida.txt conterá o a matriz triangular produzia pelo algoritmo CYK.

Na pasta input, é disponibilizado mais 7 arquivos .txt com a GLC na FNC.
