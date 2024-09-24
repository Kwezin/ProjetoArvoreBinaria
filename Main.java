package projetoEstruturaDados;

//Kauê Henrique Matias Alves - 10417894
//Matheus Alonso Varjão - 10417888
//Leonardo Moreira dos Santos - 10417555
import java.util.Scanner;
import java.util.Stack;

public class Main {
public static void main(String[] args) {
  
  Scanner scn = new Scanner(System.in);
  String expressao = "";
  BinaryTree arvore = new BinaryTree();
  boolean expressao_feita = true;
  boolean arvore_criada = false;
  

  
  while (true){
    
    int resposta;
    
    
    System.out.print("\n1. Entrada da expressão aritmética na notação infixa.\n2. Criação da árvore binária de expressão aritmética.\n3. Exibição da árvore binária de expressão aritmética.\n4. Cálculo da expressão (realizando o percurso da árvore).\n5. Encerramento do programa.\nSua escolha: ");

    resposta = scn.nextInt();
    scn.nextLine();
    
    if(resposta == 1){
      
      System.out.print("\nDigite a expressão infixa: ");
      expressao = scn.nextLine().replaceAll(" ", "");
      

      /*
      if(expressao valida){
        expressao_feita = true;
      }*/
    }
    else if(resposta == 2){
      
      if(expressao_feita){
    	
        // Transformar para pos-fixa
        Stack<Character> pilha = new Stack<Character>();
        String expressao_saida = "";
        
        for(int i =0; i < expressao.length(); i++){
          char caractere = expressao.charAt(i);
          

          if(caractere == '+' || caractere == '-'){
            while(!pilha.isEmpty() && (pilha.peek() == '+' || pilha.peek() == '-' || pilha.peek() == '*' || pilha.peek() == '/')){
              	
              expressao_saida += pilha.pop(); 
            }

            pilha.push(caractere);         
          }
          else if(caractere == '*' || caractere == '/'){
            while(!pilha.isEmpty() && (pilha.peek() == '*' || pilha.peek() == '/')){

              expressao_saida += pilha.pop(); 
            }

            pilha.push(caractere);
          }
          else if(caractere == '('){
            pilha.push(caractere);
            
          }
          else if(caractere == ')'){
            while(pilha.peek() != '('){
              
              expressao_saida += pilha.pop();
              
            }
            pilha.pop();
          }
          else{
            expressao_saida += caractere;
          }

        }
        
        while(!pilha.isEmpty()){
          expressao_saida += pilha.pop();
          
        }

        System.out.print(expressao_saida);
        Stack<NodeBase> pilha2 = new Stack<>();
        NodeOperador root = null;

        for (int j = 0; j < expressao_saida.length(); j++) {
            char caractere = expressao_saida.charAt(j);

            // Se o caractere não é um operador, empilha como NodeOperando
            if (caractere != '+' && caractere != '-' && caractere != '*' && caractere != '/') {
                pilha2.push(new NodeOperando(caractere, null)); // Cria e empilha o nó operando
            } else {
                // Cria um novo nó operador
                NodeOperador noOperador = new NodeOperador(caractere, null, null, null);

                // Desempilha os dois últimos operandos ou operadores (usando NodeBase)
                NodeBase noOperadorRight = pilha2.pop(); // Desempilha o operador ou operando da direita
                NodeBase noOperadorLeft = pilha2.pop();  // Desempilha o operador ou operando da esquerda

                // Define os filhos do nó operador
                noOperador.setRight(noOperadorRight);
                noOperador.setLeft(noOperadorLeft);

                // Empilha o nó operador para uso posterior
                pilha2.push(noOperador);

                // Se for o último caractere, define o root
                if (j == expressao_saida.length() - 1) {
                    root = noOperador;
                }
            }
        }
        System.out.print("\n\n\n");
        arvore.setRoot(root);
        arvore_criada = true;
        
       
      }
      else{
        System.out.print("Erro ao tentar criar árvore");
      }
      
    }
    else if(resposta == 3){
      if(arvore_criada) {
    	  System.out.print("\nEm Ordem: ");
          arvore.emOrdem();
          System.out.print("\nPós Ordem: ");
          arvore.posOrdem();
          System.out.print("\nPré Ordem: ");
          arvore.preOrdem();
      }
    }
    else if(resposta == 4){
      float valor = arvore.percorreContando(); 
      System.out.print("\n\n"+ valor);
    }
    else if(resposta == 5){
      break;
    }
    
  }
  
}
}
