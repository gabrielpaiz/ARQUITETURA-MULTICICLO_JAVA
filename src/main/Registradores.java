package main;

import java.util.*;

public class Registradores{
    private Map<Integer,Integer> registers;
    private int data1;
    private int data2;
    private int regW;
    private int controlRegWrite;

    private static final Registradores INSTANCE = new Registradores();

    public static Registradores getInstance(){
        return INSTANCE;
    }

    private Registradores(){
        /* Preencher essa aqui no contrutor o dicionario com todos so registradores zerados
         *  Usando for mesmo, não fiz ainda pq estou sem saco no momento
         */
        registers = new HashMap<Integer,Integer>();
        for(int i = 0; i<32;i++){
            registers.put(i, 0);
        }
        data1 = 0;
        data2 = 0;
        regW = 0;
        controlRegWrite = 0;
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

    public void writeBack(int dataW){
        if(controlRegWrite == 1){
            registers.put(regW, dataW);
        }
    }

    public int saida(){return data1;}
    public int saida2(){return data2;}

    private String switchRegister(int reg){
        switch(reg){
            case 0: return "$zero";
            case 1: return "$at";
            case 2: return "$v0";
            case 3: return "$v1";
            case 4: return "$a0";
            case 5: return "$a1";
            case 6: return "$a2";
            case 7: return "$a3";
            case 8: return "$t0";
            case 9: return "$t1";
            case 10: return "$t2";
            case 11: return "$t3";
            case 12: return "$t4";
            case 13: return "$t5";
            case 14: return "$t6";
            case 15: return "$t7";
            case 16: return "$s0";
            case 17: return "$s1";
            case 18: return "$s2";
            case 19: return "$s3";
            case 20: return "$s4";
            case 21: return "$s5";
            case 22: return "$s6";
            case 23: return "$s7";
            case 24: return "$t8";
            case 25: return "$t9";
            case 26: return "$k0";
            case 27: return "$k1";
            case 28: return "$gp";
            case 29: return "$sp";
            case 30: return "$fp";
            case 31: return "$ra";
            default: System.out.println("Deu na busca dos registradores um erro!!!"+ reg);break;
        }
        return "Erro!";
    }

    public String toString(){
        String s = "\nRegistradores:\n";
        s += switchRegister(0) + "   "+ registers.get(0)+"\n";
        for(int i = 1; i<32-4;i+= 4){
            s += switchRegister(i)+"     "+"0x"+Integer.toHexString(registers.get(i))+"  |  ";
            s += switchRegister(i+1)+"     "+"0x"+Integer.toHexString(registers.get(i+1))+"  |   ";
            s += switchRegister(i+2)+"     "+"0x"+Integer.toHexString(registers.get(i+2))+"  |  ";
            s += switchRegister(i+3)+"     "+"0x"+Integer.toHexString(registers.get(i+3))+"\n";
        }
        return s;
    }

}