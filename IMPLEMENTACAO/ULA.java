public class ULA implements Bloco{
    private int ent1;
    private int ent2;
    private int op;
    private int saida;
    private int zero;

    public ULA(int ent1, int ent2, int op){
        this.ent1 = ent1;
        this.ent2 = ent2;
        this.op = op;
        saida = 0;
        zero = 0; 
    }

    public int saida(){
        /* Vamos fazer todas as operações aqui dentro
        * Ultilizando as entradas e a op que recebemos
        * pode ser Switch Case
        * ex: Switch(op){case 0: saida = ent1 + ent2;} ou pode fazer de outro jeito
        * nós alteraremos a variavel zero aqui tbm, quando nescessario.
        */
        return saida;
    }

    public int zero(){return zero;}
}