import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Aqui vai ser nossa main class.
public class Simulador{

    public static int Mux(int ent0, int ent1, int op){
        if(op == 0){return ent0;}
        if(op == 1){return ent1;}
        System.out.println("Erro na operação de controle do Mux!!!");
        return -1;
    }

    public static List getData(String linhas)//Cria o vetor de String aonde fica os dados a serem gravados na memoria
    {
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
        
        List<String> listData = new ArrayList<String>(Arrays.asList(data));

    
        return listData;
    }

    public static List getCodigo(String linhas, List data)// Cria o vetor de String aonde fica o codigo inteiro do programa
    {
        int comeco = 0;
        int fim = linhas.length();
        Boolean aux = false;

        for(int  i = 0; i<linhas.length()-6; i++){
            if(linhas.substring(i, i+5).equals("main\n") && !aux){
                comeco = i+6;
                aux = true;
            }
            if(aux && linhas.substring(i, i+5).equals(".data")){
                fim = i-1;
                break;
            }

        }

        String [] code = linhas.substring(comeco, fim).trim().split("\n");
        String ajuste;
        List<String> listCode = new ArrayList<String>(Arrays.asList(code));

        // Para retirar os vetores em branco
        while(listCode.contains("")){
            listCode.remove("");
        } 
        
        

        // Esse for é colocar a label na mesma linha do codigo
        for(int i = 0; i<listCode.size();i++){
            if(listCode.get(i).charAt(listCode.get(i).length()-1) == ':'){
                ajuste = listCode.remove(i).trim().replace(":", "") + " " +listCode.remove(i).trim(); 
                listCode.add(i, ajuste);
            }
            listCode.add(i, listCode.remove(i).trim());
        }

        String lui = "lui "+"$at, "+ "0";
        String ori = "";
        int posicaoData = 0;

        for(int i = 0; i<listCode.size();i++){
            if(listCode.get(i).substring(0, 2).equals("la")){
                for(int j = 0;j<data.size();j++){
                    String [] splitJ = data.get(j).toString().split(" ");
                    if(listCode.get(i).contains(splitJ[0].replace(":",""))){
                            ori = "ori "+ listCode.get(i).substring(3,7) + " $at, " + posicaoData;
                    }
                    posicaoData += splitJ.length-2;
                }
                listCode.remove(i);
                listCode.add(i, lui);
                listCode.add(i+1, ori);
                posicaoData = 0;
            }
        }

        System.out.println(listCode);

        return listCode;
    }

    public static void main(String [] args){
        List<String> code = new ArrayList<String>();
        List<String> data = new ArrayList<String>();

        String linhas = "";


        //Leitura do arquivo
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

        data = getData(linhas);
        code = getCodigo(linhas, data);



        Decoder dec = new Decoder(code);

        String [] memoriaIntrucao = dec.decoder(); // Isso são todas as instruçoes do codigo em binario
        String [] instHex = dec.decoderHex(); // Isso é todas as instruçoes do codigo em hexa

        for(int i = 0; i<memoriaIntrucao.length ;i++){ 
            System.out.println(memoriaIntrucao[i]);
        }
        System.out.print("\n");
        for(int i = 0; i<instHex.length ;i++){
            System.out.println(instHex[i]);
        }
        // Não tirei os prints para vocês poderem ver como está
    }
}