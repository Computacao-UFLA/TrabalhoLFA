// Trabalho Prático de LFA
// Algoritmo CYK

// Guilherme Alves
// Henrique Jensen
// Lucas Pedroso

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
		
	private static String arqentrada;
	private static String palavra;
	private static String arqsaida;
	private static Map<String, List<String>> regrasGLC;
	private static String mat [][];
	
	// Metodo principal
	
	public static void main (String[] args) throws FileNotFoundException {
		
		// Caso nao passe os 3 argumentos		
		if (args.length == 3) {
	
			arqentrada = args[0]; // Arquivo de Entrada
			palavra = args[1]; // Palavra
			arqsaida = args[2]; // Arquivo de Saida
			regrasGLC = new HashMap<String, List<String>>(); // HashMap de List de String
			
			leitura ();    			
			
			/*
			
			// Imprimi o HashMap de List 
			
	        for (Map.Entry<String, List<String>> entry : regrasGLC.entrySet()) {
	            String key = entry.getKey();
	            List<String> values = entry.getValue();
	            System.out.print(key + " = ");
	            System.out.println(values);
	        }
	        	        
	        System.out.println();
	        
	        */
			
	        cyk ();
	        padronizar ();
	        //imprimir ();
	        writeFile ();
        
		}
		
		else {
			System.out.println("Argumentos Invalidos");
		}
	
	}
	
	
	// Metodo para a leitura do arquivo
	
	public static void leitura () throws FileNotFoundException {
	
		String esquerda = null;
		String direita;
		String simbolo;
				
		// O useDelimiter(regex) utiliza a expressao regular "[^aA-zZ]+" que desconsidera
		// Todos os caracteres que nao sao letras do alfabeto na hora da leitura
		
		Scanner leitura = new Scanner(new FileReader(arqentrada)).useDelimiter("[^aA-zZ.]+");
		
		while (leitura.hasNext()) { // Caso ainda seja possivel ler o arquivo
			
			simbolo = leitura.next(); // Entao e lido o proximo simbolo
			
			// Caso o simbolo seja do lado esquerdo
			// Eu apenas armazeno ele para utiliza-lo depois
			
			if (simbolo.matches("[A-Z]")) { 
				
				esquerda = simbolo;
				
			}
			
			else { 
							
				direita = simbolo;
				
				// Caso a simbolo do lado direito ainda nao esteja no HashMap,
				// Ele e inserido como chave no HashMap, com uma lista de string nula
				
				if (regrasGLC.get(direita) == null) {
				
					List<String> lista = new ArrayList<String>();	
					regrasGLC.put(direita, lista);
					
				}
				
				// Depois, eu busco o HashMap pelo simbolo do direita
				// E insiro o simbolo da esquerda (que eu armazei antes) na lista
				
				List<String> listaAux = regrasGLC.get(direita);
				listaAux.add(esquerda);
				regrasGLC.put(direita, listaAux);
								
			}
				
		}
				
		leitura.close();	
					
	}
	
	
	// Metodo que ira realizar os calculos do CYK
		
	public static void cyk () {
		
		int tam = palavra.length();
		int i,j;
		
		int linha = tam+1;
		int coluna = tam;
				
        mat = new String[linha][coluna]; 
        
		int limite = coluna;
		
		for (i=0; i<linha; i++) { 
	        for (j=0; j<coluna; j++) {
	        	// Preenche a primeira linha da matriz com a palavra
	        	if (i==0) {
	        		mat[i][j] = palavra.charAt(j) + ""; 
	        	}
	        	// E o resto preenche como tudo vazio ""
	        	else {
	        		mat[i][j] = "";
	        	}	        	
	        }	        
		}
		                
        limite = coluna;
        
        int k;
        int a;
        int b;
        int x;
        int y;
        
        for (i=1; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	
            	// A linha 1 da matriz e diferente porque apenas ela 
            	// Compara com a linha 0 para ver quem gera os terminais
            	// Para isso vamos criar um if apenas para ela
            	
            	if (i==1) {
            		
            		// Esse for percorre todo o HashMap
            		
            		// Entrada e a combinacao de uma chave com a sua respectiva lista
            		// De variavaeis (do lado esquerdo) que a geram
            		
	                for (Map.Entry<String, List<String>> entrada : regrasGLC.entrySet()) {
	                	
	                    String chave = entrada.getKey();

	                   
	                    // Encontro a chave no HashMap que e igual ao terminal daquela posicao da matriz
	                    if (mat[0][j].equals(chave)) {
	                    	
	                    	//System.out.println("mat: " + mat[0][j] + " chave: " + chave);
	                    	
		                    List<String> lista = entrada.getValue();
		                    
		                    // Nesse for, eu coloco TODAS as variaveis que geram aquele terminal
		                    // Em uma posicao da matriz
		                    
		                    // Caso mais de uma variavel gere o terminal, sera concatenado
		                    // Tudo em uma string: Exemplo: ABC
		                    
	                    	for(k=0; k<lista.size(); k++) {
	                    		mat[i][j] = mat[i][j] + lista.get(k);
	                    	}
	                    	
	                    }
	                    
	                }
            		
            	}
            	
            	// A partir da linha 2, sera entrado nesse else para realizar as comparacões
            	
            	else {
            		            	
	            	// Percorre todas as linhas anteriores para fazer as comparacões
	            	
        			// Aqui eu percorro uma coluna e uma diagonal ao mesmo tempo
		            // E realizo as comaracões
            		
            		for (k=i-1; k>0; k--) {

            			// Para cada posicao da matriz, talvez tenha de mais uma variavel
            			// Exemplo: AB compara com TX. Dai se faz a permutacao
            			// AT, AX, BT e BX. Para isso serve esses 2 for
            			
            			for (a=0; a<mat[k][j].length(); a++) { 
            				for (b=0; b<mat[i-k][k+j].length(); b++) {
            					
            					// Nessa parte eu concateno duas variaveis
            					// Seguindo o exemplo anterior: AB compara com TX
            					// Nessa parte eu concatenaria A com T para formar AT
            					
            					String variavel = (mat[k][j].charAt(a) + "") + (mat[i-k][k+j].charAt(b) + "");
            					
                        		// Nesse for, eu percorro o HashMap para ver se encontro o AT do exemplo
                        		
            	                for (Map.Entry<String, List<String>> entrada : regrasGLC.entrySet()) {

            	                    String chave = entrada.getKey();
            	                	
            	                    // Caso eu encontre, ele entra nesse if
            	                    if (variavel.equals(chave)) {

            		                    List<String> lista = entrada.getValue();
            		                    
            		                    // Como encontrou o AT em alguma chave do HashMap
            		                    // Entao pego a lista do HashMap de AT e insiro todos os elementos
            		                    // Da lista (que no caso sao as variaveis que deviram AT) 
            		                    // Na posicao da matriz [i][j]
            	                    	for (x=0; x<lista.size(); x++) {
            	                    		
            	                    		int contador = 0;
            	                    		
            	                    		// Porem antes se iserir as variaveis que derivam AT
            	                    		// Exemplo: S -> AT e F -> AT
            	                    		// Mas suponha que F ja esteja inserido na posicao da matriz
            	                    		// Entao antes de inserir o F, verifico se ja existe na mat[i][j]
            	                    		
            	                    		for (y=0; y<mat[i][j].length(); y++) {
            	                    			
            	                    			if ((mat[i][j].charAt(y) + "").equals(lista.get(x))) {
            	                    				contador ++;
            	                    			}
            	                    		}
            	                    		
            	                    		// Se nao existe a palavra, eu inserio a variavel na matriz
            	                    		if (contador == 0) {            	                    		
            	                    			mat[i][j] = mat[i][j] + lista.get(x);
            	                    		}
            	                    		
            	                    	}
            	                    	
            	                    }
            	                    
            	                } // Fim do percurso no HashMap
            						
            				} // Fim do for das permutacões de b
            				
            			} // Fim do for das permutacões de a

            		} // Fim do for que percorre as linhas anteriores para a comparacao
	            	
            	} // Fim do else

            } // Fim do for das colunas
            
        	limite--;
       
        }
        
        limite = coluna;
        
	}	
	
	// Metodo para padronizar a matriz com as {} e virgulas
	
	public static void padronizar () {
	
		int tam = palavra.length();
		int i, j, k;
		
		int linha = tam+1;
		int coluna = tam;
      
		int limite = coluna;
		
        for (i=1; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	
            	// Caso contenha apenas uma ou nenhuma variavel, colocamos {VARIAVEL} ou {}          	
            	if (mat[i][j].length() <= 1) {
            		mat[i][j] = "{" + mat[i][j] + "}";
            	}
            	
            	// Caso haja mais de uma variavel, colocamos as virgulas
            	else {
            		
            		String auxiliar = "";
            		
            		for (k=0; k<mat[i][j].length()-1; k++) {
            			auxiliar = auxiliar + mat[i][j].charAt(k) + ",";
            		}

            		// E por fim, padronizamos para {VARIAVEl,VARIAVEL,VARIAVEL}
            		auxiliar = "{" + auxiliar + mat[i][j].charAt(k) + "}";
            		mat[i][j] = auxiliar;
            	}
            	
            }
            
            // Na segunda linha, o limite nao diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

        }
		
		
	}
	
	public static void imprimir () {
		
		
		int tam = palavra.length();
		int i, j;
		
		int linha = tam+1;
		int coluna = tam;
      
		int limite = coluna;
					
        for (i=0; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	System.out.print(mat[i][j] + "\t");
            }
            // Na segunda linha, o limite nao diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

            System.out.println();
        }     
	        		
	       
	}
	
	public static void writeFile () {		
		try {
			int tam = palavra.length();
			int i, j;
			
			String quebraLinha = System.lineSeparator();
			
			int linha = tam+1;      
			//limite comeca em 1 pois se deve imprimir apenas 1 elemento no comeco do loop e ir aumentando
			int limite = 1;
			
		    PrintWriter escrita = new PrintWriter (arqsaida);
						
	        for (i=linha-1; i>=0; i--) {
	        	
	        	if (i == 0) {
	        		 escrita.write("p\t");
	        	}
	        	else {
	        		 escrita.write(i + "\t");
	        	}
	        	
	            for (j=0; j<limite; j++) {
	            	escrita.write(mat[i][j] + "\t");
	            }
	            escrita.write(quebraLinha);
	            
	            // Na penultima linha, o limite nao aumenta
	            // Pois ela tem a mesma quantidade de colunas da primeira linha
	            if (i > 1) {
	            	limite++;
	            }
	        }	        
	        escrita.close();
		}
        catch (FileNotFoundException e) {
            System.err.println("Arquivo de Saida nao Encontrado");
        }
	}

} // Fim da classe Main