package main;

import java.util.*;

public class Registradores{
    private Map<Integer,Integer> registers;
    private int data1;
    private int data2;
    private int regW;
    private int controlRegWrite;
    private int saida1;
    private int saida2;

    public Registradores(){
        /* Preencher essa aqui no contrutor o dicionario com todos so registradores zerados
        *  Usando for mesmo, não fiz ainda pq estou sem saco no momento
        */
        registers = new HashMap<Integer,Integer>();
        data1 = 0;
        data2 = 0;
        regW = 0;
        controlRegWrite = 0;
        saida1 = 0;
        saida2 = 0;
    }

    public void busca(int f, int s, int write, int controlData){
        /* Eu criei um dicionario para ser o banco de registradores, acho que assim vai ser mais
        * facil de trabalhar, aqui é para definir qual conteudo é para pegar no registrador
        */
        data1 = registers.get(f);
        data2 = registers.get(s);
        regW = write;
        controlRegWrite = controlData;
    }

    public void dataWrite(int dataW){
        if(controlRegWrite == 1){
            registers.put(regW, dataW);
        }
    }

    public int saida(){return data1;}
    public int saida2(){return data2;}
    
}