import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulador{
    //Aqui vai ser nossa main class.
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
    }
}