package br.com.dio;

import java.util.Scanner;

public class Main {
  public static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    boolean gameInProgress = true;
      while(gameInProgress){

        System.out.println("1-Iniciar um jogo");
        System.out.println("2-Encerrar sessão");
        int option = scanner.nextInt();

        switch(option) {
          case 1: ;
          case 2: gameInProgress = false;
          default: System.out.println("Opção invalida!");
        }
      }
    }
}


