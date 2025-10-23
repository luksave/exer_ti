package com.exerti.service;

public class SomaMultiplos {
  
  public static int somaMultiplos(int x) {
    int soma = 0;
    for(int i = 0; i < x; i++) {
      if(i % 3 == 0 || i % 5 == 0) {
        soma += i;
      }
    }
    return soma;
  }

  /**
   * Exemplo de uso: 
   * javac com/exerti/service/SomaMultiplos.java
   * java com/exerti/service/SomaMultiplos 10
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Nenhum valor informado");
      return;
    }
    
    int x = Integer.parseInt(args[0]);
    System.out.println(somaMultiplos(x));
  }
}
