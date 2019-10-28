import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Aqui vai ser nossa main class.
public class Simulador{

    public static String decToBinario(String dec, int tam){
        String ret = "";
        int iDec = Integer.parseInt(dec);
        if(iDec > 0){
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

    public static String qRegistrador(String reg){
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
            default: System.out.println("Deu na busca dos registradores um erro!!!");break;
        }
        return "";
    }

    public static String decoder(String inst, String [] code){
        Boolean control = true;
        int i = 0;
        String ret = "";
        inst = inst.replace(",", " ").replace("  ", " ");
        String [] vetInst = inst.split(" ");

        while(control){
            switch(vetInst[i]){
                case "addu":
                    ret += "000000";
                    ret += qRegistrador(vetInst[i+2]);
                    ret += qRegistrador(vetInst[i+3]);
                    ret += qRegistrador(vetInst[i+1]);
                    ret += "00000";
                    ret += "100001";
                    control = false;
                    break;
                case "addiu":
                    ret += decToBinario("9", 6);
                    ret += qRegistrador(vetInst[i+2]);
                    ret += qRegistrador(vetInst[i+1]);
                    ret += decToBinario(vetInst[i+3], 16);
                    control = false;
                    break;
                
                default: System.out.println("Deu erro no switch do decoder");
                         control = false;
                         break;
            }
        }
            return ret;
    }
    public static int Mux(int ent0, int ent1, int op){
        if(op == 0){return ent0;}
        if(op == 1){return ent1;}
        System.out.println("Erro na operação de controle do Mux!!!");
        return -1;
    }

    public static String[] getData(String linhas){
        int comeco = 0;
        int fim = linhas.length();
        String [] data;
        Boolean aux = false;
        

        for(int i = 0;i<linhas.length()-6;i++){
            if(linhas.substring(i, i+6).equals(".data\n") && !aux){
                comeco = i+6;
                aux = true;
            }
            if(aux && linhas.substring(i, i+5).equals(".text")){
                fim = i-1;
                break;
            }
        }    

        data = linhas.substring(comeco, fim).split("\n");
        System.out.println(data[0]);
    
        return data;
    }

    public static String[] getCodigo(String linhas){
        int comeco = 0;
        int fim = linhas.length();
        String [] code;
        Boolean aux = false;

        for(int  i = 0; i<linhas.length()-6; i++){
            if(linhas.substring(i, i+6).equals("main:\n") && !aux){
                comeco = i+6;
                aux = true;
            }
            if(aux && linhas.substring(i, i+5).equals(".data")){
                fim = i-1;
                break;
            }

        }
        code = linhas.substring(comeco, fim).trim().split("\n");
        System.out.println(code[0]);

        return code;
    }

    public static void main(String [] args){
        ArrayList<String> linhasCode= new ArrayList<String>();
        String linhas = "";
        String[] code;
        String[] data;
        try{
            FileReader arq = new FileReader("teste.mips");
            BufferedReader lerArq = new BufferedReader(arq);
            String l = lerArq.readLine();
            while(l != null){
                linhas += "\n"+l;
                l = lerArq.readLine();
            }

            arq.close();
        }catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
        //System.out.println(linhas);

        code = getCodigo(linhas);
        data = getData(linhas);

        System.out.println(decoder(code[0], code));
        System.out.println(decToBinario("-10", 6));
    }
}