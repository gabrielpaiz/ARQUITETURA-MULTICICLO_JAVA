package main;

import java.util.*;

public class Decoder{
    List<String> code;
    String [] instruction;

    public Decoder(List code){
        this.code = code;
        instruction = new String[code.size()/*-1*/];
    }

    public String decToBinario(String dec, int tam){
        String ret = "";
        int iDec = Integer.parseInt(dec);
        if(iDec >= 0){
            for(int i = 0;i<tam;i++){
                ret = String.valueOf(iDec%2) + ret;
                iDec = iDec/2;
            }
        }else{
            iDec += 1;
            for(int i = 0;i<tam;i++){
                if(iDec%2 == 0){
                    ret = "1" + ret;
                }else{
                    ret = "0" + ret;
                }
                iDec = iDec/2;
            }
        }
        return ret;
    }

    public String qRegistrador(String reg){
        switch(reg){
            case "$zero": return "00000";
            case "$at": return "00001";
            case "$v0": return "00010";
            case "$v1": return "00011";
            case "$a0": return "00100";
            case "$a1": return "00101";
            case "$a2": return "00110";
            case "$a3": return "00111";
            case "$t0": return "01000";
            case "$t1": return "01001";
            case "$t2": return "01010";
            case "$t3": return "01011";
            case "$t4": return "01100";
            case "$t5": return "01101";
            case "$t6": return "01110";
            case "$t7": return "01111";
            case "$s0": return "10000";
            case "$s1": return "10001";
            case "$s2": return "10010";
            case "$s3": return "10011";
            case "$s4": return "10100";
            case "$s5": return "10101";
            case "$s6": return "10110";
            case "$s7": return "10111";
            case "$t8": return "11000";
            case "$t9": return "11001";
            case "$k0": return "11010";
            case "$k1": return "11011";
            case "$gp": return "11100";
            case "$sp": return "11101";
            case "$fp": return "11110";
            case "$ra": return "11111";
            default: System.out.println("Deu na busca dos registradores um erro!!!"+ reg);break;
        }
        return "";
    }

    public String decoder_One_Line(int line){
        Boolean control = true;
        int i = 0;
        String ret = "";
        String inst = code.get(line).replace(",", " ").replace("  ", " ");
        String [] vetInst = inst.split(" ");
        String [] aux;

        while(control){
            switch(vetInst[i]){
                case "addu":
                    ret += "000000";
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+3]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += "00000";
                    ret += "100001";
                    control = false;
                    break;
                case "addiu":
                    ret += decToBinario("9", 6);
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(vetInst[i+3], 16);
                    control = false;
                    break;
                case "lw":
                    ret += "100011";
                    aux = vetInst[i+2].replace("(", "/").replace(")", "").split("/");
                    ret += qRegistrador(aux[1]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(aux[0], 16);
                    control = false;
                    break;
                case "sw":
                    ret += "101011";
                    aux = vetInst[i+2].replace("(", "/").replace(")", "").split("/");
                    ret += qRegistrador(aux[1]); // Coloca o registrador em binario 
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(aux[0], 16);
                    control = false;
                    break;
                case "and":
                    ret += "000000";
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+3]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += "00000"; // Shant
                    ret += decToBinario("36", 6); 
                    control = false;
                    break;
                case "sll":
                    ret += "000000";
                    ret += "00000";
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(vetInst[i+3], 5);
                    ret += "000000";
                    control = false;
                    break;
                case "srl":
                    ret += "000000";
                    ret += "00000";
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(vetInst[i+3], 5);
                    ret += "000010";
                    control = false;
                    break;
                case "ori":
                    ret += decToBinario("13", 6);
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(vetInst[i+3], 16);
                    control = false;
                    break;
                case "lui":
                    ret += decToBinario("15", 6);
                    ret += "00000";
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += decToBinario(vetInst[i+2], 16);
                    control = false;
                    break;
                case "beq":
                    ret += decToBinario("4", 6);
                    ret += qRegistrador(vetInst[i+1]); // Coloca o registrador em binario
                    ret += qRegistrador(vetInst[i+2]); // Coloca o registrador em binario
                    for(int j = 0;j<code.size()-1;j++){ // Percorro O programa inteiro até achar a label
                        if( (code.get(j).substring(0, (vetInst[i+3].length()))).equals(vetInst[i+3])) 
                        {
                            line = j - (line+1); //Diminua a linha q eu achei com a linha que estou decodificando
                            break;
                        }
                    }
                    ret += decToBinario(String.valueOf(line), 16);
                    control = false;
                    break;
                case "j":
                    ret += decToBinario("2", 6);
                    int j = 1280;//Nosso PC Começa em 0x500 ou 1280
                    for (String element : code) {
                         if (vetInst[i + 1].equals(element.substring(0, vetInst[i + 1].length()))) {
                            ret += decToBinario(String.valueOf(j), 26);
                            break;
                        }
                        j++;
                    }    
                    control = false;
                    break;
                    
                default: 
                    i++;
                    break;
                }
            }
            return ret;
        }

    public String[] decoder(){
        for(int i = 0; i<code.size()/*-1*/;i++){
            instruction[i] = decoder_One_Line(i);
        }

        return instruction;
    }

    public String[] decoderHex(){
        decoder();
        String[] hex = new String[instruction.length];
        for(int i=0;i<instruction.length;i++){
            hex[i] = "0x";
            for(int j = 0;j<instruction[i].length()-3;j+=4){
                int dec = Integer.parseInt(instruction[i].substring(j, j+4), 2);
                hex[i] += Integer.toString(dec,16);
            }
        }
        return hex;
    }
}


