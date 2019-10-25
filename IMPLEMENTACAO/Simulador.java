public class Simulador{
    //Aqui vai ser nossa main class.
    public static int Mux(int ent0, int ent1, int op){
        if(op == 0){return ent0;}
        if(op == 1){return ent1;}
        System.out.println("Erro na operação de controle do Mux!!!");
        return -1;
    }
    public static void main(String [] args){

    }
}