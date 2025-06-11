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
    private static List<List<Space>> preencherTabuleiro(){

        return ;
    }
}

