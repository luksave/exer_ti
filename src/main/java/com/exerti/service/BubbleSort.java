package com.exerti.service;

import java.util.Arrays;

public class BubbleSort {
  public static void bubbleSort(int[] array) {
    int n = array.length;
    boolean troca;
    //Percorre o array n-1 vezes
    for (int i = 0; i < n - 1; i++) {
      troca = false;
      //A cada iteração, o maior elemento está na última posição do array não ordenado, logo a 'bolha' fica menor
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          int temp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = temp;
        }
        if(troca) {
          break; //array já está ordenado
        }
      }
      imprimirArray(array, n - i - 1);
    }
  }


  public static void imprimirArray(int[] array, int posicaoOrdenada) {
    for (int i = 0; i < array.length; i++) {
      if(posicaoOrdenada == i) {
        System.out.printf("[");
      }

      System.out.printf("%d", array[i]);

    }
    System.out.printf("]\n");
  }

  public static void main(String[] args) {
    
    //Decidi não usar ArrayList para não fazer uso de seu método de ordenação
    int[] array = {5, 3, 2, 4, 7, 1, 0, 6};
    bubbleSort(array);

    System.out.println(Arrays.toString(array));
  }

}
