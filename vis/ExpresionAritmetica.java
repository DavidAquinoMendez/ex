/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.prolo1;
import java.util.Scanner;
import java.util.Stack;
/**
 *
 * @author cr7_3
 */
public class ExpresionAritmetica {
        Scanner leer = new Scanner(System.in);
     //Entrada de datos
        public ExpresionAritmetica(){
    System.out.println("Escribe una expresión algebraica: ");
    //**( 9 + .89 ) * 1 / 4 ^ 2 - 2  -1.381875
    Scanner leer = new Scanner(System.in);
    //espacios entre cada expresion 
    String infi = leer.nextLine();
    String infix = espacios(infi);
      
    System.out.printf("Postfija: %s\n", infixToPostfix(infix)); // cadena de caracteres %s y despues salto de 
                                                               //linea aplicamos el metodo a la cadena ingresada


    //*****codigo para obtener el resultado de la operacion 
    String[] post = infixToPostfix(infix).split(" ");   // asignamos un array donde meteremos la cadena dividida por espacios
   
    //Declaración de las pilas
    Stack < String > E = new Stack < String > (); //Pila entrada
    Stack < String > P = new Stack < String > (); //Pila de operandos

    //Añadir post (array) a la Pila de entrada (E)
    for (int i = post.length - 1; i >= 0; i--) {
      E.push(post[i]);
    }

    
    String operadores = "+-*/%^";
    while (!E.isEmpty()) { //mientras la cadena no esta vacia
      if (operadores.contains("" + E.peek())) {  //si los operadores declarados son iguales al elemento retirado 
                                                 // de la cima de la pila
        P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");//lo apila en la pila P y retira los elementos de la 
                                                        // pila E de acuerdo al numero 1 y 2 y al operador obtenido hace
                                                        //la operacion 
      }else {
        P.push(E.pop());//si solo es un numero lo apila en la pila p hasta que salga un operador
      }
    }

    //Mostrar resultados:
    System.out.println("Resultado: " + P.peek()); //imrpime el resultado devolviendo el ultimo elemento de la pila
    
        }
    
    static String infixToPostfix(String infix) {
/*Para averiguar la precedencia, tomamos el índice del
token en la cadena de operaciones y dividir por 2 (redondeando hacia abajo).
Esto nos dará: 0, 0, 1, 1, 2  */
final String ops = "-+/*^";
 
StringBuilder sb = new StringBuilder();
Stack<Integer> s = new Stack<>();
 
for (String token : infix.split("\\s")) { //obtenemos cada token de nuestra cadena dividida por espacio
if (token.isEmpty()) //si el token esta vacio continua
continue;
char c = token.charAt(0); 
int idx = ops.indexOf(c); //sacamos el valor donde si no existe en ops obtendremos un -1
// comprobar si hay operador
if (idx != -1) { 
if (s.isEmpty())
s.push(idx);
 
else {
while (!s.isEmpty()) {
int prec2 = s.peek() / 2;
int prec1 = idx / 2;
if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
sb.append(ops.charAt(s.pop())).append(' ');
else break;
}
s.push(idx);
}
}
else if (c == '(') {
s.push(-2); // -2 significa '('
}
else if (c == ')') {
// Hasta '(' en la pila, operadoras pop.
while (s.peek() != -2)
sb.append(ops.charAt(s.pop())).append(' ');
s.pop();
}
else {
sb.append(token).append(' ');
}
}
while (!s.isEmpty())
sb.append(ops.charAt(s.pop())).append(' ');
return sb.toString();
}
    
    //*********metodo para obtener el resultado de la operacion 
    private static double evaluar(String op, String n2, String n1) {
    double num1 = Double.parseDouble(n1);
    double num2 = Double.parseDouble(n2);
    if (op.equals("+")) return (num1 + num2);
    if (op.equals("-")) return (num1 - num2);
    if (op.equals("*")) return (num1 * num2);
    if (op.equals("/")) return (num1 / num2);
    if (op.equals("%")) return (num1 % num2);
    if (op.equals("^")) return (Math.pow(num1, num2));
    
    return 0;
  }

    //espacios entre expresión algebraica
  private static String espacios(String exp) {
  
    String simbols = "+-*/()^";
    String expresion = "";
 
    //Deja espacios entre operadores
    for (int i = 0; i < exp.length(); i++) {
      if (simbols.contains("" + exp.charAt(i))) {
        expresion += " " + exp.charAt(i) + " "; 
      }else expresion += exp.charAt(i);
    }
    return expresion.replaceAll("\\s+", " ").trim();
  }
    
}