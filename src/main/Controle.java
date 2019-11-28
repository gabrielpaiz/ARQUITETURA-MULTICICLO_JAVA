package main;

import main.Instrucao;

public class Controle{
    //Sei mais ou menos como vamos fazer isso daqui

    public static void execution (String [] memoriaInstrucao) {

        int instrucaoLidas = 0;

        while (true/*memoriaInstrucao não está vazio*/) {

            String [] atualInstrucao = new String[32];

            //Carrega os bits da instrução a ser processada.
            for (int i = 0; i < 32; i++)
                atualInstrucao[i] = memoriaInstrucao[i + (instrucaoLidas * 32)];
                Memoria test;











            instrucaoLidas++;

        }


    }


}