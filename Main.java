package projetoEstruturaDados;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        String expressao = "";
        BinaryTree arvore = new BinaryTree();
        boolean expressao_feita = false;
        boolean arvore_criada = false;

        while (true) {
            System.out.print(
                "\n1. Entrada da expressão aritmética na notação infixa."
                + "\n2. Criação da árvore binária de expressão aritmética."
                + "\n3. Exibição da árvore binária de expressão aritmética."
                + "\n4. Cálculo da expressão (realizando o percurso da árvore)."
                + "\n5. Encerramento do programa."
                + "\nSua escolha: "
            );

            int resposta = scn.nextInt();
            scn.nextLine();

            if (resposta == 1) {
                // Entrada da expressão infixa
                System.out.print("\nDigite a expressão infixa: ");
                expressao = scn.nextLine().replaceAll(" ", "");

                VeryBasicTokenizer vbt = new VeryBasicTokenizer(expressao);
                vbt.tokenize();

                if (vbt.getValido()) {
                    expressao_feita = true;
                } else {
                    System.out.println("Expressão inválida!");
                }

            } else if (resposta == 2) {
                // Criação da árvore binária
                if (expressao_feita) {
                    Stack<Character> pilha = new Stack<>();
                    StringBuilder expressao_saida = new StringBuilder();

                    // Convertendo para pós-fixa
                    for (int i = 0; i < expressao.length(); i++) {
                        char caractere = expressao.charAt(i);

                        // Verifica se é um dígito e acumula o número completo (com múltiplos dígitos)
                        if (Character.isDigit(caractere)) {
                            StringBuilder numeroCompleto = new StringBuilder();
                            // Continua lendo até não haver mais dígitos
                            while (i < expressao.length() && Character.isDigit(expressao.charAt(i))) {
                                numeroCompleto.append(expressao.charAt(i));
                                i++;
                            }
                            i--; // Corrige o índice após o while para não pular um caractere
                            // Adiciona o número completo e um espaço na expressão pós-fixa
                            expressao_saida.append(numeroCompleto).append(" ");
                        } 
                        // Trata operadores de menor precedência (+ ou -)
                        else if (caractere == '+' || caractere == '-') {
                            while (!pilha.isEmpty() && (pilha.peek() == '+' || pilha.peek() == '-' || pilha.peek() == '*' || pilha.peek() == '/')) {
                                expressao_saida.append(pilha.pop()).append(" ");
                            }
                            pilha.push(caractere);
                        } 
                        // Trata operadores de maior precedência (* ou /)
                        else if (caractere == '*' || caractere == '/') {
                            while (!pilha.isEmpty() && (pilha.peek() == '*' || pilha.peek() == '/')) {
                                expressao_saida.append(pilha.pop()).append(" ");
                            }
                            pilha.push(caractere);
                        } 
                        // Trata parênteses de abertura
                        else if (caractere == '(') {
                            pilha.push(caractere);
                        } 
                        // Trata parênteses de fechamento
                        else if (caractere == ')') {
                            while (!pilha.isEmpty() && pilha.peek() != '(') {
                                expressao_saida.append(pilha.pop()).append(" ");
                            }
                            if (!pilha.isEmpty()) {
                                pilha.pop(); // Remove o '(' da pilha
                            }
                        }
                    }

                    // Esvazia a pilha para o restante dos operadores
                    while (!pilha.isEmpty()) {
                        expressao_saida.append(pilha.pop()).append(" ");
                    }

                    System.out.println("Expressão Pós-fixa: " + expressao_saida);


                    // Construção da árvore
                    Stack<NodeBase> pilha2 = new Stack<>();
                    NodeOperador root = null;
                    
                    for (int j = 0; j < expressao_saida.length(); j++) {
                        char caractere = expressao_saida.charAt(j);
                        if (caractere == ' ') {
                            continue; // Ignora os espaços
                        }
                        // Se for um número, pode ser um operando com vários dígitos
                        if (Character.isDigit(caractere)) {
                            StringBuilder num = new StringBuilder();
                            num.append(caractere);
                            // Continua acumulando os dígitos até encontrar um espaço
                            while ((j + 1) < expressao_saida.length() && expressao_saida.charAt(j + 1) != ' ') {
                                j++;
                                num.append(expressao_saida.charAt(j));
                            }
                            // Empilha o operando completo
                            pilha2.push(new NodeOperando(num.toString(), null));
                            System.out.print("\n\n" + pilha2.peek().getData() + "\n\n");
                            
                        } else { 

                        	// Cria o nó operador (ajustando o construtor)
                            NodeOperador noOperador = new NodeOperador(Character.toString(caractere), null, null, null);

                            // Desempilha os dois últimos operandos ou operadores (usando NodeBase)
                            NodeBase noOperadorRight = pilha2.pop(); // Desempilha o operador ou operando da direita
                            NodeBase noOperadorLeft = pilha2.pop();  // Desempilha o operador ou operando da esquerda

                            // Define os filhos do nó operador
                            noOperador.setRight(noOperadorRight);
                            noOperador.setLeft(noOperadorLeft);

                            // Empilha o nó operador para uso posterior
                            pilha2.push(noOperador);
                        }
                    }

                    // O último elemento restante na pilha será o root
                    if (!pilha2.isEmpty()) {
                        root = (NodeOperador) pilha2.pop();
                    }

                    // Define o root na árvore
                    if (root != null) {
                        arvore.setRoot(root);
                        System.out.print("\n\n" + arvore.getRoot().getData() + "\n\n");
                        arvore_criada = true;
                    } else {
                        System.out.println("Erro ao criar a árvore.");
                    }

                } else {
                    System.out.print("\nErro: Nenhuma expressão válida foi fornecida.");
                }
        	
        
            } else if (resposta == 3) {
                // Exibição da árvore
                if (arvore_criada) {
                    System.out.print("\nEm Ordem: ");
                    arvore.emOrdem();
                    System.out.print("\nPré Ordem: ");
                    arvore.preOrdem();
                    System.out.print("\nPós Ordem: ");
                    arvore.posOrdem();
                } else {
                    System.out.println("Árvore não criada.");
                }

            } else if (resposta == 4) {
                // Cálculo da expressão
                if (arvore_criada) {
                    float valor = arvore.percorreContando();
                    System.out.print("\nResultado: " + valor);
                } else {
                    System.out.println("Árvore não criada.");
                }

            } else if (resposta == 5) {
                // Encerramento
                System.out.print("\nEncerrando o programa...");
                scn.close();
                return;

            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
