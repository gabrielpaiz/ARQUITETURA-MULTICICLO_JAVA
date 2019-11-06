import java.util.*;
public class ULA {
    public int Zero = 0; //uma das saidas da ula,se for beq,PCSrc manda sinal p cá

    public ULA(){
         
    }

    public int som(int a,int b){
        Zero = 0;
        return a+b;
    } 

    public int sub(int a,int b){
        int c=a-b;
        if (c==0){
            Zero=1;
        }else{
            Zero=0;
        }   
        return c;
    }
   
    public int mult(int a,int b){
        Zero = 0;
        return a*b;
    }

    public int div(int a,int b){
        Zero = 0;
        return a/b;
    }
	
     public int srl(int a,int qunt){
       Zero = 0;
       return (int) (a/(Math.pow(2,qunt)));
    }

    public int sll(int a,int qunt){
        Zero = 0;
        return (int) (a*(Math.pow(2,qunt)));
    }

    public int or(int a,int b){
        Zero = 0;
        return a & b;
    }

    public int and(int a,int b){
        Zero = 0;
        return a | b;
    }


    public int saida(int a,int b, int operacao) {
		
		switch(operacao) {
		case 0: return and(a,b);
        	case 1: return or(a,b); 
		case 2: return som(a,b); 
		case 3: return mult(a,b);  
		case 4: return srl(a,b); 
		case 5: return sll(a,b); 
       	 	case 6: return sub(a,b); 
        	case 7: return div(a,b);
        	default: System.out.println("ERRO!!!!!"); return -1;
		}
		
	}
}
