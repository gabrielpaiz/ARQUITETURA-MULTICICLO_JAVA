package main;

//Essa classe salva a instrução em seus formatos possíveis, para utilização na execução.
public class Instrucao {

    String[] /*[32]*/ bits;
    String[] /*[6]*/ op;
    String[] /*[5]*/ rs;
    String[] /*[5]*/ rt;
    String[] /*[5]*/ rd;
    String[] /*[5]*/ shamt;
    String[] /*[6]*/funct;
    String[] /*[16]*/ immediate;
    String[] /*[26]*/ jAdress;

    public Instrucao (String[] Instrucao) {

        for (int i = 0; i < 32; i++) {

            bits[i] = Instrucao[i];

            if (i < 6) {

                op[i] = Instrucao[i];

            }

            if (i >= 6 && i < 11) {

                rs[i] = Instrucao[i];

            }

            if (i >= 11 && i < 16) {

                rt[i] = Instrucao[i];

            }

            if (i >= 16 && i < 21) {

                rd[i] = Instrucao[i];

            }

            if (i >= 21 && i < 26) {

                shamt[i] = Instrucao[i];

            }

            if (i >= 26) {

                funct[i] = Instrucao[i];

            }

            if (i >= 16) {

                immediate[i] = Instrucao[i];

            }

            if (i >= 6) {

                jAdress[i] = Instrucao[i];

            }

        }

    }

}