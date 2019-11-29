package main;

import java.util.*;

public class Memoria{
    ArrayList <Integer> memoria = new ArrayList<Integer> (100);

    private static final Memoria INSTANCE = new Memoria();

    public static Memoria getInstance(){
        return INSTANCE;
    }

    private Memoria(){
        for(int i = 0;i<100;i++){
            memoria.add(0);
        }

    }

    public int entradas(int address,int Writedata,int memRead,int memWrite){

        if(memRead==1){
            return memoria.get(address/4);
        }

        if(memWrite==1){
            memoria.set(address/4,Writedata);
        }
        return 0;
    }

    public String toString(){
        String s = "Memoria:\n         ";
        for(int i = 0; i<5;i++){
            s+="(+0x"+Integer.toHexString((4*i))+")     ";
        }
        s+="\n";
        int count = 0;
        for(int i = 0;i<100;i++){
            count =i*4;
            if(i%5 == 4){
                s+= "0x"+Integer.toHexString(memoria.get(i))+"\n";
                continue;
            }
            if(i%5 == 0){
                String spaces = "0x"+Integer.toHexString(count);
                s+= spaces;
                for(int j = 0;j<11-spaces.length();j++){
                    s+=" ";
                }
            }
            String aux = "0x"+Integer.toHexString(memoria.get(i));
            s+= aux;
            for(int j = 0;j<11-aux.length();j++){
                s+=" ";
            }
        }
        return s;


    }

}

// nossa memoria anda de 1 em 1,endereco de 4 em 4

