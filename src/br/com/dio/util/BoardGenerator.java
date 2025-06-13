package br.com.dio.util;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardGenerator {

    public static Board generateFullBoard() {
        List<List<Space>> spaces = criarMatrizVazia();
        preencherTabuleiro(spaces, 0, 0);
        return new Board(spaces);
    }

    private static List<List<Space>> criarMatrizVazia() {
        final int SIZE = 9;
        List<List<Space>> matriz = new ArrayList<>();

        for (int l = 0; l < SIZE; l++) {
            List<Space> linha = new ArrayList<>();
            for (int c = 0; c < SIZE; c++) {
                linha.add(new Space(0, false));
            }
            matriz.add(linha);
        }

        return matriz;
    }

    private static boolean preencherTabuleiro(List<List<Space>> spaces, int row, int col) {
        // Caso base: tabuleiro completo
        if (row == 9) return true;

        // Calcula próxima posição
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        // Gera lista de números embaralhados
        List<Integer> numeros = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numeros);

        // Tenta preencher a célula com os números disponíveis
        for (Integer num : numeros) {
            if (isValidPlacement(spaces, row, col, num)) {
                spaces.get(row).get(col).setActual(num);
                spaces.get(row).get(col).setExpected(num);

                if (preencherTabuleiro(spaces, nextRow, nextCol)) return true;

                // Backtracking
                spaces.get(row).get(col).setExpected(0);
                spaces.get(row).get(col).clearSpace();
            }
        }

        return false;
    }

    public static boolean isValidPlacement(List<List<Space>> spaces, int row, int col, int value) {
        // Verifica linha
        for (int c = 0; c < 9; c++) {
            Integer current = spaces.get(row).get(c).getActual();
            if (current != null && current.equals(value)) return false;
        }

        // Verifica coluna
        for (int r = 0; r < 9; r++) {
            Integer current = spaces.get(r).get(col).getActual();
            if (current != null && current.equals(value)) return false;
        }

        // Verifica bloco 3x3
        int startRow = row - (row % 3);
        int startCol = col - (col % 3);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Integer current = spaces.get(startRow + r).get(startCol + c).getActual();
                if (current != null && current.equals(value)) return false;
            }
        }

        return true;
    }

    public static Board clonarBoard(Board board) {
        List<List<Space>> original = board.getSpaces();
        List<List<Space>> copia = new ArrayList<>();

        for (List<Space> linhaOriginal : original) {
            List<Space> novaLinha = new ArrayList<>();
            for (Space s : linhaOriginal) {
                Space copiaSpace = new Space(s.getExpected(), s.isFixed());
                copiaSpace.setActual(s.getActual()); // mantém o valor atual (pode ser null)
                novaLinha.add(copiaSpace);
            }
            copia.add(novaLinha);
        }

        return new Board(copia);
    }
}
