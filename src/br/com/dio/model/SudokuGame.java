package br.com.dio.model;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardGenerator;
import br.com.dio.util.BoardTemplate;

import java.util.*;

public class SudokuGame {

    private final Scanner scanner = new Scanner(System.in);
    private Board board;

    public void iniciar() {
        System.out.println("===== BEM-VINDO AO SUDOKU =====");
        board = BoardGenerator.generateFullBoard();
        escolherDificuldade();
        iniciarLoopDeJogo();
    }

    private void escolherDificuldade() {
        System.out.println("Escolha a dificuldade:");
        System.out.println("1 - Fácil (30 removidos)");
        System.out.println("2 - Médio (40 removidos)");
        System.out.println("3 - Difícil (50 removidos)");

        int escolha = scanner.nextInt();
        int quantidadeParaRemover = switch (escolha) {
            case 1 -> 30;
            case 2 -> 40;
            case 3 -> 50;
            default -> 40;
        };

        removerNumeros(quantidadeParaRemover);
    }

    private void removerNumeros(int quantidade) {
        Random random = new Random();
        int removidos = 0;

        while (removidos < quantidade) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            Space space = board.getSpaces().get(row).get(col);
            if (!space.isFixed() && space.getActual() != null) {
                int original = space.getActual();
                space.clearSpace();

                if (temUnicaSolucao(board)) {
                    removidos++;
                } else {
                    space.setActual(original);
                }
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getSpaces().get(row).get(col).getActual() != null) {
                    board.getSpaces().get(row).get(col).setFixed(true);
                }
            }
        }
    }

    private boolean temUnicaSolucao(Board board) {
        Board copia = BoardGenerator.clonarBoard(board);
        return contarSolucoes(copia, 0, 0, 0) == 1;
    }

    private int contarSolucoes(Board board, int row, int col, int contador) {
        if (row == 9) return contador + 1;
        if (contador > 1) return contador;

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        Space atual = board.getSpaces().get(row).get(col);
        if (atual.getActual() != null) {
            return contarSolucoes(board, nextRow, nextCol, contador);
        }

        for (int num = 1; num <= 9; num++) {
            if (BoardGenerator.isValidPlacement(board.getSpaces(), row, col, num)) {
                atual.setActual(num);
                contador = contarSolucoes(board, nextRow, nextCol, contador);
                atual.clearSpace();
            }
        }
        return contador;
    }

    private void iniciarLoopDeJogo() {
        while (true) { // Loop infinito que controlaremos internamente
            mostrarTabuleiro();

            if (board.hasErrors()) {
                System.out.println("⚠️ Atenção! Existem erros no tabuleiro (números incorretos).");
            }

            System.out.println("\nEscolha sua próxima jogada:");
            System.out.println("1 - Inserir número");
            System.out.println("2 - Apagar número");
            System.out.println("3 - Reiniciar Jogo");
            System.out.println("4 - Sair");

            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> inserirNumero();
                case 2 -> apagarNumero();
                case 3 -> {
                    iniciar();
                    return;
                }
                case 4 -> {
                    System.out.println("Obrigado por jogar!");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida.");
            }

            if (board.gameIsFinished()) {
                mostrarTabuleiro();
                System.out.println("\n🎉 Parabéns! Você completou o Sudoku corretamente! 🎉");
                break; // Sai do loop while
            }
        }
    }

    // Em SudokuGame.java

    private void mostrarTabuleiro() {
        List<Object> valores = new ArrayList<>();
        // Largura do conteúdo de cada célula.
        // Usaremos 5 para ter um bom espaçamento: "  9  "
        final int LARGURA_CELULA = 5;

        for (List<Space> linha : board.getSpaces()) {
            for (Space space : linha) {
                Integer v = space.getActual();
                String conteudoCelula;

                if (v == null) {
                    // Se a célula for nula, cria uma string de espaços em branco
                    conteudoCelula = " ".repeat(LARGURA_CELULA);
                } else {
                    // Se tiver um número, centraliza ele dentro da largura definida
                    String numeroStr = v.toString();
                    int espacos = LARGURA_CELULA - numeroStr.length();
                    int padEsquerda = espacos / 2;
                    int padDireita = espacos - padEsquerda;
                    conteudoCelula = " ".repeat(padEsquerda) + numeroStr + " ".repeat(padDireita);
                }
                valores.add(conteudoCelula);
            }
        }
        System.out.printf(BoardTemplate.BOARD_TEMPLATE, valores.toArray());
    }

    private void inserirNumero() {
        System.out.print("Linha (0-8): ");
        int row = scanner.nextInt();
        System.out.print("Coluna (0-8): ");
        int col = scanner.nextInt();
        System.out.print("Valor (1-9): ");
        int valor = scanner.nextInt();

        // >> LÓGICA ADICIONADA AQUI <<
        if (!board.changeValue(row, col, valor)) {
            System.out.println("\n⚠️ Jogada inválida! Não é possível alterar um número fixo.");
        } else {
            System.out.println("Número inserido com sucesso.");
        }
    }

    private void apagarNumero() {
        System.out.print("Linha (0-8): ");
        int row = scanner.nextInt();
        System.out.print("Coluna (0-8): ");
        int col = scanner.nextInt();

        // >> LÓGICA ADICIONADA AQUI <<
        if (!board.clearValue(row, col)) {
            System.out.println("\n⚠️ Jogada inválida! Não é possível apagar um número fixo.");
        } else {
            System.out.println("Número apagado com sucesso.");
        }
    }
}
