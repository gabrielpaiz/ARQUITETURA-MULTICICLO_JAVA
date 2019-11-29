package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulador{

    private static String ARQUIVO = "teste.mips";

    public static int Mux(int ent0, int ent1, int op){
        if(op == 0){return ent0;}
        if(op == 1){return ent1;}
        System.out.println("Erro na operação de controle do Mux!!!");
        return -1;
    }

    public static List<String> getCodigo(String linhas)// Cria o vetor de String aonde fica o codigo inteiro do programa
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

        return listCode;
    }

    public static void main(String [] args){
        List<String> code = new ArrayList<String>();

        String linhas = "";
        Registradores blreg = Registradores.getInstance();
        BlocoControle blcontrol = BlocoControle.getInstance();
        Memoria mem = Memoria.getInstance();
        ULA ulaPrincipal = new ULA();
        ULA fullAdder = new ULA();

        //Leitura do arquivo
        try{
            final String filePath =
                    System.getProperty("user.dir")+
                            File.separator+
                            "resources"+
                            File.separator+
                            ARQUIVO
                    ;
            FileReader arq = new FileReader(filePath);
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

        code = getCodigo(linhas);

        Decoder dec = new Decoder(code);

        String [] memoriaIntrucao = dec.decoder(); // Isso são todas as instruçoes do codigo em binario
        String [] instHex = dec.decoderHex(); // Isso é todas as instruçoes do codigo em hexa

        int pc = 1280;
        while(true){
        
            int pcReal = (pc-1280)/4;

            if(pcReal == code.size()){
                System.exit(0);
            }
            for(int i = 0;i<code.size();i++){
                System.out.print(instHex[i]+"   ");
                System.out.print("   "+code.get(i));
                if(i == pcReal){
                    System.out.print("  <--\n");
                }else {System.out.print("\n");}
            }
            int writeR = Integer.parseInt(memoriaIntrucao[pcReal].substring(16,21),2); // rd
            int regUm = Integer.parseInt(memoriaIntrucao[pcReal].substring(6,11),2); // rs
            int regDois = Integer.parseInt(memoriaIntrucao[pcReal].substring(11,16),2); // rt
            int immediate =  Integer.parseInt(memoriaIntrucao[pcReal].substring(16,32),2);
            int shiftNumber = Integer.parseInt(memoriaIntrucao[pcReal].substring(21,26),2);
            int jumpNumber = Integer.parseInt(memoriaIntrucao[pcReal].substring(6,32),2);

            //Parte do bloco de controle
            blcontrol.controlByOpCode(memoriaIntrucao[pcReal].substring(0,6));

            int saidaMux = BlocoControle.writeRegMux(regDois, writeR, blcontrol.regDst);

            //AluContro e MUX para a ULA
            int aluControl = blcontrol.ulaControl(blcontrol.aluOp0, blcontrol.aluOp1, blcontrol.lui, memoriaIntrucao[pcReal].substring(26,32));

            //Parte da busca no bloco de registradores e MUX para saber se é shift
            int reg2Toreg2 = Mux(regUm, regDois, blcontrol.reg2TOreg1);

            blreg.busca(reg2Toreg2, regDois, saidaMux, blcontrol.regWrite);
            saidaMux = BlocoControle.aluOp2Mux(blreg.saida2(), immediate, blcontrol.aluSrc);
            saidaMux = BlocoControle.shiftControlMux(saidaMux, shiftNumber , blcontrol.shamt);


            //pc++
            pc = fullAdder.saida(pc, 4, 2);

            //prepara numero pro jump
            jumpNumber = jumpNumber*4;

            int muxLUI = Mux(blreg.saida(), immediate, blcontrol.lui);
            //Calculo da ULA principal
            int saidaULA = ulaPrincipal.saida(muxLUI, saidaMux, aluControl);

            //prepara numero pro beq
            int pcBEQ = fullAdder.saida((immediate*4), pc, 2);
            int muxBranch = Mux(pc, pcBEQ, (blcontrol.branch & ulaPrincipal.Zero));

            //Arruma Pc se for branch ou jump
            pc = Mux(muxBranch, jumpNumber, blcontrol.jump);

            int saidaMemoria = mem.entradas(saidaULA, blreg.saida2(), blcontrol.memRead, blcontrol.memWrite);
            int memToReg = BlocoControle.memToRegMux(saidaULA, saidaMemoria, blcontrol.memToReg);

            blreg.writeBack(memToReg);
            System.out.println(blreg.toString()+"\n"+mem.toString());

            Scanner teclado = new Scanner(System.in);
            String para = teclado.next();
            for(int i = 0;i<7;i++){
                System.out.println("\r");
            }

        }
    }
}