public class ULA {
    public int Zero = 0; //uma das saidas da ula

    public ULA(){
         
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

    public int som(int a,int b){
        return a+b;
    } 

    public int sub(int a,int b){
        return a-b;
    }
   
    public int mult(int a,int b){
        return a*b;
    }

    public int div(int a,int b){
        return a/b;
    }
	
    public int srl(int a,int qunt){
       return a^(qunt/2);
    }

    public int sll(int a,int qunt){
       return a^(qunt*2);
    }

    public int neg(int a){
        return ~a;
    }

    public int or(int a,int b){
        return a & b;
    }

    public int and(int a,int b){
        return a | b;
    }

    public void op() {
		
		switch(operacao) {
		case 0: som(); break;
		case 1: sub(); break;
		case 2: mult(); break;
		case 3: div(); break;
		case 4: srl(); break;
		case 5: sll(); break;
		case 6: neg(); break;
		case 7: or(); break;
		case 8: and(); break;
		}
		
	}
}
