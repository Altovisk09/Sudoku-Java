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
        // 1. ✅ Caso base: se 'row' chegou a 9, o tabuleiro está completo → retorna true
        if(row == 9){
            return true;
        }

        // 2. 🔄 Calcula a próxima posição:
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        // 3. 🎲 Cria uma lista com os números de 1 a 9 e embaralha para gerar aleatoriedade (Collections.shuffle)
            List<Integer> numeros = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
            Collections.shuffle(numeros);

        // 4. 🔁 Para cada número da lista embaralhada:
        //     4.1 Verifica se o número pode ser colocado nessa célula (isValidPlacement(...))

        //     4.2 Se puder:
        //         - Usa .setExpected(...) e .setActual(...) no Space correspondente
        //         - Chama preencherTabuleiro(...) recursivamente com a próxima posição
        //         - Se a chamada recursiva retornar true → retorna true também (sucesso!)
        //     4.3 Se não der certo (recursão falhou), faz backtrack:
        //         - Usa .setExpected(0)
        //         - Usa .clearSpace() para limpar o valor atual

        // 5. ❌ Se nenhum número for válido, retorna false → isso ativa o backtracking da chamada anterior
        return false;
}
        private static boolean isValidPlacement(List<List<Space>> spaces, int row, int col, int value){
            //Verifica Linha
            for(int c = 0; c < 9; c++){
                Integer current = spaces.get(row).get(c).getActual();
            if(current != null && current.equals(value)) return false;
            }

            // Verifica coluna
            for(int r = 0; r < 9; r++){
                Integer current = spaces.get(r).get(col).getActual();
                if(current != null && current.equals(value)) return false;
            }

            // Verifica bloco 3x3
            int startRow = row - (row % 3);
            int startCol = col - (col % 3);

            for(int r = 0; r < 3; r++){
                for(int c = 0;c < 3; c++){
                    Integer current = spaces.get(startRow + r).get(startCol + c).getActual();
                    if(current != null && current.equals(value)) return false;
                }
            }
            return true;
        }
    }

